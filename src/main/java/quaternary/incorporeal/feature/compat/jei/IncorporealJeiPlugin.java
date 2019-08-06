package quaternary.incorporeal.feature.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import quaternary.incorporeal.IncorporeticFeatures;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;
import quaternary.incorporeal.feature.skytouching.recipe.SkytouchingRecipes;

@JEIPlugin
public class IncorporealJeiPlugin implements IModPlugin {
	@Override
	public void registerItemSubtypes(ISubtypeRegistry reg) {
		if(!IncorporeticFeatures.isEnabled(IncorporeticFeatures.JEI_COMPAT)) return;
		
		if(IncorporeticFeatures.isEnabled(IncorporeticFeatures.CYGNUS_NETWORK)) {
			reg.registerSubtypeInterpreter(CygnusNetworkItems.WORD_CARD, stack -> CygnusNetworkItems.WORD_CARD.readValueName(stack).toString());
			reg.registerSubtypeInterpreter(CygnusNetworkItems.CRYSTAL_CUBE_CARD, stack -> CygnusNetworkItems.CRYSTAL_CUBE_CARD.readValueName(stack).toString());
		}
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {
		if(!IncorporeticFeatures.isEnabled(IncorporeticFeatures.JEI_COMPAT)) return;
		
		if(IncorporeticFeatures.isEnabled(IncorporeticFeatures.SKYTOUCHING)) {
			reg.addRecipeCategories(new SkytouchRecipeCategory(reg.getJeiHelpers()));
		}
	}
	
	@Override
	public void register(IModRegistry reg) {
		if(!IncorporeticFeatures.isEnabled(IncorporeticFeatures.JEI_COMPAT)) return;
		
		if(IncorporeticFeatures.isEnabled(IncorporeticFeatures.SKYTOUCHING)) {
			reg.handleRecipes(IRecipeSkytouching.class, SkytouchRecipeWrapper::new, SkytouchRecipeCategory.UID);
			reg.addRecipes(SkytouchingRecipes.ALL, SkytouchRecipeCategory.UID);
		}
	}
}
