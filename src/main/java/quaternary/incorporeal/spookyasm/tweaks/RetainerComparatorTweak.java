package quaternary.incorporeal.spookyasm.tweaks;

import com.google.common.collect.ImmutableList;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.List;

public class RetainerComparatorTweak extends AbstractTweak {
	@Override
	protected String describe(String transformedName) {
		return "Tweaking the corporea retainer signal strength...";
	}
	
	@Override
	protected List<String> getAffectedClassNamesImpl() {
		return ImmutableList.of("vazkii.botania.common.block.corporea.BlockCorporeaRetainer");
	}
	
	@Override
	protected void patch(ClassNode node, String transformedName) {
		for(MethodNode method : node.methods) {
			if(method.name.equals("getComparatorInputOverride") || method.name.equals("func_180641_l")) {
				method.instructions.clear();
				
				method.instructions.add(new VarInsnNode(ALOAD, 1));
				method.instructions.add(new VarInsnNode(ALOAD, 2));
				method.instructions.add(new VarInsnNode(ALOAD, 3));
				
				MethodInsnNode hook = new MethodInsnNode(INVOKESTATIC, hooksClass, "retainerComparatorHook", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)I", false);
				
				method.instructions.add(hook);
				method.instructions.add(new InsnNode(IRETURN));
				break;
			}
		}
	}
}
