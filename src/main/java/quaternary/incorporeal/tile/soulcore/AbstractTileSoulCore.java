package quaternary.incorporeal.tile.soulcore;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import quaternary.incorporeal.block.soulcore.AbstractBlockSoulCore;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaReceiver;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class AbstractTileSoulCore extends TileEntity implements ITickable, IManaReceiver {
	protected GameProfile ownerProfile;
	protected int mana;
	
	protected abstract int getMaxMana();
	
	public boolean hasOwnerProfile() {
		return ownerProfile != null;
	}
	
	public Optional<GameProfile> getOwnerProfile() {
		return Optional.ofNullable(ownerProfile);
	}
	
	public void changeOwnerProfile(GameProfile newProfile) {
		if(newProfile != null && newProfile.equals(ownerProfile)) return;
		
		findPlayer().ifPresent(oldOwner -> oldOwner.attackEntityFrom(AbstractBlockSoulCore.SOUL, 5f));
		
		ownerProfile = newProfile;
		
		markDirty();
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
	}
	
	public Optional<EntityPlayer> findPlayer() {
		if(!hasOwnerProfile()) return Optional.empty();
		
		for(EntityPlayer playerEnt : world.playerEntities) {
			if(playerEnt.getGameProfile().equals(getOwnerProfile())) {
				return Optional.of(playerEnt);
			}
		}
		
		return Optional.empty();
	}
	
	public void receiveInitialMana() {
		int n = getMaxMana() / 2;
		if(mana < n) mana = n;
	}
	
	public void drainMana(int manaPoof) {
		mana -= manaPoof;
		if(mana <= 0) mana = 0;
		markDirty();
	}
	
	@Override
	public void update() {
		//looks like mana usage is disabled for this one...
		if(getMaxMana() == 0) return;
		
		if(world.isRemote) return;
		
		if(mana <= 0 && hasOwnerProfile()) {
			//Wuh oh
			changeOwnerProfile(null);
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
		markDirty();
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
