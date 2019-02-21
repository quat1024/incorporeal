package quaternary.incorporeal.tile.soulcore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEnderSoulCore extends AbstractTileSoulCore implements ITickable {
	private IItemHandler handler = EmptyHandler.INSTANCE;
	private boolean wasOnline = false;
	
	@Override
	public void update() {
		boolean isOnline = false;
		EntityPlayer owner = null;
		
		for(EntityPlayer playerEnt : world.playerEntities) {
			if(playerEnt.getGameProfile().equals(getOwnerProfile())) {
				isOnline = true;
				owner = playerEnt;
				break;
			}
		}
		
		if(!wasOnline && isOnline) {
			this.handler = new InvWrapper(owner.getInventoryEnderChest());
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
}
