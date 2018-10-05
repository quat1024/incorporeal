package quaternary.incorporeal.cygnus.serializers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;

public class CygnusDoubleSerializer implements ICygnusSerializer<Double> {
	@Override
	public ResourceLocation getName() {
		return new ResourceLocation(Incorporeal.MODID, "double");
	}
	
	@Override
	public Class<Double> getSerializedClass() {
		return Double.class;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, Double item) {
		nbt.setDouble("Double", item);
	}
	
	@Override
	public Double readFromNBT(NBTTagCompound nbt) {
		return nbt.getDouble("Double");
	}
	
	@Override
	public void writeToByteBuf(ByteBuf buf, Double item) {
		buf.writeDouble(item);
	}
	
	@Override
	public Double readFromByteBuf(ByteBuf buf) {
		return buf.readDouble();
	}
}
