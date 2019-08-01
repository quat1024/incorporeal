package quaternary.incorporeal.feature.compat.jei;

import quaternary.incorporeal.api.feature.IFeature;

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
