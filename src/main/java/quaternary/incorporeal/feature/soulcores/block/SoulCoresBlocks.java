package quaternary.incorporeal.feature.soulcores.block;

import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.BlocksModule;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;

public final class SoulCoresBlocks extends BlocksModule {
	private SoulCoresBlocks() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String ENDER_SOUL_CORE = "ender_soul_core";
		public static final String CORPOREA_SOUL_CORE = "corporea_soul_core";
		public static final String POTION_SOUL_CORE = "potion_soul_core";
	}
	
	public static BlockEnderSoulCore ENDER_SOUL_CORE = totallyNotNull();
	public static BlockCorporeaSoulCore CORPOREA_SOUL_CORE = totallyNotNull();
	public static BlockPotionSoulCore POTION_SOUL_CORE = totallyNotNull();
	
	public static void registerBlocks(IForgeRegistry<Block> blocks) {
		blocks.registerAll(
			ENDER_SOUL_CORE = name(new BlockEnderSoulCore(), RegistryNames.ENDER_SOUL_CORE),
			CORPOREA_SOUL_CORE = name(new BlockCorporeaSoulCore(), RegistryNames.CORPOREA_SOUL_CORE),
			POTION_SOUL_CORE = name(new BlockPotionSoulCore(), RegistryNames.POTION_SOUL_CORE)
		);
	}
}
