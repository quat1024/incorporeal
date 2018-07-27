package quaternary.incorporeal.spookyasm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import quaternary.incorporeal.spookyasm.tweaks.*;

import java.util.ArrayList;
import java.util.List;

public class IncorporealTransformer implements IClassTransformer, Opcodes {	
	public static final Logger LOG = LogManager.getLogger("Incorporeal ASM");
	
	private static final List<AbstractTweak> allTweaks = new ArrayList<>();
	private static final List<String> patches = new ArrayList<>();
	
	static {
		allTweaks.add(new InventoryWrapTweak());
		allTweaks.add(new CorporeaInhibitorTweak());
		allTweaks.add(new RetainerComparatorTweak());
		allTweaks.add(new TerribleHorribleNoGoodVeryBadAwfulCorporeaIndexInputHandlerTweak());
		
		for(AbstractTweak tweak : allTweaks) {
			patches.addAll(tweak.getAffectedClassNames());
		}
	}
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(!patches.contains(transformedName)) return basicClass;
		
		ClassReader reader = new ClassReader(basicClass);
		ClassNode node = new ClassNode();
		reader.accept(node, 0);
		
		for(AbstractTweak tweak : allTweaks) {
			tweak.accept(node, transformedName);
		}
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		node.accept(writer);
		
		return writer.toByteArray();
	}
}
