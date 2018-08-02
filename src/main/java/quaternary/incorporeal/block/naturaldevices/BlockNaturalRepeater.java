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

public class BlockNaturalRepeater extends Block {
	public static final int TICK_DELAY = 20;
	
	public BlockNaturalRepeater() {
		super(Material.CIRCUITS);
		
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIT, false));
	}
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool LIT = PropertyBool.create("lit");
	
	protected boolean canStay(World world, BlockPos pos) {
		return world.getBlockState(pos.down()).isTopSolid(); //Same logic as repeater, Dont mind the deprecation
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if(canStay(world, pos)) {
			updateState(world, pos, state);
		} else {
			world.destroyBlock(pos, true);
			for(EnumFacing facing : EnumFacing.VALUES) {
				world.notifyNeighborsOfStateChange(pos.offset(facing), this, false);
			}
		}
	}
	
	protected void updateState(World world, BlockPos pos, IBlockState state) {
		boolean shouldPower = shouldPower(world, pos, state);
		boolean isPowered = state.getValue(LIT);
		if(shouldPower != isPowered && !world.isBlockTickPending(pos, this)) {
			world.updateBlockTick(pos, this, TICK_DELAY, isPowered ? -2 : -1);
		}
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		boolean stillShouldPower = shouldPower(world, pos, state);
		boolean isPowered = state.getValue(LIT);
		
		if(isPowered && !stillShouldPower) {
			world.setBlockState(pos, state.withProperty(LIT, false));
		} else if(!isPowered) {
			world.setBlockState(pos, state.withProperty(LIT, true));
			if(stillShouldPower) {
				world.updateBlockTick(pos, this, TICK_DELAY, -1);
			}
		}
	}
	
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
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getWeakPower(world, pos, side);
	}
	
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(LIT) && (state.getValue(FACING) == side) ? 15 : 0;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(shouldPower(world, pos, state)) {
			world.scheduleUpdate(pos, this, 1);
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
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, LIT);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int rotationMask = state.getValue(FACING).getHorizontalIndex() & 0b0011;
		int litMask = (state.getValue(LIT) ? 0b0100 : 0);
		return rotationMask | litMask;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 0b0011)).withProperty(LIT, (meta & 0b0100) != 0);
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror) {
		return state.withRotation(mirror.toRotation(state.getValue(FACING)));
	}
}
