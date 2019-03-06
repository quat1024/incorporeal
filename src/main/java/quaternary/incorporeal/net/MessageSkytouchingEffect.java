package quaternary.incorporeal.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import vazkii.botania.common.Botania;

public class MessageSkytouchingEffect implements IncorporeticPacketHandler.IIncorporeticMessage {
	public MessageSkytouchingEffect() {
	}
	
	public MessageSkytouchingEffect(double posX, double posY, double posZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}
	
	double posX, posY, posZ;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		posX = buf.readDouble();
		posY = buf.readDouble();
		posZ = buf.readDouble();
	}
	
	public static class Handler implements IMessageHandler<MessageSkytouchingEffect, IMessage> {
		@Override
		public IMessage onMessage(MessageSkytouchingEffect m, MessageContext ctx) {
			Minecraft mc = Minecraft.getMinecraft();
			
			mc.addScheduledTask(() -> {
				//No need to check dimension since I'm just not sending this to people in a different dim
				World world = mc.world;
				
				world.addWeatherEffect(new EntityLightningBolt(world, m.posX, m.posY, m.posZ, true));
				
				final int PARTICLE_COUNT = 20;
				for(int i = 0; i < PARTICLE_COUNT; i++) {
					float mx = 3 * (float) Math.cos(i / (float) PARTICLE_COUNT * Math.PI * 2);
					float mz = 3 * (float) Math.sin(i / (float) PARTICLE_COUNT * Math.PI * 2);
					Botania.proxy.wispFX(m.posX, m.posY, m.posZ, 1, 1, 1, 20, mx, 0.6f, mz);
				}
				
				//Lol
				float pitch = 0.7f;
				for(int i = 0; i < 4; i++) {
					world.playSound(mc.player, m.posX, m.posY, m.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 1, pitch);
					pitch += 0.2f;
				}
				world.playSound(mc.player, m.posX, m.posY, m.posZ, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.WEATHER, 1, 1.2f);
			});
			
			return null;
		}
	}
}
