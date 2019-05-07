package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.cygnus.IncorporeticCygnusBlocks;
import quaternary.incorporeal.block.decorative.BlockCorporeaDeco;
import quaternary.incorporeal.block.decorative.BlockForgottenShrine;
import quaternary.incorporeal.block.decorative.BlockLokiW;
import quaternary.incorporeal.block.decorative.BlockRedStringDeco;
import quaternary.incorporeal.block.decorative.BlockRedStringFrost;
import quaternary.incorporeal.block.decorative.BlockUnstableCube;
import quaternary.incorporeal.block.naturaldevices.BlockNaturalComparator;
import quaternary.incorporeal.block.naturaldevices.BlockNaturalDeviceCrop;
import quaternary.incorporeal.block.naturaldevices.BlockNaturalRepeater;
import quaternary.incorporeal.block.soulcore.BlockCorporeaSoulCore;
import quaternary.incorporeal.block.soulcore.BlockEnderSoulCore;
import quaternary.incorporeal.block.soulcore.BlockPotionSoulCore;
import quaternary.incorporeal.etc.helper.EtcHelpers;

import java.util.Objects;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
@SuppressWarnings("unused")
public final class IncorporeticBlocks {
	private IncorporeticBlocks() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String CORPOREA_INHIBITOR = "corporea_inhibitor";
		public static final String CORPOREA_SOLIDIFIER = "corporea_solidifier";
		public static final String CORPOREA_SPARK_TINKERER = "corporea_spark_tinkerer";
		public static final String CORPOREA_RETAINER_DECREMENTER = "corporea_retainer_decrementer";
		public static final String FRAME_TINKERER = "frame_tinkerer";
		
		public static final String ENDER_SOUL_CORE = "ender_soul_core";
		public static final String CORPOREA_SOUL_CORE = "corporea_soul_core";
		public static final String POTION_SOUL_CORE = "potion_soul_core";
		
		public static final String RED_STRING_LIAR = "red_string_liar";
		
		public static final String REDSTONE_ROOT_CROP = "redstone_root_crop";
		public static final String NATURAL_REPEATER = "natural_repeater";
		public static final String NATURAL_COMPARATOR = "natural_comparator";
		
		public static final String DECORATIVE_UNSTABLE_CUBE = "unstable_cube";
		public static final String DECORATIVE_RED_STRING = "red_string_deco";
		public static final String DECORATIVE_CORPOREA = "corporea_deco";
		public static final String DECORATIVE_RED_STRING_FROST = "red_string_frost_deco";
		public static final String DECORATIVE_CORPOREA_BRICK = "corporea_brick_deco";
		public static final String FORGOTTEN_SHRINE = "forgotten_shrine";
		
		public static final String DECORATIVE_LOKIW = "lokiw";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_INHIBITOR)
	public static final BlockCorporeaInhibitor CORPOREA_INHIBITOR = EtcHelpers.definitelyIsntNullISwear();
	
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
	
	@GameRegistry.ObjectHolder(RegistryNames.POTION_SOUL_CORE)
	public static final BlockPotionSoulCore POTION_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.RED_STRING_LIAR)
	public static final BlockRedStringLiar RED_STRING_LIAR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.REDSTONE_ROOT_CROP)
	public static final BlockNaturalDeviceCrop REDSTONE_ROOT_CROP = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.NATURAL_REPEATER)
	public static final BlockNaturalRepeater NATURAL_REPEATER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.NATURAL_COMPARATOR)
	public static final BlockNaturalComparator NATURAL_COMPARATOR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.DECORATIVE_UNSTABLE_CUBE)
	public static final BlockUnstableCube DECORATIVE_UNSTABLE_CUBE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.DECORATIVE_RED_STRING)
	public static final BlockRedStringDeco DECORATIVE_RED_STRING = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.DECORATIVE_CORPOREA)
	public static final BlockCorporeaDeco DECORATIVE_CORPOREA = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.DECORATIVE_RED_STRING_FROST)
	public static final BlockRedStringDeco DECORATIVE_RED_STRING_BRICK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.DECORATIVE_CORPOREA_BRICK)
	public static final BlockCorporeaDeco DECORATIVE_CORPOREA_BRICK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.FORGOTTEN_SHRINE)
	public static final BlockForgottenShrine FORGOTTEN_SHRINE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.DECORATIVE_LOKIW)
	public static final BlockLokiW DECORATIVE_LOKIW = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		reg.register(createBlock(new BlockCorporeaInhibitor(), RegistryNames.CORPOREA_INHIBITOR));
		reg.register(createBlock(new BlockCorporeaSolidifier(), RegistryNames.CORPOREA_SOLIDIFIER));
		reg.register(createBlock(new BlockCorporeaSparkTinkerer(), RegistryNames.CORPOREA_SPARK_TINKERER));
		reg.register(createBlock(new BlockCorporeaRetainerDecrementer(), RegistryNames.CORPOREA_RETAINER_DECREMENTER));
		reg.register(createBlock(new BlockFrameTinkerer(), RegistryNames.FRAME_TINKERER));
		
		reg.register(createBlock(new BlockEnderSoulCore(), RegistryNames.ENDER_SOUL_CORE));
		reg.register(createBlock(new BlockCorporeaSoulCore(), RegistryNames.CORPOREA_SOUL_CORE));
		reg.register(createBlock(new BlockPotionSoulCore(), RegistryNames.POTION_SOUL_CORE));
		
		reg.register(createSpecialSnowflakeBlock(new BlockRedStringLiar(RegistryNames.RED_STRING_LIAR)));
		
		reg.register(createBlock(new BlockNaturalDeviceCrop(), RegistryNames.REDSTONE_ROOT_CROP));
		reg.register(createBlock(new BlockNaturalRepeater(), RegistryNames.NATURAL_REPEATER));
		reg.register(createBlock(new BlockNaturalComparator(), RegistryNames.NATURAL_COMPARATOR));
		
		reg.register(createBlock(new BlockUnstableCube(), RegistryNames.DECORATIVE_UNSTABLE_CUBE));
		reg.register(createBlock(new BlockRedStringDeco(), RegistryNames.DECORATIVE_RED_STRING));
		reg.register(createBlock(new BlockCorporeaDeco(), RegistryNames.DECORATIVE_CORPOREA));
		reg.register(createBlock(new BlockRedStringFrost(), RegistryNames.DECORATIVE_RED_STRING_FROST));
		reg.register(createBlock(new BlockCorporeaDeco(), RegistryNames.DECORATIVE_CORPOREA_BRICK));
		
		reg.register(createBlock(new BlockForgottenShrine(), RegistryNames.FORGOTTEN_SHRINE));
		
		reg.register(createBlock(new BlockLokiW(), RegistryNames.DECORATIVE_LOKIW));
		
		//These have pretty big constructors so they are broken out into their own class
		IncorporeticCygnusBlocks.registerBlocks(reg);
	}
	
	private static <T extends Block> T createBlock(T block, String name) {
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		block.setTranslationKey(Incorporeal.MODID + "." + name);
		block.setCreativeTab(Incorporeal.TAB);
		return block;
	}
	
	//For the few occasions where i need to extend a botania block and they setregname in the ctor
	private static <T extends Block> T createSpecialSnowflakeBlock(T block) {
		EtcHelpers.fixRegistryNameDespacito(block);
		ResourceLocation xd = Objects.requireNonNull(block.getRegistryName());
		block.setTranslationKey(xd.getNamespace() + "." + xd.getPath());
		block.setCreativeTab(Incorporeal.TAB);
		return block;
	}
}
