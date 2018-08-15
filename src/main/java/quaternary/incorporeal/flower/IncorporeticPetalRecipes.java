package quaternary.incorporeal.flower;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import static vazkii.botania.common.crafting.ModPetalRecipes.*;

public final class IncorporeticPetalRecipes {
	private IncorporeticPetalRecipes() {}
	
	public static RecipePetals sanvocalia;
	
	public static RecipePetals sweetAlexum;
	
	public static void init() {
		sanvocalia = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType("sanvocalia"), white, orange, orange, red, runeLust, pixieDust, redstoneRoot);
		
		sweetAlexum = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType("sweet_alexum"), red, orange, yellow, lime, lightBlue, purple, redstoneRoot);
	}
}
