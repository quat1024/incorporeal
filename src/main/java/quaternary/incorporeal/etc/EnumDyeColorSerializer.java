package quaternary.incorporeal.etc;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;

public class EnumDyeColorSerializer implements DataSerializer<EnumDyeColor> {
	public static final EnumDyeColorSerializer INST = new EnumDyeColorSerializer();
	
	static {
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
