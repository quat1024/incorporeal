package quaternary.incorporeal.feature.soulcores.tile;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;

import javax.annotation.Nullable;

public class TileCorporeaSoulCore extends AbstractTileSoulCore {
	//This isn't used for much besides being a dummy inventory you can put corporea sparks on.
	@Override
	protected int getMaxMana() {
		return 0;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		else return super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) EmptyHandler.INSTANCE;
		else return super.getCapability(capability, facing);
	}
}
