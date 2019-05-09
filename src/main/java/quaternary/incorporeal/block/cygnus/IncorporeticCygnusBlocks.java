package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticBlocks;
import quaternary.incorporeal.etc.helper.EtcHelpers;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public class IncorporeticCygnusBlocks extends IncorporeticBlocks {
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String WORD = "cygnus_word";
		public static final String CRYSTAL_CUBE = "cygnus_crystal_cube";
		
		public static final String FUNNEL = "cygnus_funnel";
		public static final String RETAINER = "cygnus_retainer";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD)
	public static BlockCygnusWord WORD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CRYSTAL_CUBE)
	public static BlockCygnusCrystalCube CRYSTAL_CUBE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.FUNNEL)
	public static BlockCygnusFunnel FUNNEL = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.RETAINER)
	public static BlockCygnusRetainer RETAINER = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		reg.registerAll(
			WORD = createBlock(new BlockCygnusWord(), RegistryNames.WORD),
			CRYSTAL_CUBE = createBlock(new BlockCygnusCrystalCube(), RegistryNames.CRYSTAL_CUBE),
			FUNNEL = createBlock(new BlockCygnusFunnel(), RegistryNames.FUNNEL),
			RETAINER = createBlock(new BlockCygnusRetainer(), RegistryNames.RETAINER)
		);
	}
}
