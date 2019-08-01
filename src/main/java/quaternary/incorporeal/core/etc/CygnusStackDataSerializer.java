package quaternary.incorporeal.core.etc;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusStack;

public class CygnusStackDataSerializer implements DataSerializer<CygnusStack> {
	public static final CygnusStackDataSerializer INST = new CygnusStackDataSerializer();
	
	public static void preinit(FMLPreInitializationEvent e) {
		DataSerializers.registerSerializer(INST);
	}
	
	@Override
	public void write(PacketBuffer buf, CygnusStack value) {
		value.toPacketBuffer(buf);
	}
	
	@Override
	public CygnusStack read(PacketBuffer buf) {
		CygnusStack stacky = new CygnusStack(0);
		stacky.fromPacketBuffer(buf);
		return stacky;
	}
	
	@Override
	public DataParameter<CygnusStack> createKey(int id) {
		return new DataParameter<>(id, this);
	}
	
	@Override
	public CygnusStack copyValue(CygnusStack value) {
		return value.copy();
	}
}
