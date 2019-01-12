package quaternary.incorporeal.tile.cygnus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.cygnus.CygnusDatatypeHelpers;
import quaternary.incorporeal.cygnus.cap.IncorporeticCygnusCapabilities;
import vazkii.botania.api.internal.VanillaPacketDispatcher;

import javax.annotation.Nullable;

public class TileCygnusRetainer extends TileCygnusBase implements ICygnusFunnelable {
	@Nullable
	private Object retained;
	
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
		Object ret = retained;
		retained = null;
		
		//it is 3am
		markDirty();
		IBlockState ok = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, ok, ok, 2);
		
		return ret;
	}
	
	@Override
	public void acceptItemFromCygnus(Object item) {
		retained = item;
		
		markDirty();
		IBlockState ok = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, ok, ok, 2);
	}
	
	public boolean hasRetainedObject() {
		return retained != null;
	}
	
	@Nullable
	public Object getRetainedObject() {
		return retained;
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
		
		IBlockState ok = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, ok, ok, 3);
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
