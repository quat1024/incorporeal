package quaternary.incorporeal.core;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.feature.IFeature;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.CompatLexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import javax.annotation.Nullable;

public abstract class LexiconModule {	
	protected static LexiconEntry craftingEntry(IFeature feature, IForgeRegistryEntry subject, LexiconCategory category, KnowledgeType knowledgeType, int pageCount) {
		String name = Incorporeal.MODID + '.' + Preconditions.checkNotNull(subject.getRegistryName()).getPath();
		ItemStack icon;
		
		if(subject instanceof Block) {
			icon = new ItemStack((Block) subject);
		} else if(subject instanceof Item) {
			icon = new ItemStack((Item) subject);
		} else {
			throw new IllegalArgumentException("Can't determine the lexicon page item for " + subject + " if you see this quat is a big stupid");
		}
		
		ResourceLocation unprefixed = subject.getRegistryName();
		ResourceLocation prefixed = new ResourceLocation(
			unprefixed.getNamespace(),
			feature.name() + "/" + unprefixed.getPath()
		);
		
		LexiconPage terminalPage = new PageCraftingRecipe(".flavor", prefixed);
		
		return entryWithFinalPage(name, icon, category, knowledgeType, pageCount, terminalPage);
	}
	
	protected static LexiconEntry flowerEntry(String flowerName, RecipePetals craftingRecipe, LexiconCategory category, KnowledgeType knowledgeType, int pageCount) {
		ItemStack icon = ItemBlockSpecialFlower.ofType(flowerName);
		LexiconPage finalPage = new PagePetalRecipe<>(".flavor", craftingRecipe);
		return entryWithFinalPage(flowerName, icon, category, knowledgeType, pageCount, finalPage);
	}
	
	protected static LexiconEntry entryWithFinalPage(String name, ItemStack icon, LexiconCategory category, KnowledgeType knowledgeType, int pageCount, @Nullable LexiconPage finalPage) {
		LexiconEntry entry = new CompatLexiconEntry(name, category, Incorporeal.NAME);
		for(int i = 0; i < pageCount; i++) {
			//Just using addPage is tempting but for some reason the
			//unlocalized name gets hacked into shape in BasicLexiconEntry
			//But only if you don't use addPage ¯\_(ツ)_/¯
			entry.setLexiconPages(new PageText(String.valueOf(i)));
		}
		
		if(finalPage != null) entry.setLexiconPages(finalPage);
		
		entry.setIcon(icon);
		entry.setKnowledgeType(knowledgeType);
		
		return entry;
	}
}
