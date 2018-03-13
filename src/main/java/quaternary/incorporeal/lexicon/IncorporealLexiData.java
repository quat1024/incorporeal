package quaternary.incorporeal.lexicon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.incorporeal.Incorporeal;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.CompatLexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class IncorporealLexiData {
	public static LexiconEntry corporeaLiar;
	public static LexiconEntry corporeaTickets;
	public static LexiconEntry corporeaTinkerer;
	
	@GameRegistry.ItemStackHolder(value = "incorporeal:corporea_liar")
	public static final ItemStack LIAR_ICON = ItemStack.EMPTY;
	
	@GameRegistry.ItemStackHolder(value = "incorporeal:corporea_ticket")
	public static final ItemStack TICKET_ICON = ItemStack.EMPTY;
	
	@GameRegistry.ItemStackHolder(value = "incorporeal:corporea_spark_tinkerer")
	public static final ItemStack TINKERER_ICON = ItemStack.EMPTY;
	
	public static void init() {
		ResourceLocation liarRecipe = new ResourceLocation(Incorporeal.MODID, "corporea_liar");
		
		corporeaLiar = new CompatLexiconEntry("corporea_liar", BotaniaAPI.categoryEnder, Incorporeal.NAME);
		corporeaLiar.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe(".flavor", liarRecipe));
		corporeaLiar.setKnowledgeType(BotaniaAPI.elvenKnowledge);
		corporeaLiar.setIcon(LIAR_ICON);
		
		ResourceLocation solidifierRecipe = new ResourceLocation(Incorporeal.MODID, "corporea_solidifier");
		ResourceLocation liquifierRecipe = new ResourceLocation(Incorporeal.MODID, "corporea_liquifier");
		
		corporeaTickets = new CompatLexiconEntry("corporea_ticketing", BotaniaAPI.categoryEnder, Incorporeal.NAME);
		corporeaTickets.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe(".flavor1", solidifierRecipe), new PageCraftingRecipe(".flavor2", liquifierRecipe));
		corporeaTickets.setKnowledgeType(BotaniaAPI.elvenKnowledge);
		corporeaTickets.setIcon(TICKET_ICON);
		
		ResourceLocation tinkererRecipe = new ResourceLocation(Incorporeal.MODID, "corporea_spark_tinkerer");
		
		corporeaTinkerer = new CompatLexiconEntry("corporea_tinkerer", BotaniaAPI.categoryEnder, Incorporeal.NAME);
		corporeaTinkerer.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe(".flavor", tinkererRecipe));
		corporeaTinkerer.setKnowledgeType(BotaniaAPI.elvenKnowledge);
		corporeaTinkerer.setIcon(TINKERER_ICON);
	}
}
