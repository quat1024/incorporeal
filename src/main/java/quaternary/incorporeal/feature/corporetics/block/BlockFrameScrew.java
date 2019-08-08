package quaternary.incorporeal.feature.corporetics.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFrameScrew extends Block {
	public BlockFrameScrew(boolean reversed) {
		super(Material.CIRCUITS);
		this.reversed = reversed;
		
		setHardness(2.0F);
		setResistance(5F);
		
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.UP).withProperty(POWERED, false));
	}
	
	public boolean reversed;
	
	//Pasted from the End Rod
	public static final AxisAlignedBB UD_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
	public static final AxisAlignedBB NS_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1.0D);
	public static final AxisAlignedBB EW_AABB = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block otherBlock = reversed ? CorporeticsBlocks.FRAME_SCREW : CorporeticsBlocks.FRAME_SCREW_REVERSED;
		world.setBlockState(pos, otherBlock.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(POWERED, state.getValue(POWERED)));
		world.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 0.5f, 1.3f);
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		updated(world, state, pos);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		updated(world, state, pos);
	}
	
	private void updated(World world, IBlockState state, BlockPos pos) {
		boolean shouldPower = world.isBlockPowered(pos);
		boolean isPowered = state.getValue(POWERED);
		if(shouldPower != isPowered) {
			world.setBlockState(pos, state.withProperty(POWERED, shouldPower));
			
			if(shouldPower) {
				EnumFacing facing = state.getValue(FACING);
				
				for(EntityItemFrame frame : world.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos.offset(facing)))) {
					turnItemFrame(frame, reversed);
				}
				
				for(EntityItemFrame frame : world.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos.offset(facing, -1)))) {
					turnItemFrame(frame, reversed);
				}
			}
		}
	}
	
	private static void turnItemFrame(EntityItemFrame frame, boolean reversed) {
		if(!frame.getDisplayedItem().isEmpty()) {
			int nextRotation = frame.getRotation() + (reversed ? 1 : -1);
			if(nextRotation == -1) nextRotation = 7;
			frame.setItemRotation(nextRotation);
			frame.playSound(SoundEvents.ENTITY_ITEMFRAME_ROTATE_ITEM, 1, 1);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		//Need to override this because the inverted frame screw is a different block.
		return Item.getItemFromBlock(CorporeticsBlocks.FRAME_SCREW);
	}
	
	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		//Item frames rely on this returning true :eyes:
		return true;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch(state.getValue(FACING)) {
			case UP:    case DOWN:  return UD_AABB;
			case NORTH: case SOUTH: return NS_AABB;
			default: return EW_AABB;
		}
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int face = state.getValue(FACING).getIndex();
		return face | (state.getValue(POWERED) ? 8 : 0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState()
			.withProperty(POWERED, (meta & 0b1000) != 0)
			.withProperty(FACING, EnumFacing.byIndex(meta & 0b0111));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWERED);
	}
}
