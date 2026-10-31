import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Reports declarations that still carry obfuscated (decompiler generated) names.
 *
 * Usage:
 *   ObfuscationReport summary <srcDir>
 *   ObfuscationReport class <srcDir> <ClassName>
 */
public final class ObfuscationReport {
    private static Trees trees;

    public static void main(String[] args) throws Exception {
        String mode = args[0];
        File root = new File(args[1]);
        String only = args.length > 2 ? args[2] : null;

        List<File> files = new ArrayList<>();
        collect(root, files);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);
        Iterable<? extends JavaFileObject> input = fm.getJavaFileObjects(files.toArray(new File[0]));
        JavacTask task = (JavacTask) compiler.getTask(null, fm, null,
                List.of("-proc:none", "-encoding", "UTF-8", "-nowarn"), null, input);
        trees = Trees.instance(task);
        List<CompilationUnitTree> units = new ArrayList<>();
        task.parse().forEach(units::add);
        task.analyze();

        if (mode.equals("summary")) {
            summary(units);
        } else if (mode.equals("list")) {
            listNames(units, only);
        } else if (mode.equals("shadows")) {
            shadows(units);
        } else {
            dump(units, only);
        }
    }

    /** Conventional geometry/colour shorthand that is readable as-is. */
    private static final Set<String> ALLOWED = new TreeSet<>(List.of(
            "x", "y", "dx", "dy", "sx", "sy", "tx", "ty", "ax", "ay", "az", "tz", "sz", "id",
            "x1", "y1", "x2", "y2", "x3", "y3"));

    private static boolean obfuscated(String name) {
        if (name.startsWith("var") && name.length() > 3 && name.substring(3).chars().allMatch(Character::isDigit)) {
            return true;
        }
        if (name.startsWith("arg") && name.length() > 3 && name.substring(3).chars().allMatch(Character::isDigit)) {
            return true;
        }
        if (ALLOWED.contains(name)) {
            return false;
        }
        if (name.length() >= 2 && name.equals(name.toUpperCase(Locale.ROOT))) {
            return false;
        }
        if (name.length() == 1) {
            return Character.isLetter(name.charAt(0));
        }
        if (name.length() == 2) {
            return (Character.isLetter(name.charAt(0)) || name.charAt(0) == '_')
                    && (Character.isLetterOrDigit(name.charAt(1)));
        }
        return false;
    }

    /** Reports private fields hidden by a field of the same name in a superclass. */
    private static void shadows(List<CompilationUnitTree> units) {
        int found = 0;
        for (CompilationUnitTree unit : units) {
            for (Tree t : unit.getTypeDecls()) {
                if (!(t instanceof ClassTree)) {
                    continue;
                }
                ClassTree ct = (ClassTree) t;
                TypeElement te = (TypeElement) trees.getElement(new TreePath(new TreePath(unit), ct));
                Set<String> own = new TreeSet<>();
                for (Tree member : ct.getMembers()) {
                    if (member instanceof VariableTree) {
                        own.add(((VariableTree) member).getName().toString());
                    }
                }
                TypeElement sup = superclassOf(te);
                while (sup != null) {
                    for (Element e : sup.getEnclosedElements()) {
                        if (e.getKind() == ElementKind.FIELD && own.contains(e.getSimpleName().toString())) {
                            System.out.println(ct.getSimpleName() + " hides " + sup.getSimpleName()
                                    + "." + e.getSimpleName());
                            found++;
                        }
                    }
                    sup = superclassOf(sup);
                }
            }
        }
        System.out.println("hidden fields: " + found);
    }

    private static TypeElement superclassOf(TypeElement type) {
        javax.lang.model.type.TypeMirror sup = type.getSuperclass();
        if (sup == null || sup.getKind() != javax.lang.model.type.TypeKind.DECLARED) {
            return null;
        }
        Element e = ((javax.lang.model.type.DeclaredType) sup).asElement();
        return e instanceof TypeElement && !((TypeElement) e).getQualifiedName().contentEquals("java.lang.Object")
                ? (TypeElement) e : null;
    }

    /** One line per remaining obfuscated declaration, optionally filtered by class. */
    private static void listNames(List<CompilationUnitTree> units, String only) {
        for (CompilationUnitTree unit : units) {
            for (Tree t : unit.getTypeDecls()) {
                if (!(t instanceof ClassTree)) {
                    continue;
                }
                String cls = ((ClassTree) t).getSimpleName().toString();
                if (only != null && !only.equals(cls)) {
                    continue;
                }
                new TreePathScanner<Void, Void>() {
                    @Override
                    public Void visitMethod(MethodTree node, Void unused) {
                        Element e = trees.getElement(getCurrentPath());
                        if (e instanceof ExecutableElement && e.getKind() == ElementKind.METHOD
                                && obfuscated(e.getSimpleName().toString())) {
                            StringBuilder sig = new StringBuilder(e.getSimpleName() + "(");
                            List<? extends VariableElement> params = ((ExecutableElement) e).getParameters();
                            for (int i = 0; i < params.size(); i++) {
                                if (i > 0) {
                                    sig.append(',');
                                }
                                sig.append(params.get(i).asType());
                            }
                            System.out.println(cls + " M " + sig + ")");
                        }
                        return super.visitMethod(node, unused);
                    }

                    @Override
                    public Void visitVariable(VariableTree node, Void unused) {
                        Element e = trees.getElement(getCurrentPath());
                        if (e instanceof VariableElement && obfuscated(e.getSimpleName().toString())) {
                            System.out.println(cls + " " + e.getKind() + " " + e.getSimpleName());
                        }
                        return super.visitVariable(node, unused);
                    }
                }.scan(unit, null);
            }
        }
    }

    private static void summary(List<CompilationUnitTree> units) {
        TreeMap<String, int[]> stats = new TreeMap<>();
        for (CompilationUnitTree unit : units) {
            for (Tree t : unit.getTypeDecls()) {
                if (!(t instanceof ClassTree)) {
                    continue;
                }
                String cls = ((ClassTree) t).getSimpleName().toString();
                int[] s = stats.computeIfAbsent(cls, k -> new int[4]);
                new TreePathScanner<Void, Void>() {
                    @Override
                    public Void visitClass(ClassTree node, Void unused) {
                        Element e = trees.getElement(getCurrentPath());
                        if (e instanceof TypeElement && obfuscated(node.getSimpleName().toString())) {
                            s[3]++;
                        }
                        return super.visitClass(node, unused);
                    }

                    @Override
                    public Void visitMethod(MethodTree node, Void unused) {
                        Element e = trees.getElement(getCurrentPath());
                        boolean ob = e instanceof ExecutableElement
                                && e.getKind() == ElementKind.METHOD
                                && obfuscated(e.getSimpleName().toString());
                        if (ob) {
                            s[0]++;
                        }
                        return super.visitMethod(node, unused);
                    }

                    @Override
                    public Void visitVariable(VariableTree node, Void unused) {
                        Element e = trees.getElement(getCurrentPath());
                        if (e instanceof VariableElement && obfuscated(e.getSimpleName().toString())) {
                            if (e.getKind() == ElementKind.FIELD || e.getKind() == ElementKind.ENUM_CONSTANT) {
                                s[1]++;
                            } else {
                                s[2]++;
                            }
                        }
                        return super.visitVariable(node, unused);
                    }
                }.scan(unit, null);
            }
        }
        int[] total = new int[4];
        System.out.printf(Locale.ROOT, "%-18s %7s %7s %8s %7s%n", "class", "methods", "fields", "params", "nested");
        stats.forEach((cls, s) -> {
            if (s[0] + s[1] + s[2] + s[3] == 0) {
                return;
            }
            System.out.printf(Locale.ROOT, "%-18s %7d %7d %8d %7d%n", cls, s[0], s[1], s[2], s[3]);
            for (int i = 0; i < 4; i++) {
                total[i] += s[i];
            }
        });
        System.out.printf(Locale.ROOT, "%-18s %7d %7d %8d %7d%n", "TOTAL", total[0], total[1], total[2], total[3]);
    }

    /** Lists every obfuscated declaration of one class with the named symbols it touches. */
    private static void dump(List<CompilationUnitTree> units, String className) {
        for (CompilationUnitTree unit : units) {
            for (Tree t : unit.getTypeDecls()) {
                if (!(t instanceof ClassTree) || !((ClassTree) t).getSimpleName().contentEquals(className)) {
                    continue;
                }
                System.out.println("== " + className + " ==");
                // Field usage: which methods touch each field.
                TreeMap<String, Set<String>> fieldUsers = new TreeMap<>();
                TreeMap<String, String> fieldInit = new TreeMap<>();
                TreeMap<String, String> fieldType = new TreeMap<>();
                for (Tree member : ((ClassTree) t).getMembers()) {
                    if (member instanceof VariableTree) {
                        VariableTree vt = (VariableTree) member;
                        fieldUsers.putIfAbsent(vt.getName().toString(), new TreeSet<>());
                        fieldInit.put(vt.getName().toString(), oneLine(vt.getInitializer()));
                        fieldType.put(vt.getName().toString(), vt.getType().toString());
                        continue;
                    }
                    if (!(member instanceof MethodTree) || ((MethodTree) member).getBody() == null) {
                        continue;
                    }
                    String mname = ((MethodTree) member).getName().toString();
                    new TreePathScanner<Void, Void>() {
                        @Override
                        public Void visitIdentifier(IdentifierTree node, Void unused) {
                            String id = node.getName().toString();
                            if (fieldUsers.containsKey(id)) {
                                fieldUsers.get(id).add(mname);
                            }
                            return null;
                        }

                        @Override
                        public Void visitMemberSelect(MemberSelectTree node, Void unused) {
                            String id = node.getIdentifier().toString();
                            if (fieldUsers.containsKey(id)) {
                                fieldUsers.get(id).add(mname);
                            }
                            return null;
                        }
                    }.scan(new TreePath(new TreePath(unit), member), null);
                }
                for (String f : fieldUsers.keySet()) {
                    if (!obfuscated(f)) {
                        continue;
                    }
                    System.out.println("  FIELD " + f + " : " + fieldType.get(f)
                            + (fieldInit.get(f).isEmpty() ? "" : " = " + fieldInit.get(f))
                            + "   used in " + fieldUsers.get(f));
                }
                System.out.println();
                for (Tree member : ((ClassTree) t).getMembers()) {
                    if (!(member instanceof MethodTree)) {
                        continue;
                    }
                    TreePath path = new TreePath(new TreePath(unit), member);
                    Element e = trees.getElement(path);
                    if (e == null) {
                        continue;
                    }
                    String name = e.getSimpleName().toString();
                    boolean ob = e.getKind() == ElementKind.METHOD && obfuscated(name);
                    if (member instanceof VariableTree) {
                        continue;
                    }
                    MethodTree mt = (MethodTree) member;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mt.getParameters().size(); i++) {
                        if (i > 0) {
                            sb.append(", ");
                        }
                        VariableTree p = mt.getParameters().get(i);
                        sb.append(p.getName()).append(": ").append(p.getType());
                    }
                    String mods = mt.getModifiers().getFlags().toString().replaceAll("[\\[\\]]", "");
                    if (!ob) {
                        System.out.println("  [m] " + mt.getName() + "(" + sb + ")");
                        continue;
                    }
                    System.out.println("\n  METHOD " + name + "(" + sb + ") [" + mods + "]");
                    if (mt.getBody() == null) {
                        continue;
                    }
                    Set<String> ids = new TreeSet<>();
                    Set<String> calls = new TreeSet<>();
                    Set<String> strings = new TreeSet<>();
                    TreePath bodyPath = new TreePath(path, mt.getBody());
                    new TreePathScanner<Void, Void>() {
                        @Override
                        public Void visitIdentifier(IdentifierTree node, Void unused) {
                            addSymbol(ids, calls, node.getName().toString());
                            return null;
                        }

                        @Override
                        public Void visitMemberSelect(MemberSelectTree node, Void unused) {
                            addSymbol(ids, calls, node.getIdentifier().toString());
                            return null;
                        }

                        @Override
                        public Void visitMethodInvocation(com.sun.source.tree.MethodInvocationTree node, Void unused) {
                            addSymbol(calls, calls, node.getMethodSelect().toString());
                            return super.visitMethodInvocation(node, unused);
                        }

                        @Override
                        public Void visitLiteral(LiteralTree node, Void unused) {
                            if (node.getValue() instanceof String) {
                                String v = (String) node.getValue();
                                if (!v.isBlank() && v.length() < 70) {
                                    strings.add(v.replace('\n', ' '));
                                }
                            }
                            return null;
                        }
                    }.scan(bodyPath, null);
                    if (!strings.isEmpty()) {
                        System.out.println("    strings: " + strings);
                    }
                    if (!calls.isEmpty()) {
                        System.out.println("    calls:   " + calls);
                    }
                    if (!ids.isEmpty()) {
                        System.out.println("    idents:  " + ids);
                    }
                }
            }
        }
    }

    private static void addSymbol(Set<String> ids, Set<String> calls, String name) {
        if (name.length() < 3 || obfuscated(name)) {
            return;
        }
        ids.add(name);
    }

    private static String oneLine(Tree tree) {
        if (tree == null) {
            return "";
        }
        String s = tree.toString().replace('\n', ' ');
        return s.length() > 80 ? s.substring(0, 80) + "..." : s;
    }

    private static void collect(File file, List<File> out) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children == null) {
                return;
            }
            for (File child : children) {
                collect(child, out);
            }
        } else if (file.getName().endsWith(".java")) {
            out.add(file);
        }
    }
}
