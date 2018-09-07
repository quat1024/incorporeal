package quaternary.incorporeal.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import quaternary.incorporeal.etc.helper.CorporeaHelper2;
import vazkii.botania.common.entity.EntityCorporeaSpark;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileCorporeaSparkTinkerer extends TileEntity {
	private EnumDyeColor myNetwork = EnumDyeColor.WHITE; //the default setting
	
	//Thx jamie
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	public void doSwap() {
		if(world.isRemote) return;
		
		//look for other corporea sparks nearby
		List<EntityCorporeaSpark> nearbySparks = new ArrayList<>();
		for(EnumFacing offset : EnumFacing.HORIZONTALS) {
			EntityCorporeaSpark spork = CorporeaHelper2.getSparkEntityForBlock(world, pos.offset(offset));
			if(spork != null) nearbySparks.add(spork);
		}
		
		if(nearbySparks.size() == 0) return;
		
		//choose one
		Collections.shuffle(nearbySparks);
		EntityCorporeaSpark spork = nearbySparks.get(0);
		
		//switch my network color with that one
		EnumDyeColor itsNetwork = spork.getNetwork();
		
		spork.setNetwork(myNetwork);
		myNetwork = itsNetwork;
		
		CorporeaHelper2.causeSparkRelink(spork);
		
		//TODO: don't steal vanilla soundevents for nonvanilla things (incorrect subtitles!)
		world.playSound(null, pos, SoundEvents.ENTITY_ITEMFRAME_ADD_ITEM, SoundCategory.BLOCKS, 1f, 1f);
	}
	
	public void setNetwork(EnumDyeColor color) {
		myNetwork = color;
		
		IBlockState memes = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, memes, memes, 3);
	}
	
	public EnumDyeColor getNetwork() {
		return myNetwork;
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
	public void readFromNBT(NBTTagCompound nbt) {		
		myNetwork = EnumDyeColor.byMetadata(MathHelper.clamp(nbt.getInteger("Network"), 0, 15));
		
		super.readFromNBT(nbt);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Network", myNetwork.getMetadata());
		return super.writeToNBT(nbt);
	}
}
