package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.state.BotaniaStateProps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockFrameTinkerer extends Block implements ILexiconable {
	public BlockFrameTinkerer() {
		super(Material.WOOD);
		this.setHardness(2.0F);
		this.setResistance(5F);
		this.setSoundType(SoundType.WOOD);
		
		setDefaultState(getDefaultState().withProperty(BotaniaStateProps.POWERED, false));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = player.getHeldItem(hand);
		if(!held.isEmpty()) {
			if(!world.isRemote) {
				ItemStack deposit = held.splitStack(1);
				spawnItem(world, pos, deposit);
			}
			return true;
		}
		
		return false;
	}
	
	//Powering
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		boolean shouldPower = world.getRedstonePowerFromNeighbors(pos) > 0;
		boolean isPowered = state.getValue(BotaniaStateProps.POWERED);
		if(isPowered != shouldPower) {
			world.setBlockState(pos, state.withProperty(BotaniaStateProps.POWERED, shouldPower));
			
			if(!world.isRemote && shouldPower) {
				//Find what stack to switch
				EntityItem ent = null;
				ItemStack inWorldStack = null;
				
				List<EntityItem> itemEnts = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos));
				
				if(itemEnts.isEmpty()) {
					inWorldStack = ItemStack.EMPTY;
				} else {
					Collections.shuffle(itemEnts);
					for(EntityItem e : itemEnts) {
						if(!e.getItem().isEmpty() && e.getItem().getCount() == 1) {
							ent = e;
							inWorldStack = e.getItem();
							break;
						}
					}
					
					if(ent == null) return; //couldn't find one
				}
				
				//Choose an item frame to switch with
				//Todo 1.13, keep in mind new item frame location possibilities.
				List<EntityItemFrame> nearbyFrames = new ArrayList<>(2);
				for(EnumFacing horiz : EnumFacing.HORIZONTALS) {
					nearbyFrames.addAll(world.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos.offset(horiz)))); //4 cardinal directions
				}
				if(nearbyFrames.isEmpty()) return;
				EntityItemFrame frame = nearbyFrames.get(world.rand.nextInt(nearbyFrames.size()));
				
				//Place the switched item in the item frame
				ItemStack frameStack = frame.getDisplayedItem().copy();
				frame.setDisplayedItem(inWorldStack.copy());
				inWorldStack = frameStack;
				
				if(!inWorldStack.isEmpty()) {
					//Removing an item from a frame doesn't play the sound so let's fix it!
					world.playSound(null, frame.posX, frame.posY, frame.posZ, SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1f, 1);
				}
				
				//Resolve the other item
				if(ent == null) {
					//There wasn't an item already on the plate? Put one there
					spawnItem(world, pos, inWorldStack);
				} else {
					//There was an item already on the plate?
					ent.setItem(inWorldStack);
					ent.setPickupDelay(30);
				}
			}
		}
	}
	
	private static void spawnItem(World world, BlockPos pos, ItemStack stack) {
		EntityItem ent = new EntityItem(world, pos.getX() + .5, pos.getY() + 3 / 16d, pos.getZ() + .5, stack);
		ent.motionX = 0;
		ent.motionY = 0;
		ent.motionZ = 0;
		ent.setPickupDelay(30);
		world.spawnEntity(ent);
	}
	
	//State boilerplate
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BotaniaStateProps.POWERED);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BotaniaStateProps.POWERED, meta == 1);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BotaniaStateProps.POWERED) ? 1 : 0;
	}
	
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0d, 0d, 0d, 1d, 3/16d, 1d);
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.frameTinkerer;
	}
}
