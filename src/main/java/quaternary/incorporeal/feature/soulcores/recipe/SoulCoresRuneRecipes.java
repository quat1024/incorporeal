package quaternary.incorporeal.feature.soulcores.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.feature.soulcores.item.SoulCoresItems;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.item.ModItems;

public final class SoulCoresRuneRecipes {
	private SoulCoresRuneRecipes() {
	}
	
	public static RecipeRuneAltar soulCoreFrame;
	public static RecipeRuneAltar enderSoulCore;
	public static RecipeRuneAltar corporeaSoulCore;
	public static RecipeRuneAltar potionSoulCore;
	
	public static void init() {
		SoulCoresRuneRecipes.soulCoreFrame = BotaniaAPI.registerRuneAltarRecipe(
			new ItemStack(SoulCoresItems.SOUL_CORE_FRAME),
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
		
		SoulCoresRuneRecipes.enderSoulCore = SoulCoresRuneRecipes.createStandardSoulCoreRecipe(25000, SoulCoresItems.ENDER_SOUL_CORE, new ItemStack(ModItems.enderHand));
		
		SoulCoresRuneRecipes.corporeaSoulCore = SoulCoresRuneRecipes.createStandardSoulCoreRecipe(300, SoulCoresItems.CORPOREA_SOUL_CORE, new ItemStack(ModItems.corporeaSpark, 1, 0));
		
		SoulCoresRuneRecipes.potionSoulCore = SoulCoresRuneRecipes.createStandardSoulCoreRecipe(25000, SoulCoresItems.POTION_SOUL_CORE, new ItemStack(ModItems.bloodPendant));
	}
	
	public static RecipeRuneAltar createStandardSoulCoreRecipe(int price, Item out, ItemStack special) {
		return BotaniaAPI.registerRuneAltarRecipe(
			new ItemStack(out),
			price,
			new ItemStack(SoulCoresItems.SOUL_CORE_FRAME),
			new ItemStack(ModItems.manaResource, 1, 9), //dragonstone
			new ItemStack(ModItems.manaResource, 1, 9), //dragonstone
			new ItemStack(ModItems.manaResource, 1, 22), //manaweave cloth
			new ItemStack(ModItems.manaResource, 1, 22), //manaweave cloth
			special
		);
	}
}
