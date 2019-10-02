package quaternary.incorporeal.feature.cygnusnetwork.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusAction;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.IncorporeticCygnusActions;

import javax.annotation.Nullable;

public class TileCygnusWord extends TileCygnusBase {
	private ICygnusAction action = IncorporeticCygnusActions.NOTHING;
	
	public void accept(ICygnusStack stack) {
		if(stack == null) return;
		action.accept(stack);
	}
	
	public ICygnusAction getAction() {
		return action;
	}
	
	public void setAction(ICygnusAction action) {
		this.action = action;
		
		if(world != null && world.isRemote) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Action", Incorporeal.API.getCygnusStackActionRegistry().nameOf(action).toString());
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		ICygnusAction a = Incorporeal.API.getCygnusStackActionRegistry().get(
			new ResourceLocation(nbt.getString("Action"))
		);
		
		if(a == null) a = IncorporeticCygnusActions.NOTHING;
		setAction(a);
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 6969, writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		IBlockState memes = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, memes, memes, 3);
	}
}
