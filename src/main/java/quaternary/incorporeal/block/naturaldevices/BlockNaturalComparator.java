package quaternary.incorporeal.block.naturaldevices;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockNaturalComparator extends AbstractBlockNaturalDevice {
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
			} else if(behind2.getMaterial() == Material.AIR) {
				EntityItemFrame frameyboi = findItemFrame(world, comparatorFacing, behind2Pos);
				return frameyboi != null && !frameyboi.getDisplayedItem().isEmpty();
			}
		}
		
		return false;
	}
	
	//CopyPaste from BlockRedstoneComparator
	@Nullable
	private EntityItemFrame findItemFrame(World world, final EnumFacing facing, BlockPos pos) {
		List<EntityItemFrame> list = world.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 1), (double)(pos.getZ() + 1)), doot -> doot != null && doot.getHorizontalFacing() == facing);
		return list.size() == 1 ? list.get(0) : null;
	}
}
