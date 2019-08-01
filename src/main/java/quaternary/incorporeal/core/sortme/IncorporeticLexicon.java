package quaternary.incorporeal.core.sortme;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.feature.corporetics.block.CorporeticsBlocks;
import quaternary.incorporeal.feature.corporetics.item.CorporeticsItems;
import quaternary.incorporeal.feature.corporetics.recipe.CorporeticsPetalRecipes;
import quaternary.incorporeal.feature.naturaldevices.item.NaturalDevicesItems;
import quaternary.incorporeal.feature.soulcores.item.SoulCoresItems;
import quaternary.incorporeal.feature.soulcores.recipe.SoulCoresRuneRecipes;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.CompatLexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageRuneRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class IncorporeticLexicon {
	private IncorporeticLexicon() {}
	
	public static LexiconCategory categoryCorporetic = null;
	
	//PRE-ELVEN
	public static LexiconEntry frameTinkerer;
	public static LexiconEntry naturalDevices;
	public static LexiconEntry unstableCubes;
	
	public static LexiconEntry sweetAlexum;
	
	//ELVEN
	//items
	public static LexiconEntry fracturedSpace;
	public static LexiconEntry ticketConjurer;
	
	//corporea blocks
	public static LexiconEntry corporeaInhibitor;
	public static LexiconEntry corporeaSolidifier;
	public static LexiconEntry corporeaTinkerer;
	public static LexiconEntry corporeaRetainerDecrementer;
	public static LexiconEntry soulCores;
	
	//etc
	public static LexiconEntry sanvocalia;
	public static LexiconEntry redStringLiar;
	
	public static void init() {
		//init stuff
		categoryCorporetic = BotaniaAPI.categoryEnder;
		
		/////
		newEntryType = BotaniaAPI.basicKnowledge; //Basic Knowledge entries
		/////
		
		frameTinkerer = buildCraftingEntry(CorporeticsBlocks.FRAME_TINKERER, BotaniaAPI.categoryDevices, 1);
		
		//tfw u build castles of abstractions but they come crashing down on you
		naturalDevices = new CompatLexiconEntry("incorporeal.naturalDevices", BotaniaAPI.categoryDevices, Incorporeal.NAME).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"));
		naturalDevices.setIcon(new ItemStack(NaturalDevicesItems.NATURAL_COMPARATOR));
		naturalDevices.addExtraDisplayedRecipe(new ItemStack(NaturalDevicesItems.NATURAL_REPEATER));
		naturalDevices.addExtraDisplayedRecipe(new ItemStack(ModItems.manaResource, 1, 6));
		naturalDevices.setKnowledgeType(newEntryType);
		
		unstableCubes = new CompatLexiconEntry("incorporeal.unstableCubes", BotaniaAPI.categoryMisc, Incorporeal.NAME).setLexiconPages(
			new PageText("0"),
			new PageCraftingRecipe(".flavor", 
				Arrays.stream(EnumDyeColor.values())
				.map(color -> "decorative/unstable_cube/unstable_cube_" + color.getName())
				.map(str -> new ResourceLocation(Incorporeal.MODID, str))
				.collect(Collectors.toList())
			)
		);
		//anyhoo
		
		sweetAlexum = buildFlowerEntry("sweet_alexum", CorporeticsPetalRecipes.sweetAlexum, BotaniaAPI.categoryFunctionalFlowers, 2);
		
		/////
		newEntryType = BotaniaAPI.elvenKnowledge; //Elven Knowledge entries
		/////
		
		fracturedSpace = buildCraftingEntry(CorporeticsItems.FRACTURED_SPACE_ROD, BotaniaAPI.categoryTools, 2);
		ticketConjurer = buildCraftingEntry(CorporeticsItems.TICKET_CONJURER, categoryCorporetic, 2);
		
		corporeaInhibitor = buildCraftingEntry(CorporeticsBlocks.CORPOREA_INHIBITOR, categoryCorporetic, 1);
		corporeaSolidifier = buildCraftingEntry(CorporeticsBlocks.CORPOREA_SOLIDIFIER, categoryCorporetic, 2);
		corporeaTinkerer = buildCraftingEntry(CorporeticsBlocks.CORPOREA_SPARK_TINKERER, categoryCorporetic, 2);
		corporeaRetainerDecrementer = buildCraftingEntry(CorporeticsBlocks.CORPOREA_RETAINER_DECREMENTER, categoryCorporetic, 2);
		
		soulCores = new CompatLexiconEntry("incorporeal.soulCores", categoryCorporetic, Incorporeal.NAME);
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
		soulCores.setKnowledgeType(newEntryType);
		soulCores.addExtraDisplayedRecipe(new ItemStack(SoulCoresItems.CORPOREA_SOUL_CORE));
		soulCores.addExtraDisplayedRecipe(new ItemStack(SoulCoresItems.ENDER_SOUL_CORE));
		soulCores.setIcon(new ItemStack(SoulCoresItems.CORPOREA_SOUL_CORE));
		
		sanvocalia = buildFlowerEntry("sanvocalia", CorporeticsPetalRecipes.sanvocalia, BotaniaAPI.categoryFunctionalFlowers, 1);
		
		redStringLiar = buildCraftingEntry(CorporeticsBlocks.RED_STRING_LIAR, categoryCorporetic, 2);
	}
	
	private static LexiconEntry buildCraftingEntry(IForgeRegistryEntry subject, LexiconCategory category, int pageCount) {
		String name = Incorporeal.MODID + '.' + Preconditions.checkNotNull(subject.getRegistryName()).getPath();
		ItemStack icon;
		
		if(subject instanceof Block) {
			icon = new ItemStack((Block) subject);
		} else if (subject instanceof Item) {
			icon = new ItemStack((Item) subject);
		} else {
			throw new IllegalArgumentException("Can't determine the lexicon page item for " + subject + " if you see this quat is a big stupid");
		}
		
		LexiconPage terminalPage = new PageCraftingRecipe(".flavor", subject.getRegistryName());
		
		return buildEntryInternal(name, icon, category, pageCount, terminalPage);
	}
	
	private static LexiconEntry buildFlowerEntry(String name, RecipePetals recipe, LexiconCategory category, int pageCount) {
		ItemStack icon = ItemBlockSpecialFlower.ofType(name);
		
		LexiconPage terminalPage = new PagePetalRecipe<>(".flavor", recipe);
		
		return buildEntryInternal(name, icon, category, pageCount, terminalPage);
	}
	
	//A little global state to save typing never hurt anyone
	//Only tiny potato can judge me
	private static KnowledgeType newEntryType = BotaniaAPI.basicKnowledge;
	
	private static LexiconEntry buildEntryInternal(String name, ItemStack icon, LexiconCategory category, int pageCount, LexiconPage terminalPage) {		
		LexiconEntry entry = new CompatLexiconEntry(name, category, Incorporeal.NAME);
		for(int i=0; i < pageCount; i++) {
			//Just using addPage is tempting but for some reason the
			//unlocalized name gets hacked into shape in BasicLexiconEntry
			//But only if you don't use addPage ¯\_(ツ)_/¯
			entry.setLexiconPages(new PageText(String.valueOf(i)));
		}
		entry.setLexiconPages(terminalPage);
		entry.setIcon(icon);
		entry.setKnowledgeType(newEntryType);
		
		return entry;
	}
}
