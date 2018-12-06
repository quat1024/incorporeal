package quaternary.incorporeal.block.naturaldevices;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class AbstractBlockNaturalDevice extends Block implements ILexiconable {
	protected abstract boolean shouldPower(World world, BlockPos pos, IBlockState state);
	
	public static final int TICK_DELAY = 20;
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool LIT = PropertyBool.create("lit");
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0, 1/16d, 0, 1, 3/16d, 1);
	
	public AbstractBlockNaturalDevice() {
		super(Material.CIRCUITS, MapColor.BROWN);
		setHardness(0);
		setSoundType(SoundType.WOOD);
		setLightOpacity(0);
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIT, false));
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return canStay(world, pos);
	}
	
	protected boolean canStay(World world, BlockPos pos) {
		Material underMaterial = world.getBlockState(pos.down()).getMaterial();
		return underMaterial == Material.GROUND || underMaterial == Material.GRASS;
		//return world.getBlockState(pos.down()).isTopSolid(); //Same logic as repeater
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
			/*
			for(EnumFacing facing : EnumFacing.VALUES) {
				world.notifyNeighborsOfStateChange(pos.offset(facing), this, false);
			}*/
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
			notifyAround(world, pos, state);
			
		} else if(!isPowered) {
			world.setBlockState(pos, state.withProperty(LIT, true));
			notifyAround(world, pos, state);
			
			if(stillShouldPower) {
				world.updateBlockTick(pos, this, TICK_DELAY, -1);
			}
		}
	}
	
	private void notifyAround(World world, BlockPos pos, IBlockState state) {
		EnumFacing inputSide = state.getValue(FACING);
		BlockPos notifyAroundPos = pos.offset(inputSide.getOpposite());
		world.neighborChanged(notifyAroundPos, this, pos);
		world.notifyNeighborsOfStateExcept(notifyAroundPos, this, inputSide);
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
		return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 0b0011)).withProperty(LIT, (meta & 0b0100) != 0);
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
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.naturalDevices;
	}
}
