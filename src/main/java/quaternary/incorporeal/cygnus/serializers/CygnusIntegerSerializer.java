package quaternary.incorporeal.cygnus.serializers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;

public class CygnusIntegerSerializer implements ICygnusSerializer<Integer> {
	@Override
	public ResourceLocation getName() {
		return new ResourceLocation(Incorporeal.MODID, "integer");
	}
	
	@Override
	public Class<Integer> getSerializedClass() {
		return Integer.class;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, Integer item) {
		nbt.setInteger("Int", item);
	}
	
	@Override
	public Integer readFromNBT(NBTTagCompound nbt) {
		return nbt.getInteger("Int");
	}
	
	@Override
	public void writeToByteBuf(ByteBuf buf, Integer item) {
		buf.writeInt(item);
	}
	
	@Override
	public Integer readFromByteBuf(ByteBuf buf) {
		return buf.readInt();
	}
}
