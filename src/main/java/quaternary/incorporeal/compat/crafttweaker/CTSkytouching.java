package quaternary.incorporeal.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.recipe.skytouch.IncorporeticSkytouchingRecipes;
import quaternary.incorporeal.recipe.skytouch.RecipeSkytouching;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

@ZenRegister
@ZenClass("mods.incorporeal.Skytouching")
public class CTSkytouching {
	//zen stuff
	
	@ZenMethod
	public static void addRecipe(IItemStack out, IIngredient in) {
		addRecipe(out, in, RecipeSkytouching.DEFAULT_MINY, RecipeSkytouching.DEFAULT_MAXY, RecipeSkytouching.DEFAULT_MULTIPLIER);
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack out, IIngredient in, int minY, int maxY, int multiplier) {
		act(() -> {
			IncorporeticSkytouchingRecipes.register(new RecipeSkytouchingCT(out, in, minY, maxY, multiplier));
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
		act(() -> {
			IncorporeticSkytouchingRecipes.removeIf(r -> {
				for(ItemStack inStack : r.getGenericInputs()) {
					if(CraftTweakerMC.matches(removalTarget, inStack)) return true;
				}
				
				return false;
			});
		}, "removing recipes that match", removalTarget.toCommandString());
	}
	
	@ZenMethod
	public static void removeAll() {
		act(IncorporeticSkytouchingRecipes::clear, "clearing all skytouching recipes >:D");
	}
	
	private static void act(Runnable action, Object... message) {
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
		try {
			ACTIONS.forEach(CraftTweakerAPI::apply);
		} catch(Exception e) {
			CraftTweakerAPI.logError("There was a problem applying an Incorporeal action");
			StringWriter out = new StringWriter();
			e.printStackTrace(new PrintWriter(out));
			CraftTweakerAPI.logError(out.toString());
		}
	}
}
