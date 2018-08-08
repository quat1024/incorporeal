package quaternary.incorporeal.tile.soulcore;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class AbstractTileSoulCore extends TileEntity {
	private GameProfile ownerProfile;
	
	public GameProfile getOwnerProfile() {
		return ownerProfile;
	}
	
	public void setOwnerProfile(GameProfile ownerProfile) {
		this.ownerProfile = ownerProfile;
		markDirty();
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
		IBlockState memes = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, memes, memes, 3); //i guess
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(ownerProfile != null) {
			nbt.setTag("Owner", NBTUtil.writeGameProfile(new NBTTagCompound(), ownerProfile));
		}
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		ownerProfile = NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag("Owner"));
	}
}
