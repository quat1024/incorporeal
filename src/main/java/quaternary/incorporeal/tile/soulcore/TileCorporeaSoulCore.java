package quaternary.incorporeal.tile.soulcore;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import quaternary.incorporeal.etc.DummyItemHandler;

import javax.annotation.Nullable;

public class TileCorporeaSoulCore extends AbstractTileSoulCore {
	IItemHandler handler = new DummyItemHandler();
	
	//This isn't used for much besides being a dummy inventory you can put corporea sparks on.
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		else return super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) handler;
		else return super.getCapability(capability, facing);
	}
}
