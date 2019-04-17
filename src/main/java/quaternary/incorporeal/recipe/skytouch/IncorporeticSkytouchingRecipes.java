package quaternary.incorporeal.recipe.skytouch;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public final class IncorporeticSkytouchingRecipes {
	private IncorporeticSkytouchingRecipes() {}
	
	public static List<IRecipeSkytouching> ALL = new LinkedList<>();
	
	public static int LOWEST_SKYTOUCH_Y = Integer.MAX_VALUE;
	
	public static IRecipeSkytouching cygnusSpark;
	public static IRecipeSkytouching masterCygnusSpark;
	
	public static IRecipeSkytouching cygnusWord;
	public static IRecipeSkytouching cygnusFunnel;
	public static IRecipeSkytouching cygnusRetainer;
	public static IRecipeSkytouching cygnusCrystalCube;
	
	public static void init() {
		cygnusSpark = register(IncorporeticCygnusItems.CYGNUS_SPARK, new ItemStack(ModItems.corporeaSpark, 1, 0));
		masterCygnusSpark = register(IncorporeticCygnusItems.MASTER_CYGNUS_SPARK, new ItemStack(ModItems.corporeaSpark, 1, 1));
		
		cygnusWord = register(IncorporeticCygnusItems.WORD, ModBlocks.corporeaInterceptor);
		cygnusFunnel = register(IncorporeticCygnusItems.FUNNEL, ModBlocks.corporeaFunnel);
		cygnusRetainer = register(IncorporeticCygnusItems.RETAINER, ModBlocks.corporeaRetainer);
		cygnusCrystalCube = register(IncorporeticCygnusItems.CRYSTAL_CUBE, ModBlocks.corporeaCrystalCube);
	}
	
	public static IRecipeSkytouching register(IRecipeSkytouching r) {
		ALL.add(r);
		
		if(r.getMinY() < LOWEST_SKYTOUCH_Y) {
			LOWEST_SKYTOUCH_Y = r.getMinY();
		}
		
		return r;
	}
	
	public static void clear() {
		ALL.clear();
		LOWEST_SKYTOUCH_Y = Integer.MAX_VALUE;
	}
	
	public static void removeIf(Predicate<IRecipeSkytouching> cond) {
		ALL.removeIf(cond);
		
		LOWEST_SKYTOUCH_Y = Integer.MAX_VALUE;
		ALL.forEach(r -> {
			if(r.getMinY() < LOWEST_SKYTOUCH_Y) {
				LOWEST_SKYTOUCH_Y = r.getMinY();
			}
		});
	}
	
	//Literally just to save typing...
	private static IRecipeSkytouching register(Object out, Object in) {
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
