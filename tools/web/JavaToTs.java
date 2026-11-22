import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
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
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Translates the decompiled game sources to TypeScript.
 *
 * The output is a mechanical but faithful rendering of the Java semantics rather
 * than idiomatic TypeScript: the goal is that every expression, cast and integer
 * operation keeps the behaviour of the Java code. A handful of constructs (64 bit
 * flag storage, the fixed point helpers) are adjusted by hand afterwards.
 *
 * Usage: java JavaToTs &lt;source dir&gt; &lt;output dir&gt;
 */
public final class JavaToTs {

    private static final Map<Tree, TreePath> PATHS = new HashMap<>();
    private static JavacTask task;
    private static Trees trees;
    private static final Set<String> GAME_CLASSES = new LinkedHashSet<>();
    /** Classes whose TypeScript is maintained by hand instead of generated. */
    private static final Set<String> HAND_WRITTEN = Set.of("ScreenFade");
    /** "Owner#name(paramTypes)" -> TypeScript method name (overloads get a suffix). */
    private static final Map<String, String> METHOD_NAMES = new HashMap<>();
    /** "Owner#field" -> TypeScript field name (renamed when a method shares the name). */
    private static final Map<String, String> FIELD_NAMES = new HashMap<>();

    private static final Set<String> RUNTIME_NAMES = new LinkedHashSet<>(List.of(
            "Graphics", "Image", "Font", "Canvas", "Display", "Displayable", "Alert", "AlertType", "Screen",
            "MIDlet", "MIDletStateChangeException",
            "RecordStore", "RecordStoreException", "RecordStoreNotFoundException", "RecordStoreNotOpenException",
            "InvalidRecordIDException", "RecordStoreFullException", "RecordListener",
            "Player", "PlayerListener", "Manager", "VolumeControl", "Control", "MediaException",
            "DataInputStream", "DataOutputStream", "ByteArrayInputStream", "ByteArrayOutputStream",
            "IOException", "InputStream",
            "Random", "Timer", "TimerTask", "StringBuffer", "Stack", "Thread",
            "IllegalArgumentException", "IllegalStateException", "SecurityException", "InterruptedException",
            "RuntimeException", "NullPointerException", "UnsupportedOperationException", "IndexOutOfBoundsException",
            "arraycopy", "chr", "gc", "exitApp", "getProperty", "charsToBytes", "bytesToString", "loadResource", "compareStrings"));

    public static void main(String[] args) throws Exception {
        Path sourceDir = Paths.get(args[0]);
        Path outputDir = Paths.get(args[1]);
        String wantedPackage = args.length > 2 ? args[2] : "com.ea.sims3";
        List<File> files = new ArrayList<>();
        try (var stream = Files.walk(sourceDir)) {
            stream.filter(path -> path.toString().endsWith(".java"))
                    .sorted()
                    .forEach(path -> files.add(path.toFile()));
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);
        Iterable<? extends JavaFileObject> input = fileManager.getJavaFileObjects(files.toArray(new File[0]));
        task = (JavacTask) compiler.getTask(null, fileManager, null,
                List.of("-proc:none", "-encoding", "UTF-8", "-nowarn",
                        "-sourcepath", sourceDir.toString(), "-classpath", sourceDir.toString()),
                null, input);

        List<CompilationUnitTree> units = new ArrayList<>();
        task.parse().forEach(units::add);
        task.analyze();
        trees = Trees.instance(task);

        for (CompilationUnitTree unit : units) {
            new TreePathScanner<Void, Void>() {
                @Override
                public Void scan(Tree tree, Void unused) {
                    TreePath parent = getCurrentPath();
                    if (tree != null && parent != null) {
                        PATHS.put(tree, new TreePath(parent, tree));
                    }
                    return super.scan(tree, unused);
                }
            }.scan(new TreePath(unit), null);
        }

        for (CompilationUnitTree unit : units) {
            if (!wantedPackage.equals(unit.getPackageName().toString())) {
                continue;
            }
            for (Tree declaration : unit.getTypeDecls()) {
                if (declaration instanceof ClassTree classTree) {
                    GAME_CLASSES.add(classTree.getSimpleName().toString());
                }
            }
        }

        indexNames(units);

        Files.createDirectories(outputDir);
        for (CompilationUnitTree unit : units) {
            if (!wantedPackage.equals(unit.getPackageName().toString())) {
                continue;
            }
            for (Tree declaration : unit.getTypeDecls()) {
                if (declaration instanceof ClassTree classTree) {
                    String name = classTree.getSimpleName().toString();
                    if (HAND_WRITTEN.contains(name)) {
                        System.out.println("skipped " + name + " (hand written)");
                        continue;
                    }
                    String code = new Generator(unit, name).generate(classTree);
                    Path target = outputDir.resolve(name + ".ts");
                    Files.writeString(target, code, StandardCharsets.UTF_8);
                    System.out.println("wrote " + target);
                }
            }
        }

        if (!AUDIT.isEmpty()) {
            System.out.println();
            System.out.println("=== numeric audit ===");
            AUDIT.forEach((key, count) -> System.out.printf("%-56s %d%n", key, count));
        }
    }

    /**
     * TypeScript has no method overloading, so Java overloads are renamed here
     * once and every reference resolves through the same table.
     *
     * Classes are processed base first so an override always keeps the name its
     * superclass method was given; unrelated classes that happen to share a
     * method name are not affected.
     */
    private static void indexNames(List<CompilationUnitTree> units) {
        List<ClassTree> classes = new ArrayList<>();
        Map<String, ClassTree> byName = new HashMap<>();
        for (CompilationUnitTree unit : units) {
            for (Tree declaration : unit.getTypeDecls()) {
                if (declaration instanceof ClassTree classTree
                        && GAME_CLASSES.contains(classTree.getSimpleName().toString())) {
                    classes.add(classTree);
                    byName.put(classTree.getSimpleName().toString(), classTree);
                }
            }
        }
        classes.sort((left, right) -> Integer.compare(depth(left, byName), depth(right, byName)));

        for (ClassTree classTree : classes) {
            Map<String, List<ExecutableElement>> byMemberName = new HashMap<>();
            for (Tree member : classTree.getMembers()) {
                if (member instanceof MethodTree methodTree && !methodTree.getName().contentEquals("<init>")
                        && elementOf(methodTree) instanceof ExecutableElement method) {
                    byMemberName.computeIfAbsent(methodTree.getName().toString(),
                            key -> new ArrayList<>()).add(method);
                }
            }
            for (Map.Entry<String, List<ExecutableElement>> entry : byMemberName.entrySet()) {
                List<ExecutableElement> methods = entry.getValue();
                methods.sort((left, right) -> signature(left).compareTo(signature(right)));
                Set<String> taken = new LinkedHashSet<>(ancestorMethodNames(methods.get(0)));
                List<ExecutableElement> remaining = new ArrayList<>();
                for (ExecutableElement method : methods) {
                    String inherited = inheritedName(method);
                    if (inherited != null) {
                        METHOD_NAMES.put(className(method) + "#" + signature(method), inherited);
                        taken.add(inherited);
                    } else {
                        remaining.add(method);
                    }
                }
                int suffix = 1;
                for (ExecutableElement method : remaining) {
                    String candidate = entry.getKey();
                    while (taken.contains(candidate)) {
                        suffix++;
                        candidate = entry.getKey() + "_" + suffix;
                    }
                    taken.add(candidate);
                    if (!candidate.equals(entry.getKey())) {
                        METHOD_NAMES.put(className(method) + "#" + signature(method), candidate);
                    }
                }
            }
        }

        for (ClassTree classTree : classes) {
            Set<String> methodNames = new LinkedHashSet<>();
            for (Tree member : classTree.getMembers()) {
                if (member instanceof MethodTree methodTree && !methodTree.getName().contentEquals("<init>")) {
                    methodNames.add(methodTree.getName().toString());
                }
            }
            String ownerName = classTree.getSimpleName().toString();
            for (Tree member : classTree.getMembers()) {
                if (member instanceof VariableTree variable && methodNames.contains(variable.getName().toString())) {
                    FIELD_NAMES.put(ownerName + "#" + variable.getName(), variable.getName() + "Value");
                }
            }
        }
        System.out.println("renamed " + METHOD_NAMES.size() + " overloads, " + FIELD_NAMES.size() + " fields");
    }

    private static int depth(ClassTree classTree, Map<String, ClassTree> byName) {
        int depth = 0;
        Tree current = classTree.getExtendsClause();
        while (current != null) {
            ClassTree parent = byName.get(current.toString());
            if (parent == null) {
                break;
            }
            depth++;
            current = parent.getExtendsClause();
        }
        return depth;
    }

    /** Method names visible from superclasses, which TypeScript will not let us shadow. */
    private static Set<String> ancestorMethodNames(ExecutableElement method) {
        Set<String> names = new LinkedHashSet<>();
        TypeElement owner = (TypeElement) method.getEnclosingElement();
        TypeMirror supertype = owner.getSuperclass();
        while (supertype != null && supertype.getKind() == TypeKind.DECLARED) {
            TypeElement parent = (TypeElement) ((javax.lang.model.type.DeclaredType) supertype).asElement();
            if (!GAME_CLASSES.contains(parent.getSimpleName().toString())) {
                break;
            }
            for (Element member : parent.getEnclosedElements()) {
                if (member instanceof ExecutableElement candidate) {
                    names.add(candidate.getSimpleName().toString());
                }
            }
            supertype = parent.getSuperclass();
        }
        return names;
    }

    private static String className(Element element) {
        return element.getEnclosingElement().getSimpleName().toString();
    }

    /** Name already chosen for an overridden method, or null. */
    private static String inheritedName(ExecutableElement method) {
        TypeElement owner = (TypeElement) method.getEnclosingElement();
        TypeMirror supertype = owner.getSuperclass();
        while (supertype != null && supertype.getKind() == TypeKind.DECLARED) {
            TypeElement parent = (TypeElement) ((javax.lang.model.type.DeclaredType) supertype).asElement();
            if (!GAME_CLASSES.contains(parent.getSimpleName().toString())) {
                return null;
            }
            for (Element member : parent.getEnclosedElements()) {
                if (member instanceof ExecutableElement candidate && signature(candidate).equals(signature(method))) {
                    String mapped = METHOD_NAMES.get(parent.getSimpleName() + "#" + signature(candidate));
                    return mapped != null ? mapped : candidate.getSimpleName().toString();
                }
            }
            supertype = parent.getSuperclass();
        }
        return null;
    }

    private static String signature(ExecutableElement method) {
        StringBuilder builder = new StringBuilder(method.getSimpleName()).append('(');
        List<? extends VariableElement> parameters = method.getParameters();
        for (int index = 0; index < parameters.size(); index++) {
            if (index > 0) {
                builder.append(',');
            }
            builder.append(parameters.get(index).asType().toString());
        }
        return builder.append(')').toString();
    }

    private static String tsMethodName(ExecutableElement method) {
        Element owner = method.getEnclosingElement();
        if (owner == null || !GAME_CLASSES.contains(owner.getSimpleName().toString())) {
            return method.getSimpleName().toString();
        }
        String mapped = METHOD_NAMES.get(className(method) + "#" + signature(method));
        return mapped != null ? mapped : method.getSimpleName().toString();
    }

    private static String tsFieldName(VariableElement field) {
        String mapped = FIELD_NAMES.get(field.getEnclosingElement().getSimpleName() + "#"
                + field.getSimpleName());
        return mapped != null ? mapped : field.getSimpleName().toString();
    }

    // ------------------------------------------------------------------ helpers

    private static TypeMirror typeOf(Tree tree) {
        TreePath path = PATHS.get(tree);
        if (path == null) {
            return null;
        }
        try {
            return trees.getTypeMirror(path);
        } catch (RuntimeException error) {
            return null;
        }
    }

    private static Element elementOf(Tree tree) {
        TreePath path = PATHS.get(tree);
        if (path == null) {
            return null;
        }
        try {
            return trees.getElement(path);
        } catch (RuntimeException error) {
            return null;
        }
    }

    private static boolean isIntegral(TypeMirror type) {
        if (type == null) {
            return false;
        }
        switch (type.getKind()) {
            case INT:
            case LONG:
            case SHORT:
            case BYTE:
            case CHAR:
                return true;
            default:
                return false;
        }
    }

    private static boolean isLong(TypeMirror type) {
        return type != null && type.getKind() == TypeKind.LONG;
    }

    private static boolean isInt(TypeMirror type) {
        return type != null && type.getKind() == TypeKind.INT;
    }

    /**
     * Marks an emission whose 64 bit result a JavaScript number cannot hold. The
     * post processing step rewrites the handful of places that need it; generate.sh
     * fails if a marker survives, so no inexact operation can slip through.
     */
    private static String inexact(String code, String what) {
        return code + " /* SIM_INEXACT " + what + " */";
    }

    /** Java's narrowing conversion back to the target of a compound assignment. */
    private static String narrow(TypeMirror type, String code) {
        if (type == null) {
            return code;
        }
        switch (type.getKind()) {
            case BYTE:
                return "(" + code + " << 24 >> 24)";
            case SHORT:
                return "(" + code + " << 16 >> 16)";
            case CHAR:
                return "(" + code + " & 0xffff)";
            default:
                return code;
        }
    }

    private static boolean isStringType(TypeMirror type) {
        return type != null && type.toString().equals("java.lang.String");
    }

    /**
     * Numeric parity audit: when SIM_AUDIT is set the translator records every
     * arithmetic operator together with the kinds of its operands, so the
     * emissions that need reviewing can be enumerated instead of guessed at.
     */
    private static final Map<String, Integer> AUDIT = new TreeMap<>();

    private static void audit(String category, String detail) {
        if (System.getenv("SIM_AUDIT") == null) {
            return;
        }
        AUDIT.merge(category + " " + detail, 1, Integer::sum);
    }

    private static String kindOf(TypeMirror type) {
        if (type == null) {
            return "?";
        }
        TypeKind kind = type.getKind();
        if (kind == TypeKind.ARRAY) {
            return "array";
        }
        if (kind == TypeKind.DECLARED) {
            return type.toString();
        }
        if (kind == TypeKind.BOOLEAN) {
            return "boolean";
        }
        return kind.name().toLowerCase(Locale.ROOT);
    }

    // ----------------------------------------------------------------- generator

    private static final class Generator {
        private final CompilationUnitTree unit;
        private final String className;
        private final StringBuilder out = new StringBuilder();
        private final Set<String> used = new LinkedHashSet<>();
        private int indent;
        private boolean hasBaseClass;

        Generator(CompilationUnitTree unit, String className) {
            this.unit = unit;
            this.className = className;
        }

        String generate(ClassTree tree) {
            emitClass(tree);

            StringBuilder header = new StringBuilder();
            header.append("// Generated from ").append(className)
                    .append(".java by tools/web/JavaToTs.java; keep this file in sync.\n");

            List<String> runtime = new ArrayList<>();
            List<String> game = new ArrayList<>();
            for (String name : used) {
                if (name.equals(className)) {
                    continue;
                }
                if (RUNTIME_NAMES.contains(name)) {
                    runtime.add(name);
                } else if (GAME_CLASSES.contains(name)) {
                    game.add(name);
                }
            }
            if (!runtime.isEmpty()) {
                header.append("import { ").append(String.join(", ", runtime)).append(" } from '../runtime';\n");
            }
            for (String name : game) {
                header.append("import { ").append(name).append(" } from './").append(name).append("';\n");
            }
            header.append('\n');
            return header + out.toString();
        }

        // ------------------------------------------------------------ structure

        private void emitClass(ClassTree tree) {
            if (tree.getKind() == Tree.Kind.INTERFACE) {
                emitInterface(tree);
                return;
            }

            add("export ");
            if (tree.getModifiers().getFlags().contains(Modifier.ABSTRACT)) {
                add("abstract ");
            }
            add("class ").add(tree.getSimpleName().toString());

            Tree extendsClause = tree.getExtendsClause();
            hasBaseClass = extendsClause != null;
            if (extendsClause != null) {
                add(" extends ").add(typeName(extendsClause));
            }
            add(" {\n");
            indent++;

            List<MethodTree> constructors = new ArrayList<>();
            for (Tree member : tree.getMembers()) {
                if (member instanceof MethodTree methodTree && methodTree.getName().contentEquals("<init>")) {
                    constructors.add(methodTree);
                    continue;
                }
                emitMember(member);
            }
            emitConstructors(constructors);

            indent--;
            add("}\n");
        }

        /** One TypeScript constructor; Java overloads dispatch on their arguments. */
        private void emitConstructors(List<MethodTree> constructors) {
            if (constructors.isEmpty()) {
                return;
            }
            if (constructors.size() == 1) {
                emitMethod(constructors.get(0));
                return;
            }
            add("constructor(...args: any[]) {\n");
            indent++;
            boolean first = true;
            for (MethodTree constructor : constructors) {
                List<? extends VariableTree> parameters = constructor.getParameters();
                StringBuilder condition = new StringBuilder("args.length === " + parameters.size());
                for (int index = 0; index < parameters.size(); index++) {
                    String check = typeCheck(parameters.get(index), "args[" + index + "]");
                    if (check != null) {
                        condition.append(" && ").append(check);
                    }
                }
                add(first ? "if (" : "else if (").add(condition).add(") {\n");
                first = false;
                indent++;
                for (int index = 0; index < parameters.size(); index++) {
                    VariableTree parameter = parameters.get(index);
                    add("const ").add(parameter.getName().toString()).add(" = args[").add(String.valueOf(index))
                            .add("] as ").add(tsType(typeOf(parameter.getType()),
                                    parameter.getType().toString())).add(";\n");
                }
                for (StatementTree statement : constructor.getBody().getStatements()) {
                    if (!hasBaseClass && statement.getKind() == Tree.Kind.EXPRESSION_STATEMENT
                            && ((ExpressionStatementTree) statement).getExpression().toString().startsWith("super(")) {
                        continue;
                    }
                    statement(statement);
                }
                indent--;
                add("}\n");
            }
            indent--;
            add("}\n");
        }

        private String typeCheck(VariableTree parameter, String expression) {
            String type = tsType(typeOf(parameter.getType()), parameter.getType().toString());
            switch (type) {
                case "number":
                    return "typeof " + expression + " === \"number\"";
                case "boolean":
                    return "typeof " + expression + " === \"boolean\"";
                case "string":
                    return "typeof " + expression + " === \"string\"";
                case "Int8Array":
                case "Int16Array":
                case "Int32Array":
                case "Uint8Array":
                case "Uint16Array":
                case "Float64Array":
                    return expression + " instanceof " + type;
                case "any":
                    return null;
                default:
                    if (GAME_CLASSES.contains(type) || RUNTIME_NAMES.contains(type)) {
                        return expression + " instanceof " + type;
                    }
                    return null;
            }
        }

        /** Interfaces in this game only hold constants, so they become plain objects. */
        private void emitInterface(ClassTree tree) {
            add("export const ").add(tree.getSimpleName().toString()).add(" = {\n");
            indent++;
            for (Tree member : tree.getMembers()) {
                if (member instanceof VariableTree variable) {
                    add(variable.getName().toString()).add(": ");
                    add(variable.getInitializer() == null ? "null" : expr(variable.getInitializer()));
                    add(",\n");
                }
            }
            indent--;
            add("};\n");
        }

        private void emitMember(Tree member) {
            switch (member.getKind()) {
                case VARIABLE:
                    emitField((VariableTree) member);
                    return;
                case METHOD:
                    emitMethod((MethodTree) member);
                    return;
                case BLOCK:
                    add("static {\n");
                    indent++;
                    for (StatementTree statement : ((BlockTree) member).getStatements()) {
                        statement(statement);
                    }
                    indent--;
                    add("}\n");
                    return;
                default:
                    throw new IllegalStateException("unsupported member: " + member.getKind() + " at " + position(member));
            }
        }

        private void emitField(VariableTree variable) {
            Element element = elementOf(variable);
            boolean isStatic = element != null && element.getModifiers().contains(Modifier.STATIC);
            TypeMirror type = element == null ? null : element.asType();
            String typeText = tsType(type, variable.getType().toString());

            if (isStatic) {
                add("static ");
            }
            String fieldName = element instanceof VariableElement variableElement
                    ? tsFieldName(variableElement)
                    : variable.getName().toString();
            add(fieldName).add(": ").add(typeText);
            ExpressionTree initializer = variable.getInitializer();
            add(" = ");
            add(initializer == null ? defaultValue(typeText) : expr(initializer));
            add(";\n");
        }

        private void emitMethod(MethodTree method) {
            if (method.getName().contentEquals("<init>")) {
                add("constructor(");
                emitParameters(method);
                add(") {\n");
                indent++;
                if (method.getBody() != null) {
                    for (StatementTree statement : method.getBody().getStatements()) {
                        // Java's implicit super() has no meaning without a base class.
                        if (!hasBaseClass && statement.getKind() == Tree.Kind.EXPRESSION_STATEMENT
                                && ((ExpressionStatementTree) statement).getExpression().toString()
                                        .startsWith("super(")) {
                            continue;
                        }
                        statement(statement);
                    }
                }
                indent--;
                add("}\n");
                return;
            }

            Element element = elementOf(method);
            boolean isStatic = method.getModifiers().getFlags().contains(Modifier.STATIC);
            boolean isAbstract = method.getModifiers().getFlags().contains(Modifier.ABSTRACT)
                    || method.getBody() == null;

            if (isStatic) {
                add("static ");
            }
            if (isAbstract) {
                add("abstract ");
            }
            String methodName = element instanceof ExecutableElement executableMethod
                    ? tsMethodName(executableMethod)
                    : method.getName().toString();
            add(methodName).add("(");
            emitParameters(method);
            add("): ");
            TypeMirror returnType = element == null ? null : ((ExecutableElement) element).getReturnType();
            add(tsType(returnType));
            if (isAbstract) {
                add(";\n");
                return;
            }
            add(" {\n");
            indent++;
            for (StatementTree statement : method.getBody().getStatements()) {
                statement(statement);
            }
            indent--;
            add("}\n");
        }

        private void emitParameters(MethodTree method) {
            List<String> parts = new ArrayList<>();
            for (VariableTree parameter : method.getParameters()) {
                parts.add(parameter.getName() + ": "
                        + tsType(typeOf(parameter.getType()), parameter.getType().toString()));
            }
            add(String.join(", ", parts));
        }

        // ----------------------------------------------------------- statements

        private void statement(StatementTree tree) {
            switch (tree.getKind()) {
                case VARIABLE: {
                    VariableTree variable = (VariableTree) tree;
                    String type = tsType(typeOf(variable.getType()), variable.getType().toString());
                    add("let ").add(variable.getName().toString()).add(": ").add(type);
                    add(" = ");
                    add(variable.getInitializer() == null ? defaultValue(type) : expr(variable.getInitializer()));
                    add(";\n");
                    return;
                }
                case EXPRESSION_STATEMENT: {
                    add(expr(((ExpressionStatementTree) tree).getExpression())).add(";\n");
                    return;
                }
                case IF: {
                    IfTree ifTree = (IfTree) tree;
                    add("if (").add(expr(ifTree.getCondition())).add(") ");
                    body(ifTree.getThenStatement());
                    if (ifTree.getElseStatement() != null) {
                        add("else ");
                        body(ifTree.getElseStatement());
                    }
                    return;
                }
                case WHILE_LOOP: {
                    WhileLoopTree loop = (WhileLoopTree) tree;
                    add("while (").add(expr(loop.getCondition())).add(") ");
                    body(loop.getStatement());
                    return;
                }
                case DO_WHILE_LOOP: {
                    DoWhileLoopTree loop = (DoWhileLoopTree) tree;
                    add("do ");
                    body(loop.getStatement());
                    add("while (").add(expr(loop.getCondition())).add(");\n");
                    return;
                }
                case FOR_LOOP:
                    emitFor((ForLoopTree) tree);
                    return;
                case ENHANCED_FOR_LOOP:
                    emitEnhancedFor((EnhancedForLoopTree) tree);
                    return;
                case SWITCH:
                    emitSwitch((SwitchTree) tree);
                    return;
                case RETURN: {
                    ReturnTree returnTree = (ReturnTree) tree;
                    add("return");
                    if (returnTree.getExpression() != null) {
                        add(" ").add(expr(returnTree.getExpression()));
                    }
                    add(";\n");
                    return;
                }
                case THROW:
                    add("throw ").add(expr(((ThrowTree) tree).getExpression())).add(";\n");
                    return;
                case BREAK: {
                    BreakTree breakTree = (BreakTree) tree;
                    add("break");
                    if (breakTree.getLabel() != null) {
                        add(" ").add(breakTree.getLabel().toString());
                    }
                    add(";\n");
                    return;
                }
                case CONTINUE: {
                    ContinueTree continueTree = (ContinueTree) tree;
                    add("continue");
                    if (continueTree.getLabel() != null) {
                        add(" ").add(continueTree.getLabel().toString());
                    }
                    add(";\n");
                    return;
                }
                case LABELED_STATEMENT: {
                    LabeledStatementTree labeled = (LabeledStatementTree) tree;
                    add(labeled.getLabel().toString()).add(": ");
                    body(labeled.getStatement());
                    return;
                }
                case SYNCHRONIZED:
                    body(((SynchronizedTree) tree).getBlock());
                    return;
                case BLOCK:
                    body(tree);
                    return;
                case TRY:
                    emitTry((TryTree) tree);
                    return;
                case EMPTY_STATEMENT:
                    return;
                default:
                    throw new IllegalStateException("unsupported statement: " + tree.getKind()
                            + " at " + position(tree));
            }
        }

        private void body(StatementTree tree) {
            add("{\n");
            indent++;
            if (tree instanceof BlockTree block) {
                for (StatementTree statement : block.getStatements()) {
                    statement(statement);
                }
            } else {
                statement(tree);
            }
            indent--;
            add("}\n");
        }

        private void emitFor(ForLoopTree loop) {
            List<String> parts = new ArrayList<>();
            for (StatementTree initializer : loop.getInitializer()) {
                parts.add(initializer(initializer));
            }
            List<String> updates = new ArrayList<>();
            for (ExpressionStatementTree update : loop.getUpdate()) {
                updates.add(expr(update.getExpression()));
            }
            add("for (").add(String.join(", ", parts)).add("; ");
            add(loop.getCondition() == null ? "" : expr(loop.getCondition()));
            add("; ").add(String.join(", ", updates)).add(") ");
            body(loop.getStatement());
        }

        private String initializer(StatementTree tree) {
            if (tree instanceof VariableTree variable) {
                String type = tsType(typeOf(variable.getType()), variable.getType().toString());
                String result = "let " + variable.getName() + ": " + type;
                if (variable.getInitializer() != null) {
                    result += " = " + expr(variable.getInitializer());
                }
                return result;
            }
            return expr(((ExpressionStatementTree) tree).getExpression());
        }

        private void emitEnhancedFor(EnhancedForLoopTree loop) {
            String type = tsType(typeOf(loop.getVariable().getType()), loop.getVariable().getType().toString());
            add("for (const ").add(loop.getVariable().getName().toString()).add(": ").add(type)
                    .add(" of ").add(expr(loop.getExpression())).add(") ");
            body(loop.getStatement());
        }

        private void emitSwitch(SwitchTree tree) {
            add("switch (").add(expr(tree.getExpression())).add(") {\n");
            indent++;
            for (CaseTree caseTree : tree.getCases()) {
                List<? extends ExpressionTree> labels = caseTree.getExpressions();
                if (labels.isEmpty()) {
                    add("default:\n");
                } else {
                    for (ExpressionTree label : labels) {
                        add("case ").add(expr(label)).add(":\n");
                    }
                }
                indent++;
                if (caseTree.getStatements() != null) {
                    for (StatementTree statement : caseTree.getStatements()) {
                        statement(statement);
                    }
                }
                indent--;
            }
            indent--;
            add("}\n");
        }

        /**
         * JavaScript has a single catch clause, so Java's typed catches become an
         * instanceof chain. Catches for Exception/Throwable act as the fallback and
         * an unmatched error is rethrown.
         */
        private void emitTry(TryTree tree) {
            add("try ");
            body(tree.getBlock());
            List<? extends CatchTree> catches = tree.getCatches();
            if (!catches.isEmpty()) {
                String catchName = catches.get(0).getParameter().getName().toString();
                add("catch (").add(catches.get(0).getParameter().getName().toString()).add(") {\n");
                indent++;
                for (int index = 0; index < catches.size(); index++) {
                    CatchTree catchTree = catches.get(index);
                    String type = catchType(catchTree);
                    boolean fallback = isCatchAll(type);
                    if (fallback) {
                        for (StatementTree statement : catchTree.getBlock().getStatements()) {
                            statement(statement);
                        }
                        break;
                    }
                    add(index == 0 ? "if" : "else if").add(" (").add(catchName)
                            .add(" instanceof ").add(type).add(") ");
                    body(catchTree.getBlock());
                    if (index == catches.size() - 1) {
                        add("else {\n");
                        indent++;
                        add("throw ").add(catchName).add(";\n");
                        indent--;
                        add("}\n");
                    }
                }
                indent--;
                add("}\n");
            }
            if (tree.getFinallyBlock() != null) {
                add("finally ");
                body(tree.getFinallyBlock());
            }
        }

        private String catchType(CatchTree catchTree) {
            Tree typeTree = catchTree.getParameter().getType();
            TypeMirror type = typeOf(typeTree);
            if (type != null && type.getKind() == TypeKind.DECLARED) {
                return declaredType(type.toString());
            }
            return simpleFromText(typeTree.toString());
        }

        private boolean isCatchAll(String type) {
            return type.equals("java.lang.Exception") || type.equals("java.lang.Throwable")
                    || type.equals("java.lang.RuntimeException") || type.equals("Exception")
                    || type.equals("Throwable") || type.equals("RuntimeException");
        }

        // ---------------------------------------------------------- expressions

        private String expr(ExpressionTree tree) {
            return expr((Tree) tree, 0);
        }

        private String expr(Tree tree, int minPrecedence) {
            String text = rawExpr(tree);
            return precedence(tree) < minPrecedence ? "(" + text + ")" : text;
        }

        private String rawExpr(Tree tree) {
            switch (tree.getKind()) {
                case INT_LITERAL:
                case LONG_LITERAL:
                case FLOAT_LITERAL:
                case DOUBLE_LITERAL:
                case BOOLEAN_LITERAL:
                    return String.valueOf(((LiteralTree) tree).getValue());
                case CHAR_LITERAL:
                    return String.valueOf((int) (Character) ((LiteralTree) tree).getValue());
                case STRING_LITERAL:
                    return quote((String) ((LiteralTree) tree).getValue());
                case NULL_LITERAL:
                    return "null";
                case IDENTIFIER:
                    return identifier((IdentifierTree) tree);
                case MEMBER_SELECT:
                    return memberSelect((MemberSelectTree) tree);
                case METHOD_INVOCATION:
                    return invocation((MethodInvocationTree) tree);
                case NEW_CLASS:
                    return newClass((NewClassTree) tree);
                case NEW_ARRAY:
                    return newArray((NewArrayTree) tree);
                case ASSIGNMENT:
                    return assignment((AssignmentTree) tree);
                case CONDITIONAL_EXPRESSION: {
                    ConditionalExpressionTree conditional = (ConditionalExpressionTree) tree;
                    return expr(conditional.getCondition(), 5) + " ? "
                            + expr(conditional.getTrueExpression(), 4) + " : "
                            + expr(conditional.getFalseExpression(), 4);
                }
                case TYPE_CAST:
                    return cast((TypeCastTree) tree);
                case INSTANCE_OF: {
                    InstanceOfTree instanceOf = (InstanceOfTree) tree;
                    TypeMirror target = typeOf(instanceOf.getType());
                    if (target instanceof ArrayType) {
                        return "Array.isArray(" + expr(instanceOf.getExpression()) + ")";
                    }
                    return expr(instanceOf.getExpression(), 12) + " instanceof "
                            + tsType(target, instanceOf.getType().toString());
                }
                case ARRAY_ACCESS: {
                    ArrayAccessTree access = (ArrayAccessTree) tree;
                    return expr(access.getExpression(), 20) + "[" + expr(access.getIndex()) + "]";
                }
                case PARENTHESIZED:
                    return expr(((ParenthesizedTree) tree).getExpression());
                default:
                    break;
            }

            if (tree instanceof CompoundAssignmentTree compound) {
                // `byte b; b += 1` narrows back to byte in Java, and every int operation
                // wraps at 32 bits; both need to be spelled out for JavaScript.
                TypeMirror targetType = typeOf(compound.getVariable());
                audit("compound", tree.getKind() + " " + kindOf(targetType) + "/"
                        + kindOf(typeOf(compound.getExpression())));
                if (isIntegral(targetType)) {
                    String combined = compound(targetType, tree.getKind(),
                            expr(compound.getVariable(), 16), expr(compound.getExpression(), 15));
                    if (combined != null) {
                        return expr(compound.getVariable(), 16) + " = " + combined;
                    }
                }
                return expr(compound.getVariable(), 4) + " " + compoundOperator(tree.getKind()) + " "
                        + expr(compound.getExpression(), 4);
            }
            if (tree instanceof UnaryTree unary) {
                return unary(unary);
            }
            if (tree instanceof BinaryTree binary) {
                return binary(binary);
            }
            throw new IllegalStateException("unsupported expression: " + tree.getKind() + " at " + position(tree));
        }

        private String assignment(AssignmentTree assignment) {
            return expr(assignment.getVariable(), 4) + " = " + expr(assignment.getExpression(), 4);
        }

        private String unary(UnaryTree unary) {
            String operand = expr(unary.getExpression(), 16);
            boolean intOperand = isInt(typeOf(unary));
            switch (unary.getKind()) {
                case UNARY_MINUS:
                    // negating Integer.MIN_VALUE keeps it negative in Java but not in
                    // JavaScript, so the int result is wrapped like every other int op.
                    return intOperand ? "((" + operand + " * -1) | 0)" : "-" + operand;
                case UNARY_PLUS:
                    return "+" + operand;
                case LOGICAL_COMPLEMENT:
                    return "!" + operand;
                case BITWISE_COMPLEMENT:
                    return "~" + operand;
                case PREFIX_INCREMENT:
                    return "++" + operand;
                case PREFIX_DECREMENT:
                    return "--" + operand;
                case POSTFIX_INCREMENT:
                    return operand + "++";
                case POSTFIX_DECREMENT:
                    return operand + "--";
                default:
                    throw new IllegalStateException("unsupported unary: " + unary.getKind());
            }
        }

        private String binary(BinaryTree binary) {
            Tree.Kind kind = binary.getKind();
            TypeMirror leftType = typeOf(binary.getLeftOperand());
            TypeMirror rightType = typeOf(binary.getRightOperand());

            if (kind == Tree.Kind.PLUS && isStringType(typeOf(binary))) {
                return stringOperand(binary.getLeftOperand(), 13) + " + "
                        + stringOperand(binary.getRightOperand(), 14);
            }

            audit("binary", kind + " " + kindOf(leftType) + "/" + kindOf(rightType));

            int precedence = precedence(binary);
            String left = expr(binary.getLeftOperand(), precedence);
            String right = expr(binary.getRightOperand(), precedence + 1);

            boolean isWide = isLong(leftType) || isLong(rightType);
            if (isWide && (kind == Tree.Kind.LEFT_SHIFT || kind == Tree.Kind.RIGHT_SHIFT
                    || kind == Tree.Kind.UNSIGNED_RIGHT_SHIFT)) {
                // 64 bit shifts do not fit a JavaScript int32; scale instead.
                if (kind == Tree.Kind.UNSIGNED_RIGHT_SHIFT) {
                    return inexact("((" + left + ") >>> " + right + ")", "long unsigned shift");
                }
                String factor = shiftFactor(binary.getRightOperand(), 63);
                if (kind == Tree.Kind.LEFT_SHIFT) {
                    // A left shift leaves the exactly representable range once the
                    // result passes 2^53. A literal distance is reviewed case by case
                    // (the fixed point scalings stay small); a variable one is not, so
                    // it has to be rewritten by hand.
                    String scaled = "((" + left + ") * " + factor + ")";
                    return binary.getRightOperand() instanceof LiteralTree
                            ? scaled
                            : inexact(scaled, "long left shift");
                }
                return "Math.floor((" + left + ") / " + factor + ")";
            }
            if (isWide && (kind == Tree.Kind.AND || kind == Tree.Kind.OR || kind == Tree.Kind.XOR)) {
                return inexact("(" + left + " " + binaryOperator(kind) + " " + right + ")", "long bitwise");
            }
            if (kind == Tree.Kind.DIVIDE && isIntegral(leftType) && isIntegral(rightType)) {
                if (isWide) {
                    // Exact: for a dividend that a double can hold, the quotient is
                    // further than half an ulp away from the next integer.
                    return "Math.trunc(" + left + " / " + right + ")";
                }
                // The `| 0` wrapper needs its own parentheses: `|` binds looser than
                // the operators around it, so `x | 0 - y` would not mean `(x|0) - y`.
                return "((" + left + " / " + right + ") | 0)";
            }
            if (kind == Tree.Kind.REMAINDER && isIntegral(leftType) && isIntegral(rightType)) {
                if (isWide) {
                    return "(" + left + " % " + right + ")";
                }
                return "((" + left + " % " + right + ") | 0)";
            }
            if (isInt(typeOf(binary))) {
                switch (kind) {
                    case MULTIPLY:
                        // JavaScript multiplication does not wrap at 32 bits and loses
                        // precision past 2^53; Math.imul is exactly Java's int product.
                        return "Math.imul(" + left + ", " + right + ")";
                    case PLUS:
                    case MINUS:
                    case UNSIGNED_RIGHT_SHIFT:
                        return "((" + left + " " + binaryOperator(kind) + " " + right + ") | 0)";
                    default:
                        break;
                }
            }
            return left + " " + binaryOperator(kind) + " " + right;
        }

        /**
         * Java masks a shift distance to the width of the promoted left operand, so
         * `x << 64` is `x << 0`; scaling by 2^distance has to mask the same way.
         */
        private String shiftFactor(ExpressionTree amount, int mask) {
            if (amount instanceof LiteralTree literal && literal.getValue() instanceof Number number) {
                long distance = number.longValue() & mask;
                return String.valueOf((long) Math.pow(2, distance));
            }
            if (mask == 31) {
                return "Math.pow(2, " + expr(amount) + " & 31)";
            }
            return "Math.pow(2, " + expr(amount) + " & " + mask + ")";
        }

        private String stringOperand(ExpressionTree operand, int precedence) {
            TypeMirror type = typeOf(operand);
            String text = expr(operand, precedence);
            if (type != null && type.getKind() == TypeKind.CHAR) {
                used.add("chr");
                return "chr(" + text + ")";
            }
            return text;
        }

        private String cast(TypeCastTree cast) {
            TypeMirror target = typeOf(cast.getType());
            String inner = expr(cast.getExpression(), 16);
            audit("cast", kindOf(target) + " <- " + kindOf(typeOf(cast.getExpression())));
            if (target == null) {
                return inner;
            }
            switch (target.getKind()) {
                case BYTE:
                    return "(" + inner + " << 24 >> 24)";
                case SHORT:
                    return "(" + inner + " << 16 >> 16)";
                case CHAR:
                    return "(" + inner + " & 0xffff)";
                case INT:
                    return "(" + inner + " | 0)";
                case LONG:
                case FLOAT:
                case DOUBLE:
                    return inner;
                default:
                    break;
            }
            return "(" + inner + " as unknown as " + tsType(target, cast.getType().toString()) + ")";
        }

        private String newClass(NewClassTree tree) {
            String type = tsType(typeOf(tree), tree.getIdentifier().toString());
            if (type.equals("number") || type.equals("boolean")) {
                return tree.getArguments().isEmpty() ? defaultValue(type) : expr(tree.getArguments().get(0));
            }
            if (type.equals("string")) {
                List<String> arguments = new ArrayList<>();
                for (ExpressionTree argument : tree.getArguments()) {
                    arguments.add(expr(argument));
                }
                used.add("bytesToString");
                return "bytesToString(" + String.join(", ", arguments) + ")";
            }
            List<String> arguments = new ArrayList<>();
            for (ExpressionTree argument : tree.getArguments()) {
                arguments.add(expr(argument));
            }
            return "new " + type + "(" + String.join(", ", arguments) + ")";
        }

        private String newArray(NewArrayTree tree) {
            TypeMirror type = typeOf(tree);
            if (!(type instanceof ArrayType arrayType)) {
                throw new IllegalStateException("unknown array type at " + position(tree) + ": " + type
                        + " for " + tree);
            }
            List<? extends ExpressionTree> initializers = tree.getInitializers();
            if (initializers != null) {
                List<String> parts = new ArrayList<>();
                for (ExpressionTree initializer : initializers) {
                    parts.add(expr(initializer));
                }
                String joined = String.join(", ", parts);
                if (arrayType.getComponentType().getKind() == TypeKind.ARRAY) {
                    return "[" + joined + "]";
                }
                String elementType = elementTypeFor(arrayType.getComponentType());
                if (isTypedArray(elementType)) {
                    return elementType + ".from([" + joined + "])";
                }
                return "[" + joined + "]";
            }
            return newArrayWithDimensions(arrayType, tree.getDimensions(), 0);
        }

        private String newArrayWithDimensions(ArrayType type, List<? extends ExpressionTree> dimensions, int index) {
            TypeMirror component = type.getComponentType();
            String size = expr(dimensions.get(index));
            if (component instanceof ArrayType nested) {
                if (index + 1 >= dimensions.size()) {
                    // new short[n][] leaves the inner arrays unspecified (null).
                    return "new Array(" + size + ").fill(null)";
                }
                String inner = newArrayWithDimensions(nested, dimensions, index + 1);
                return "Array.from({ length: " + size + " }, () => " + inner + ")";
            }
            String elementType = elementTypeFor(component);
            if (isTypedArray(elementType)) {
                return "new " + elementType + "(" + size + ")";
            }
            return "new Array(" + size + ").fill(" + ("boolean".equals(elementType) ? "false" : "null") + ")";
        }

        private String identifier(IdentifierTree tree) {
            Element element = elementOf(tree);
            String name = tree.getName().toString();
            if (name.equals("this") || name.equals("super")) {
                return name;
            }
            if (element == null) {
                return name;
            }
            switch (element.getKind()) {
                case FIELD:
                case ENUM_CONSTANT: {
                    VariableElement field = (VariableElement) element;
                    if (field.getModifiers().contains(Modifier.STATIC)) {
                        String owner = typeName(field.getEnclosingElement());
                        if (owner.equals("Integer")) {
                            return name.equals("MAX_VALUE") ? "2147483647" : "-2147483648";
                        }
                        if (owner.equals("Long")) {
                            return name.equals("MAX_VALUE") ? "9223372036854775807" : "-9223372036854775808";
                        }
                        return owner + "." + tsFieldName(field);
                    }
                    return "this." + tsFieldName(field);
                }
                case LOCAL_VARIABLE:
                case PARAMETER:
                case EXCEPTION_PARAMETER:
                case RESOURCE_VARIABLE:
                    return name;
                case CLASS:
                case INTERFACE:
                case ENUM:
                    return typeName(element);
                case METHOD: {
                    ExecutableElement method = (ExecutableElement) element;
                    if (method.getModifiers().contains(Modifier.STATIC)) {
                        return typeName(method.getEnclosingElement()) + "." + tsMethodName(method);
                    }
                    return "this." + tsMethodName(method);
                }
                default:
                    return name;
            }
        }

        private String memberSelect(MemberSelectTree tree) {
            String name = tree.getIdentifier().toString();
            Element element = elementOf(tree);
            if (tree.getExpression() instanceof IdentifierTree qualifier
                    && qualifier.getName().contentEquals("super")
                    && element instanceof VariableElement inheritedField) {
                // TS only allows super for methods; inherited state lives on this.
                return "this." + tsFieldName(inheritedField);
            }
            if (element instanceof VariableElement field) {
                name = tsFieldName(field);
            }
            if (element instanceof VariableElement field && field.getModifiers().contains(Modifier.STATIC)) {
                String owner = typeName(field.getEnclosingElement());
                if (owner.equals("Integer")) {
                    return name.equals("MAX_VALUE") ? "2147483647" : "-2147483648";
                }
                if (owner.equals("Long")) {
                    return name.equals("MAX_VALUE") ? "9223372036854775807" : "-9223372036854775808";
                }
                name = field.getSimpleName().toString();
            }
            return expr(tree.getExpression(), 20) + "." + name;
        }

        private String invocation(MethodInvocationTree tree) {
            ExpressionTree select = tree.getMethodSelect();
            Element element = elementOf(tree);
            List<String> arguments = new ArrayList<>();
            for (ExpressionTree argument : tree.getArguments()) {
                arguments.add(expr(argument));
            }
            String joined = String.join(", ", arguments);

            if (select instanceof IdentifierTree identifier) {
                String name = identifier.getName().toString();
                if (name.equals("super") || name.equals("this")) {
                    return name + "(" + joined + ")";
                }
                if (element instanceof ExecutableElement method) {
                    String tsName = tsMethodName(method);
                    if (method.getModifiers().contains(Modifier.STATIC)) {
                        return typeName(method.getEnclosingElement()) + "." + tsName + "(" + joined + ")";
                    }
                    return "this." + tsName + "(" + joined + ")";
                }
                return "this." + name + "(" + joined + ")";
            }

            MemberSelectTree memberSelect = (MemberSelectTree) select;
            String name = memberSelect.getIdentifier().toString();
            ExpressionTree receiver = memberSelect.getExpression();
            if (element instanceof ExecutableElement method) {
                name = tsMethodName(method);
            }

            if (element != null && element.getEnclosingElement() instanceof TypeElement owner) {
                String mapped = mapCall(owner.getQualifiedName().toString(), name, receiver, tree, arguments);
                if (mapped != null) {
                    return mapped;
                }
            }

            return expr(receiver, 20) + "." + name + "(" + joined + ")";
        }

        /** Rewrites the Java library calls the game makes. */
        private String mapCall(String owner, String name, ExpressionTree receiver, MethodInvocationTree tree,
                               List<String> arguments) {
            String joined = String.join(", ", arguments);
            switch (owner) {
                case "java.lang.System":
                    switch (name) {
                        case "arraycopy":
                            used.add("arraycopy");
                            return "arraycopy(" + joined + ")";
                        case "currentTimeMillis":
                            return "Date.now()";
                        case "gc":
                            used.add("gc");
                            return "gc()";
                        case "exit":
                            used.add("exitApp");
                            return "exitApp()";
                        case "getProperty":
                            used.add("getProperty");
                            return "getProperty(" + joined + ")";
                        default:
                            return null;
                    }
                case "java.io.PrintStream":
                case "java.io.PrintWriter":
                    return "console.log(" + joined + ")";
                case "java.lang.Math":
                    return "Math." + name + "(" + joined + ")";
                case "java.lang.Thread":
                    if (name.equals("sleep") || name.equals("yield")) {
                        return "void 0";
                    }
                    return null;
                case "java.lang.Integer":
                case "java.lang.Long":
                    if (name.equals("toString")) {
                        return "String(" + joined + ")";
                    }
                    if (name.equals("parseInt") || name.equals("valueOf")) {
                        return "Number(" + joined + ")";
                    }
                    return null;
                case "java.lang.String":
                    return mapStringCall(name, receiver, tree, arguments);
                case "java.lang.StringBuffer":
                    // append(char) must append the character, not its code point.
                    if (name.equals("append") && typeOf(tree.getArguments().get(0)).getKind() == TypeKind.CHAR) {
                        used.add("chr");
                        return expr(receiver, 20) + ".append(chr(" + arguments.get(0) + "))";
                    }
                    return null;
                case "java.lang.Throwable":
                case "java.lang.Exception":
                case "java.lang.RuntimeException":
                    if (name.equals("printStackTrace")) {
                        return "console.error(" + expr(receiver, 20) + ")";
                    }
                    if (name.equals("getMessage")) {
                        return "(" + expr(receiver, 20) + " as any).message";
                    }
                    return null;
                case "java.lang.Class":
                    if (name.equals("getResourceAsStream")) {
                        used.add("loadResource");
                        used.add("ByteArrayInputStream");
                        return "new ByteArrayInputStream(loadResource(" + joined + "))";
                    }
                    return null;
                default:
                    return null;
            }
        }

        private String mapStringCall(String name, ExpressionTree receiver, MethodInvocationTree tree,
                                     List<String> arguments) {
            String target = expr(receiver, 20);
            switch (name) {
                case "length":
                    return target + ".length";
                case "charAt":
                    return target + ".charCodeAt(" + arguments.get(0) + ")";
                case "equals":
                    return "(" + target + " === " + arguments.get(0) + ")";
                case "equalsIgnoreCase":
                    return "(" + target + ".toLowerCase() === " + arguments.get(0) + ".toLowerCase())";
                case "toString":
                    return target;
                case "valueOf":
                    if (tree.getArguments().get(0) != null
                            && typeOf(tree.getArguments().get(0)).getKind() == TypeKind.CHAR) {
                        used.add("chr");
                        return "chr(" + arguments.get(0) + ")";
                    }
                    return "String(" + arguments.get(0) + ")";
                case "indexOf":
                case "lastIndexOf":
                    used.add("chr");
                    if (arguments.size() > 1) {
                        return target + "." + name + "(chr(" + arguments.get(0) + "), " + arguments.get(1) + ")";
                    }
                    return target + "." + name + "(chr(" + arguments.get(0) + "))";
                case "getBytes":
                    used.add("charsToBytes");
                    return "charsToBytes(" + target + ")";
                case "compareTo":
                    used.add("compareStrings");
                    return "compareStrings(" + target + ", " + arguments.get(0) + ")";
                default:
                    return null;
            }
        }

        // ------------------------------------------------------------- utilities

        private Generator add(CharSequence text) {
            if (out.length() == 0 || out.charAt(out.length() - 1) == '\n') {
                out.append("\t".repeat(Math.max(0, indent)));
            }
            out.append(text);
            return this;
        }

        private int position(Tree tree) {
            long start = trees.getSourcePositions().getStartPosition(unit, tree);
            return (int) start;
        }

        private String typeName(Tree tree) {
            Element element = elementOf(tree);
            return element == null ? tree.toString() : typeName(element);
        }

        private String typeName(Element element) {
            String name = element.getSimpleName().toString();
            used.add(name);
            return name;
        }

        private String tsType(Tree typeTree) {
            return tsType(typeOf(typeTree), typeTree.toString());
        }

        private String tsType(TypeMirror type) {
            return tsType(type, null);
        }

        private String tsType(TypeMirror type, String fallback) {
            if (type == null) {
                return simpleFromText(fallback);
            }
            switch (type.getKind()) {
                case INT:
                case LONG:
                case SHORT:
                case BYTE:
                case CHAR:
                case FLOAT:
                case DOUBLE:
                    return "number";
                case BOOLEAN:
                    return "boolean";
                case VOID:
                    return "void";
                case ARRAY:
                    return arrayType((ArrayType) type);
                case DECLARED:
                    return declaredType(type.toString());
                default:
                    return simpleFromText(fallback);
            }
        }

        private String arrayType(ArrayType type) {
            TypeMirror component = type.getComponentType();
            if (component.getKind() == TypeKind.ARRAY) {
                return arrayType((ArrayType) component) + "[]";
            }
            String element = elementTypeFor(component);
            return isTypedArray(element) ? element : element + "[]";
        }

        private String elementTypeFor(TypeMirror component) {
            switch (component.getKind()) {
                case INT:
                    return "Int32Array";
                case LONG:
                    return "Float64Array";
                case SHORT:
                    return "Int16Array";
                case BYTE:
                    return "Int8Array";
                case CHAR:
                    return "Uint16Array";
                case BOOLEAN:
                    return "boolean";
                case ARRAY:
                    return arrayType((ArrayType) component);
                default:
                    return tsType(component);
            }
        }

        private boolean isTypedArray(String name) {
            return name.endsWith("Int32Array") || name.endsWith("Int16Array") || name.endsWith("Int8Array")
                    || name.endsWith("Uint16Array") || name.endsWith("Uint8Array") || name.endsWith("Float64Array");
        }

        private String declaredType(String qualified) {
            switch (qualified) {
                case "java.lang.String":
                    return "string";
                case "java.lang.Object":
                    return "any";
                case "java.lang.Integer":
                case "java.lang.Short":
                case "java.lang.Byte":
                case "java.lang.Long":
                case "java.lang.Character":
                    return "number";
                case "java.lang.Boolean":
                    return "boolean";
                default:
                    break;
            }
            String simple = qualified.substring(qualified.lastIndexOf('.') + 1);
            used.add(simple);
            return simple;
        }

        private String simpleFromText(String text) {
            if (text == null) {
                return "any";
            }
            if (text.endsWith("[]")) {
                return simpleFromText(text.substring(0, text.length() - 2)) + "[]";
            }
            switch (text) {
                case "int":
                case "long":
                case "short":
                case "byte":
                case "char":
                case "float":
                case "double":
                    return "number";
                case "boolean":
                    return "boolean";
                case "void":
                    return "void";
                case "String":
                    return "string";
                case "Object":
                    return "any";
                default:
                    break;
            }
            String simple = text.substring(text.lastIndexOf('.') + 1);
            used.add(simple);
            return simple;
        }

        private String defaultValue(String type) {
            switch (type) {
                case "number":
                    return "0";
                case "boolean":
                    return "false";
                default:
                    return "null!";
            }
        }

        private int precedence(Tree tree) {
            switch (tree.getKind()) {
                case PARENTHESIZED:
                    return precedence(((ParenthesizedTree) tree).getExpression());
                case CONDITIONAL_EXPRESSION:
                    return 4;
                case ASSIGNMENT:
                case PLUS_ASSIGNMENT:
                case MINUS_ASSIGNMENT:
                case MULTIPLY_ASSIGNMENT:
                case DIVIDE_ASSIGNMENT:
                case REMAINDER_ASSIGNMENT:
                case AND_ASSIGNMENT:
                case OR_ASSIGNMENT:
                case XOR_ASSIGNMENT:
                case LEFT_SHIFT_ASSIGNMENT:
                case RIGHT_SHIFT_ASSIGNMENT:
                case UNSIGNED_RIGHT_SHIFT_ASSIGNMENT:
                    return 3;
                case CONDITIONAL_OR:
                    return 5;
                case CONDITIONAL_AND:
                    return 6;
                case OR:
                    return 7;
                case XOR:
                    return 8;
                case AND:
                    return 9;
                case EQUAL_TO:
                case NOT_EQUAL_TO:
                    return 10;
                case LESS_THAN:
                case GREATER_THAN:
                case LESS_THAN_EQUAL:
                case GREATER_THAN_EQUAL:
                case INSTANCE_OF:
                    return 11;
                case LEFT_SHIFT:
                case RIGHT_SHIFT:
                case UNSIGNED_RIGHT_SHIFT:
                    return 12;
                case PLUS:
                case MINUS:
                    return 13;
                case MULTIPLY:
                case DIVIDE:
                case REMAINDER:
                    return 14;
                case PREFIX_INCREMENT:
                case PREFIX_DECREMENT:
                case UNARY_MINUS:
                case UNARY_PLUS:
                case LOGICAL_COMPLEMENT:
                case BITWISE_COMPLEMENT:
                    return 15;
                case POSTFIX_INCREMENT:
                case POSTFIX_DECREMENT:
                    return 16;
                default:
                    return 20;
            }
        }

        private String binaryOperator(Tree.Kind kind) {
            switch (kind) {
                case PLUS:
                    return "+";
                case MINUS:
                    return "-";
                case MULTIPLY:
                    return "*";
                case DIVIDE:
                    return "/";
                case REMAINDER:
                    return "%";
                case LESS_THAN:
                    return "<";
                case GREATER_THAN:
                    return ">";
                case LESS_THAN_EQUAL:
                    return "<=";
                case GREATER_THAN_EQUAL:
                    return ">=";
                case EQUAL_TO:
                    return "===";
                case NOT_EQUAL_TO:
                    return "!==";
                case CONDITIONAL_AND:
                    return "&&";
                case CONDITIONAL_OR:
                    return "||";
                case AND:
                    return "&";
                case OR:
                    return "|";
                case XOR:
                    return "^";
                case LEFT_SHIFT:
                    return "<<";
                case RIGHT_SHIFT:
                    return ">>";
                case UNSIGNED_RIGHT_SHIFT:
                    return ">>>";
                default:
                    throw new IllegalStateException("unsupported binary: " + kind);
            }
        }

    /**
     * Right hand side of `target op= value` for an integral target, or null when
     * JavaScript's own compound operator already behaves like Java's.
     */
        private String compound(TypeMirror targetType, Tree.Kind kind, String target, String value) {
            // The wrapped forms below read the target twice, which is only safe while the
            // left hand side has no side effect (every index in this game is a variable).
            if (target.contains("++") || target.contains("--") || target.contains("(")) {
                throw new IllegalStateException("compound assignment target needs hand written "
                        + "single evaluation: " + target);
            }
            if (isLong(targetType)) {
                switch (kind) {
                    case DIVIDE_ASSIGNMENT:
                        return "Math.trunc(" + target + " / " + value + ")";
                    case REMAINDER_ASSIGNMENT:
                        return "(" + target + " % " + value + ")";
                    case LEFT_SHIFT_ASSIGNMENT:
                        return inexact("(" + target + " * " + factorFor(value, 63) + ")", "long left shift");
                    case RIGHT_SHIFT_ASSIGNMENT:
                        return "Math.floor(" + target + " / " + factorFor(value, 63) + ")";
                    case AND_ASSIGNMENT:
                    case OR_ASSIGNMENT:
                    case XOR_ASSIGNMENT:
                    case UNSIGNED_RIGHT_SHIFT_ASSIGNMENT:
                        return inexact("(" + target + " " + compoundOperator(kind).replace("=", "") + " " + value + ")",
                                "long " + kind);
                    default:
                        return null;
                }
            }
            String inner = intCompound(kind, target, value);
            if (inner == null) {
                inner = "(" + target + " " + compoundOperator(kind).replace("=", "") + " " + value + ")";
            }
            return narrow(targetType, inner);
        }

        /** Scaled operand for a 64 bit shift, keeping the Java shift distance mask. */
        private static String factorFor(String value, int mask) {
            if (value.matches("-?\\d+")) {
                return String.valueOf((long) Math.pow(2, Long.parseLong(value) & mask));
            }
            return "Math.pow(2, " + value + " & " + mask + ")";
        }

        private static String intCompound(Tree.Kind kind, String target, String value) {
            switch (kind) {
                case PLUS_ASSIGNMENT:
                    return "((" + target + " + " + value + ") | 0)";
                case MINUS_ASSIGNMENT:
                    return "((" + target + " - " + value + ") | 0)";
                case MULTIPLY_ASSIGNMENT:
                    return "Math.imul(" + target + ", " + value + ")";
                case DIVIDE_ASSIGNMENT:
                    return "((" + target + " / " + value + ") | 0)";
                case REMAINDER_ASSIGNMENT:
                    return "((" + target + " % " + value + ") | 0)";
                case UNSIGNED_RIGHT_SHIFT_ASSIGNMENT:
                    return "((" + target + " >>> " + value + ") | 0)";
                default:
                    return null;
            }
        }

        private String compoundOperator(Tree.Kind kind) {
            switch (kind) {
                case PLUS_ASSIGNMENT:
                    return "+=";
                case MINUS_ASSIGNMENT:
                    return "-=";
                case MULTIPLY_ASSIGNMENT:
                    return "*=";
                case DIVIDE_ASSIGNMENT:
                    return "/=";
                case REMAINDER_ASSIGNMENT:
                    return "%=";
                case AND_ASSIGNMENT:
                    return "&=";
                case OR_ASSIGNMENT:
                    return "|=";
                case XOR_ASSIGNMENT:
                    return "^=";
                case LEFT_SHIFT_ASSIGNMENT:
                    return "<<=";
                case RIGHT_SHIFT_ASSIGNMENT:
                    return ">>=";
                case UNSIGNED_RIGHT_SHIFT_ASSIGNMENT:
                    return ">>>=";
                default:
                    throw new IllegalStateException("unsupported compound: " + kind);
            }
        }

        private static String quote(String text) {
            StringBuilder builder = new StringBuilder("\"");
            for (int i = 0; i < text.length(); i++) {
                char character = text.charAt(i);
                switch (character) {
                    case '"':
                        builder.append("\\\"");
                        break;
                    case '\\':
                        builder.append("\\\\");
                        break;
                    case '\n':
                        builder.append("\\n");
                        break;
                    case '\r':
                        builder.append("\\r");
                        break;
                    case '\t':
                        builder.append("\\t");
                        break;
                    default:
                        if (character < 0x20 || character > 0x7e) {
                            builder.append(String.format("\\u%04x", (int) character));
                        } else {
                            builder.append(character);
                        }
                }
            }
            return builder.append('"').toString();
        }
    }

    private JavaToTs() {
    }
}
