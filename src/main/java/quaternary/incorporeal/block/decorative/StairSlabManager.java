package quaternary.incorporeal.block.decorative;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

public class StairSlabManager {
	public StairSlabManager(Block block) {
		this(block, block.getMaterial(null), block.getMapColor(null, null, null));
	}
	
	public StairSlabManager(Block block, Material mat, MapColor color) {
		Preconditions.checkNotNull(block, "No block!");
		Preconditions.checkNotNull(mat, "No material!");
		Preconditions.checkNotNull(color, "No color!");
		
		singleSlabBlock = new SlabbyBoi(false, block, mat, color, block instanceof BlockLokiW);
		doubleSlabBlock = new SlabbyBoi(true, block, mat, color, block instanceof BlockLokiW);
		
		slabItem = new ItemSlab(singleSlabBlock, singleSlabBlock, doubleSlabBlock);
		
		stairsBlock = new StairsBoio(block.getDefaultState(), block instanceof BlockLokiW);
		stairsItem = new ItemBlock(stairsBlock);
	}
	
	private final BlockSlab singleSlabBlock;
	private final BlockSlab doubleSlabBlock;
	private final ItemSlab slabItem;
	
	private final BlockStairs stairsBlock;
	private final ItemBlock stairsItem;
	
	public BlockSlab singleSlabBlock() {
		return singleSlabBlock;
	}
	
	public BlockSlab doubleSlabBlock() {
		return doubleSlabBlock;
	}
	
	public ItemSlab getSlabItem() {
		return slabItem;
	}
	
	public BlockStairs stairsBlock() {
		return stairsBlock;
	}
	
	public ItemBlock getStairsItem() {
		return stairsItem;
	}
	
	public static final PropertyEnum<Variant> DUMMY_VARIANT = PropertyEnum.create("variant", Variant.class);
	
	public enum Variant implements IStringSerializable {
		DUMMY;
		
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
	
	public static class SlabbyBoi extends BlockSlab {
		public SlabbyBoi(boolean isDouble, Block block, Material mat, MapColor color, boolean specialLokiWHacks) {
			super(mat, color);
			this.isDouble = isDouble;
			this.fullBlock = block;
			this.specialLokiWHacks = specialLokiWHacks;
			
			setDefaultState(getDefaultState().withProperty(DUMMY_VARIANT, Variant.DUMMY));
			
			if(specialLokiWHacks) {
				translucent = true;
				setLightOpacity(0);
			}
		}
		
		private final boolean isDouble;
		private final Block fullBlock;
		private final boolean specialLokiWHacks;
		
		@Override
		public String getTranslationKey(int meta) {
			return fullBlock.getTranslationKey() + ".slab";
		}
		
		@Override
		public boolean isDouble() {
			return isDouble;
		}
		
		@Override
		public IProperty<?> getVariantProperty() {
			return DUMMY_VARIANT;
		}
		
		@Override
		public Comparable<?> getTypeForItem(ItemStack stack) {
			return Variant.DUMMY;
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public BlockRenderLayer getRenderLayer() {
			if(specialLokiWHacks) return BlockRenderLayer.CUTOUT_MIPPED;
			else return super.getRenderLayer();
		}
		
		@Override
		public boolean isFullBlock(IBlockState state) {
			if(specialLokiWHacks) return false;
			else return super.isFullBlock(state);
		}
		
		@Override
		public boolean isFullCube(IBlockState state) {
			if(specialLokiWHacks) return false;
			else return super.isFullCube(state);
		}
		
		@Override
		public boolean isOpaqueCube(IBlockState state) {
			if(specialLokiWHacks) return false;
			else return super.isOpaqueCube(state);
		}
		
		@Override
		public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
			if(specialLokiWHacks) return false;
			else return super.doesSideBlockRendering(state, world, pos, face);
		}
		
		@Override
		protected BlockStateContainer createBlockState() {
			if(isDouble()) {
				return new BlockStateContainer(this, DUMMY_VARIANT);
			} else {
				return new BlockStateContainer(this, DUMMY_VARIANT, BlockSlab.HALF);
			}
		}
		
		@Override
		public IBlockState getStateFromMeta(int meta) {
			if(isDouble()) {
				return getDefaultState();
			} else {
				return getDefaultState().withProperty(BlockSlab.HALF, meta == 1 ? EnumBlockHalf.TOP : EnumBlockHalf.BOTTOM);
			}
		}
		
		@Override
		public int getMetaFromState(IBlockState state) {
			if(isDouble()) {
				return 0;
			} else {
				return state.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP ? 1 : 0;
			}
		}
	}
	
	public static class StairsBoio extends BlockStairs {
		//It's protected lolol
		public StairsBoio(IBlockState modelState, boolean specialLokiWHacks) {
			super(modelState);
			this.specialLokiWHacks = specialLokiWHacks;
			if(specialLokiWHacks) {
				translucent = true;
				setLightOpacity(0);
			}
		}
		
		private final boolean specialLokiWHacks;
		
		@SideOnly(Side.CLIENT)
		@Override
		public BlockRenderLayer getRenderLayer() {
			if(specialLokiWHacks) return BlockRenderLayer.CUTOUT_MIPPED;
			else return super.getRenderLayer();
		}
		
		@Override
		public boolean isFullBlock(IBlockState state) {
			if(specialLokiWHacks) return false;
			else return super.isFullBlock(state);
		}
		
		@Override
		public boolean isFullCube(IBlockState state) {
			if(specialLokiWHacks) return false;
			else return super.isFullCube(state);
		}
		
		@Override
		public boolean isOpaqueCube(IBlockState state) {
			if(specialLokiWHacks) return false;
			else return super.isOpaqueCube(state);
		}
		
		@Override
		public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
			if(specialLokiWHacks) return false;
			else return super.doesSideBlockRendering(state, world, pos, face);
		}
	}
}
