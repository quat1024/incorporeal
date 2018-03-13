package quaternary.incorporeal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import quaternary.incorporeal.block.*;
import quaternary.incorporeal.item.ItemCorporeaTicket;
import quaternary.incorporeal.item.ItemPortableCorporeaIndex;

import java.util.ArrayList;
import java.util.List;

public class Stuff {
	public static final List<Block> BLOCKS;
	public static final List<Item> ITEMS;
	
	static {
		BLOCKS = new ArrayList<>();
		
		BLOCKS.add(new BlockCorporeaLiar());
		BLOCKS.add(new BlockCorporeaSolidifier());
		BLOCKS.add(new BlockCorporeaLiquifier());
		BLOCKS.add(new BlockCorporeaSparkTinkerer());
		
		ITEMS = new ArrayList<>();
		
		for(Block b : BLOCKS) {
			ItemBlock item = new ItemBlock(b);
			item.setRegistryName(b.getRegistryName());
			ITEMS.add(item);
		}
		
		ITEMS.add(new ItemPortableCorporeaIndex());
		ITEMS.add(new ItemCorporeaTicket());
	}
}
