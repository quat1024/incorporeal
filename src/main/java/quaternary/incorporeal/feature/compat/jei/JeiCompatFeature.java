package quaternary.incorporeal.feature.compat.jei;

import quaternary.incorporeal.api.feature.IFeature;

//Can't actually implement imodplugin because this class is referenced in non-jei environments.
public class JeiCompatFeature implements IFeature {
	@Override
	public String name() {
		return "jeiCompat";
	}
	
	@Override
	public String description() {
		return "Compatibility for JEI.";
	}
	
	@Override
	public String subcategory() {
		return "compat";
	}
}
