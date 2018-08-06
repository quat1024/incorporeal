package quaternary.incorporeal.block.naturaldevices;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNaturalRepeater extends BlockNaturalDeviceBase {
	@Override
	protected boolean shouldPower(World world, BlockPos pos, IBlockState state) {
		//Modified paste from redstonediode
		EnumFacing enumfacing = state.getValue(FACING);
		BlockPos blockpos = pos.offset(enumfacing);
		int i = world.getRedstonePower(blockpos, enumfacing);
		
		if (i >= 15) {
			return true;
		} else {
			IBlockState iblockstate = world.getBlockState(blockpos);
			if(iblockstate.getBlock() == Blocks.REDSTONE_WIRE) {
				return iblockstate.getValue(BlockRedstoneWire.POWER) > 0;
			} else return i > 0;
		}
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		//Paste from redstonediode
		EnumFacing enumfacing = state.getValue(FACING);
		BlockPos blockpos = pos.offset(enumfacing.getOpposite());
		if(net.minecraftforge.event.ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.getBlockState(pos), java.util.EnumSet.of(enumfacing.getOpposite()), false).isCanceled())
			return;
		worldIn.neighborChanged(blockpos, this, pos);
		worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
	}
	
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		//Modified paste from redstonediode
		if (state.getValue(LIT)) {
			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
		}
		
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
	}
}
