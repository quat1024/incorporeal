package quaternary.incorporeal.feature.naturaldevices.lexicon;

import net.minecraft.item.ItemStack;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.core.LexiconModule;
import quaternary.incorporeal.feature.naturaldevices.item.NaturalDevicesItems;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lexicon.CompatLexiconEntry;
import vazkii.botania.common.lexicon.page.PageText;

import static vazkii.botania.api.BotaniaAPI.basicKnowledge;
import static vazkii.botania.api.BotaniaAPI.categoryDevices;

public class NaturalDevicesLexicon extends LexiconModule {
	public static LexiconEntry naturalDevices;
	
	public static void register() {
		//tfw u build castles of abstractions but they come crashing down on you
		naturalDevices = new CompatLexiconEntry("incorporeal.naturalDevices", categoryDevices, Incorporeal.NAME).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"));
		naturalDevices.setIcon(new ItemStack(NaturalDevicesItems.NATURAL_COMPARATOR));
		naturalDevices.addExtraDisplayedRecipe(new ItemStack(NaturalDevicesItems.NATURAL_REPEATER));
		naturalDevices.addExtraDisplayedRecipe(new ItemStack(ModItems.manaResource, 1, 6));
		naturalDevices.setKnowledgeType(basicKnowledge);
	}
}
