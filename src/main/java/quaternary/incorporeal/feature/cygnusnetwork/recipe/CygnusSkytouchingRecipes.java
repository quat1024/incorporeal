package quaternary.incorporeal.feature.cygnusnetwork.recipe;

import net.minecraft.item.ItemStack;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;
import quaternary.incorporeal.feature.skytouching.recipe.SkytouchingRecipes;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

public final class CygnusSkytouchingRecipes {
	private CygnusSkytouchingRecipes() {}
	
	public static IRecipeSkytouching cygnusSpark;
	public static IRecipeSkytouching masterCygnusSpark;
	
	public static IRecipeSkytouching cygnusWord;
	public static IRecipeSkytouching cygnusFunnel;
	public static IRecipeSkytouching cygnusRetainer;
	public static IRecipeSkytouching cygnusCrystalCube;
	
	public static IRecipeSkytouching bookUpgrade;
	
	public static void register() {
		cygnusSpark = SkytouchingRecipes.register(
			CygnusNetworkItems.CYGNUS_SPARK,
			new ItemStack(ModItems.corporeaSpark, 1, 0)
		);
		
		masterCygnusSpark = SkytouchingRecipes.register(
			CygnusNetworkItems.MASTER_CYGNUS_SPARK,
			new ItemStack(ModItems.corporeaSpark, 1, 1)
		);
		
		cygnusWord = SkytouchingRecipes.register(
			CygnusNetworkItems.WORD, ModBlocks.corporeaInterceptor
		);
		
		cygnusFunnel = SkytouchingRecipes.register(
			CygnusNetworkItems.FUNNEL, ModBlocks.corporeaFunnel
		);
		
		cygnusRetainer = SkytouchingRecipes.register(
			CygnusNetworkItems.RETAINER, ModBlocks.corporeaRetainer
		);
		
		cygnusCrystalCube = SkytouchingRecipes.register(
			CygnusNetworkItems.CRYSTAL_CUBE, ModBlocks.corporeaCrystalCube
		);
		
		
	}
}
