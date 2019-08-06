package quaternary.incorporeal.feature.decorative.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.ItemsModule;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import quaternary.incorporeal.feature.decorative.block.DecorativeBlocks;

public final class DecorativeItems extends ItemsModule {
	private DecorativeItems() {
	}
	
	public static ItemBlock UNSTABLE_CUBE = null;
	public static ItemBlock RED_STRING_TILE = null;
	public static ItemBlock CORPOREA_TILE = null;
	public static ItemBlock RED_STRING_FROST = null;
	public static ItemBlock CORPOREA_BRICKS = null;
	public static ItemBlock FORGOTTEN_SHRINE = null;
	public static ItemBlock LOKIW = null;
	
	public static void registerItems(IForgeRegistry<Item> items) {
		items.registerAll(
			UNSTABLE_CUBE = itemBlock(new ItemCloth(DecorativeBlocks.UNSTABLE_CUBE)),
			RED_STRING_TILE = itemBlock(new ItemBlock(DecorativeBlocks.RED_STRING_TILE)),
			CORPOREA_TILE = itemBlock(new ItemBlock(DecorativeBlocks.CORPOREA_TILE)),
			RED_STRING_FROST = itemBlock(new ItemBlock(DecorativeBlocks.RED_STRING_FROST)),
			CORPOREA_BRICKS = itemBlock(new ItemBlock(DecorativeBlocks.CORPOREA_BRICKS)),
			FORGOTTEN_SHRINE = itemBlock(new ItemBlock(DecorativeBlocks.FORGOTTEN_SHRINE)),
			LOKIW = itemBlock(new ItemBlock(DecorativeBlocks.LOKIW))
		);
		
		//probably a better place to home these so i'm not having to borrow from the blocks class...ah well
		EtcHelpers.forEach((m) -> {
			m.nameItems();
			m.forEachItem(items::register);
		}, DecorativeBlocks.redStringTilePieces, DecorativeBlocks.corporeaBrickPieces, DecorativeBlocks.corporeaTilePieces, DecorativeBlocks.lokiwPieces);
	}
}
