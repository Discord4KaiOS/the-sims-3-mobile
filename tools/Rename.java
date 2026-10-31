import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Applies a symbol-resolved rename to Java sources.
 *
 * Mapping file format (one entry per line, '#' starts a comment):
 *   C <class>                  <newName>
 *   F <class> <field>          <newName>
 *   M <class> <method>/<argc>  <newName>
 *
 * Every occurrence is resolved through javac, so a rename can never hit an
 * unrelated identifier that happens to share the spelling.
 */
public final class Rename {
    private static JavacTask task;
    private static Trees trees;
    private static SourcePositions positions;

    private static final Map<String, String> CLASSES = new HashMap<>();
    private static final Map<String, String> FIELDS = new HashMap<>();
    private static final Map<String, String> METHODS = new HashMap<>();
    private static final Map<String, String> VARIABLES = new HashMap<>();
    private static final java.util.Set<String> USED = new java.util.HashSet<>();

    public static void main(String[] args) throws Exception {
        loadMapping(args[0]);
        System.out.println("mapping: " + CLASSES.size() + " classes, " + FIELDS.size()
                + " fields, " + METHODS.size() + " methods, " + VARIABLES.size() + " variables");

        List<File> files = new ArrayList<>();
        collect(new File(args[1]), files);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);
        Iterable<? extends JavaFileObject> input = fm.getJavaFileObjects(files.toArray(new File[0]));
        task = (JavacTask) compiler.getTask(null, fm, null,
                List.of("-proc:none", "-encoding", "UTF-8", "-nowarn"), null, input);
        trees = Trees.instance(task);
        positions = trees.getSourcePositions();

        List<CompilationUnitTree> units = new ArrayList<>();
        task.parse().forEach(units::add);
        task.analyze();

        StringBuilder allText = new StringBuilder();
        int edits = 0;
        Map<String, String> fileRenames = new LinkedHashMap<>();
        for (CompilationUnitTree unit : units) {
            String path = Path.of(unit.getSourceFile().toUri()).toString();
            String text = unit.getSourceFile().getCharContent(true).toString();
            allText.append(text).append('\n');
            Map<Integer, String> fileEdits = new HashMap<>();

            new TreePathScanner<Void, Void>() {
                @Override
                public Void visitClass(ClassTree node, Void unused) {
                    addDeclaration(getCurrentPath(), unit, text, fileEdits, node, node.getSimpleName().toString());
                    Element element = trees.getElement(getCurrentPath());
                    if (element instanceof TypeElement
                            && getCurrentPath().getParentPath().getLeaf() instanceof CompilationUnitTree) {
                        String newName = CLASSES.get(node.getSimpleName().toString());
                        if (newName != null) {
                            fileRenames.put(path, newName + ".java");
                        }
                    }
                    return super.visitClass(node, unused);
                }

                @Override
                public Void visitMethod(MethodTree node, Void unused) {
                    String declared = node.getName().toString();
                    if ("<init>".equals(declared)) {
                        declared = enclosingClassName(getCurrentPath());
                    }
                    addDeclaration(getCurrentPath(), unit, text, fileEdits, node, declared);
                    return super.visitMethod(node, unused);
                }

                @Override
                public Void visitVariable(VariableTree node, Void unused) {
                    addDeclaration(getCurrentPath(), unit, text, fileEdits, node, node.getName().toString());
                    return super.visitVariable(node, unused);
                }

                @Override
                public Void visitIdentifier(IdentifierTree node, Void unused) {
                    String id = node.getName().toString();
                    if (id.equals("super") || id.equals("this")) {
                        return null;
                    }
                    String newName = renamed(trees.getElement(getCurrentPath()));
                    if (newName != null) {
                        long start = positions.getStartPosition(unit, node);
                        long end = positions.getEndPosition(unit, node);
                        if (start >= 0 && end >= 0
                                && text.substring((int) start, (int) end).equals(node.getName().toString())) {
                            fileEdits.put((int) start, newName);
                        }
                    }
                    return null;
                }

                @Override
                public Void visitMemberSelect(MemberSelectTree node, Void unused) {
                    String newName = renamed(trees.getElement(getCurrentPath()));
                    if (newName != null) {
                        long end = positions.getEndPosition(unit, node);
                        String name = node.getIdentifier().toString();
                        int start = (int) end - name.length();
                        if (start >= 0 && text.startsWith(name, start)) {
                            fileEdits.put(start, newName);
                        }
                    }
                    return super.visitMemberSelect(node, unused);
                }
            }.scan(unit, null);

            edits += applyEdits(path, text, fileEdits);
        }

        for (Map.Entry<String, String> e : fileRenames.entrySet()) {
            Path from = Path.of(e.getKey());
            Path to = from.resolveSibling(e.getValue());
            Files.move(from, to);
            System.out.println("renamed file " + from.getFileName() + " -> " + to.getFileName());
        }
        System.out.println("applied " + edits + " edits");
        String allSourceText = allText.toString();

        // Only report entries whose old name still exists in the sources: entries
        // from earlier batches are simply no-ops now.
        // Current class names: only entries whose owner still exists can be typos.
        java.util.Set<String> currentClasses = new java.util.HashSet<>();
        for (CompilationUnitTree u : units) {
            for (com.sun.source.tree.Tree t : u.getTypeDecls()) {
                if (t instanceof ClassTree) {
                    currentClasses.add(((ClassTree) t).getSimpleName().toString());
                }
            }
        }
        java.util.Map<String, java.util.Set<String>> declared = new HashMap<>();
        for (CompilationUnitTree u : units) {
            for (com.sun.source.tree.Tree t : u.getTypeDecls()) {
                if (!(t instanceof ClassTree)) {
                    continue;
                }
                ClassTree ct = (ClassTree) t;
                java.util.Set<String> names = declared.computeIfAbsent(
                        ct.getSimpleName().toString(), k -> new java.util.HashSet<>());
                for (com.sun.source.tree.Tree member : ct.getMembers()) {
                    if (member instanceof VariableTree) {
                        names.add(((VariableTree) member).getName().toString());
                    } else if (member instanceof MethodTree) {
                        MethodTree mt = (MethodTree) member;
                        StringBuilder sb = new StringBuilder(mt.getName().toString()).append('(');
                        for (int i = 0; i < mt.getParameters().size(); i++) {
                            if (i > 0) {
                                sb.append(',');
                            }
                            sb.append(simpleTypeText(mt.getParameters().get(i).getType()));
                        }
                        names.add(sb.append(')').toString());
                    }
                }
            }
        }
        java.util.Set<String> identifiers = currentClasses;
        java.util.List<String> unused = new ArrayList<>();
        CLASSES.forEach((k, v) -> {
            if (!USED.contains("C " + k) && identifiers.contains(k)) {
                unused.add("C " + k);
            }
        });
        FIELDS.forEach((k, v) -> {
            String owner = k.substring(0, k.indexOf('#'));
            String name = k.substring(k.indexOf('#') + 1);
            java.util.Set<String> names = declared.get(owner);
            if (!USED.contains("F " + k) && names != null && names.contains(name)) {
                unused.add("F " + k);
            }
        });
        METHODS.forEach((k, v) -> {
            String owner = k.substring(0, k.indexOf('#'));
            String sig = k.substring(k.indexOf('#') + 1);
            java.util.Set<String> names = declared.get(owner);
            if (!USED.contains("M " + k) && names != null && names.contains(sig)) {
                unused.add("M " + k);
            }
        });
        // Variable entries are derived from the current sources, so they are not
        // reported: after a batch is applied the old varN names are simply gone.
        if (!unused.isEmpty()) {
            System.out.println("UNUSED mapping entries (" + unused.size() + "):");
            unused.forEach(u -> System.out.println("  " + u));
        }
    }

    private static int applyEdits(String path, String text, Map<Integer, String> edits) throws Exception {
        if (edits.isEmpty()) {
            return 0;
        }
        List<Map.Entry<Integer, String>> list = new ArrayList<>(edits.entrySet());
        list.sort(Comparator.comparing(Map.Entry<Integer, String>::getKey).reversed());
        StringBuilder sb = new StringBuilder(text);
        for (Map.Entry<Integer, String> edit : list) {
            int start = edit.getKey();
            int end = start;
            while (end < sb.length() && Character.isJavaIdentifierPart(sb.charAt(end))) {
                end++;
            }
            sb.replace(start, end, edit.getValue());
        }
        Files.writeString(Path.of(path), sb.toString(), StandardCharsets.UTF_8);
        return list.size();
    }

    /** Records the rename of a declaration's name token. */
    private static void addDeclaration(TreePath path, CompilationUnitTree unit, String text,
                                       Map<Integer, String> edits,
                                       com.sun.source.tree.Tree decl, String name) {
        Element element = trees.getElement(path);
        String newName = renamed(element);
        if (newName == null) {
            return;
        }
        long start = positions.getStartPosition(unit, decl);
        long end = positions.getEndPosition(unit, decl);
        if (start < 0 || end < 0) {
            return;
        }
        int idx = findDeclarationName(text, (int) start, (int) end, name);
        if (idx >= 0) {
            edits.put(idx, newName);
        } else {
            System.out.println("WARN could not locate declaration name '" + name + "' near " + start);
        }
    }

    /** Finds the name token of a declaration: a whole word not followed by an identifier. */
    private static int findDeclarationName(String text, int start, int end, String name) {
        int from = start;
        while (from < end) {
            int idx = text.indexOf(name, from);
            if (idx < 0 || idx >= end) {
                return -1;
            }
            boolean leftOk = idx == 0 || !Character.isJavaIdentifierPart(text.charAt(idx - 1));
            int after = idx + name.length();
            boolean rightOk = after >= text.length() || !Character.isJavaIdentifierPart(text.charAt(after));
            if (leftOk && rightOk) {
                int k = after;
                while (k < text.length() && Character.isWhitespace(text.charAt(k))) {
                    k++;
                }
                char next = k < text.length() ? text.charAt(k) : ';';
                // a declaration name is followed by a type/body delimiter or a
                // keyword that can follow a class name
                if (next == '(' || next == '=' || next == ';' || next == ',' || next == ')'
                        || next == '[' || next == '{') {
                    return idx;
                }
                if (Character.isJavaIdentifierStart(next)) {
                    int wordEnd = k;
                    while (wordEnd < text.length() && Character.isJavaIdentifierPart(text.charAt(wordEnd))) {
                        wordEnd++;
                    }
                    String word = text.substring(k, wordEnd);
                    if (word.equals("extends") || word.equals("implements") || word.equals("throws")) {
                        return idx;
                    }
                }
            }
            from = idx + 1;
        }
        return -1;
    }

    /** Source name of the class that declares the given path. */
    private static String enclosingClassName(TreePath path) {
        TreePath p = path;
        while (p != null) {
            if (p.getLeaf() instanceof ClassTree) {
                return ((ClassTree) p.getLeaf()).getSimpleName().toString();
            }
            p = p.getParentPath();
        }
        return "";
    }

    private static String renamed(Element element) {
        if (element == null) {
            return null;
        }
        String owner = element.getEnclosingElement() == null ? ""
                : element.getEnclosingElement().getSimpleName().toString();
        switch (element.getKind()) {
            case CLASS:
            case INTERFACE:
                return CLASSES.get(element.getSimpleName().toString());
            case CONSTRUCTOR: {
                String name = CLASSES.get(owner);
                if (name != null) {
                    USED.add("C " + owner);
                }
                return name;
            }
            case FIELD: {
                String key = owner + "#" + element.getSimpleName();
                String name = FIELDS.get(key);
                if (name != null) {
                    USED.add("F " + key);
                }
                return name;
            }
            case METHOD: {
                ExecutableElement method = (ExecutableElement) element;
                String sig = signature(method);
                String key = owner + "#" + sig;
                String name = METHODS.get(key);
                if (name != null) {
                    USED.add("M " + key);
                    return name;
                }
                // an override takes the name of the method it overrides
                if (element.getEnclosingElement() instanceof TypeElement) {
                    String inherited = lookupInSupertypes((TypeElement) element.getEnclosingElement(),
                            method.getSimpleName().toString(), sig);
                    if (inherited != null) {
                        return inherited;
                    }
                }
                return null;
            }
            case PARAMETER:
            case LOCAL_VARIABLE:
            case EXCEPTION_PARAMETER: {
                Element parent = element.getEnclosingElement();
                if (parent instanceof ExecutableElement) {
                    String key = parent.getEnclosingElement().getSimpleName() + "#"
                            + signature((ExecutableElement) parent) + "#" + element.getSimpleName();
                    String name = VARIABLES.get(key);
                    if (name != null) {
                        USED.add("V " + key);
                    }
                    return name;
                }
                return null;
            }
            default:
                return null;
        }
    }

    /** "name(type,type)" with simple type names, used as the mapping key. */
    private static String signature(ExecutableElement method) {
        StringBuilder sb = new StringBuilder(method.getSimpleName().toString()).append('(');
        List<? extends VariableElement> params = method.getParameters();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(simpleType(params.get(i).asType()));
        }
        return sb.append(')').toString();
    }

    /** Simple type name from a source type tree (for building mapping keys). */
    private static String simpleTypeText(com.sun.source.tree.Tree node) {
        if (node == null) {
            return "?";
        }
        String text = node.toString();
        int lt = text.indexOf('<');
        if (lt >= 0) {
            text = text.substring(0, lt);
        }
        int dot = text.lastIndexOf('.');
        if (dot >= 0) {
            text = text.substring(dot + 1);
        }
        return text;
    }

    private static String simpleType(javax.lang.model.type.TypeMirror type) {
        if (type.getKind() == javax.lang.model.type.TypeKind.ARRAY) {
            return simpleType(((javax.lang.model.type.ArrayType) type).getComponentType()) + "[]";
        }
        if (type.getKind() == javax.lang.model.type.TypeKind.DECLARED) {
            return ((javax.lang.model.type.DeclaredType) type).asElement().getSimpleName().toString();
        }
        return type.toString();
    }

    /** Finds a rename declared on a supertype for a method with the same signature. */
    private static String lookupInSupertypes(TypeElement type, String name, String sig) {
        List<TypeElement> supertypes = new ArrayList<>();
        collectSupertypes(type, supertypes);
        for (TypeElement s : supertypes) {
            String found = METHODS.get(s.getSimpleName() + "#" + sig);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private static void collectSupertypes(TypeElement type, List<TypeElement> out) {
        TypeElement superclass = superclassOf(type);
        if (superclass != null && !out.contains(superclass)) {
            out.add(superclass);
            collectSupertypes(superclass, out);
        }
        for (javax.lang.model.type.TypeMirror i : type.getInterfaces()) {
            if (i.getKind() == javax.lang.model.type.TypeKind.DECLARED) {
                Element e = ((javax.lang.model.type.DeclaredType) i).asElement();
                if (e instanceof TypeElement && !out.contains(e)) {
                    out.add((TypeElement) e);
                    collectSupertypes((TypeElement) e, out);
                }
            }
        }
    }

    private static TypeElement superclassOf(TypeElement type) {
        javax.lang.model.type.TypeMirror sup = type.getSuperclass();
        if (sup.getKind() == javax.lang.model.type.TypeKind.DECLARED) {
            Element e = ((javax.lang.model.type.DeclaredType) sup).asElement();
            if (e instanceof TypeElement) {
                return (TypeElement) e;
            }
        }
        return null;
    }

    private static void loadMapping(String file) throws Exception {
        for (String line : Files.readAllLines(Path.of(file), StandardCharsets.UTF_8)) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            String[] p = line.split("\\s+");
            switch (p[0]) {
                case "C" -> CLASSES.put(p[1], p[2]);
                case "F" -> FIELDS.put(p[1] + "#" + p[2], p[3]);
                case "M" -> METHODS.put(p[1] + "#" + p[2], p[3]);
                case "V" -> VARIABLES.put(p[1] + "#" + p[2] + "#" + p[3], p[4]);
                default -> throw new IllegalArgumentException("bad mapping line: " + line);
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
            } else if (f.getName().endsWith(".java")) {
                out.add(f);
            }
        }
    }
}
