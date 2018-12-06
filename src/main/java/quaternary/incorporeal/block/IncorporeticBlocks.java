package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.cygnus.IncorporeticCygnusBlocks;
import quaternary.incorporeal.block.decorative.BlockUnstableCube;
import quaternary.incorporeal.block.naturaldevices.BlockNaturalComparator;
import quaternary.incorporeal.block.naturaldevices.BlockNaturalDeviceCrop;
import quaternary.incorporeal.block.naturaldevices.BlockNaturalRepeater;
import quaternary.incorporeal.block.soulcore.BlockCorporeaSoulCore;
import quaternary.incorporeal.block.soulcore.BlockEnderSoulCore;
import quaternary.incorporeal.etc.helper.EtcHelpers;

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
		public static final String CORPOREA_RETAINER_DECREMENTER = "corporea_retainer_decrementer";
		public static final String FRAME_TINKERER = "frame_tinkerer";
		
		public static final String ENDER_SOUL_CORE = "ender_soul_core";
		public static final String CORPOREA_SOUL_CORE = "corporea_soul_core";
		
		public static final String REDSTONE_ROOT_CROP = "redstone_root_crop";
		public static final String NATURAL_REPEATER = "natural_repeater";
		public static final String NATURAL_COMPARATOR = "natural_comparator";
		
		public static final String DECORATIVE_UNSTABLE_CUBE = "unstable_cube";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_INHIBITOR)
	public static final BlockCorporeaInhibitor CORPOREA_INHIBITOR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_LIAR)
	public static final BlockCorporeaLiar CORPOREA_LIAR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SOLIDIFIER)
	public static final BlockCorporeaSolidifier CORPOREA_SOLIDIFIER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SPARK_TINKERER)
	public static final BlockCorporeaSparkTinkerer CORPOREA_SPARK_TINKERER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_RETAINER_DECREMENTER)
	public static final BlockCorporeaRetainerDecrementer CORPOREA_RETAINER_DECREMENTER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.FRAME_TINKERER)
	public static final BlockFrameTinkerer FRAME_TINKERER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.ENDER_SOUL_CORE)
	public static final BlockEnderSoulCore ENDER_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SOUL_CORE)
	public static final BlockCorporeaSoulCore CORPOREA_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.REDSTONE_ROOT_CROP)
	public static final BlockNaturalDeviceCrop REDSTONE_ROOT_CROP = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.NATURAL_REPEATER)
	public static final BlockNaturalRepeater NATURAL_REPEATER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.NATURAL_COMPARATOR)
	public static final BlockNaturalComparator NATURAL_COMPARATOR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.DECORATIVE_UNSTABLE_CUBE)
	public static final BlockUnstableCube DECORATIVE_UNSTABLE_CUBE = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		reg.register(createBlock(new BlockCorporeaInhibitor(), RegistryNames.CORPOREA_INHIBITOR));
		reg.register(createBlock(new BlockCorporeaLiar(), RegistryNames.CORPOREA_LIAR));
		reg.register(createBlock(new BlockCorporeaSolidifier(), RegistryNames.CORPOREA_SOLIDIFIER));
		reg.register(createBlock(new BlockCorporeaSparkTinkerer(), RegistryNames.CORPOREA_SPARK_TINKERER));
		reg.register(createBlock(new BlockCorporeaRetainerDecrementer(), RegistryNames.CORPOREA_RETAINER_DECREMENTER));
		reg.register(createBlock(new BlockFrameTinkerer(), RegistryNames.FRAME_TINKERER));
		
		reg.register(createBlock(new BlockEnderSoulCore(), RegistryNames.ENDER_SOUL_CORE));
		reg.register(createBlock(new BlockCorporeaSoulCore(), RegistryNames.CORPOREA_SOUL_CORE));
		
		reg.register(createBlock(new BlockNaturalDeviceCrop(), RegistryNames.REDSTONE_ROOT_CROP));
		reg.register(createBlock(new BlockNaturalRepeater(), RegistryNames.NATURAL_REPEATER));
		reg.register(createBlock(new BlockNaturalComparator(), RegistryNames.NATURAL_COMPARATOR));
		
		reg.register(createBlock(new BlockUnstableCube(), RegistryNames.DECORATIVE_UNSTABLE_CUBE));
		
		//These have pretty big constructors so they are broken out into their own class
		IncorporeticCygnusBlocks.registerBlocks(reg);
	}
	
	private static <T extends Block> T createBlock(T block, String name) {
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		block.setTranslationKey(Incorporeal.MODID + "." + name);
		block.setCreativeTab(Incorporeal.TAB);
		return block;
	}
}
