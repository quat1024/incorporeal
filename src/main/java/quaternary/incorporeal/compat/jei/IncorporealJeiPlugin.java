package quaternary.incorporeal.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;
import quaternary.incorporeal.recipe.skytouch.IncorporeticSkytouchingRecipes;
import quaternary.incorporeal.recipe.skytouch.RecipeSkytouching;

@JEIPlugin
public class IncorporealJeiPlugin implements IModPlugin {
	@Override
	public void registerItemSubtypes(ISubtypeRegistry reg) {
		reg.registerSubtypeInterpreter(IncorporeticCygnusItems.WORD_CARD, stack -> IncorporeticCygnusItems.WORD_CARD.readValueName(stack).toString());
		reg.registerSubtypeInterpreter(IncorporeticCygnusItems.CRYSTAL_CUBE_CARD, stack -> IncorporeticCygnusItems.CRYSTAL_CUBE_CARD.readValueName(stack).toString());
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {
		reg.addRecipeCategories(new SkytouchRecipeCategory(reg.getJeiHelpers()));
	}
	
	@Override
	public void register(IModRegistry reg) {
		reg.handleRecipes(IRecipeSkytouching.class, SkytouchRecipeWrapper::new, SkytouchRecipeCategory.UID);
		reg.addRecipes(IncorporeticSkytouchingRecipes.ALL, SkytouchRecipeCategory.UID);
	}
}
