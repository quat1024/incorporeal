package quaternary.incorporeal.spookyasm.tweaks;

import com.google.common.collect.ImmutableList;
import org.objectweb.asm.tree.*;

import java.util.List;

public class TerribleHorribleNoGoodVeryBadAwfulCorporeaIndexInputHandlerTweak extends AbstractTweak {
	@Override
	protected String describe(String transformedName) {
		switch(transformedName) {
			case tileClass:
				return "Patching the corporea index input handler factory...";
			case handlerClass:
				return "Making it possible to extend the corporea index input handler...";
			case selfPatchClass:
				return "Performing an extremely hacky self-patch...";
			default: return "?!";
		}
	}
	
	@Override
	protected List<String> getAffectedClassNamesImpl() {
		return ImmutableList.of(tileClass, handlerClass, selfPatchClass);
	}
	
	private static final String tileClass = "vazkii.botania.common.block.tile.corporea.TileCorporeaIndex";
	private static final String handlerClass ="vazkii.botania.common.block.tile.corporea.TileCorporeaIndex$InputHandler";
	private static final String selfPatchClass = "quaternary.incorporeal.etc.HackyCorporeaInputHandler";
	private static final String sacrificialClass = "quaternary.incorporeal.etc.SacrificialGoat"; 
	
	private static final String handlerClassInternal = handlerClass.replace('.', '/');
	private static final String selfPatchClassInternal = selfPatchClass.replace('.', '/');
	private static final String sacrificialClassInternal = sacrificialClass.replace('.', '/');
	
	@Override
	protected void patch(ClassNode node, String transformedName) {
		if(transformedName.equals(tileClass)) {
			for(MethodNode method : node.methods) {
				if(method.name.equals("getInputHandler")) {
					for(int insIndex = 0; insIndex < method.instructions.size(); insIndex++) {
						AbstractInsnNode ins = method.instructions.get(insIndex);
						if(ins.getOpcode() == NEW) {
							//Make it make a new instance of my custom handler class, instead of the regular one. 
							TypeInsnNode t = (TypeInsnNode) ins;
							t.desc = selfPatchClassInternal;
						} else if(ins.getOpcode() == INVOKESPECIAL) {
							//Patch the call to <init> to call my constructor instead.
							MethodInsnNode m = (MethodInsnNode) ins;
							m.owner = selfPatchClassInternal;
						}
					}
					
					return;
				}
			}
		} else if(transformedName.equals(handlerClass)) {
			//Make it not final.
			node.access &= ~ACC_FINAL;
			
			for(MethodNode method : node.methods) {
				if(method.name.equals("<init>")) {
					//Neuter the constructor so it doesn't register itself as a corporea input autocomplete handler.
					//My subclass will do it too, and it will just make a mess, registering twice.
					for(int insIndex = 0; insIndex < method.instructions.size(); insIndex++) {
						AbstractInsnNode ins = method.instructions.get(insIndex);
						if(ins.getOpcode() == INVOKESTATIC) {
							method.instructions.remove(ins);
						}
					}
				} else if(method.name.equals("onChatMessage")) {
					//Strip the SubscribeEvent annotation
					method.visibleAnnotations.clear();
				}
			}
		} else if(transformedName.equals(selfPatchClass)) {
			//Make the hacky class extend the regular corporea input handler
			//I can't do this from regular Java, since it's final and would lead to a compile error!
			node.superName = handlerClassInternal;
			
			//Patch any lingering references to the sacrificial class to the regular input handler.
			//Hopefully this gets them all.
			for(MethodNode method : node.methods) {
				for(int insIndex = 0; insIndex < method.instructions.size(); insIndex++) {
					AbstractInsnNode ins = method.instructions.get(insIndex);
					if(ins instanceof TypeInsnNode) {
						TypeInsnNode t = (TypeInsnNode) ins;
						if(t.desc.equals(sacrificialClassInternal)) t.desc = handlerClassInternal;
					} else if (ins instanceof MethodInsnNode) {
						MethodInsnNode m = (MethodInsnNode) ins;
						if(m.owner.equals(sacrificialClassInternal)) m.owner = handlerClassInternal;
					}
				}
			}
		}
	}
}
