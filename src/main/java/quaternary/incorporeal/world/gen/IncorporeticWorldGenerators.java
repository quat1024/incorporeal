package quaternary.incorporeal.world.gen;

import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.incorporeal.world.gen.feature.CorporeaHut;

public final class IncorporeticWorldGenerators {
	private IncorporeticWorldGenerators() {}
	
	public static void init() {
		GameRegistry.registerWorldGenerator(new CorporeaHut(), 1);
	}
}
