package quaternary.incorporeal.api.cygnus;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.api.IIncorporealAPI;

/**
 * This class is used for writing the contents of Cygnus stacks to disk and for sending them over the network.
 * 
 * @see IIncorporealAPI#getCygnusSerializerRegistry() 
 * @see ICygnusSerializerRegistry#registerSerializer(ICygnusSerializer) 
 *
 * @author quaternary
 * @since 1.1
 */
public interface ICygnusSerializer<T> {
	/**
	 * @return A globally-unique ResourceLocation that describes this Cygnus serializer.
	 */
	ResourceLocation getType();
	
	/**
	 * @return The class that this Cygnus serializer is in charge of.
	 */
	Class<T> getSerializedClass();
	
	/**
	 * Write this item to an NBT tag compound.
	 * You may use any keys other than "Depth" and "Type", which are used internally.
	 * 
	 * For example: <pre>nbt.setInteger("Int", item);</pre>
	 * 
	 * @param nbt The NBT tag compound to write to.
	 * @param item The item to write to the compound.
	 */
	void writeToNBT(NBTTagCompound nbt, T item);
	
	/**
	 * Read an item from the NBT tag compound.
	 * @param nbt The NBT tag compound to read items from.
	 * @return The item.
	 */
	T readFromNBT(NBTTagCompound nbt);
	
	/**
	 * Serialize this item to a PacketBuffer.
	 * @param buf The PacketBuffer to write to.
	 * @param item The item to write to the buffer.
	 */
	void writeToPacketBuffer(PacketBuffer buf, T item);
	
	/**
	 * Deserialize an item from a PacketBuffer.
	 * @param buf The PacketBuffer to read from.
	 * @return The item read from the buffer.
	 */
	T readFromPacketBuffer(PacketBuffer buf);
}
