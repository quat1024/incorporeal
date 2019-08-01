package quaternary.incorporeal.feature.decorative.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.ItemsModule;
import quaternary.incorporeal.feature.decorative.block.DecorativeBlocks;
import quaternary.incorporeal.feature.decorative.block.pieces.PieceManager;

public class DecorativeItems extends ItemsModule {
	private DecorativeItems() {}
	
	public static ItemBlock UNSTABLE_CUBE = totallyNotNull();
	public static ItemBlock RED_STRING_TILE = totallyNotNull();
	public static ItemBlock CORPOREA_TILE = totallyNotNull();
	public static ItemBlock RED_STRING_FROST = totallyNotNull();
	public static ItemBlock CORPOREA_BRICKS = totallyNotNull();
	public static ItemBlock FORGOTTEN_SHRINE = totallyNotNull();
	public static ItemBlock LOKIW = totallyNotNull();
	
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
		finishManagers(items,
			DecorativeBlocks.redStringTilePieces,
			DecorativeBlocks.corporeaBrickPieces,
			DecorativeBlocks.corporeaTilePieces,
			DecorativeBlocks.lokiwPieces
		);
	}
	
	private static void finishManagers(IForgeRegistry<Item> items, PieceManager...mgrs) {
		for(PieceManager mgr : mgrs) {
			mgr.nameItems();
			mgr.forEachItem(items::register);
		}
	}
}
