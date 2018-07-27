package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.soulcore.BlockCorporeaSoulCore;
import quaternary.incorporeal.block.soulcore.BlockEnderSoulCore;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public class IncorporeticBlocks {
	public static class RegistryNames {
		public static final String CORPOREA_INHIBITOR = "corporea_inhibitor";
		public static final String CORPOREA_LIAR = "corporea_liar";
		public static final String CORPOREA_SOLIDIFIER = "corporea_solidifier";
		public static final String CORPOREA_SPARK_TINKERER = "corporea_spark_tinkerer";
		public static final String FRAME_TINKERER = "frame_tinkerer";
		public static final String ENDER_SOUL_CORE = "ender_soul_core";
		public static final String CORPOREA_SOUL_CORE = "corporea_soul_core";
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
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		reg.register(createBlock(new BlockCorporeaInhibitor(), RegistryNames.CORPOREA_INHIBITOR));
		reg.register(createBlock(new BlockCorporeaLiar(), RegistryNames.CORPOREA_LIAR));
		reg.register(createBlock(new BlockCorporeaSolidifier(), RegistryNames.CORPOREA_SOLIDIFIER));
		reg.register(createBlock(new BlockCorporeaSparkTinkerer(), RegistryNames.CORPOREA_SPARK_TINKERER));
		reg.register(createBlock(new BlockFrameTinkerer(), RegistryNames.FRAME_TINKERER));
		reg.register(createBlock(new BlockEnderSoulCore(), RegistryNames.ENDER_SOUL_CORE));
		reg.register(createBlock(new BlockCorporeaSoulCore(), RegistryNames.CORPOREA_SOUL_CORE));
	}
	
	private static <T extends Block> T createBlock(T block, String name) {
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		block.setUnlocalizedName(Incorporeal.MODID + "." + name);
		
		return block;
	}
}
