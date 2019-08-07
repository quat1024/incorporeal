package quaternary.incorporeal.feature.skytouching;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.feature.compat.crafttweaker.CTSkytouching;
import quaternary.incorporeal.feature.skytouching.event.WorldTickEventHandler;

public class SkytouchingFeature implements IFeature {
	@Override
	public String name() {
		return "skytouching";
	}
	
	@Override
	public String description() {
		return "New ways to craft items. (By default, recipes only exist for Cygnus items, but you can add more with crafttweaker even if the Cygnus network is disabled.)";
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		WorldTickEventHandler.register();
		
		if(Loader.isModLoaded("crafttweaker")) {
			CTSkytouching.init();
		}
	}
}
