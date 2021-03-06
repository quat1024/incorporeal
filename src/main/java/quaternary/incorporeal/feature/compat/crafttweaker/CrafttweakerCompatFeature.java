package quaternary.incorporeal.feature.compat.crafttweaker;

import quaternary.incorporeal.api.feature.IFeature;

import java.util.Collection;
import java.util.Collections;

public class CrafttweakerCompatFeature implements IFeature {
	@Override
	public String name() {
		return "crafttweakerCompat";
	}
	
	@Override
	public String description() {
		return "Add skytouching recipes with Crafttweaker.";
	}
	
	@Override
	public String subcategory() {
		return "compat";
	}
	
	@Override
	public Collection<String> requiredModIDs() {
		return Collections.singletonList("crafttweaker");
	}
}
