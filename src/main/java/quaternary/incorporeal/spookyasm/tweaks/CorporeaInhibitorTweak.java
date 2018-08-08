package quaternary.incorporeal.spookyasm.tweaks;

import com.google.common.collect.ImmutableList;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.List;
import java.util.ListIterator;

public class CorporeaInhibitorTweak extends AbstractTweak {
	@Override
	protected String describe(String transformedName) {
		return "Adding hook for Corporea Inhibitor...";
	}
	
	@Override
	protected List<String> getAffectedClassNamesImpl() {
		return ImmutableList.of("vazkii.botania.common.entity.EntityCorporeaSpark");
	}
	
	@Override
	protected void patch(ClassNode node, String transformedName) {
		for(MethodNode method : node.methods) {
			if(method.name.equals("getNearbySparks")) {
				InsnList instructions = method.instructions;
				
				ListIterator<AbstractInsnNode> inserator = instructions.iterator();
				while(inserator.hasNext()) {
					AbstractInsnNode insn = inserator.next();
					if(insn.getOpcode() != ARETURN) continue;
					
					inserator.previous();
					
					inserator.add(new VarInsnNode(ALOAD, 0));
					inserator.add(new MethodInsnNode(INVOKESTATIC, hooksClass, "nearbyCorporeaSparkHook", "(Ljava/util/List;Lvazkii/botania/common/entity/EntityCorporeaSpark;)Ljava/util/List;", false));
					
					return;
				}
			}
		}
	}
}
