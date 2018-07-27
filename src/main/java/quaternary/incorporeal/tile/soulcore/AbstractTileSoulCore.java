package quaternary.incorporeal.tile.soulcore;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

public abstract class AbstractTileSoulCore extends TileEntity {
	public UUID ownerUUID;
	
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	
	public void setOwnerUUID(UUID ownerUUID) {
		this.ownerUUID = ownerUUID;
		onOwnerSet();
		markDirty();
	}
	
	public void onOwnerSet() {
		;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("ownerUUID", NBTUtil.createUUIDTag(getOwnerUUID()));
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setOwnerUUID(NBTUtil.getUUIDFromTag(nbt.getCompoundTag("ownerUUID")));
	}
}
