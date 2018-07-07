package quaternary.incorporeal.spookyasm.tweaks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import quaternary.incorporeal.spookyasm.IncorporealTransformer;

import java.util.List;

public abstract class AbstractTweak implements Opcodes {
	protected abstract String describe(String transformedName);
	protected abstract List<String> getAffectedClassNamesImpl();
	protected abstract void patch(ClassNode node, String transformedName);
	
	List<String> cache = null;
	public List<String> getAffectedClassNames() {
		if(cache == null) cache = getAffectedClassNamesImpl();
		return cache;
	}
	
	public void accept(ClassNode node, String transformedName) {
		if(getAffectedClassNames().contains(transformedName)) {
			IncorporealTransformer.LOG.info(describe(transformedName));
			patch(node, transformedName);
		}
	}
	
	protected static final String hooksClass = "quaternary/incorporeal/spookyasm/Hooks";
}
