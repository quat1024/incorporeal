package quaternary.incorporeal.feature.cygnusnetwork.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class CameraEaseHandler {
	private CameraEaseHandler() {
	}
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(CameraEaseHandler.class);
	}
	
	public static float easedYaw = 0;
	public static float easedPitch = 0;
	
	public static float easeStrength = .06f;
	
	@SubscribeEvent
	public static void clientTick(TickEvent.RenderTickEvent e) {
		if(e.phase == TickEvent.Phase.END) {
			Minecraft mc = Minecraft.getMinecraft();
			Entity view = mc.getRenderViewEntity();
			if(!mc.isGamePaused() && view != null) {
				easedYaw += (view.rotationYaw - easedYaw) * mc.getRenderPartialTicks() * easeStrength;
				easedPitch += (view.rotationPitch - easedPitch) * mc.getRenderPartialTicks() * easeStrength;
			}
		}
	}
}
