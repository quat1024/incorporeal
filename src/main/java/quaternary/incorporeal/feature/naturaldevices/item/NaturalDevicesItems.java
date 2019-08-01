package quaternary.incorporeal.feature.naturaldevices.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.ItemsModule;
import quaternary.incorporeal.feature.naturaldevices.block.NaturalDevicesBlocks;

public class NaturalDevicesItems extends ItemsModule {
	private NaturalDevicesItems() {}
	
	public static ItemBlock NATURAL_REPEATER = totallyNotNull();
	public static ItemBlock NATURAL_COMPARATOR = totallyNotNull();
	
	public static void registerItems(IForgeRegistry<Item> items) {
		items.registerAll(
			NATURAL_REPEATER = itemBlock(new ItemBlock(NaturalDevicesBlocks.NATURAL_REPEATER)),
			NATURAL_COMPARATOR = itemBlock(new ItemBlock(NaturalDevicesBlocks.NATURAL_COMPARATOR))
		);
	}
}
