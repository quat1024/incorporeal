package quaternary.incorporeal.feature.cygnusnetwork.lexicon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.IForgeRegistryEntry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.core.LexiconModule;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusRegistries;
import quaternary.incorporeal.feature.cygnusnetwork.block.CygnusNetworkBlocks;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;
import quaternary.incorporeal.feature.cygnusnetwork.recipe.CygnusSkytouchingRecipes;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.lexicon.page.PageText;

import java.util.ArrayList;
import java.util.List;

public class CygnusNetworkLexicon extends LexiconModule {
	public static LexiconCategory category;
	public static KnowledgeType knowledge;
	
	public static LexiconEntry MASTER_CYGNUS_SPARK;
	public static LexiconEntry CYGNUS_SPARK;
	
	public static LexiconEntry CYGNUS_FUNNEL;
	public static LexiconEntry CYGNUS_TICKET;
	
	public static LexiconEntry CYGNUS_WORD;
	public static LexiconEntry CYGNUS_CRYSTAL_CUBE;
	public static LexiconEntry CYGNUS_RETAINER;
	
	public static void register() {
		category = new LexiconCategory("incorporeal.category.cygnus") {
			//TODO update botania to the one that lets you hide categories lmao
		}.setIcon(new ResourceLocation(Incorporeal.MODID, "TODO"));
		BotaniaAPI.addCategory(category);
		
		knowledge = BotaniaAPI.registerKnowledgeType("incorporeal.cygnus", TextFormatting.RED, false);
		
		///
		
		MASTER_CYGNUS_SPARK = skytouchingEntry(CygnusNetworkItems.MASTER_CYGNUS_SPARK, CygnusSkytouchingRecipes.masterCygnusSpark, category, knowledge, 4);
		
		CYGNUS_SPARK = skytouchingEntry(CygnusNetworkItems.CYGNUS_SPARK, CygnusSkytouchingRecipes.cygnusSpark, category, knowledge, 4);
		
		CYGNUS_FUNNEL = skytouchingEntry(CygnusNetworkBlocks.FUNNEL, CygnusSkytouchingRecipes.cygnusFunnel, category, knowledge, 5);
		
		//TODO: *remove* this crafting recipe, make it use a funnelable and you just shove data onto a sheet of paper
		//CYGNUS_TICKET
		
		CYGNUS_WORD = skytouchingEntry(CygnusNetworkBlocks.WORD, CygnusSkytouchingRecipes.cygnusWord, category, knowledge, 3);
		List<LexiconPage> pages = new ArrayList<>();
		CygnusRegistries.ACTIONS.forEach(a -> a.document(pages));
		pages.forEach(CYGNUS_WORD::addPage);
		
		CYGNUS_CRYSTAL_CUBE = skytouchingEntry(CygnusNetworkBlocks.CRYSTAL_CUBE, CygnusSkytouchingRecipes.cygnusCrystalCube, category, knowledge, 3);
		pages.clear();
		CygnusRegistries.CONDITIONS.forEach(c -> c.document(pages));
		pages.forEach(CYGNUS_CRYSTAL_CUBE::addPage);
		
		CYGNUS_RETAINER = skytouchingEntry(CygnusNetworkBlocks.RETAINER, CygnusSkytouchingRecipes.cygnusRetainer, category, knowledge, 2);
	}
	
	protected static LexiconEntry skytouchingEntry(IForgeRegistryEntry<?> subject, IRecipeSkytouching recipe, LexiconCategory category, KnowledgeType knowledge, int pageCount) {
		ItemStack icon = toIcon(subject);
		String name = icon.getItem().getRegistryName().toString().replace(':', '.'); //fuckin fight me!
		LexiconPage finalPage = new PageText("TODO recipeSkytouching page");
		return entryWithFinalPage(name, icon, category, knowledge, pageCount, finalPage);
	}
}
