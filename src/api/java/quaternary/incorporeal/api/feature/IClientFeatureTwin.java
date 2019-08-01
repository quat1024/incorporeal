package quaternary.incorporeal.api.feature;

import net.minecraftforge.client.event.ColorHandlerEvent;

public interface IClientFeatureTwin {
	default void preinit() {}
	default void models() {}
	default void statemappers() {}
	default void tesrs() {}
	default void blockColors(ColorHandlerEvent.Block e) {}
	default void itemColors(ColorHandlerEvent.Item e) {}
	
	IClientFeatureTwin DUMMY = new IClientFeatureTwin() {};
}
