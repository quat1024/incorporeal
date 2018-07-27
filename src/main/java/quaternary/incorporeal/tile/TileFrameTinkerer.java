package quaternary.incorporeal.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TileFrameTinkerer extends TileEntity {
	private ItemStack heldStack = ItemStack.EMPTY;
	
	public TileFrameTinkerer() {}
	
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	public void doSwap() {
		if(world.isRemote) return;
		
		List<EntityItemFrame> nearbyFrames = new ArrayList<>(2);
		for(EnumFacing horiz : EnumFacing.HORIZONTALS) {
			nearbyFrames.addAll(world.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos.offset(horiz))));
		}
		
		EntityItemFrame frame = nearbyFrames.get(world.rand.nextInt(nearbyFrames.size()));
		
		//Perform switch
		ItemStack frameStack = frame.getDisplayedItem().copy();
		frame.setDisplayedItem(heldStack.copy());
		heldStack = frameStack;
	}
	
	public void copyAndSetItem(ItemStack stack) {
		heldStack = stack.copy();
	}
	
	public ItemStack getItemCopy() {
		return heldStack.copy();
	}
}
