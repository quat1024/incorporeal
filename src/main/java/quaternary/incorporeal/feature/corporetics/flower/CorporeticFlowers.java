package quaternary.incorporeal.feature.corporetics.flower;

import quaternary.incorporeal.core.FlowersModule;

public final class CorporeticFlowers extends FlowersModule {
	private CorporeticFlowers() {}
	
	public static void register() {
		register("sanvocalia", SubTileSanvocalia.class, SubTileSanvocalia.Mini.class);
		
		//This is so sad, Alexa play Despacito.
		register("sweet_alexum", SubTileSweetAlexum.class, SubTileSweetAlexum.Mini.class);
	}
}
