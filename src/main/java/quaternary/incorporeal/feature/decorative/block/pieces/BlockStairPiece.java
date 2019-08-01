package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStairPiece extends BlockStairs {
	public BlockStairPiece(IBlockState modelState, boolean specialLokiWHacks) {
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
