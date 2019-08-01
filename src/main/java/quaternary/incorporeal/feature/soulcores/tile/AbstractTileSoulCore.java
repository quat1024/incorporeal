package quaternary.incorporeal.feature.soulcores.tile;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.client.core.handler.HUDHandler;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class AbstractTileSoulCore extends TileEntity implements ITickable, IManaReceiver {
	protected GameProfile ownerProfile;
	protected int mana;
	
	public static final DamageSource SOUL = new DamageSource("incorporeal.soul").setMagicDamage();
	
	protected abstract int getMaxMana();
	
	public boolean hasOwnerProfile() {
		return ownerProfile != null;
	}
	
	public GameProfile getOwnerProfile() {
		return ownerProfile;
	}
	
	public boolean setOwnerProfile(GameProfile newProfile) {
		if(newProfile != null && newProfile.equals(ownerProfile)) return false;
		ownerProfile = newProfile;
		markDirty();
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
		return true;
	}
	
	public Optional<EntityPlayer> findPlayer() {
		if(!hasOwnerProfile()) return Optional.empty();
		GameProfile prof = getOwnerProfile();
		
		for(EntityPlayer playerEnt : world.playerEntities) {
			if(playerEnt.getGameProfile().equals(prof)) {
				return Optional.of(playerEnt);
			}
		}
		
		return Optional.empty();
	}
	
	public boolean click(EntityPlayer player) {
		boolean isDifferent = setOwnerProfile(player.getGameProfile());
		if(isDifferent) {
			if(!world.isRemote) {
				player.attackEntityFrom(SOUL, 5f);
				receiveInitialMana();
			}
			return true;
		}
		
		return false;
	}
	
	public void receiveInitialMana() {
		int n = getMaxMana() / 2;
		if(mana < n) mana = n;
		
		markDirty();
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
	}
	
	public void drainMana(int manaPoof) {
		if(manaPoof < 0) manaPoof = 0;
		mana -= manaPoof;
		if(mana <= 0) mana = 0;
		
		markDirty();
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
	}
	
	@Override
	public void update() {
		//looks like mana usage is disabled for this one...
		if(getMaxMana() == 0) return;
		if(world.isRemote) return;
		
		if(mana <= 0 && hasOwnerProfile()) {
			//Wuh oh
			findPlayer().ifPresent(owningPlayer -> owningPlayer.attackEntityFrom(SOUL, 5f));
			setOwnerProfile(null);
			world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5f, 1.2f);
		}
	}
	
	public int getComparatorValue() {
		if(getMaxMana() == 0) return 0;
		else return Math.round(EtcHelpers.rangeRemap(mana, 0, getMaxMana(), 0, 15));
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
	
	@SideOnly(Side.CLIENT)
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, BlockPos pos) {
		Item i = Item.getItemFromBlock(getBlockType());
		String name = I18n.format(i.getTranslationKey() + ".name"); //:thonkjang:
		
		HUDHandler.drawSimpleManaHUD(0xee4444, mana, getMaxMana(), name, res);
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
