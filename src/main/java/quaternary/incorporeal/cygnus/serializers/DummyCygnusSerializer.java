package quaternary.incorporeal.cygnus.serializers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;

public class DummyCygnusSerializer<T> implements ICygnusSerializer<T> {
	@Override
	public ResourceLocation getName() {
		return new ResourceLocation(Incorporeal.MODID, "dummy_serializer");
	}
	
	@Override
	public Class<T> getSerializedClass() {
		return null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, T item) {
		//No-op
	}
	
	@Override
	public T readFromNBT(NBTTagCompound nbt) {
		return null;
	}
	
	@Override
	public void writeToByteBuf(ByteBuf buf, T item) {
		//No-op
	}
	
	@Override
	public T readFromByteBuf(ByteBuf buf) {
		return null;
	}
}
