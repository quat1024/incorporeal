package quaternary.incorporeal.feature.compat.infraredstone;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import quaternary.incorporeal.api.feature.IFeature;

public class InfraRedstoneCompatFeature implements IFeature {
	@Override
	public String name() {
		return "infraRedstoneCompat";
	}
	
	@Override
	public String description() {
		return "Various small compatibility things with InfraRedstone.";
	}
	
	@Override
	public String subcategory() {
		return "compat";
	}
	
	@Override
	public void preinit(FMLPreInitializationEvent e) {
		//delegate to another class because this Feature class will get loaded on startup when enumerating features.
		InfraRedstoneCompat.preinit(e);
	}
	
	@Override
	public void postinit(FMLPostInitializationEvent e) {
		InfraRedstoneCompat.postinit(e);
	}
}
