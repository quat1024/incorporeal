package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.feature.decorative.lexicon.DecorativeLexicon;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public abstract class BlockSlabPiece extends BlockSlab implements ILexiconable {
	public BlockSlabPiece(Block block, Material mat, MapColor color, boolean cutout) {
		super(mat, color);
		this.mainBlock = block;
		this.cutout = cutout;
		
		setHardness(block.blockHardness);
		setResistance(block.getExplosionResistance(null) * 5 / 3f);
		setSoundType(block.getSoundType());
		setHarvestLevel(block.getHarvestTool(block.getDefaultState()), block.getHarvestLevel(block.getDefaultState()));
		
		if(!isDouble()) {
			setDefaultState(getDefaultState().withProperty(HALF, EnumBlockHalf.BOTTOM));
		}
		setDefaultState(getDefaultState().withProperty(DUMMY_VARIANT, Variant.DUMMY));
		
		if(cutout) {
			translucent = true;
			setLightOpacity(0);
		}
		
		this.fullBlock = isDouble();
	}
	
	public static final PropertyEnum<Variant> DUMMY_VARIANT = PropertyEnum.create("variant", Variant.class);
	
	public final Block mainBlock;
	public final boolean cutout;
	
	@Override
	public String getTranslationKey(int meta) {
		return mainBlock.getTranslationKey() + ".slab";
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
		if(cutout) return BlockRenderLayer.CUTOUT_MIPPED;
		else return super.getRenderLayer();
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		if(cutout) return false;
		else return super.isFullBlock(state);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		if(cutout) return false;
		else return super.isFullCube(state);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		if(cutout) return false;
		else return super.isOpaqueCube(state);
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		if(cutout) return false;
		else return super.doesSideBlockRendering(state, world, pos, face);
	}
	
	@Override
	public boolean getUseNeighborBrightness(IBlockState state) {
		return !isDouble();
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag mistake) {
		mainBlock.addInformation(stack, worldIn, tooltip, mistake);
	}
	
	public static class Half extends BlockSlabPiece {
		public Half(Block block, Material mat, MapColor color, boolean cutout) {
			super(block, mat, color, cutout);
		}
		
		@Override
		public boolean isDouble() {
			return false;
		}
	}
	
	public static class Double extends BlockSlabPiece {
		public Double(Block block, Material mat, MapColor color, boolean cutout) {
			super(block, mat, color, cutout);
		}
		
		@Override
		public boolean isDouble() {
			return true;
		}
	}
	
	public enum Variant implements IStringSerializable {
		DUMMY;
		
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return DecorativeLexicon.elvenDecoration;
	}
}
