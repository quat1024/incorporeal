package quaternary.incorporeal.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticFluffBlocks;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public class IncorporeticFluffItems extends IncorporeticItems {
	@GameRegistry.ObjectHolder(IncorporeticFluffBlocks.RegistryNames.CORPOREA_TILE_STAIRS)
	public static ItemBlock CORPOREA_TILE_STAIRS = null;
	
	@GameRegistry.ObjectHolder(IncorporeticFluffBlocks.RegistryNames.CORPOREA_TILE_SINGLE_SLAB)
	public static ItemSlab CORPOREA_TILE_SLAB = null;
	
	@GameRegistry.ObjectHolder(IncorporeticFluffBlocks.RegistryNames.CORPOREA_BRICKS_STAIRS)
	public static ItemBlock CORPOREA_BRICKS_STAIRS = null;
	
	@GameRegistry.ObjectHolder(IncorporeticFluffBlocks.RegistryNames.CORPOREA_BRICKS_SINGLE_SLAB)
	public static ItemSlab CORPOREA_BRICKS_SLAB = null;
	
	@GameRegistry.ObjectHolder(IncorporeticFluffBlocks.RegistryNames.RED_STRING_TILE_STAIRS)
	public static ItemBlock RED_STRING_TILE_STAIRS = null;
	
	@GameRegistry.ObjectHolder(IncorporeticFluffBlocks.RegistryNames.RED_STRING_TILE_SINGLE_SLAB)
	public static ItemSlab RED_STRING_TILE_SLAB = null;
	
	@GameRegistry.ObjectHolder(IncorporeticFluffBlocks.RegistryNames.LOKIW_STAIRS)
	public static ItemBlock LOKIW_STAIRS = null;
	
	@GameRegistry.ObjectHolder(IncorporeticFluffBlocks.RegistryNames.LOKIW_SINGLE_SLAB)
	public static ItemSlab LOKIW_SLAB = null;
	
	public static void registerItems(IForgeRegistry<Item> reg) {
		reg.registerAll(
			createItem(CORPOREA_TILE_STAIRS = IncorporeticFluffBlocks.corporeaTile.getStairsItem(), IncorporeticFluffBlocks.RegistryNames.CORPOREA_TILE_STAIRS),
			createItem(CORPOREA_TILE_SLAB = IncorporeticFluffBlocks.corporeaTile.getSlabItem(), IncorporeticFluffBlocks.RegistryNames.CORPOREA_TILE_SINGLE_SLAB),
			
			createItem(CORPOREA_BRICKS_STAIRS = IncorporeticFluffBlocks.corporeaBricks.getStairsItem(), IncorporeticFluffBlocks.RegistryNames.CORPOREA_BRICKS_STAIRS),
			createItem(CORPOREA_BRICKS_SLAB = IncorporeticFluffBlocks.corporeaBricks.getSlabItem(), IncorporeticFluffBlocks.RegistryNames.CORPOREA_BRICKS_SINGLE_SLAB),
			
			createItem(RED_STRING_TILE_STAIRS = IncorporeticFluffBlocks.redStringTile.getStairsItem(), IncorporeticFluffBlocks.RegistryNames.RED_STRING_TILE_STAIRS),
			createItem(RED_STRING_TILE_SLAB = IncorporeticFluffBlocks.redStringTile.getSlabItem(), IncorporeticFluffBlocks.RegistryNames.RED_STRING_TILE_SINGLE_SLAB),
			
			createItem(LOKIW_STAIRS = IncorporeticFluffBlocks.lokiW.getStairsItem(), IncorporeticFluffBlocks.RegistryNames.LOKIW_STAIRS),
			createItem(LOKIW_SLAB = IncorporeticFluffBlocks.lokiW.getSlabItem(), IncorporeticFluffBlocks.RegistryNames.LOKIW_SINGLE_SLAB)
		);
	}
}
