package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public class IncorporeticBlocks {
	public static class RegistryNames {
		public static final String CORPOREA_INHIBITOR = "corporea_inhibitor";
		public static final String CORPOREA_LIAR = "corporea_liar";
		public static final String CORPOREA_SOLIDIFIER = "corporea_solidifier";
		public static final String CORPOREA_SPARK_TINKERER = "corporea_spark_tinkerer";
		public static final String FRAME_TINKERER = "frame_tinkerer";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_INHIBITOR)
	public static final Block CORPOREA_INHIBITOR = Blocks.AIR;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_LIAR)
	public static final Block CORPOREA_LIAR = Blocks.AIR;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SOLIDIFIER)
	public static final Block CORPOREA_SOLIDIFIER = Blocks.AIR;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SPARK_TINKERER)
	public static final Block CORPOREA_SPARK_TINKERER = Blocks.AIR;
	
	@GameRegistry.ObjectHolder(RegistryNames.FRAME_TINKERER)
	public static final Block FRAME_TINKERER = Blocks.AIR;
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		reg.register(createBlock(new BlockCorporeaInhibitor(), RegistryNames.CORPOREA_INHIBITOR));
		reg.register(createBlock(new BlockCorporeaLiar(), RegistryNames.CORPOREA_LIAR));
		reg.register(createBlock(new BlockCorporeaSolidifier(), RegistryNames.CORPOREA_SOLIDIFIER));
		reg.register(createBlock(new BlockCorporeaSparkTinkerer(), RegistryNames.CORPOREA_SPARK_TINKERER));
		reg.register(createBlock(new BlockFrameTinkerer(), RegistryNames.FRAME_TINKERER));
	}
	
	private static <T extends Block> T createBlock(T block, String name) {
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		block.setUnlocalizedName(Incorporeal.MODID + "." + name);
		
		return block;
	}
}
