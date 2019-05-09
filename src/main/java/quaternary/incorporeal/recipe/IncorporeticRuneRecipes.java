package quaternary.incorporeal.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.item.IncorporeticItems;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.item.ModItems;

public final class IncorporeticRuneRecipes {
	private IncorporeticRuneRecipes() {}
	
	public static RecipeRuneAltar soulCoreFrame;
	
	public static RecipeRuneAltar enderSoulCore;
	public static RecipeRuneAltar corporeaSoulCore;
	public static RecipeRuneAltar potionSoulCore;
	
	public static RecipeRuneAltar lokiW;
	
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
		
		enderSoulCore = createStandardSoulCoreRecipe(25000, IncorporeticItems.ENDER_SOUL_CORE, new ItemStack(ModItems.enderHand));
		
		corporeaSoulCore = createStandardSoulCoreRecipe(300, IncorporeticItems.CORPOREA_SOUL_CORE, new ItemStack(ModItems.corporeaSpark, 1, 0));
		
		potionSoulCore = createStandardSoulCoreRecipe(25000, IncorporeticItems.POTION_SOUL_CORE, new ItemStack(ModItems.bloodPendant));
		
		lokiW = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(IncorporeticItems.LOKIW), 12000, (Object[]) EtcHelpers.fill(
			new ItemStack[10],
			(i) -> EtcHelpers.skullOf("Loki270")
		));
	}
	
	private static RecipeRuneAltar createStandardSoulCoreRecipe(int price, Item out, ItemStack special) {
		return BotaniaAPI.registerRuneAltarRecipe(
						new ItemStack(out),
						price,
						new ItemStack(IncorporeticItems.SOUL_CORE_FRAME),
						new ItemStack(ModItems.manaResource, 1, 9), //dragonstone
						new ItemStack(ModItems.manaResource, 1, 9), //dragonstone
						new ItemStack(ModItems.manaResource, 1, 22), //manaweave cloth
						new ItemStack(ModItems.manaResource, 1, 22), //manaweave cloth
						special
		);
	}
}
