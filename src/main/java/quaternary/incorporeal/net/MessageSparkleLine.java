package quaternary.incorporeal.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.botania.common.Botania;

import java.awt.*;

public class MessageSparkleLine implements IncorporeticPacketHandler.IIncorporeticMessage {
	public MessageSparkleLine() {}
	
	public MessageSparkleLine(Vec3d point1, Vec3d point2, int decay) {
		this.point1 = point1;
		this.point2 = point2;
		this.decay = decay;
	}
	
	public MessageSparkleLine(Vec3i point1, Vec3i point2, int decay) {
		this.point1 = new Vec3d(point1).add(.5, .5, .5);
		this.point2 = new Vec3d(point2).add(.5, .5, .5);
		this.decay = decay;
	}
	
	private Vec3d point1;
	private Vec3d point2;
	private int decay;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(point1.x);
		buf.writeDouble(point1.y);
		buf.writeDouble(point1.z);
		buf.writeDouble(point2.x);
		buf.writeDouble(point2.y);
		buf.writeDouble(point2.z);
		buf.writeInt(decay);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		point1 = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		point2 = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		decay = buf.readInt();
	}
	
	public static class Handler implements IMessageHandler<MessageSparkleLine, IMessage> {
		@Override
		public IMessage onMessage(MessageSparkleLine message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				//Loosely based on PacketBotaniaEffect's SPARK_NET_INDICATOR
				//Modified to use passed-in vectors instead of entities
				//and to use Ved3d instead of the custom Vector3
				Vec3d diff = message.point2.subtract(message.point1);
				Vec3d movement = diff.normalize().scale(.2); //Scale it up a bit bc there's really a lot of particles
				int iters = (int) (diff.length() / movement.length());
				float huePer = 1F / iters;
				float hueSum = (float) Math.random();
				
				Vec3d currentPos = message.point1;
				Botania.proxy.setSparkleFXNoClip(true);
				for(int i = 0; i < iters; i++) {
					float hue = i * huePer + hueSum;
					Color color = Color.getHSBColor(hue, 1F, 1F);
					float r = Math.min(1F, color.getRed() / 255F + 0.4F);
					float g = Math.min(1F, color.getGreen() / 255F + 0.4F);
					float b = Math.min(1F, color.getBlue() / 255F + 0.4F);
					
					Botania.proxy.sparkleFX(currentPos.x, currentPos.y, currentPos.z, r, g, b, 1F, message.decay);
					currentPos = currentPos.add(movement);
				}
				Botania.proxy.setSparkleFXNoClip(false);
			});
			
			return null;
		}
	}
}
