package quaternary.incorporeal.flower;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.crafting.ModPetalRecipes;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public final class IncorporeticPetalRecipes {
	private IncorporeticPetalRecipes() {}
	
	public static RecipePetals sanvocalia;
	
	public static RecipePetals sweetAlexum;
	
	public static void init() {
		sanvocalia = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType("sanvocalia"), ModPetalRecipes.white, ModPetalRecipes.orange, ModPetalRecipes.orange, ModPetalRecipes.red, ModPetalRecipes.runeLust, ModPetalRecipes.pixieDust);
		
		sweetAlexum = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType("sweet_alexum"), ModPetalRecipes.red, ModPetalRecipes.orange, ModPetalRecipes.yellow, ModPetalRecipes.lime, ModPetalRecipes.lightBlue, ModPetalRecipes.purple, ModPetalRecipes.redstoneRoot);
	}
}
