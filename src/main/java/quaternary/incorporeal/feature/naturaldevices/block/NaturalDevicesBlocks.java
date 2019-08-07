package quaternary.incorporeal.feature.naturaldevices.block;

import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.BlocksModule;

public final class NaturalDevicesBlocks extends BlocksModule {
	public static final class RegistryNames {
		private RegistryNames() {
		}
		
		public static final String REDSTONE_ROOT_CROP = "redstone_root_crop";
		public static final String NATURAL_REPEATER = "natural_repeater";
		public static final String NATURAL_COMPARATOR = "natural_comparator";
	}
	
	public static BlockNaturalDeviceCrop REDSTONE_ROOT_CROP = null;
	public static BlockNaturalRepeater NATURAL_REPEATER = null;
	public static BlockNaturalComparator NATURAL_COMPARATOR = null;
	
	public static void register(IForgeRegistry<Block> blocks) {
		blocks.registerAll(
			REDSTONE_ROOT_CROP = name(new BlockNaturalDeviceCrop(), RegistryNames.REDSTONE_ROOT_CROP),
			NATURAL_REPEATER = name(new BlockNaturalRepeater(), RegistryNames.NATURAL_REPEATER),
			NATURAL_COMPARATOR = name(new BlockNaturalComparator(), RegistryNames.NATURAL_COMPARATOR)
		);
	}
}
