package quaternary.incorporeal.recipe;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import static vazkii.botania.common.crafting.ModPetalRecipes.lightBlue;
import static vazkii.botania.common.crafting.ModPetalRecipes.lime;
import static vazkii.botania.common.crafting.ModPetalRecipes.orange;
import static vazkii.botania.common.crafting.ModPetalRecipes.pixieDust;
import static vazkii.botania.common.crafting.ModPetalRecipes.purple;
import static vazkii.botania.common.crafting.ModPetalRecipes.red;
import static vazkii.botania.common.crafting.ModPetalRecipes.redstoneRoot;
import static vazkii.botania.common.crafting.ModPetalRecipes.runeLust;
import static vazkii.botania.common.crafting.ModPetalRecipes.white;
import static vazkii.botania.common.crafting.ModPetalRecipes.yellow;

public final class IncorporeticPetalRecipes {
	private IncorporeticPetalRecipes() {}
	
	public static RecipePetals sanvocalia;
	
	public static RecipePetals sweetAlexum;
	
	public static void init() {
		sanvocalia = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType("sanvocalia"), white, orange, orange, red, runeLust, pixieDust, redstoneRoot);
		
		sweetAlexum = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType("sweet_alexum"), red, orange, yellow, lime, lightBlue, purple, redstoneRoot);
	}
}
