package quaternary.incorporeal.spookyasm.tweaks;

import java.util.List;
import java.util.ListIterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.google.common.collect.ImmutableList;

import link.infra.technological.TechnologicalTransformer;

public class EvilTweak extends AbstractTweak {
	@Override
	protected String describe(String transformedName) {
		return "Adding evil hook...";
	}
	
	@Override
	protected List<String> computeAffectedClassNames() {
		return ImmutableList.of("vazkii.botania.client.core.handler.HUDHandler");
	}
	
	@Override
	protected void patch(ClassNode node, String transformedName) {
		for(MethodNode methodNode : node.methods) {
			if(methodNode.name.equals("renderManaBar")) {
				InsnList instructions = methodNode.instructions;
				
				ListIterator<AbstractInsnNode> inserator = instructions.iterator();
				while(inserator.hasNext()) {
					AbstractInsnNode insn = inserator.next();
					if(insn.getOpcode() != RETURN) continue;
					
					inserator.previous();
					
					inserator.add(new VarInsnNode(ILOAD, 0)); // x
					inserator.add(new VarInsnNode(ILOAD, 1)); // y
					inserator.add(new VarInsnNode(ILOAD, 4)); // mana
					inserator.add(new VarInsnNode(ILOAD, 5)); // maxMana
					inserator.add(new MethodInsnNode(INVOKESTATIC, hooksClass, "displayEvil", "(IIII)V", false));
					
					break;
				}
				return;
			}
		}
	}
}
