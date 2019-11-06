package quaternary.incorporeal.feature.cygnusnetwork.lexicon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.core.LexiconModule;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusRegistries;
import quaternary.incorporeal.feature.cygnusnetwork.block.CygnusNetworkBlocks;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;
import quaternary.incorporeal.feature.cygnusnetwork.recipe.CygnusSkytouchingRecipes;
import quaternary.incorporeal.feature.skytouching.lexicon.PageSkytouching;
import quaternary.incorporeal.feature.skytouching.lexicon.SkytouchingLexicon;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.ILexicon;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CygnusNetworkLexicon extends LexiconModule {
	public static LexiconCategory category;
	public static final List<Consumer<List<LexiconPage>>> FUNNELABLE_DOCUMENTERS = new ArrayList<>();
	
	public static LexiconEntry CYGNUS_BASICS;
	public static LexiconEntry CYGNUS_TYPES;
	
	public static LexiconEntry CYGNUS_SPARKS;
	public static LexiconEntry CYGNUS_FUNNEL;
	
	public static LexiconEntry CYGNUS_WORD;
	public static LexiconEntry CYGNUS_CRYSTAL_CUBE;
	public static LexiconEntry CYGNUS_RETAINER;
	public static LexiconEntry CYGNUS_TICKET;
	
	public static void earlyRegister() {
		category = new LexiconCategory("incorporeal.category.cygnus") {
			@Override
			public boolean isVisible(ItemStack stack) {
				//Are u happy HUBRY
				return ((ILexicon) stack.getItem()).isKnowledgeUnlocked(stack, SkytouchingLexicon.knowledge);
			}
		}.setIcon(new ResourceLocation(Incorporeal.MODID, "textures/lexicon/categories/cygnus.png")).setPriority(1);
		BotaniaAPI.addCategory(category);
	}
	
	public static void register() {
		CYGNUS_BASICS = justTextEntry("cygnus_basics", ItemStack.EMPTY, category, SkytouchingLexicon.knowledge, 5).setPriority();
		CYGNUS_TYPES = justTextEntry("cygnus_types", ItemStack.EMPTY, category, SkytouchingLexicon.knowledge, 1).setPriority();
		
		CYGNUS_SPARKS = skytouchingEntry(CygnusNetworkItems.MASTER_CYGNUS_SPARK, CygnusSkytouchingRecipes.masterCygnusSpark, category, SkytouchingLexicon.knowledge, 3);
		CYGNUS_SPARKS.setLexiconPages(new PageSkytouching(".flavor2", CygnusSkytouchingRecipes.cygnusSpark));
		CYGNUS_SPARKS.addExtraDisplayedRecipe(new ItemStack(CygnusNetworkItems.CYGNUS_SPARK));
		
		CYGNUS_FUNNEL = skytouchingEntry(CygnusNetworkBlocks.FUNNEL, CygnusSkytouchingRecipes.cygnusFunnel, category, SkytouchingLexicon.knowledge, 3);
		
		CYGNUS_WORD = skytouchingEntry(CygnusNetworkBlocks.WORD, CygnusSkytouchingRecipes.cygnusWord, category, SkytouchingLexicon.knowledge, 3);
		
		
		CYGNUS_CRYSTAL_CUBE = skytouchingEntry(CygnusNetworkBlocks.CRYSTAL_CUBE, CygnusSkytouchingRecipes.cygnusCrystalCube, category, SkytouchingLexicon.knowledge, 2);
		
		CYGNUS_RETAINER = skytouchingEntry(CygnusNetworkBlocks.RETAINER, CygnusSkytouchingRecipes.cygnusRetainer, category, SkytouchingLexicon.knowledge, 3);
		
		CYGNUS_TICKET = justTextEntry("cygnus_ticket", new ItemStack(CygnusNetworkItems.CYGNUS_TICKET), category, SkytouchingLexicon.knowledge, 2);
		
		///
		
		List<LexiconPage> pages = new ArrayList<>();
		
		//datatypes
		CygnusRegistries.DATATYPES.forEach(t -> t.document(pages));
		pages.forEach(CYGNUS_TYPES::addPage);
		pages.clear();
		
		//funnelables
		FUNNELABLE_DOCUMENTERS.forEach(d -> d.accept(pages));
		CygnusRegistries.LOOSE_FUNNELABLES.forEach(l -> l.document(pages));
		pages.forEach(CYGNUS_FUNNEL::addPage);
		pages.clear();
		
		//actions (words)
		CygnusRegistries.ACTIONS.forEach(a -> a.document(pages));
		pages.forEach(CYGNUS_WORD::addPage);
		pages.clear();
		
		//conditions (crystal cubes)
		CygnusRegistries.CONDITIONS.forEach(c -> c.document(pages));
		pages.forEach(CYGNUS_CRYSTAL_CUBE::addPage);
	}
}
