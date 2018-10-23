package quaternary.incorporeal.spookyasm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import quaternary.incorporeal.spookyasm.tweaks.AbstractTweak;
import quaternary.incorporeal.spookyasm.tweaks.CorporeaInhibitorTweak;
import quaternary.incorporeal.spookyasm.tweaks.InventoryWrapTweak;
import quaternary.incorporeal.spookyasm.tweaks.EvilTweak;

import java.util.ArrayList;
import java.util.List;

public class IncorporealTransformer implements IClassTransformer, Opcodes {	
	public static final Logger LOG = LogManager.getLogger("Incorporeal ASM");
	
	private static final List<AbstractTweak> allTweaks = new ArrayList<>();
	private static final List<String> patches = new ArrayList<>();
	
	static {
		allTweaks.add(new InventoryWrapTweak());
		allTweaks.add(new CorporeaInhibitorTweak());
		allTweaks.add(new EvilTweak());
		
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
		
		ClassWriter writer = new SpookyClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		node.accept(writer);
		
		return writer.toByteArray();
	}
}
