package quaternary.incorporeal.block.naturaldevices;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNaturalComparator extends BlockNaturalDeviceBase {
	@Override
	public boolean getWeakChanges(IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	@Override
	protected boolean shouldPower(World world, BlockPos pos, IBlockState state) {
		EnumFacing comparatorFacing = state.getValue(FACING);
		BlockPos behindPos = pos.offset(comparatorFacing);
		IBlockState behind = world.getBlockState(behindPos);
		
		if(behind.hasComparatorInputOverride()) {
			return behind.getComparatorInputOverride(world, behindPos) > 0;
		} else if(behind.getBlock() instanceof BlockRedstoneWire) {
			return behind.getValue(BlockRedstoneWire.POWER) > 0;
		} else if(world.getRedstonePower(behindPos, comparatorFacing) > 0) {
			return true;
		} else if(behind.isNormalCube()){
			BlockPos behind2Pos = pos.offset(comparatorFacing, 2);
			IBlockState behind2 = world.getBlockState(behind2Pos);
			if(behind2.hasComparatorInputOverride()) {
				return behind2.getComparatorInputOverride(world, behind2Pos) > 0;
				//No item frame stuff since it would just always be on lol.
			}
		}
		
		return false;
	}
}
