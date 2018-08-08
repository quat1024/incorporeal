package quaternary.incorporeal.spookyasm.tweaks;

import com.google.common.collect.ImmutableList;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.List;

public class InventoryWrapTweak extends AbstractTweak {
	@Override
	protected String describe(String transformedName) {
		return "Adding inventory wrap hook...";
	}
	
	@Override
	protected List<String> getAffectedClassNamesImpl() {
		return ImmutableList.of("vazkii.botania.common.core.handler.InternalMethodHandler");
	}
	
	@Override
	protected void patch(ClassNode node, String transformedName) {
		for(MethodNode methodNode : node.methods) {
			if(methodNode.name.equals("wrapInventory")) {
				InsnList instructions = methodNode.instructions;
				
				//search backwards for the ifnonnull instruction
				int searchIndex = instructions.size() - 1;
				for(; instructions.get(searchIndex).getOpcode() != IFNONNULL; searchIndex--);
				
				//jump to the aload above this instruction
				searchIndex--;
				
				//add the Fun Stuff (tm) above this aload
				AbstractInsnNode insertionPoint = instructions.get(searchIndex);
				
				//atm the local variables look like:
				//0: this (maybe?)
				//1: list<invwithlocation>
				//2: the arraylist that will be returned at the end
				//3: iterator for the list
				//4: InvWithLocation
				//5: the ICorporeaSpark on that inventory
				//6: the iwrappedinventory I want to change
				
				instructions.insertBefore(insertionPoint, new VarInsnNode(ALOAD, 4));
				instructions.insertBefore(insertionPoint, new VarInsnNode(ALOAD, 5));
				instructions.insertBefore(insertionPoint, new VarInsnNode(ALOAD, 6));
				
				instructions.insertBefore(insertionPoint, new MethodInsnNode(INVOKESTATIC, hooksClass, "invWrapHook", "(Lvazkii/botania/api/corporea/InvWithLocation;Lvazkii/botania/api/corporea/ICorporeaSpark;Lvazkii/botania/api/corporea/IWrappedInventory;)Lvazkii/botania/api/corporea/IWrappedInventory;", false));
				
				instructions.insertBefore(insertionPoint, new VarInsnNode(ASTORE, 6));
				
				return;
			}
		}
	}
}
