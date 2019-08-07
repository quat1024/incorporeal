package quaternary.incorporeal.feature.corporetics.lexicon;

import quaternary.incorporeal.core.LexiconModule;
import quaternary.incorporeal.feature.corporetics.block.CorporeticsBlocks;
import quaternary.incorporeal.feature.corporetics.item.CorporeticsItems;
import quaternary.incorporeal.feature.corporetics.recipe.CorporeticsPetalRecipes;
import vazkii.botania.api.lexicon.LexiconEntry;

import static vazkii.botania.api.BotaniaAPI.basicKnowledge;
import static vazkii.botania.api.BotaniaAPI.categoryDevices;
import static vazkii.botania.api.BotaniaAPI.categoryEnder;
import static vazkii.botania.api.BotaniaAPI.categoryFunctionalFlowers;
import static vazkii.botania.api.BotaniaAPI.categoryTools;
import static vazkii.botania.api.BotaniaAPI.elvenKnowledge;

public class CorporeticsLexicon extends LexiconModule {
	//pre-elven
	public static LexiconEntry frameTinkerer;
	public static LexiconEntry sweetAlexum;
	
	//elven
	public static LexiconEntry fracturedSpace;
	public static LexiconEntry ticketConjurer;
	
	public static LexiconEntry corporeaInhibitor;
	public static LexiconEntry corporeaSolidifier;
	public static LexiconEntry corporeaTinkerer;
	public static LexiconEntry corporeaRetainerDecrementer;
	
	public static LexiconEntry sanvocalia;
	public static LexiconEntry redStringLiar;
	
	public static void register() {
		frameTinkerer = craftingEntry(CorporeticsBlocks.FRAME_TINKERER, categoryDevices, basicKnowledge, 1);
		sweetAlexum = flowerEntry("sweet_alexum", CorporeticsPetalRecipes.sweetAlexum, categoryFunctionalFlowers, basicKnowledge, 2);
		
		fracturedSpace = craftingEntry(CorporeticsItems.FRACTURED_SPACE_ROD, categoryTools, elvenKnowledge, 2);
		ticketConjurer = craftingEntry(CorporeticsItems.TICKET_CONJURER, categoryEnder, elvenKnowledge, 2);
		
		corporeaInhibitor = craftingEntry(CorporeticsBlocks.CORPOREA_INHIBITOR, categoryEnder, elvenKnowledge, 1);
		corporeaSolidifier = craftingEntry(CorporeticsBlocks.CORPOREA_SOLIDIFIER, categoryEnder, elvenKnowledge, 2);
		corporeaTinkerer = craftingEntry(CorporeticsBlocks.CORPOREA_SPARK_TINKERER, categoryEnder, elvenKnowledge, 2);
		corporeaRetainerDecrementer = craftingEntry(CorporeticsBlocks.CORPOREA_RETAINER_DECREMENTER, categoryEnder, elvenKnowledge, 2);
		
		sanvocalia = flowerEntry("sanvocalia", CorporeticsPetalRecipes.sanvocalia, categoryFunctionalFlowers, elvenKnowledge, 1);
		
		redStringLiar = craftingEntry(CorporeticsBlocks.RED_STRING_LIAR, categoryEnder, elvenKnowledge, 2);
	}
}
