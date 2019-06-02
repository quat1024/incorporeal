package quaternary.incorporeal.etc;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class EnumDyeColorDataSerializer implements DataSerializer<EnumDyeColor> {
	public static final EnumDyeColorDataSerializer INST = new EnumDyeColorDataSerializer();
	
	public static void preinit(FMLPreInitializationEvent e) {
		DataSerializers.registerSerializer(INST);
	}
	
	@Override
	public void write(PacketBuffer buf, EnumDyeColor value) {
		buf.writeByte(value.getMetadata());
	}
	
	@Override
	public EnumDyeColor read(PacketBuffer buf) {
		return EnumDyeColor.byMetadata(buf.readByte());
	}
	
	@Override
	public DataParameter<EnumDyeColor> createKey(int id) {
		return new DataParameter<>(id, this);
	}
	
	@Override
	public EnumDyeColor copyValue(EnumDyeColor value) {
		return value;
	}
}
