package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCorporeaInhibitorRedstone extends BlockCorporeaInhibitor {
	public BlockCorporeaInhibitorRedstone() {
		setDefaultState(getDefaultState().withProperty(POWERED, false));
	}
	
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	@Override
	public boolean shouldBlockCorporea(World world, IBlockState state) {
		return state.getValue(POWERED);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		boolean shouldPower = world.isBlockPowered(pos);
		boolean isPowered = state.getValue(POWERED);
		
		if(shouldPower != isPowered) {
			world.setBlockState(pos, state.withProperty(POWERED, shouldPower));
			notifyNearbyCorporeaSparksDeferred(world, pos);
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, POWERED);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(POWERED, meta == 1);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}
}
