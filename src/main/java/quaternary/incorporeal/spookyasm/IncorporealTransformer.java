package quaternary.incorporeal.spookyasm;

import com.google.common.collect.ImmutableList;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import quaternary.incorporeal.Incorporeal;

import java.util.List;

public class IncorporealTransformer implements IClassTransformer, Opcodes {
	
	String internalMethodHandlerName = "vazkii.botania.common.core.handler.InternalMethodHandler";
	
	List<String> patches = ImmutableList.of(internalMethodHandlerName);
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(!patches.contains(transformedName)) return basicClass;
		
		ClassReader reader = new ClassReader(basicClass);
		ClassNode node = new ClassNode();
		reader.accept(node, 0);
		
		if(transformedName.equals(internalMethodHandlerName)) {
			Incorporeal.LOGGER.info("Patching Botania's internal method handler...");
			patchInternalMethodHandler(node);
		}
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		node.accept(writer);
		
		Incorporeal.LOGGER.info("Finished patching !");
		
		return writer.toByteArray();
	}
	
	public void patchInternalMethodHandler(ClassNode node) {
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
				
				instructions.insertBefore(insertionPoint, new MethodInsnNode(INVOKESTATIC, "quaternary/incorporeal/spookyasm/Hooks", "invWrapHook", "(Lvazkii/botania/api/corporea/InvWithLocation;Lvazkii/botania/api/corporea/ICorporeaSpark;Lvazkii/botania/api/corporea/IWrappedInventory;)Lvazkii/botania/api/corporea/IWrappedInventory;", false));
				
				instructions.insertBefore(insertionPoint, new VarInsnNode(ASTORE, 6));
			}
		}
	}
}
