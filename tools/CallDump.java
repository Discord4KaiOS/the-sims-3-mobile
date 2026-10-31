import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.nio.file.*;
import java.util.*;

/** Dumps "class#method  name  desc" for every call into the game's own classes. */
public class CallDump {
    public static void main(String[] args) throws Exception {
        for (String file : args) {
            ClassNode cn = new ClassNode();
            new ClassReader(Files.readAllBytes(Paths.get(file))).accept(cn, 0);
            String cls = cn.name.substring(cn.name.lastIndexOf('/') + 1);
            for (MethodNode mn : cn.methods) {
                for (AbstractInsnNode insn : mn.instructions) {
                    if (insn instanceof MethodInsnNode) {
                        MethodInsnNode c = (MethodInsnNode) insn;
                        if (!c.owner.startsWith("com/ea/sims3") && !c.owner.equals("java/lang/Object")) {
                            continue;
                        }
                        String owner = c.owner.substring(c.owner.lastIndexOf('/') + 1);
                        System.out.println(cls + "#" + mn.name + mn.desc + "  " + owner + "." + c.name + "  " + c.desc);
                    }
                }
            }
        }
    }
}
