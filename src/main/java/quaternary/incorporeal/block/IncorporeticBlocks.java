package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.naturaldevices.BlockNaturalDeviceCrop;
import quaternary.incorporeal.block.naturaldevices.BlockNaturalRepeater;
import quaternary.incorporeal.block.soulcore.BlockCorporeaSoulCore;
import quaternary.incorporeal.block.soulcore.BlockEnderSoulCore;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
@SuppressWarnings("unused")
public final class IncorporeticBlocks {
	private IncorporeticBlocks() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String CORPOREA_INHIBITOR = "corporea_inhibitor";
		public static final String CORPOREA_LIAR = "corporea_liar";
		public static final String CORPOREA_SOLIDIFIER = "corporea_solidifier";
		public static final String CORPOREA_SPARK_TINKERER = "corporea_spark_tinkerer";
		public static final String FRAME_TINKERER = "frame_tinkerer";
		
		public static final String ENDER_SOUL_CORE = "ender_soul_core";
		public static final String CORPOREA_SOUL_CORE = "corporea_soul_core";
		
		public static final String REDSTONE_ROOT_CROP = "redstone_root_crop";
		public static final String NATURAL_REPEATER = "natural_repeater";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_INHIBITOR)
	public static final BlockCorporeaInhibitor CORPOREA_INHIBITOR = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_LIAR)
	public static final BlockCorporeaLiar CORPOREA_LIAR = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SOLIDIFIER)
	public static final BlockCorporeaSolidifier CORPOREA_SOLIDIFIER = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SPARK_TINKERER)
	public static final BlockCorporeaSparkTinkerer CORPOREA_SPARK_TINKERER = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.FRAME_TINKERER)
	public static final BlockFrameTinkerer FRAME_TINKERER = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.ENDER_SOUL_CORE)
	public static final BlockEnderSoulCore ENDER_SOUL_CORE = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SOUL_CORE)
	public static final BlockCorporeaSoulCore CORPOREA_SOUL_CORE = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.REDSTONE_ROOT_CROP)
	public static final BlockNaturalDeviceCrop REDSTONE_ROOT_CROP = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.NATURAL_REPEATER)
	public static final BlockNaturalRepeater NATURAL_REPEATER = null;
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		reg.register(createBlock(new BlockCorporeaInhibitor(), RegistryNames.CORPOREA_INHIBITOR));
		reg.register(createBlock(new BlockCorporeaLiar(), RegistryNames.CORPOREA_LIAR));
		reg.register(createBlock(new BlockCorporeaSolidifier(), RegistryNames.CORPOREA_SOLIDIFIER));
		reg.register(createBlock(new BlockCorporeaSparkTinkerer(), RegistryNames.CORPOREA_SPARK_TINKERER));
		reg.register(createBlock(new BlockFrameTinkerer(), RegistryNames.FRAME_TINKERER));
		
		reg.register(createBlock(new BlockEnderSoulCore(), RegistryNames.ENDER_SOUL_CORE));
		reg.register(createBlock(new BlockCorporeaSoulCore(), RegistryNames.CORPOREA_SOUL_CORE));
		
		reg.register(createBlock(new BlockNaturalDeviceCrop(), RegistryNames.REDSTONE_ROOT_CROP));
		reg.register(createBlock(new BlockNaturalRepeater(), RegistryNames.NATURAL_REPEATER));
	}
	
	private static <T extends Block> T createBlock(T block, String name) {
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		block.setUnlocalizedName(Incorporeal.MODID + "." + name);
		
		return block;
	}
}
