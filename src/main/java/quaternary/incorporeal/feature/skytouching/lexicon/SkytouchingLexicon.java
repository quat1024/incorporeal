package quaternary.incorporeal.feature.skytouching.lexicon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.core.LexiconModule;
import quaternary.incorporeal.feature.skytouching.recipe.SkytouchingRecipes;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconEntry;

public class SkytouchingLexicon extends LexiconModule {
	public static LexiconEntry skytouchHint;
	
	public static KnowledgeType knowledge;
	
	public static void earlyRegister() {
		knowledge = BotaniaAPI.registerKnowledgeType("incorporeal.skytouch", TextFormatting.DARK_AQUA, false);
	}
	
	public static void register() {
		skytouchHint = entryWithFinalPage(Incorporeal.MODID + ".skytouch_hint", ItemStack.EMPTY, BotaniaAPI.categoryMisc, BotaniaAPI.elvenKnowledge, 1, new PageSkytouching(".flavor", SkytouchingRecipes.bookUpgrade));
	}
}
