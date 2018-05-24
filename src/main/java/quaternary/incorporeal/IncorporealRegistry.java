package quaternary.incorporeal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.incorporeal.block.*;
import quaternary.incorporeal.item.ItemCorporeaTicket;
import quaternary.incorporeal.item.ItemPortableCorporeaIndex;
import quaternary.incorporeal.tile.*;

import java.util.ArrayList;
import java.util.List;

public class IncorporealRegistry {
	public static final List<Block> BLOCKS = new ArrayList<>();
	public static final List<Item> ITEMS = new ArrayList<>();
	
	static {		
		BLOCKS.add(new BlockCorporeaLiar());
		BLOCKS.add(new BlockCorporeaSolidifier());
		BLOCKS.add(new BlockCorporeaSparkTinkerer());
		BLOCKS.add(new BlockFrameTinkerer());
		
		for(Block b : BLOCKS) {
			ItemBlock item = new ItemBlock(b);
			item.setRegistryName(b.getRegistryName());
			ITEMS.add(item);
		}
		
		ITEMS.add(new ItemPortableCorporeaIndex());
		ITEMS.add(new ItemCorporeaTicket());
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileCorporeaLiar.class, Incorporeal.MODID + ":liar");
		GameRegistry.registerTileEntity(TileCorporeaSolidifier.class, Incorporeal.MODID + ":solidifier");
		GameRegistry.registerTileEntity(TileCorporeaSparkTinkerer.class, Incorporeal.MODID + ":corporea_tinkerer");
		GameRegistry.registerTileEntity(TileFrameTinkerer.class, Incorporeal.MODID + ":frame_tinkerer");
	}
}
