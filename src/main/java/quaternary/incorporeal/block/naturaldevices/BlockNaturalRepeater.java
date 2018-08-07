package quaternary.incorporeal.block.naturaldevices;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockNaturalRepeater extends AbstractBlockNaturalDevice {
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
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if(state.getValue(LIT)) {
			//Based on copypasta from BlockRedstoneRepeater
			EnumFacing enumfacing = state.getValue(FACING);
			double d0 = (double)((float)pos.getX() + 0.5F) + (double)(rand.nextFloat() - 0.5F) * 0.2D;
			double d1 = (double)((float)pos.getY() + 0.4F) + (double)(rand.nextFloat() - 0.5F) * 0.2D;
			double d2 = (double)((float)pos.getZ() + 0.5F) + (double)(rand.nextFloat() - 0.5F) * 0.2D;
			float f = (rand.nextBoolean() ? -5 : 7) / 16f;
			
			double d3 = (double)(f * (float)enumfacing.getFrontOffsetX());
			double d4 = (double)(f * (float)enumfacing.getFrontOffsetZ());
			world.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
		}
	}
}
