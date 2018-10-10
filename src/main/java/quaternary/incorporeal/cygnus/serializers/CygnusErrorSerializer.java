package quaternary.incorporeal.cygnus.serializers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;
import quaternary.incorporeal.cygnus.CygnusError;

public class CygnusErrorSerializer implements ICygnusSerializer<CygnusError> {
	@Override
	public ResourceLocation getType() {
		return new ResourceLocation(Incorporeal.MODID, "cygnus_error");
	}
	
	@Override
	public Class<CygnusError> getSerializedClass() {
		return CygnusError.class;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, CygnusError item) {
		//No-op
	}
	
	@Override
	public CygnusError readFromNBT(NBTTagCompound nbt) {
		return new CygnusError();
	}
	
	@Override
	public void writeToPacketBuffer(PacketBuffer buf, CygnusError item) {
		//No-op
	}
	
	@Override
	public CygnusError readFromPacketBuffer(PacketBuffer buf) {
		return new CygnusError();
	}
}
