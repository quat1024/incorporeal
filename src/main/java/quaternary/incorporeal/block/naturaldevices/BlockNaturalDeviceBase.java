package quaternary.incorporeal.block.naturaldevices;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BlockNaturalDeviceBase extends Block {
	protected abstract boolean shouldPower(World world, BlockPos pos, IBlockState state);
	
	public static final int TICK_DELAY = 20;
	
	public BlockNaturalDeviceBase() {
		super(Material.CIRCUITS, MapColor.BROWN);
		
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIT, false));
	}
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool LIT = PropertyBool.create("lit");
	
	protected boolean canStay(World world, BlockPos pos) {
		return world.getBlockState(pos.down()).isTopSolid(); //Same logic as repeater, Dont mind the deprecation
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		if(world instanceof World && pos.getY() == neighbor.getY()) {
			IBlockState state = world.getBlockState(pos);
			neighborChanged(state, (World) world, pos, state.getBlock(), neighbor);
		}
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
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
		return side != null && (state.getValue(FACING) == side || state.getValue(FACING) == side.getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(shouldPower(world, pos, state)) {
			world.scheduleUpdate(pos, this, 1);
		}
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
