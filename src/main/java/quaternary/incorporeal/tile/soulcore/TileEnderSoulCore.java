package quaternary.incorporeal.tile.soulcore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEnderSoulCore extends AbstractTileSoulCore {
	private IItemHandler handler = EmptyHandler.INSTANCE;
	private boolean wasOnline = false;
	
	@Nullable private EntityPlayer owner = null;
	
	@Override
	protected int getMaxMana() {
		return 5000;
	}
	
	@Override
	public void update() {
		super.update();
		if(world.isRemote) return;
		
		boolean isOnline = false;
		
		if(hasOwnerProfile()) {
			for(EntityPlayer playerEnt : world.playerEntities) {
				if(playerEnt.getGameProfile().equals(getOwnerProfile())) {
					isOnline = true;
					owner = playerEnt;
					break;
				}
			}
		}
		
		if(!wasOnline && isOnline) {
			this.handler = new ManaDrainingInvWrapper(owner.getInventoryEnderChest());
		} else if (wasOnline && !isOnline){
			this.handler = EmptyHandler.INSTANCE;
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
	
	//non-static inner class
	public class ManaDrainingInvWrapper extends InvWrapper {
		public ManaDrainingInvWrapper(IInventory inv) {
			super(inv);
		}
		
		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			ItemStack sup = super.extractItem(slot, amount, simulate);
			if(!simulate) drainMana(5 * sup.getCount());
			return sup;
		}
	}
}
