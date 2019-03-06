package quaternary.incorporeal.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import quaternary.incorporeal.recipe.skytouch.IncorporeticSkytouchingRecipes;
import quaternary.incorporeal.recipe.skytouch.RecipeSkytouching;

@JEIPlugin
public class IncorporealJeiPlugin implements IModPlugin {
	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {
		reg.addRecipeCategories(new SkytouchRecipeCategory(reg.getJeiHelpers()));
	}
	
	@Override
	public void register(IModRegistry reg) {
		reg.handleRecipes(RecipeSkytouching.class, SkytouchRecipeWrapper::new, SkytouchRecipeCategory.UID);
		reg.addRecipes(IncorporeticSkytouchingRecipes.ALL, SkytouchRecipeCategory.UID);
	}
}
