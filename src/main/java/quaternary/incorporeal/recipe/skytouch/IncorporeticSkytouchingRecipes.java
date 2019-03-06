package quaternary.incorporeal.recipe.skytouch;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

import java.util.LinkedList;
import java.util.List;

public final class IncorporeticSkytouchingRecipes {
	private IncorporeticSkytouchingRecipes() {}
	
	public static List<RecipeSkytouching> ALL = new LinkedList<>();
	
	public static RecipeSkytouching cygnusSpark;
	public static RecipeSkytouching masterCygnusSpark;
	
	public static RecipeSkytouching cygnusWord;
	public static RecipeSkytouching cygnusFunnel;
	public static RecipeSkytouching cygnusRetainer;
	public static RecipeSkytouching cygnusCrystalCube;
	
	public static void init() {
		cygnusSpark = register(IncorporeticCygnusItems.CYGNUS_SPARK, new ItemStack(ModItems.corporeaSpark, 1, 0));
		masterCygnusSpark = register(IncorporeticCygnusItems.MASTER_CYGNUS_SPARK, new ItemStack(ModItems.corporeaSpark, 1, 1));
		
		cygnusWord = register(IncorporeticCygnusItems.WORD, ModBlocks.corporeaInterceptor);
		cygnusFunnel = register(IncorporeticCygnusItems.FUNNEL, ModBlocks.corporeaFunnel);
		cygnusRetainer = register(IncorporeticCygnusItems.RETAINER, ModBlocks.corporeaRetainer);
		cygnusCrystalCube = register(IncorporeticCygnusItems.CRYSTAL_CUBE, ModBlocks.corporeaCrystalCube);
	}
	
	public static RecipeSkytouching register(RecipeSkytouching r) {
		ALL.add(r);
		return r;
	}
	
	//Literally just to save typing...
	private static RecipeSkytouching register(Object out, Object in) {
		ItemStack outStack;
		ItemStack inStack;
		
		if(out instanceof Item) outStack = new ItemStack((Item) out);
		else if(out instanceof ItemStack) outStack = (ItemStack) out;
		else if(out instanceof Block) outStack = new ItemStack((Block) out);
		else throw new IllegalArgumentException();
		
		if(in instanceof Item) inStack = new ItemStack((Item) in);
		else if(in instanceof ItemStack) inStack = (ItemStack) in;
		else if(in instanceof Block) inStack = new ItemStack((Block) in);
		else throw new IllegalArgumentException();
		
		return register(new RecipeSkytouching(outStack, inStack));
	}
}
