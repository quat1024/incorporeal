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
public class IncorporeticBlocks {
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
		
		public static final String UNSTABLE_CUBE = "unstable_cube";
		public static final String RED_STRING_TILE = "red_string_deco";
		public static final String CORPOREA_TILE = "corporea_deco";
		public static final String RED_STRING_FROST = "red_string_frost_deco";
		public static final String CORPOREA_BRICKS = "corporea_brick_deco";
		public static final String FORGOTTEN_SHRINE = "forgotten_shrine";
		
		public static final String LOKIW = "lokiw";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_INHIBITOR)
	public static BlockCorporeaInhibitor CORPOREA_INHIBITOR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SOLIDIFIER)
	public static BlockCorporeaSolidifier CORPOREA_SOLIDIFIER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SPARK_TINKERER)
	public static BlockCorporeaSparkTinkerer CORPOREA_SPARK_TINKERER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_RETAINER_DECREMENTER)
	public static BlockCorporeaRetainerDecrementer CORPOREA_RETAINER_DECREMENTER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.FRAME_TINKERER)
	public static BlockFrameTinkerer FRAME_TINKERER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.ENDER_SOUL_CORE)
	public static BlockEnderSoulCore ENDER_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_SOUL_CORE)
	public static BlockCorporeaSoulCore CORPOREA_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.POTION_SOUL_CORE)
	public static BlockPotionSoulCore POTION_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.RED_STRING_LIAR)
	public static BlockRedStringLiar RED_STRING_LIAR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.REDSTONE_ROOT_CROP)
	public static BlockNaturalDeviceCrop REDSTONE_ROOT_CROP = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.NATURAL_REPEATER)
	public static BlockNaturalRepeater NATURAL_REPEATER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.NATURAL_COMPARATOR)
	public static BlockNaturalComparator NATURAL_COMPARATOR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.UNSTABLE_CUBE)
	public static BlockUnstableCube UNSTABLE_CUBE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.RED_STRING_TILE)
	public static BlockRedStringDeco RED_STRING_TILE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_TILE)
	public static BlockCorporeaDeco CORPOREA_TILE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.RED_STRING_FROST)
	public static BlockRedStringDeco RED_STRING_FROST = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_BRICKS)
	public static BlockCorporeaDeco CORPOREA_BRICKS = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.FORGOTTEN_SHRINE)
	public static BlockForgottenShrine FORGOTTEN_SHRINE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.LOKIW)
	public static BlockLokiW LOKIW = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		reg.registerAll(
			CORPOREA_INHIBITOR = createBlock(new BlockCorporeaInhibitor(), RegistryNames.CORPOREA_INHIBITOR),
			CORPOREA_SOLIDIFIER = createBlock(new BlockCorporeaSolidifier(), RegistryNames.CORPOREA_SOLIDIFIER),
			CORPOREA_SPARK_TINKERER = createBlock(new BlockCorporeaSparkTinkerer(), RegistryNames.CORPOREA_SPARK_TINKERER),
			CORPOREA_RETAINER_DECREMENTER = createBlock(new BlockCorporeaRetainerDecrementer(), RegistryNames.CORPOREA_RETAINER_DECREMENTER),
			FRAME_TINKERER = createBlock(new BlockFrameTinkerer(), RegistryNames.FRAME_TINKERER),
			
			ENDER_SOUL_CORE = createBlock(new BlockEnderSoulCore(), RegistryNames.ENDER_SOUL_CORE),
			CORPOREA_SOUL_CORE = createBlock(new BlockCorporeaSoulCore(), RegistryNames.CORPOREA_SOUL_CORE),
			POTION_SOUL_CORE = createBlock(new BlockPotionSoulCore(), RegistryNames.POTION_SOUL_CORE),
			
			RED_STRING_LIAR = createSpecialSnowflakeBlock(new BlockRedStringLiar(RegistryNames.RED_STRING_LIAR)),
			
			REDSTONE_ROOT_CROP = createBlock(new BlockNaturalDeviceCrop(), RegistryNames.REDSTONE_ROOT_CROP),
			NATURAL_REPEATER = createBlock(new BlockNaturalRepeater(), RegistryNames.NATURAL_REPEATER),
			NATURAL_COMPARATOR = createBlock(new BlockNaturalComparator(), RegistryNames.NATURAL_COMPARATOR),
			
			UNSTABLE_CUBE = createBlock(new BlockUnstableCube(), RegistryNames.UNSTABLE_CUBE),
			RED_STRING_TILE = createBlock(new BlockRedStringDeco(), RegistryNames.RED_STRING_TILE),
			CORPOREA_TILE = createBlock(new BlockCorporeaDeco(), RegistryNames.CORPOREA_TILE),
			RED_STRING_FROST = createBlock(new BlockRedStringFrost(), RegistryNames.RED_STRING_FROST),
			CORPOREA_BRICKS = createBlock(new BlockCorporeaDeco(), RegistryNames.CORPOREA_BRICKS),
			
			FORGOTTEN_SHRINE = createBlock(new BlockForgottenShrine(), RegistryNames.FORGOTTEN_SHRINE),
			
			LOKIW = createBlock(new BlockLokiW(), RegistryNames.LOKIW)
		);
		
		IncorporeticCygnusBlocks.registerBlocks(reg);
		IncorporeticFluffBlocks.registerBlocks(reg);
	}
	
	protected static <T extends Block> T createBlock(T block, String name) {
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		block.setTranslationKey(Incorporeal.MODID + "." + name);
		block.setCreativeTab(Incorporeal.TAB);
		return block;
	}
	
	//For the few occasions where i need to extend a botania block and they setregname in the ctor
	protected static <T extends Block> T createSpecialSnowflakeBlock(T block) {
		EtcHelpers.fixRegistryNameDespacito(block);
		ResourceLocation xd = Objects.requireNonNull(block.getRegistryName());
		block.setTranslationKey(xd.getNamespace() + "." + xd.getPath());
		block.setCreativeTab(Incorporeal.TAB);
		return block;
	}
}
