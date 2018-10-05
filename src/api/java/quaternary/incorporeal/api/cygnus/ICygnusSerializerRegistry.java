package quaternary.incorporeal.api.cygnus;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public interface ICygnusSerializerRegistry {
	public void registerSerializer(ICygnusSerializer<?> serializer);
	
	public <T> void writeToNBT(NBTTagCompound nbt, T item);
	public <T> T readFromNBT(NBTTagCompound nbt);
	public <T> void writeToByteBuf(ByteBuf buf, T item);
	public <T> T readFromByteBuf(ByteBuf buf);
}
