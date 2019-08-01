package quaternary.incorporeal.core.sortme;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.core.BlocksModule;
import quaternary.incorporeal.feature.decorative.block.DecorativeBlocks;
import quaternary.incorporeal.feature.decorative.block.pieces.PieceManager;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public class IncorporeticFluffBlocks extends IncorporeticBlocks {
	public static class RegistryNames {
		private static final String STAIRS = "_stairs";
		private static final String DOUBLE_SLAB = "_double_slab";
		private static final String SINGLE_SLAB = "_slab";
		
		public static final String CORPOREA_TILE_STAIRS = IncorporeticBlocks.RegistryNames.CORPOREA_TILE + STAIRS;
		public static final String CORPOREA_TILE_DOUBLE_SLAB = IncorporeticBlocks.RegistryNames.CORPOREA_TILE + DOUBLE_SLAB;
		public static final String CORPOREA_TILE_SINGLE_SLAB = IncorporeticBlocks.RegistryNames.CORPOREA_TILE + SINGLE_SLAB;
		
		public static final String CORPOREA_BRICKS_STAIRS = IncorporeticBlocks.RegistryNames.CORPOREA_BRICKS + STAIRS;
		public static final String CORPOREA_BRICKS_DOUBLE_SLAB = IncorporeticBlocks.RegistryNames.CORPOREA_BRICKS + DOUBLE_SLAB;
		public static final String CORPOREA_BRICKS_SINGLE_SLAB = IncorporeticBlocks.RegistryNames.CORPOREA_BRICKS + SINGLE_SLAB;
		
		public static final String RED_STRING_TILE_STAIRS = IncorporeticBlocks.RegistryNames.RED_STRING_TILE + STAIRS;
		public static final String RED_STRING_TILE_DOUBLE_SLAB = IncorporeticBlocks.RegistryNames.RED_STRING_TILE + DOUBLE_SLAB;
		public static final String RED_STRING_TILE_SINGLE_SLAB = IncorporeticBlocks.RegistryNames.RED_STRING_TILE + SINGLE_SLAB;
		
		public static final String LOKIW_STAIRS = IncorporeticBlocks.RegistryNames.LOKIW + STAIRS;
		public static final String LOKIW_DOUBLE_SLAB = IncorporeticBlocks.RegistryNames.LOKIW + DOUBLE_SLAB;
		public static final String LOKIW_SINGLE_SLAB = IncorporeticBlocks.RegistryNames.LOKIW + SINGLE_SLAB;
	}
	
	public static PieceManager corporeaTile;
	public static PieceManager corporeaBricks;
	public static PieceManager redStringTile;
	public static PieceManager lokiW;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_TILE_STAIRS)
	public static BlockStairs CORPOREA_TILE_STAIRS = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_TILE_DOUBLE_SLAB)
	public static BlockSlab CORPOREA_TILE_DOUBLE_SLAB = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_TILE_SINGLE_SLAB)
	public static BlockSlab CORPOREA_TILE_SINGLE_SLAB = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_BRICKS_STAIRS)
	public static BlockStairs CORPOREA_BRICKS_STAIRS = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_BRICKS_DOUBLE_SLAB)
	public static BlockSlab CORPOREA_BRICKS_DOUBLE_SLAB = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_BRICKS_SINGLE_SLAB)
	public static BlockSlab CORPOREA_BRICKS_SINGLE_SLAB = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.RED_STRING_TILE_STAIRS)
	public static BlockStairs RED_STRING_TILE_STAIRS = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.RED_STRING_TILE_DOUBLE_SLAB)
	public static BlockSlab RED_STRING_TILE_DOUBLE_SLAB = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.RED_STRING_TILE_SINGLE_SLAB)
	public static BlockSlab RED_STRING_TILE_SINGLE_SLAB = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.LOKIW_STAIRS)
	public static BlockStairs LOKIW_STAIRS = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.LOKIW_DOUBLE_SLAB)
	public static BlockSlab LOKIW_DOUBLE_SLAB = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.LOKIW_SINGLE_SLAB)
	public static BlockSlab LOKIW_SINGLE_SLAB = null;
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		corporeaTile = new PieceManager(DecorativeBlocks.CORPOREA_TILE);
		corporeaBricks = new PieceManager(DecorativeBlocks.CORPOREA_BRICKS);
		redStringTile = new PieceManager(DecorativeBlocks.RED_STRING_TILE);
		lokiW = new PieceManager(DecorativeBlocks.LOKIW);
		
		reg.registerAll(
			BlocksModule.name(CORPOREA_TILE_STAIRS = corporeaTile.stairsBlock(), RegistryNames.CORPOREA_TILE_STAIRS),
			BlocksModule.name(CORPOREA_TILE_DOUBLE_SLAB = corporeaTile.doubleSlabBlock(), RegistryNames.CORPOREA_TILE_DOUBLE_SLAB),
			BlocksModule.name(CORPOREA_TILE_SINGLE_SLAB = corporeaTile.singleSlabBlock(), RegistryNames.CORPOREA_TILE_SINGLE_SLAB),
			
			BlocksModule.name(CORPOREA_BRICKS_STAIRS = corporeaBricks.stairsBlock(), RegistryNames.CORPOREA_BRICKS_STAIRS),
			BlocksModule.name(CORPOREA_BRICKS_DOUBLE_SLAB = corporeaBricks.doubleSlabBlock(), RegistryNames.CORPOREA_BRICKS_DOUBLE_SLAB),
			BlocksModule.name(CORPOREA_BRICKS_SINGLE_SLAB = corporeaBricks.singleSlabBlock(), RegistryNames.CORPOREA_BRICKS_SINGLE_SLAB),
			
			BlocksModule.name(RED_STRING_TILE_STAIRS = redStringTile.stairsBlock(), RegistryNames.RED_STRING_TILE_STAIRS),
			BlocksModule.name(RED_STRING_TILE_DOUBLE_SLAB = redStringTile.doubleSlabBlock(), RegistryNames.RED_STRING_TILE_DOUBLE_SLAB),
			BlocksModule.name(RED_STRING_TILE_SINGLE_SLAB = redStringTile.singleSlabBlock(), RegistryNames.RED_STRING_TILE_SINGLE_SLAB),
			
			BlocksModule.name(LOKIW_STAIRS = lokiW.stairsBlock(), RegistryNames.LOKIW_STAIRS),
			BlocksModule.name(LOKIW_DOUBLE_SLAB = lokiW.doubleSlabBlock(), RegistryNames.LOKIW_DOUBLE_SLAB),
			BlocksModule.name(LOKIW_SINGLE_SLAB = lokiW.singleSlabBlock(), RegistryNames.LOKIW_SINGLE_SLAB)
		);
	}
}
