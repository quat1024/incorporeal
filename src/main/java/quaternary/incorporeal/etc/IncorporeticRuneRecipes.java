package quaternary.incorporeal.etc;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.item.IncorporeticItems;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.item.ModItems;

public final class IncorporeticRuneRecipes {
	private IncorporeticRuneRecipes() {}
	
	public static RecipeRuneAltar soulCoreFrame;
	
	public static RecipeRuneAltar enderSoulCore;
	public static RecipeRuneAltar corporeaSoulCore;
	
	public static void init() {
		soulCoreFrame = BotaniaAPI.registerRuneAltarRecipe(
						new ItemStack(IncorporeticItems.SOUL_CORE_FRAME),
						12000, //tier 3 rune cost
						new ItemStack(Blocks.ICE),
						new ItemStack(Blocks.ICE),
						new ItemStack(Blocks.ICE),
						new ItemStack(Blocks.ICE),
						new ItemStack(ModItems.manaResource, 1, 8), //pixie dust
						new ItemStack(Blocks.ICE),
						new ItemStack(Blocks.ICE),
						new ItemStack(Blocks.ICE),
						new ItemStack(Blocks.ICE),
						new ItemStack(ModItems.manaResource, 1, 8) //pixie dust
		);
		
		enderSoulCore = createStandardSoulCoreRecipe(IncorporeticItems.ENDER_SOUL_CORE, new ItemStack(Blocks.ENDER_CHEST));
		
		//TODO this is pretty expensive for what it is - rebalance?
		corporeaSoulCore = createStandardSoulCoreRecipe(IncorporeticItems.CORPOREA_SOUL_CORE, new ItemStack(ModItems.corporeaSpark, 1, 0));
	}
	
	private static RecipeRuneAltar createStandardSoulCoreRecipe(Item out, ItemStack special) {
		return BotaniaAPI.registerRuneAltarRecipe(
						new ItemStack(out),
						30000,
						new ItemStack(IncorporeticItems.SOUL_CORE_FRAME),
						new ItemStack(ModItems.manaResource, 1, 9), //dragonstone
						new ItemStack(ModItems.manaResource, 1, 9), //dragonstone
						new ItemStack(ModItems.manaResource, 1, 22), //manaweave cloth
						new ItemStack(ModItems.manaResource, 1, 22), //manaweave cloth
						special
		);
	}
}
