package quaternary.incorporeal.api.impl.cygnus;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;
import quaternary.incorporeal.api.cygnus.ICygnusSerializerRegistry;
import quaternary.incorporeal.cygnus.CygnusError;

import java.util.ArrayList;
import java.util.List;

public class CygnusSerializerRegistry implements ICygnusSerializerRegistry {
	private final List<ICygnusSerializer<?>> serializers = new ArrayList<>();
	private final BiMap<ICygnusSerializer<?>, Integer> netIDs = HashBiMap.create();
	
	public void registerSerializer(ICygnusSerializer<?> serializer) {
		serializers.add(serializer);
		netIDs.put(serializer, netIDs.size());
	}
	
	public <T> void writeToNBT(NBTTagCompound nbt, T item) {
		for(ICygnusSerializer<?> serializer : serializers) {
			if(serializer.getSerializedClass() == item.getClass()) {
				nbt.setString("Type", serializer.getType().toString());
				((ICygnusSerializer<T>) serializer).writeToNBT(nbt, item);
				return;
			}
		}
		
		throw new IllegalArgumentException("No matching cygnus serializer registered for " + item.getClass());
	}
	
	public Object readFromNBT(NBTTagCompound nbt) {
		ResourceLocation type = new ResourceLocation(nbt.getString("Type"));
		
		for(ICygnusSerializer<?> serializer : serializers) {
			if(serializer.getType().equals(type)) {
				return serializer.readFromNBT(nbt);
			}
		}
		
		//maybe the mod providing this serializer was removed or something?
		Incorporeal.LOGGER.warn("Couldn't find a cygnus serializer to deserialize " + type + ", maybe the mod was removed? Replacing it with a CygnusError");
		return new CygnusError();
	}
	
	public <T> void writeToPacketBuffer(PacketBuffer buf, T item) {
		for(ICygnusSerializer<?> serializer : serializers) {
			if(serializer.getSerializedClass() == item.getClass()) {
				buf.writeInt(netIDs.get(serializer));
				((ICygnusSerializer<T>) serializer).writeToPacketBuffer(buf, item);
				return;
			}
		}
		
		throw new IllegalArgumentException("No matching cygnus serializer registered for " + item.getClass());
	}
	
	public Object readFromPacketBuffer(PacketBuffer buf) {
		//no null checks here since bytebufs are very short-term, right?
		//there's not going to be a mod removed over the lifetime of this object
		return netIDs.inverse().get(buf.readInt()).readFromPacketBuffer(buf);
	}
}
