package quaternary.incorporeal.lexicon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.etc.PetalRecipes;
import quaternary.incorporeal.tile.flower.SubTileSanvocalia;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.CompatLexiconEntry;
import vazkii.botania.common.lexicon.page.*;

public class IncorporeticLexiData {
	public static LexiconEntry corporeaLiarEntry;
	public static LexiconEntry corporeaTicketsEntry;
	public static LexiconEntry corporeaTinkererEntry;
	public static LexiconEntry sanvocaliaEntry;
	public static LexiconEntry frameTinkererEntry;
	
	@GameRegistry.ItemStackHolder(value = "incorporeal:corporea_liar")
	public static final ItemStack LIAR_ICON = ItemStack.EMPTY;
	
	@GameRegistry.ItemStackHolder(value = "incorporeal:corporea_ticket")
	public static final ItemStack TICKET_ICON = ItemStack.EMPTY;
	
	@GameRegistry.ItemStackHolder(value = "incorporeal:corporea_spark_tinkerer")
	public static final ItemStack TINKERER_ICON = ItemStack.EMPTY;
	
	@GameRegistry.ItemStackHolder(value = "incorporeal:frame_tinkerer")
	public static final ItemStack FRAME_TINKERER_ICON = ItemStack.EMPTY;
	
	public static void init() {
		ResourceLocation liarRecipe = new ResourceLocation(Incorporeal.MODID, "corporea_liar");
		
		corporeaLiarEntry = new CompatLexiconEntry("corporea_liar", BotaniaAPI.categoryEnder, Incorporeal.NAME);
		corporeaLiarEntry.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe(".flavor", liarRecipe));
		corporeaLiarEntry.setKnowledgeType(BotaniaAPI.elvenKnowledge);
		corporeaLiarEntry.setIcon(LIAR_ICON);
		
		ResourceLocation solidifierRecipe = new ResourceLocation(Incorporeal.MODID, "corporea_solidifier");
		
		corporeaTicketsEntry = new CompatLexiconEntry("corporea_ticketing", BotaniaAPI.categoryEnder, Incorporeal.NAME);
		corporeaTicketsEntry.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe(".flavor", solidifierRecipe));
		corporeaTicketsEntry.setKnowledgeType(BotaniaAPI.elvenKnowledge);
		corporeaTicketsEntry.setIcon(TICKET_ICON);
		
		ResourceLocation tinkererRecipe = new ResourceLocation(Incorporeal.MODID, "corporea_spark_tinkerer");
		
		corporeaTinkererEntry = new CompatLexiconEntry("corporea_tinkerer", BotaniaAPI.categoryEnder, Incorporeal.NAME);
		corporeaTinkererEntry.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe(".flavor", tinkererRecipe));
		corporeaTinkererEntry.setKnowledgeType(BotaniaAPI.elvenKnowledge);
		corporeaTinkererEntry.setIcon(TINKERER_ICON);
		
		sanvocaliaEntry = new CompatLexiconEntry("sanvocalia", BotaniaAPI.categoryFunctionalFlowers, Incorporeal.NAME);
		sanvocaliaEntry.setLexiconPages(new PageText("0"), new PagePetalRecipe<>(".flavor", PetalRecipes.sanvocaliaRecipe));
		sanvocaliaEntry.setKnowledgeType(BotaniaAPI.elvenKnowledge);
		sanvocaliaEntry.setIcon(ItemBlockSpecialFlower.ofType(SubTileSanvocalia.NAME));
		
		frameTinkererEntry = new CompatLexiconEntry("frame_tinkerer", BotaniaAPI.categoryDevices, Incorporeal.NAME);
		frameTinkererEntry.setLexiconPages(new PageText("0"));
		frameTinkererEntry.setIcon(FRAME_TINKERER_ICON);
	}
}
