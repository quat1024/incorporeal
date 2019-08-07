package quaternary.incorporeal.feature.decorative.recipe;

import net.minecraft.item.ItemStack;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import quaternary.incorporeal.feature.decorative.item.DecorativeItems;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;

public final class DecorativeRuneRecipes {
	private DecorativeRuneRecipes() {
	}
	
	public static RecipeRuneAltar lokiW;
	
	public static void register() {
		lokiW = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(DecorativeItems.LOKIW), 12000, (Object[]) EtcHelpers.fill(
			new ItemStack[10],
			(i) -> EtcHelpers.skullOf("Loki270")
		));
	}
}
