package quaternary.incorporeal.feature.naturaldevices.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.ItemsModule;
import quaternary.incorporeal.feature.naturaldevices.block.NaturalDevicesBlocks;

public final class NaturalDevicesItems extends ItemsModule {
	private NaturalDevicesItems() {
	}
	
	public static ItemBlock NATURAL_REPEATER = null;
	public static ItemBlock NATURAL_COMPARATOR = null;
	
	public static void register(IForgeRegistry<Item> items) {
		items.registerAll(
			NATURAL_REPEATER = itemBlock(new ItemBlock(NaturalDevicesBlocks.NATURAL_REPEATER)),
			NATURAL_COMPARATOR = itemBlock(new ItemBlock(NaturalDevicesBlocks.NATURAL_COMPARATOR))
		);
	}
}
