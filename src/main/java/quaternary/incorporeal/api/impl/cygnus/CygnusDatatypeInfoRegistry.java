package quaternary.incorporeal.api.impl.cygnus;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatypeInfo;
import quaternary.incorporeal.api.cygnus.ICygnusDatatypeInfoRegistry;
import quaternary.incorporeal.cygnus.CygnusError;

public class CygnusDatatypeInfoRegistry implements ICygnusDatatypeInfoRegistry {
	private final BiMap<Class, ICygnusDatatypeInfo<?>> datatypesByClass = HashBiMap.create();
	private final BiMap<ResourceLocation, ICygnusDatatypeInfo<?>> datatypesByResourceLocation = HashBiMap.create();
	private final BiMap<Integer, ICygnusDatatypeInfo<?>> datatypesByNetID = HashBiMap.create();
	
	public void registerDatatype(ICygnusDatatypeInfo<?> datatype) {
		datatypesByClass.put(datatype.getTypeClass(), datatype);
		datatypesByResourceLocation.put(datatype.getResourceLocation(), datatype);
		datatypesByNetID.put(datatypesByNetID.size(), datatype);
	}
	
	@Override
	public <T> ICygnusDatatypeInfo<T> getDatatypeForClass(Class<T> classs) {
		ICygnusDatatypeInfo<?> type = datatypesByClass.get(classs);
		if(type == null) throw new IllegalArgumentException("No Cygnus datatype registered for class " + classs);
		else return (ICygnusDatatypeInfo<T>) type;
	}
	
	public <T> void writeToNBT(NBTTagCompound nbt, T item) {
		ICygnusDatatypeInfo<?> datatype = datatypesByClass.get(item.getClass());
		if(datatype == null) {
			throw new IllegalArgumentException("No matching Cygnus datatype registered for " + item.getClass());
		} else {
			nbt.setString("Type", datatype.getResourceLocation().toString());
			((ICygnusDatatypeInfo<T>) datatype).writeToNBT(nbt, item);
		}
	}
	
	public Object readFromNBT(NBTTagCompound nbt) {
		ResourceLocation type = new ResourceLocation(nbt.getString("Type"));
		ICygnusDatatypeInfo<?> datatype = datatypesByResourceLocation.get(type);
		if(datatype == null) {
			//maybe the mod providing this serializer was removed or something?
			//nbt is long-term storage, after all
			Incorporeal.LOGGER.warn("Couldn't find a Cygnus datatype to deserialize " + type + ", maybe the mod was removed? Replacing it with a CygnusError");
			return new CygnusError(CygnusError.REMOVED_TYPE);
		} else {
			return datatype.readFromNBT(nbt);
		}
	}
	
	public <T> void writeToPacketBuffer(PacketBuffer buf, T item) {
		ICygnusDatatypeInfo<?> datatype = datatypesByClass.get(item.getClass());
		if(datatype == null) {
			throw new IllegalArgumentException("No matching cygnus serializer registered for " + item.getClass());
		} else {
			buf.writeInt(datatypesByNetID.inverse().get(datatype));
			((ICygnusDatatypeInfo<T>) datatype).writeToPacketBuffer(buf, item);
			return;
		}
	}
	
	public Object readFromPacketBuffer(PacketBuffer buf) {
		//no null checks here since bytebufs are very short-term, right?
		//there's not going to be a mod removed over the lifetime of this object
		return datatypesByNetID.get(buf.readInt()).readFromPacketBuffer(buf);
	}
}
