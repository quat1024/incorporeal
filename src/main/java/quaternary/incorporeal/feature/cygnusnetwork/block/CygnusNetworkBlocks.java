package quaternary.incorporeal.feature.cygnusnetwork.block;

import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.BlocksModule;

public final class CygnusNetworkBlocks extends BlocksModule {
	private CygnusNetworkBlocks() { }
	
	public static final class RegistryNames {
		private RegistryNames() { }
		
		public static final String WORD = "cygnus_word";
		public static final String CRYSTAL_CUBE = "cygnus_crystal_cube";
		public static final String FUNNEL = "cygnus_funnel";
		public static final String RETAINER = "cygnus_retainer";
	}
	
	public static BlockCygnusWord WORD = null;
	public static BlockCygnusCrystalCube CRYSTAL_CUBE = null;
	public static BlockCygnusFunnel FUNNEL = null;
	public static BlockCygnusRetainer RETAINER = null;
	
	public static void register(IForgeRegistry<Block> blocks) {
		blocks.registerAll(
			WORD = name(new BlockCygnusWord(), RegistryNames.WORD),
			CRYSTAL_CUBE = name(new BlockCygnusCrystalCube(), RegistryNames.CRYSTAL_CUBE),
			FUNNEL = name(new BlockCygnusFunnel(), RegistryNames.FUNNEL),
			RETAINER = name(new BlockCygnusRetainer(), RegistryNames.RETAINER)
		);
	}
}
