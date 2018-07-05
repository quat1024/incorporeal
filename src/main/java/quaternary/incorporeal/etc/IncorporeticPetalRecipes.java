package quaternary.incorporeal.etc;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.crafting.ModPetalRecipes;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public class IncorporeticPetalRecipes {
	public static RecipePetals sanvocalia;
	
	public static void init() {
		sanvocalia = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType("sanvocalia"), ModPetalRecipes.white, ModPetalRecipes.orange, ModPetalRecipes.orange, ModPetalRecipes.red, ModPetalRecipes.runeLust, ModPetalRecipes.pixieDust);
	}
}
