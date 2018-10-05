package quaternary.incorporeal.api.cygnus;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public interface ICygnusSerializer<T> {
	ResourceLocation getName();
	Class<T> getSerializedClass();
	
	void writeToNBT(NBTTagCompound nbt, T item);
	T readFromNBT(NBTTagCompound nbt);
	
	void writeToByteBuf(ByteBuf buf, T item);
	T readFromByteBuf(ByteBuf buf);
}
