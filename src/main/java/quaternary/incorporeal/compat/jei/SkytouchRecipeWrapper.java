package quaternary.incorporeal.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import quaternary.incorporeal.recipe.skytouch.RecipeSkytouching;

public class SkytouchRecipeWrapper implements IRecipeWrapper {
	public SkytouchRecipeWrapper(RecipeSkytouching recipe) {
		this.recipe = recipe;
	}
	
	public final RecipeSkytouching recipe;
	
	@Override
	public void getIngredients(IIngredients ing) {
		ing.setInput(VanillaTypes.ITEM, recipe.in);
		ing.setOutput(VanillaTypes.ITEM, recipe.out);
	}
}
