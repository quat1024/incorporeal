package quaternary.incorporeal.tile.soulcore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEnderSoulCore extends AbstractTileSoulCore implements ITickable {
	private IItemHandler handler;
	boolean wasOnline = false;
	
	@Override
	public void update() {
		boolean isOnline = false;
		EntityPlayer owner = null;
		
		//TODO: Would be nice if it worked crossdimensionally.
		for(EntityPlayer playerEnt : world.playerEntities) {
			if(playerEnt.getUniqueID().equals(ownerUUID)) {
				isOnline = true;
				owner = playerEnt;
				break;
			}
		}
		
		if(!wasOnline && isOnline) {
			this.handler = new InvWrapper(owner.getInventoryEnderChest());
		} else if (wasOnline && !isOnline){
			this.handler = new DummyItemHandler();
		}
		
		wasOnline = isOnline;
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		else return super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) handler;
		else return super.getCapability(capability, facing);
	}
	
	/**
	 * Fake item handler used for when players are not currently in this dimension
	 * Better than just not providing a handler at all, so things like corporea sparks don't fall off.
	 * */
	public static class DummyItemHandler implements IItemHandler {
		@Override
		public int getSlots() {
			return 0;
		}
		
		@Nonnull
		@Override
		public ItemStack getStackInSlot(int slot) {
			return ItemStack.EMPTY;
		}
		
		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			return stack;
		}
		
		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return ItemStack.EMPTY;
		}
		
		@Override
		public int getSlotLimit(int slot) {
			return 0;
		}
	}
}
