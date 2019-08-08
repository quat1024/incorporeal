package quaternary.incorporeal.feature.corporetics.block;

import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.BlocksModule;

public final class CorporeticsBlocks extends BlocksModule {
	private CorporeticsBlocks() {
	}
	
	public static final class RegistryNames {
		private RegistryNames() {
		}
		
		public static final String CORPOREA_INHIBITOR = "corporea_inhibitor";
		public static final String CORPOREA_SOLIDIFIER = "corporea_solidifier";
		public static final String CORPOREA_SPARK_TINKERER = "corporea_spark_tinkerer";
		public static final String CORPOREA_RETAINER_DECREMENTER = "corporea_retainer_decrementer";
		public static final String FRAME_TINKERER = "frame_tinkerer";
		public static final String FRAME_SCREW = "frame_screw";
		public static final String FRAME_SCREW_REVERSED = "frame_screw_reversed";
		public static final String RED_STRING_LIAR = "red_string_liar";
	}
	
	public static BlockCorporeaInhibitor CORPOREA_INHIBITOR = null;
	public static BlockCorporeaSolidifier CORPOREA_SOLIDIFIER = null;
	public static BlockCorporeaSparkTinkerer CORPOREA_SPARK_TINKERER = null;
	public static BlockCorporeaRetainerDecrementer CORPOREA_RETAINER_DECREMENTER = null;
	public static BlockFrameTinkerer FRAME_TINKERER = null;
	public static BlockFrameScrew FRAME_SCREW = null;
	public static BlockFrameScrew FRAME_SCREW_REVERSED = null;
	public static BlockRedStringLiar RED_STRING_LIAR = null;
	
	public static void register(IForgeRegistry<Block> blocks) {
		blocks.registerAll(
			CORPOREA_INHIBITOR = name(new BlockCorporeaInhibitor(), RegistryNames.CORPOREA_INHIBITOR),
			CORPOREA_SOLIDIFIER = name(new BlockCorporeaSolidifier(), RegistryNames.CORPOREA_SOLIDIFIER),
			CORPOREA_SPARK_TINKERER = name(new BlockCorporeaSparkTinkerer(), RegistryNames.CORPOREA_SPARK_TINKERER),
			CORPOREA_RETAINER_DECREMENTER = name(new BlockCorporeaRetainerDecrementer(), RegistryNames.CORPOREA_RETAINER_DECREMENTER),
			FRAME_TINKERER = name(new BlockFrameTinkerer(), RegistryNames.FRAME_TINKERER),
			FRAME_SCREW = name(new BlockFrameScrew(false), RegistryNames.FRAME_SCREW),
			FRAME_SCREW_REVERSED = name(new BlockFrameScrew(true), RegistryNames.FRAME_SCREW_REVERSED),
			RED_STRING_LIAR = specialSnowflakeRemap(new BlockRedStringLiar(RegistryNames.RED_STRING_LIAR))
		);
	}
}
