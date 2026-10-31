import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;
import java.nio.file.*;
import java.util.*;

/**
 * Reports every call that passes a null constant as an argument, together with
 * the exact descriptor the original bytecode invoked. Those call sites need an
 * explicit cast once the code is expressed as Java source, because Java would
 * otherwise bind the null to the most derived overload.
 */
public class NullArgs {
    public static void main(String[] args) throws Exception {
        for (String file : args) {
            ClassNode cn = new ClassNode();
            new ClassReader(Files.readAllBytes(Paths.get(file))).accept(cn, 0);
            for (MethodNode mn : cn.methods) {
                if (mn.instructions.size() == 0) {
                    continue;
                }
                Analyzer<SourceValue> analyzer = new Analyzer<>(new SourceInterpreter());
                Frame<SourceValue>[] frames;
                try {
                    frames = analyzer.analyze(cn.name, mn);
                } catch (AnalyzerException e) {
                    continue;
                }
                for (int i = 0; i < mn.instructions.size(); i++) {
                    AbstractInsnNode insn = mn.instructions.get(i);
                    if (!(insn instanceof MethodInsnNode)) {
                        continue;
                    }
                    MethodInsnNode call = (MethodInsnNode) insn;
                    Frame<SourceValue> f = frames[i];
                    if (f == null) {
                        continue;
                    }
                    Type[] params = Type.getArgumentTypes(call.desc);
                    int nulls = 0;
                    List<Integer> nullArgs = new ArrayList<>();
                    for (int p = 0; p < params.length; p++) {
                        int slot = f.getStackSize() - params.length + p;
                        SourceValue v = f.getStack(slot);
                        boolean isNull = false;
                        for (AbstractInsnNode prod : v.insns) {
                            if (prod.getOpcode() == Opcodes.ACONST_NULL) {
                                isNull = true;
                            } else if (prod.getOpcode() == Opcodes.CHECKCAST) {
                                AbstractInsnNode prev = prod.getPrevious();
                                if (prev != null && prev.getOpcode() == Opcodes.ACONST_NULL) {
                                    isNull = true;
                                }
                            }
                        }
                        if (isNull) {
                            nulls++;
                            nullArgs.add(p);
                        }
                    }
                    if (nulls > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int p : nullArgs) {
                            sb.append(p).append(':').append(params[p].getClassName()).append(' ');
                        }
                        System.out.println(cn.name.substring(cn.name.lastIndexOf('/') + 1)
                                + " " + mn.name + mn.desc
                                + "  =>  " + call.owner.substring(call.owner.lastIndexOf('/') + 1)
                                + "." + call.name + call.desc
                                + "   nullArgs[" + sb.toString().trim() + "]");
                    }
                }
            }
        }
    }
}
