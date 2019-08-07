package quaternary.incorporeal.feature.decorative.lexicon;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.core.LexiconModule;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.CompatLexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import java.util.Arrays;
import java.util.stream.Collectors;

import static vazkii.botania.api.BotaniaAPI.categoryMisc;

public class DecorativeLexicon extends LexiconModule {
	public static LexiconEntry unstableCubes;
	
	public static void register() {
		unstableCubes = new CompatLexiconEntry("incorporeal.unstableCubes", categoryMisc, Incorporeal.NAME).setLexiconPages(
			new PageText("0"),
			new PageCraftingRecipe(".flavor",
				Arrays.stream(EnumDyeColor.values())
					.map(color -> "decorative/unstable_cube/unstable_cube_" + color.getName())
					.map(str -> new ResourceLocation(Incorporeal.MODID, str))
					.collect(Collectors.toList())
			)
		);
	}
}
