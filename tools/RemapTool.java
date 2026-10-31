import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

/**
 * Prepares the obfuscated J2ME jar for decompilation:
 *  - moves all classes into package com.ea.sims3
 *  - renames duplicated field names (legal in bytecode, illegal in Java source)
 *  - renames methods that only differ in their return type, which Java source
 *    cannot express
 */
public final class RemapTool {
    static final String TARGET_PKG = "com/ea/sims3/";

    public static void main(String[] args) throws Exception {
        File srcDir = new File(args[0]);
        File outJar = new File(args[1]);
        PrintStream report = new PrintStream(new FileOutputStream(args[2]), true, "UTF-8");

        List<File> classFiles = new ArrayList<>();
        collect(srcDir, classFiles);

        Map<String, String> classTargets = new LinkedHashMap<>();
        Map<String, Map<String, String>> fieldRenames = new LinkedHashMap<>();
        Map<String, Set<String>> methodDescsByName = new TreeMap<>();
        Map<String, Set<String>> methodsPerClass = new LinkedHashMap<>();
        Map<String, List<String>> fieldsByClassInOrder = new LinkedHashMap<>();
        Map<String, String> superNameOf = new LinkedHashMap<>();

        for (File f : classFiles) {
            byte[] bytes = Files.readAllBytes(f.toPath());
            ClassReader cr = new ClassReader(bytes);
            String internal = cr.getClassName();
            classTargets.put(internal, TARGET_PKG + internal.substring(internal.lastIndexOf('/') + 1));

            List<String> fields = new ArrayList<>();
            Set<String> methods = new LinkedHashSet<>();
            String[] superName = new String[1];
            cr.accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
                public void visit(int version, int access, String name, String signature,
                                  String superName, String[] interfaces) {
                    superNameOf.put(name, superName);
                }

                @Override
                public FieldVisitor visitField(int access, String name, String desc, String sig, Object val) {
                    fields.add(name + " " + desc);
                    return null;
                }

                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String sig, String[] ex) {
                    methods.add(name + " " + desc);
                    methodDescsByName.computeIfAbsent(name, k -> new TreeSet<>()).add(desc);
                    return null;
                }
            }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

            fieldsByClassInOrder.put(internal, fields);
            methodsPerClass.put(internal, methods);
        }

        Map<String, String> methodRenames = computeMethodRenames(methodDescsByName);

        // Field names must be unique across the inheritance chain as well, otherwise
        // a subclass field shadows an inherited one and the decompiled source becomes
        // ambiguous. Parents are processed first so inherited names are final.
        List<String> ordered = new ArrayList<>();
        Set<String> visiting = new LinkedHashSet<>();
        for (String c : fieldsByClassInOrder.keySet()) {
            orderByHierarchy(c, superNameOf, fieldsByClassInOrder, ordered, visiting);
        }
        for (String c : ordered) {
            Set<String> inherited = new LinkedHashSet<>();
            String parent = superNameOf.get(c);
            while (parent != null) {
                List<String> parentFields = fieldsByClassInOrder.get(parent);
                if (parentFields != null) {
                    for (String f : parentFields) {
                        inherited.add(fieldRenames.getOrDefault(parent, new LinkedHashMap<>())
                                .getOrDefault(f, f.substring(0, f.indexOf(' '))));
                    }
                }
                parent = superNameOf.get(parent);
            }
            fieldRenames.put(c, dedupeFieldNames(fieldsByClassInOrder.get(c), inherited));
        }

        final Map<String, Map<String, String>> fieldsByClass = fieldRenames;
        Remapper remapper = new Remapper() {
            @Override
            public String map(String internalName) {
                String t = classTargets.get(internalName);
                return t != null ? t : internalName;
            }

            @Override
            public String mapFieldName(String owner, String name, String descriptor) {
                Map<String, String> m = fieldsByClass.get(owner);
                if (m == null) {
                    m = fieldsByClass.get(owner.substring(owner.lastIndexOf('/') + 1));
                }
                if (m == null) {
                    m = fieldsByClass.get(TARGET_PKG + owner.substring(owner.lastIndexOf('/') + 1));
                }
                if (m != null) {
                    String renamed = m.get(name + " " + descriptor);
                    if (renamed != null) {
                        return renamed;
                    }
                }
                return name;
            }

            @Override
            public String mapMethodName(String owner, String name, String descriptor) {
                String renamed = methodRenames.get(name + " " + descriptor);
                return renamed != null ? renamed : name;
            }
        };

        Map<String, byte[]> out = new LinkedHashMap<>();
        for (File f : classFiles) {
            byte[] bytes = Files.readAllBytes(f.toPath());
            ClassReader cr = new ClassReader(bytes);
            ClassWriter cw = new ClassWriter(0);
            cr.accept(new ClassRemapper(cw, remapper), 0);
            out.put(classTargets.get(cr.getClassName()) + ".class", cw.toByteArray());
        }

        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(outJar))) {
            for (Map.Entry<String, byte[]> e : out.entrySet()) {
                jos.putNextEntry(new JarEntry(e.getKey()));
                jos.write(e.getValue());
                jos.closeEntry();
            }
        }

        for (Map.Entry<String, String> e : classTargets.entrySet()) {
            report.println("class " + e.getKey().replace('/', '.') + " -> " + e.getValue().replace('/', '.'));
        }
        for (Map.Entry<String, Map<String, String>> e : fieldRenames.entrySet()) {
            for (Map.Entry<String, String> r : e.getValue().entrySet()) {
                report.println("field " + e.getKey() + " " + r.getKey() + " -> " + r.getValue());
            }
        }
        for (Map.Entry<String, String> e : methodRenames.entrySet()) {
            report.println("method " + e.getKey() + " -> " + e.getValue());
        }
        report.close();

        verify(methodsPerClass, methodRenames, fieldRenames);
        System.out.println("classes: " + out.size()
                + ", renamed fields: " + countRenames(fieldRenames)
                + ", renamed methods: " + methodRenames.size());
    }

    private static int countRenames(Map<String, Map<String, String>> map) {
        int n = 0;
        for (Map<String, String> m : map.values()) {
            n += m.size();
        }
        return n;
    }

    /**
     * Renames the fields of one class so that all field names are unique inside
     * the class and do not collide with names inherited from superclasses.
     */
    private static Map<String, String> dedupeFieldNames(List<String> fields, Set<String> inherited) {
        Map<String, String> renames = new LinkedHashMap<>();
        Set<String> taken = new LinkedHashSet<>(inherited);
        for (String key : fields) {
            String name = key.substring(0, key.indexOf(' '));
            if (!taken.contains(name)) {
                taken.add(name);
                continue;
            }
            int n = 2;
            String candidate;
            do {
                candidate = name + n++;
            } while (taken.contains(candidate));
            taken.add(candidate);
            renames.put(key, candidate);
        }
        return renames;
    }

    private static void orderByHierarchy(String className, Map<String, String> superNameOf,
                                        Map<String, List<String>> fieldsByClass,
                                        List<String> ordered, Set<String> visiting) {
        if (ordered.contains(className) || !visiting.add(className)) {
            return;
        }
        String parent = superNameOf.get(className);
        if (parent != null && fieldsByClass.containsKey(parent)) {
            orderByHierarchy(parent, superNameOf, fieldsByClass, ordered, visiting);
        }
        ordered.add(className);
    }

    /**
     * Builds a global rename for methods that share a name and parameter list
     * but differ in return type. The mapping is a pure function of
     * (name, descriptor) so inherited/overriding methods stay connected.
     */
    private static Map<String, String> computeMethodRenames(Map<String, Set<String>> descsByName) {
        Map<String, String> renames = new LinkedHashMap<>();
        for (Map.Entry<String, Set<String>> e : descsByName.entrySet()) {
            String name = e.getKey();
            Map<String, List<String>> byParams = new TreeMap<>();
            for (String desc : e.getValue()) {
                String params = desc.substring(0, desc.indexOf(')') + 1);
                byParams.computeIfAbsent(params, k -> new ArrayList<>()).add(desc);
            }
            for (List<String> group : byParams.values()) {
                if (group.size() < 2) {
                    continue;
                }
                List<String> sorted = new ArrayList<>(group);
                java.util.Collections.sort(sorted);
                for (int i = 1; i < sorted.size(); i++) {
                    renames.put(name + " " + sorted.get(i), name + (i + 1));
                }
            }
        }
        return renames;
    }

    private static void verify(Map<String, Set<String>> methodsPerClass,
                               Map<String, String> methodRenames,
                               Map<String, Map<String, String>> fieldRenames) {
        for (Map.Entry<String, Set<String>> e : methodsPerClass.entrySet()) {
            Set<String> seen = new LinkedHashSet<>();
            for (String key : e.getValue()) {
                String[] parts = key.split(" ", 2);
                String newName = methodRenames.getOrDefault(key, parts[0]);
                String params = parts[1].substring(0, parts[1].indexOf(')') + 1);
                if (!seen.add(newName + params)) {
                    System.out.println("WARN duplicate method after rename in " + e.getKey()
                            + ": " + newName + params);
                }
            }
        }
    }

    private static void collect(File dir, List<File> out) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                collect(f, out);
            } else if (f.getName().endsWith(".class")) {
                out.add(f);
            }
        }
    }
}
