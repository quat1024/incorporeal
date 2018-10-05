package quaternary.incorporeal.api.cygnus;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import quaternary.incorporeal.api.IIncorporealAPI;

/**
 * Tell Incorporeal about your <pre>ICygnusSerializers</pre> here.
 * There's also a few (intended for internal-use) methods that loop over this serializer registry and
 * try to choose the right one, for any item. Kinda cool. Not really.
 * 
 * @see IIncorporealAPI#getCygnusSerializerRegistry() 
 * 
 * @author quaternary
 * @since 1.1
 */
public interface ICygnusSerializerRegistry {
	/**
	 * Tell Incorporeal about an ICygnusSerializer.
	 * @param serializer The serializer you want Minecraft to start using.
	 */
	void registerSerializer(ICygnusSerializer<?> serializer);
	
	<T> void writeToNBT(NBTTagCompound nbt, T item);
	<T> T readFromNBT(NBTTagCompound nbt);
	<T> void writeToByteBuf(ByteBuf buf, T item);
	<T> T readFromByteBuf(ByteBuf buf);
}
