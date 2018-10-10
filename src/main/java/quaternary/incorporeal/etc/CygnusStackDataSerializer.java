package quaternary.incorporeal.etc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import quaternary.incorporeal.cygnus.CygnusStack;

public class CygnusStackDataSerializer implements DataSerializer<CygnusStack> {
	@Override
	public void write(PacketBuffer buf, CygnusStack value) {
		//TODO: Actually use the toBytes methods lmao
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
