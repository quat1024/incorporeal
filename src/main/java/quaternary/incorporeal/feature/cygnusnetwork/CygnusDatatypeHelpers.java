package quaternary.incorporeal.feature.cygnusnetwork;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;

public final class CygnusDatatypeHelpers {
	private CygnusDatatypeHelpers() {}
	
	//A map of the class a datatype represents to the datatype.
	private static final BiMap<Class, ICygnusDatatype<?>> typesByClass = HashBiMap.create();
	
	//A map of an arbitrary, unique number to a datatype.
	//Not guaranteed to be the same across multiple game launcher.
	private static final BiMap<Integer, ICygnusDatatype<?>> typesByNetworkID = HashBiMap.create();
	
	private static BiMap<ResourceLocation, ICygnusDatatype<?>> typesByResourceLocation;
	
	public static void init() {
		if(!typesByClass.isEmpty()) throw new IllegalStateException("Already initialized");
		
		typesByResourceLocation = Incorporeal.API.getCygnusDatatypeRegistry().backingMap();
		
		int i = 0;
		for(ICygnusDatatype<?> type : typesByResourceLocation.values()) {
			typesByClass.put(type.getTypeClass(), type);
			
			typesByNetworkID.put(i, type);
			i++;
		}
	}
	
	public static <T> ICygnusDatatype<T> forClass(Class<T> classs) {
		ICygnusDatatype<?> type = typesByClass.get(classs);
		if(type == null) throw new IllegalArgumentException("No Cygnus datatype registered for class " + classs);
		else return (ICygnusDatatype<T>) type;
	}
	
	public static <T> void writeToNBT(NBTTagCompound nbt, T item) {
		ICygnusDatatype<?> datatype = typesByClass.get(item.getClass());
		if(datatype == null) {
			throw new IllegalArgumentException("No Cygnus datatype registered for " + item.getClass());
		} else {
			nbt.setString("Type", typesByResourceLocation.inverse().get(datatype).toString());
			((ICygnusDatatype<T>) datatype).writeToNBT(nbt, item);
		}
	}
	
	public static Object readFromNBT(NBTTagCompound nbt) {
		ResourceLocation type = new ResourceLocation(nbt.getString("Type"));
		ICygnusDatatype<?> datatype = typesByResourceLocation.get(type);
		if(datatype == null) {
			//maybe the mod providing this serializer was removed or something?
			//nbt is long-term storage, after all
			//so don't hard-fail here
			Incorporeal.LOGGER.warn("Couldn't find a Cygnus datatype to deserialize " + type + ", maybe the mod was removed? Replacing it with a CygnusError");
			return new CygnusError(CygnusError.REMOVED_TYPE);
		} else {
			return datatype.readFromNBT(nbt);
		}
	}
	
	public static <T> void writeToPacketBuffer(PacketBuffer buf, T item) {
		ICygnusDatatype<?> datatype = typesByClass.get(item.getClass());
		if(datatype == null) {
			throw new IllegalArgumentException("No Cygnus datatype registered for " + item.getClass());
		} else {
			buf.writeInt(typesByNetworkID.inverse().get(datatype));
			((ICygnusDatatype<T>) datatype).writeToPacketBuffer(buf, item);
		}
	}
	
	public static Object readFromPacketBuffer(PacketBuffer buf) {
		return typesByNetworkID.get(buf.readInt()).readFromPacketBuffer(buf);
	}
}
