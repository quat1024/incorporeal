package quaternary.incorporeal.core.client;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.IncorporeticFeatures;
import quaternary.incorporeal.api.feature.IClientFeatureTwin;

public final class ClientRegistryEvents {
	private ClientRegistryEvents() {
	}
	
	public static void preinit() {
		IncorporeticFeatures.forEach(f -> f.client().preinit());
		
		MinecraftForge.EVENT_BUS.register(ClientRegistryEvents.class);
	}
	
	@SubscribeEvent
	public static void models(ModelRegistryEvent e) {
		IncorporeticFeatures.forEach(f -> {
			IClientFeatureTwin clientTwin = f.client();
			clientTwin.models();
			clientTwin.statemappers();
			clientTwin.tesrs();
		});
	}
	
	@SubscribeEvent
	public static void blockColors(ColorHandlerEvent.Block e) {
		IncorporeticFeatures.forEach(f -> f.client().blockColors(e));
	}
	
	@SubscribeEvent
	public static void itemColors(ColorHandlerEvent.Item e) {
		IncorporeticFeatures.forEach(f -> f.client().itemColors(e));
	}
}