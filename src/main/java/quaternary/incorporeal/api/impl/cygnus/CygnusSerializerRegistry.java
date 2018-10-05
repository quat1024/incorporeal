package quaternary.incorporeal.api.impl.cygnus;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;
import quaternary.incorporeal.api.cygnus.ICygnusSerializerRegistry;
import quaternary.incorporeal.cygnus.serializers.DummyCygnusSerializer;

import java.util.ArrayList;
import java.util.List;

public class CygnusSerializerRegistry implements ICygnusSerializerRegistry {
	private final List<ICygnusSerializer<?>> serializers = new ArrayList<>();
	private final BiMap<ICygnusSerializer<?>, Integer> netIDs = HashBiMap.create();
	private int netID = 0;
	
	public void registerSerializer(ICygnusSerializer<?> serializer) {
		serializers.add(serializer);
		netIDs.put(serializer, netID++);
	}
	
	public <T> void writeToNBT(NBTTagCompound nbt, T item) {
		ICygnusSerializer<T> serializer = byClass(item.getClass());
		nbt.setString("Type", serializer.getName().toString());
		serializer.writeToNBT(nbt, item);
	}
	
	public <T> T readFromNBT(NBTTagCompound nbt) {
		ICygnusSerializer<T> serializer = byResourceLocation(new ResourceLocation(nbt.getString("Type")));
		return serializer.readFromNBT(nbt);
	}
	
	public <T> void writeToByteBuf(ByteBuf buf, T item) {
		ICygnusSerializer<T> serializer = byClass(item.getClass());
		buf.writeShort(netIDs.get(serializer));
		serializer.writeToByteBuf(buf, item);
	}
	
	public <T> T readFromByteBuf(ByteBuf buf) {
		ICygnusSerializer<T> serializer = byNetID(buf.readShort());
		return serializer.readFromByteBuf(buf);
	}
	
	private <T> ICygnusSerializer<T> byResourceLocation(ResourceLocation name) {
		for(ICygnusSerializer<?> serializer : serializers) {
			if(serializer.getName().equals(name)) {
				return (ICygnusSerializer<T>) serializer;
			}
		}
		
		//TODO: This won't handle removing mods very well...
		Incorporeal.LOGGER.info("Can't find the Cygnus serializer for resource " + name + ", returning a dummy");
		return new DummyCygnusSerializer<>();
	}
	
	private <T> ICygnusSerializer<T> byClass(Class<?> tclass) {
		for(ICygnusSerializer<?> serializer : serializers) {
			if(serializer.getSerializedClass().equals(tclass)) {
				return (ICygnusSerializer<T>) serializer;
			}
		}
		
		Incorporeal.LOGGER.info("Can't find the Cygnus serializer for class " + tclass + ", returning a dummy");
		return new DummyCygnusSerializer<>();
	}
	
	private <T> ICygnusSerializer<T> byNetID(int netID) {
		ICygnusSerializer<?> serializer = netIDs.inverse().get(netID);
		if(serializer != null) return (ICygnusSerializer<T>) serializer;
		
		Incorporeal.LOGGER.info("Can't find the Cygnus serializer for net ID " + netID + ", returning a dummy");
		return new DummyCygnusSerializer<>();
	}
}
