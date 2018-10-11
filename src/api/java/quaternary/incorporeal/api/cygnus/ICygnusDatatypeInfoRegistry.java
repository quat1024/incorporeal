package quaternary.incorporeal.api.cygnus;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import quaternary.incorporeal.api.IIncorporealAPI;

/**
 * Tell Incorporeal about your {@link ICygnusDatatypeInfo}s here.
 * There's also a few (intended for internal-use) methods that loop over this serializer registry and
 * try to choose the right one, for any item. Kinda cool. Not really.
 * 
 * @see IIncorporealAPI#getCygnusDatatypeInfoRegistry() 
 * 
 * @author quaternary
 * @since 1.1
 */
public interface ICygnusDatatypeInfoRegistry {
	/**
	 * Tell Incorporeal about an ICygnusDatatypeInfo.
	 * @param datatype The datatype you want Incorporeal to know about.
	 */
	void registerDatatype(ICygnusDatatypeInfo<?> datatype);
	
	/**
	 * @return the ICygnusDatatypeInfo instance that objects of this class will use
	 */
	<T> ICygnusDatatypeInfo<T> getDatatypeForClass(Class<T> classs);
	
	<T> void writeToNBT(NBTTagCompound nbt, T item);
	Object readFromNBT(NBTTagCompound nbt);
	<T> void writeToPacketBuffer(PacketBuffer buf, T item);
	Object readFromPacketBuffer(PacketBuffer buf);
}
