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
import quaternary.incorporeal.feature.skytouching.lexicon.PageSkytouching;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CygnusNetworkLexicon extends LexiconModule {
	public static LexiconCategory category;
	public static KnowledgeType knowledge;
	public static final List<Consumer<List<LexiconPage>>> FUNNELABLE_DOCUMENTERS = new ArrayList<>();
	
	public static LexiconEntry CYGNUS_BASICS;
	public static LexiconEntry CYGNUS_TYPES;
	
	public static LexiconEntry CYGNUS_SPARKS;
	public static LexiconEntry CYGNUS_FUNNEL;
	
	public static LexiconEntry CYGNUS_WORD;
	public static LexiconEntry CYGNUS_CRYSTAL_CUBE;
	public static LexiconEntry CYGNUS_RETAINER;
	
	public static void register() {
		category = new LexiconCategory("incorporeal.category.cygnus") {
			//TODO update botania to the one that lets you hide categories lmao
		}.setIcon(new ResourceLocation(Incorporeal.MODID, "TODO"));
		BotaniaAPI.addCategory(category);
		
		knowledge = BotaniaAPI.registerKnowledgeType("incorporeal.cygnus", TextFormatting.DARK_AQUA, false);
		
		///
		
		CYGNUS_BASICS = justTextEntry("cygnus_basics", ItemStack.EMPTY, category, knowledge, 5).setPriority();
		CYGNUS_TYPES = justTextEntry("cygnus_types", ItemStack.EMPTY, category, knowledge, 1).setPriority();
		
		CYGNUS_SPARKS = skytouchingEntry(CygnusNetworkItems.MASTER_CYGNUS_SPARK, CygnusSkytouchingRecipes.masterCygnusSpark, category, knowledge, 3);
		CYGNUS_SPARKS.setLexiconPages(new PageSkytouching("asdfghjkl", CygnusSkytouchingRecipes.cygnusSpark));
		CYGNUS_SPARKS.addExtraDisplayedRecipe(new ItemStack(CygnusNetworkItems.CYGNUS_SPARK));
		
		CYGNUS_FUNNEL = skytouchingEntry(CygnusNetworkBlocks.FUNNEL, CygnusSkytouchingRecipes.cygnusFunnel, category, knowledge, 3);
		
		CYGNUS_WORD = skytouchingEntry(CygnusNetworkBlocks.WORD, CygnusSkytouchingRecipes.cygnusWord, category, knowledge, 3);
		
		CYGNUS_CRYSTAL_CUBE = skytouchingEntry(CygnusNetworkBlocks.CRYSTAL_CUBE, CygnusSkytouchingRecipes.cygnusCrystalCube, category, knowledge, 3);
		
		CYGNUS_RETAINER = skytouchingEntry(CygnusNetworkBlocks.RETAINER, CygnusSkytouchingRecipes.cygnusRetainer, category, knowledge, 3);
		
		///
		
		List<LexiconPage> pages = new ArrayList<>();
		CygnusRegistries.DATATYPES.forEach(t -> t.document(pages));
		pages.forEach(CYGNUS_TYPES::addPage);
		
		pages.clear();
		FUNNELABLE_DOCUMENTERS.forEach(d -> d.accept(pages));
		CygnusRegistries.LOOSE_FUNNELABLES.forEach(l -> l.document(pages));
		pages.forEach(CYGNUS_FUNNEL::addPage);
		
		pages.clear();
		CygnusRegistries.ACTIONS.forEach(a -> a.document(pages));
		pages.forEach(CYGNUS_WORD::addPage);
		
		pages.clear();
		CygnusRegistries.CONDITIONS.forEach(c -> c.document(pages));
		pages.forEach(CYGNUS_CRYSTAL_CUBE::addPage);
	}
	
	protected static LexiconEntry skytouchingEntry(IForgeRegistryEntry<?> subject, IRecipeSkytouching recipe, LexiconCategory category, KnowledgeType knowledge, int pageCount) {
		ItemStack icon = toIcon(subject);
		String name = icon.getItem().getRegistryName().toString().replace(':', '.'); //fuckin fight me!
		LexiconPage finalPage = new PageSkytouching(".flavor", recipe);
		return entryWithFinalPage(name, icon, category, knowledge, pageCount, finalPage);
	}
}
