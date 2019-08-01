package quaternary.incorporeal.core.client;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.feature.IClientFeatureTwin;
import quaternary.incorporeal.IncorporeticFeatures;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class ClientRegistryEvents {
	private ClientRegistryEvents() {}
	
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
	
	public static void preinit() {
		IncorporeticFeatures.forEach(f -> f.client().preinit());
	}
}