package quaternary.incorporeal.tile.soulcore;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import vazkii.botania.api.mana.IManaReceiver;

import javax.annotation.Nullable;

public abstract class AbstractTileSoulCore extends TileEntity implements ITickable, IManaReceiver {
	private GameProfile ownerProfile;
	protected int mana;
	
	protected abstract int getMaxMana();
	
	public boolean hasOwnerProfile() {
		return ownerProfile != null;
	}
	
	@Nullable
	public GameProfile getOwnerProfile() {
		return ownerProfile;
	}
	
	public void setOwnerProfile(GameProfile ownerProfile) {
		this.ownerProfile = ownerProfile;
		markDirty();
	}
	
	public void receiveInitialMana() {
		mana = getMaxMana() / 2;
	}
	
	public void drainMana(int manaPoof) {
		mana -= manaPoof;
		if(mana <= 0) mana = 0;
	}
	
	@Override
	public void update() {
		//looks like mana usage is disabled for this one...
		if(getMaxMana() == 0) return;
		
		if(world.isRemote) return;
		
		if(mana <= 0) {
			//Wuh oh
			setOwnerProfile(null);
			world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5f, 1.2f);
		}
	}
	
	public int getComparatorValue() {
		return Math.round(EtcHelpers.rangeRemap(mana, 0, getMaxMana(), 0, 15));
	}
	
	@Override
	public boolean isFull() {
		if(getMaxMana() == 0) return true;
		else return mana >= getMaxMana();
	}
	
	@Override
	public void recieveMana(int moreMana) {
		mana = Math.min(mana + moreMana, getMaxMana());
	}
	
	@Override
	public boolean canRecieveManaFromBursts() {
		return getMaxMana() != 0;
	}
	
	@Override
	public int getCurrentMana() {
		return 0;
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
		
		nbt.setInteger("Mana", mana);
		
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if(nbt.hasKey("Owner")) {
			ownerProfile = NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag("Owner"));
		} else {
			ownerProfile = null;
		}
		
		mana = nbt.getInteger("Mana");
	}
}
