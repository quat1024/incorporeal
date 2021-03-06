package quaternary.incorporeal.feature.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.IncorporeticFeatures;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.feature.skytouching.recipe.RecipeSkytouching;
import quaternary.incorporeal.feature.skytouching.recipe.SkytouchingRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

@ZenRegister
@ZenClass("mods.incorporeal.Skytouching")
public final class CTSkytouching {
	private CTSkytouching() { }
	//zen stuff
	
	@ZenMethod
	public static void addRecipe(IItemStack out, IIngredient in) {
		addRecipe(out, in, RecipeSkytouching.DEFAULT_MINY, RecipeSkytouching.DEFAULT_MAXY, RecipeSkytouching.DEFAULT_MULTIPLIER);
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack out, IIngredient in, int minY, int maxY, int multiplier) {
		act(IncorporeticFeatures.SKYTOUCHING, () -> {
				SkytouchingRecipes.register(new RecipeSkytouchingCT(out, in, minY, maxY, multiplier));
			},
			"adding new skytouching recipe:",
			"output:", out.toCommandString(),
			"input:", in.toCommandString(),
			"minY:", minY,
			"maxY:", maxY,
			"multiplier:", multiplier
		);
	}
	
	@ZenMethod
	public static void remove(IItemStack removalTarget) {
		act(IncorporeticFeatures.SKYTOUCHING, () -> {
			SkytouchingRecipes.removeIf(r -> {
				for(ItemStack inStack : r.getGenericInputs()) {
					if(CraftTweakerMC.matches(removalTarget, inStack)) return true;
				}
				
				return false;
			});
		}, "removing recipes that match", removalTarget.toCommandString());
	}
	
	@ZenMethod
	public static void removeAll() {
		act(IncorporeticFeatures.SKYTOUCHING, SkytouchingRecipes::clear, "clearing all skytouching recipes >:D");
	}
	
	private static void act(IFeature requiredFeature, Runnable action, Object... message) {
		if(!IncorporeticFeatures.isEnabled(requiredFeature)) {
			CraftTweakerAPI.logError("Feature " + requiredFeature.name() + " is not enabled in Incorporeal's settings and cannot be used from CraftTweaker!");
			return;
		}
		
		ACTIONS.add(new IAction() {
			@Override
			public void apply() {
				action.run();
			}
			
			@Override
			public String describe() {
				StringBuilder wow = new StringBuilder();
				
				for(int i = 0; i < message.length; i++) {
					wow.append(message[i].toString());
					if(i != message.length - 1) wow.append(' ');
				}
				
				return wow.toString();
			}
		});
	}
	
	//internal stuff
	public static List<IAction> ACTIONS = new LinkedList<>();
	
	public static void init() {
		if(!IncorporeticFeatures.isEnabled(IncorporeticFeatures.CRAFTTWEAKER_COMPAT)) {
			CraftTweakerAPI.logError("CraftTweaker compat is disabled in Incorporeal's settings.");
			return;
		}
		
		try {
			ACTIONS.forEach(CraftTweakerAPI::apply);
		} catch(RuntimeException e) {
			CraftTweakerAPI.logError("There was a problem applying an Incorporeal action");
			StringWriter out = new StringWriter();
			e.printStackTrace(new PrintWriter(out));
			CraftTweakerAPI.logError(out.toString());
		}
	}
}
