package quaternary.incorporeal.feature.decorative.lexicon;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.core.LexiconModule;
import quaternary.incorporeal.feature.decorative.block.DecorativeBlocks;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.CompatLexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static vazkii.botania.api.BotaniaAPI.categoryMisc;
import static vazkii.botania.api.BotaniaAPI.elvenKnowledge;

public class DecorativeLexicon extends LexiconModule {
	public static LexiconEntry unstableCubes;
	
	public static LexiconEntry elvenDecoration;
	
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
		
		elvenDecoration = new CompatLexiconEntry("incorporeal.elvenDecoration", categoryMisc, Incorporeal.NAME)
			.setLexiconPages(
				new PageText("0"),
				new PageCraftingRecipe(".corporea", resify(
					"decorative/corporea_deco"
				)),
				new PageCraftingRecipe(".corporeaBrick", resify(
					"decorative/corporea_brick_deco"
				)),
				new PageCraftingRecipe(".redString", resify(
					"decorative/red_string_deco"
				)),
				new PageCraftingRecipe(".redStringFrost", resify(
					"decorative/red_string_frost_deco"
				)),
				new PageCraftingRecipe(".stairs", resify(
					"decorative/corporea_brick_deco_stairs",
					"decorative/corporea_deco_stairs",
					"decorative/red_string_deco_stairs"
				)),
				new PageCraftingRecipe(".slabs", resify(
					"decorative/corporea_brick_deco_slab",
					"decorative/corporea_deco_slab",
					"decorative/red_string_deco_slab"
				)),
				new PageCraftingRecipe(".walls", resify(
					"decorative/corporea_brick_deco_wall",
					"decorative/red_string_deco_wall"
				))
			).setKnowledgeType(elvenKnowledge);
		
		DecorativeBlocks.forEachPieceManager(m -> {
			m.forEachItem(i -> {
				if(i.getRegistryName().getPath().contains("lokiw")) return; //Shh!!!
				elvenDecoration.addExtraDisplayedRecipe(new ItemStack(i));
			});
		});
	}
	
	private static List<ResourceLocation> resify(String... in) {
		List<ResourceLocation> a = new ArrayList<>(in.length);
		for(String s : in) {
			a.add(new ResourceLocation(Incorporeal.MODID, s));
		}
		return a;
	}
}
