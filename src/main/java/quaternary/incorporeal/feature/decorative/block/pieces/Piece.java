package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import quaternary.incorporeal.core.BlocksModule;
import quaternary.incorporeal.core.ItemsModule;

import java.util.function.Consumer;

//kinda sketchy "pieces" system to quickly add new stairs/slabs/cruft, inspired a bit by extrapieces
public abstract class Piece<B extends Block, I extends Item> {
	public abstract void forEachBlock(Consumer<? super B> func);
	
	public abstract void forEachItem(Consumer<? super I> func);
	
	public Piece(Block derivedBlock, String type) {
		this.derivedBlock = derivedBlock;
		this.type = type;
	}
	
	public final Block derivedBlock;
	public final String type;
	
	protected String parentName() {
		return derivedBlock.getRegistryName().getPath();
	}
	
	//must override if you use more than one block
	public void nameBlocks() {
		forEachBlock(b -> BlocksModule.name(b, parentName() + "_" + type));
	}
	
	//must override if you don't use itemblocks
	public void nameItems() {
		forEachItem(i -> ItemsModule.itemBlock((ItemBlock) i));
	}
	
	public static class Wall extends Piece<BlockWall, ItemBlock> {
		public Wall(Block block, Material mat, MapColor color, boolean cutout) {
			super(block, "wall");
			
			wallBlock = new BlockWallPiece(block, mat, color, cutout);
			Piece.copyProps(block, wallBlock);
			
			wallItem = new ItemBlock(wallBlock);
		}
		
		public BlockWall wallBlock;
		public ItemBlock wallItem;
		
		@Override
		public void forEachBlock(Consumer<? super BlockWall> func) {
			func.accept(wallBlock);
		}
		
		@Override
		public void forEachItem(Consumer<? super ItemBlock> func) {
			func.accept(wallItem);
		}
	}
	
	public static class Fence extends Piece<Block, ItemBlock> {
		public Fence(Block block, Material mat, MapColor color, boolean cutout) {
			super(block, "fence");
			
			fenceBlock = new BlockFencePiece(block, mat, color, cutout);
			fenceGateBlock = new BlockFenceGatePiece(block, mat, color, cutout);
			
			Piece.copyProps(block, fenceBlock, fenceGateBlock);
			
			fenceItem = new ItemBlock(fenceBlock);
			fenceGateItem = new ItemBlock(fenceGateBlock);
		}
		
		public BlockFence fenceBlock;
		public BlockFenceGate fenceGateBlock;
		
		public ItemBlock fenceItem;
		public ItemBlock fenceGateItem;
		
		@Override
		public void forEachBlock(Consumer<? super Block> func) {
			func.accept(fenceBlock);
			func.accept(fenceGateBlock);
		}
		
		@Override
		public void forEachItem(Consumer<? super ItemBlock> func) {
			func.accept(fenceItem);
			func.accept(fenceGateItem);
		}
		
		@Override
		public void nameBlocks() {
			BlocksModule.name(fenceBlock, parentName() + "_fence");
			BlocksModule.name(fenceGateBlock, parentName() + "_fence_gate");
		}
	}
	
	public static class Slab extends Piece<BlockSlabPiece, ItemSlab> {
		public Slab(Block block, Material mat, MapColor color, boolean cutout) {
			super(block, "slab");
			
			singleSlabBlock = new BlockSlabPiece.Half(block, mat, color, cutout);
			doubleSlabBlock = new BlockSlabPiece.Double(block, mat, color, cutout);
			
			slabItem = new ItemSlab(singleSlabBlock, singleSlabBlock, doubleSlabBlock);
		}
		
		public BlockSlabPiece singleSlabBlock;
		public BlockSlabPiece doubleSlabBlock;
		public ItemSlab slabItem;
		
		@Override
		public void forEachBlock(Consumer<? super BlockSlabPiece> func) {
			func.accept(singleSlabBlock);
			func.accept(doubleSlabBlock);
		}
		
		@Override
		public void forEachItem(Consumer<? super ItemSlab> func) {
			func.accept(slabItem);
		}
		
		@Override
		public void nameBlocks() {
			BlocksModule.name(singleSlabBlock, parentName() + "_slab");
			BlocksModule.name(doubleSlabBlock, parentName() + "_double_slab");
		}
	}
	
	public static class Stair extends Piece<BlockStairPiece, ItemBlock> {
		public Stair(Block block, Material mat, MapColor color, boolean cutout) {
			super(block, "stairs");
			
			stairBlock = new BlockStairPiece(block, mat, color, cutout);
			stairItem = new ItemBlock(stairBlock);
		}
		
		public BlockStairPiece stairBlock;
		public ItemBlock stairItem;
		
		@Override
		public void forEachBlock(Consumer<? super BlockStairPiece> func) {
			func.accept(stairBlock);
		}
		
		@Override
		public void forEachItem(Consumer<? super ItemBlock> func) {
			func.accept(stairItem);
		}
	}
	
	private static void copyProps(Block src, Block... dst) {
		for(Block block : dst) {
			block.setHardness(src.blockHardness);
			block.setResistance(src.getExplosionResistance(null) * 5 / 3f);
		}
	}
}
