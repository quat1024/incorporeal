package quaternary.incorporeal.feature.cygnusnetwork.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusDatatypeHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.cap.IncorporeticCygnusCapabilities;
import vazkii.botania.common.block.ModBlocks;

import javax.annotation.Nullable;

public class TileCygnusRetainer extends TileCygnusBase implements ICygnusFunnelable {
	@Nullable
	private Object retained;
	private int comparator;
	
	@Override
	public boolean canGiveCygnusItem() {
		return true;
	}
	
	@Override
	public boolean canAcceptCygnusItem() {
		return true;
	}
	
	@Nullable
	@Override
	public Object giveItemToCygnus() {
		return retained;
	}
	
	@Override
	public void acceptItemFromCygnus(Object item) {
		if(item == null || world.getBlockState(pos.down()).getBlock() == ModBlocks.manaVoid) {
			retained = null;
			comparator = 0;
		} else {
			retained = item;
			comparator = CygnusDatatypeHelpers.forClass(item.getClass()).toComparatorUnchecked(item);
		}
		
		updateStuff();
	}
	
	private void updateStuff() {
		//it is 3am
		markDirty();
		IBlockState ok = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, ok, ok, 2);
		world.updateComparatorOutputLevel(pos, getBlockType());
	}
	
	public void wand() {
		acceptItemFromCygnus(null);
	}
	
	public boolean hasRetainedObject() {
		return retained != null;
	}
	
	@Nullable
	public Object getRetainedObject() {
		return retained;
	}
	
	public int getComparator() {
		return comparator;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(retained != null) {
			NBTTagCompound retainedNBT = new NBTTagCompound();
			CygnusDatatypeHelpers.writeToNBT(retainedNBT, retained);
			nbt.setTag("Retained", retainedNBT);
		}
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("Retained")) {
			retained = CygnusDatatypeHelpers.readFromNBT(nbt.getCompoundTag("Retained"));
			comparator = CygnusDatatypeHelpers.forClass(retained.getClass()).toComparatorUnchecked(retained);
		} else retained = null;
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 6969, getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		updateStuff();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == IncorporeticCygnusCapabilities.FUNNEL_CAP;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == IncorporeticCygnusCapabilities.FUNNEL_CAP) {
			return (T) this;
		} else return null;
	}
}
