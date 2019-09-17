package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
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

public class BlockStairPiece extends BlockStairs implements ILexiconable {
	public BlockStairPiece(Block block, Material mat, MapColor color, boolean cutout) {
		super(block.getDefaultState());
		this.cutout = cutout;
		this.mainBlock = block;
		if(cutout) {
			translucent = true;
			setLightOpacity(0);
		}
		
		setHarvestLevel(block.getHarvestTool(block.getDefaultState()), block.getHarvestLevel(block.getDefaultState()));
	}
	
	private final Block mainBlock;
	private final boolean cutout;
	
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag mistake) {
		mainBlock.addInformation(stack, worldIn, tooltip, mistake);
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return DecorativeLexicon.elvenDecoration;
	}
}
