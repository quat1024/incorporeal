package quaternary.incorporeal.feature.soulcores.lexicon;

import net.minecraft.item.ItemStack;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.core.LexiconModule;
import quaternary.incorporeal.feature.soulcores.item.SoulCoresItems;
import quaternary.incorporeal.feature.soulcores.recipe.SoulCoresRuneRecipes;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.CompatLexiconEntry;
import vazkii.botania.common.lexicon.page.PageRuneRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import static vazkii.botania.api.BotaniaAPI.categoryEnder;
import static vazkii.botania.api.BotaniaAPI.elvenKnowledge;

public class SoulCoresLexicon extends LexiconModule {
	public static LexiconEntry soulCores;
	
	public static void register() {
		soulCores = new CompatLexiconEntry("incorporeal.soulCores", categoryEnder, Incorporeal.NAME);
		soulCores.setLexiconPages(
			new PageText("0"),
			new PageText("1"),
			new PageRuneRecipe(".flavor2", SoulCoresRuneRecipes.soulCoreFrame),
			new PageText("3"),
			new PageRuneRecipe(".flavor3", SoulCoresRuneRecipes.enderSoulCore),
			new PageText("4"),
			new PageRuneRecipe(".flavor4", SoulCoresRuneRecipes.corporeaSoulCore),
			new PageText("5"),
			new PageRuneRecipe(".flavor5", SoulCoresRuneRecipes.potionSoulCore)
		);
		soulCores.setKnowledgeType(elvenKnowledge);
		soulCores.addExtraDisplayedRecipe(new ItemStack(SoulCoresItems.CORPOREA_SOUL_CORE));
		soulCores.addExtraDisplayedRecipe(new ItemStack(SoulCoresItems.ENDER_SOUL_CORE));
		soulCores.setIcon(new ItemStack(SoulCoresItems.CORPOREA_SOUL_CORE));
	}
}
