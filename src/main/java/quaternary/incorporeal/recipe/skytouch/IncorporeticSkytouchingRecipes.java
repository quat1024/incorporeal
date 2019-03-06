package quaternary.incorporeal.recipe.skytouch;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;
import vazkii.botania.common.item.ModItems;

import java.util.LinkedList;
import java.util.List;

public final class IncorporeticSkytouchingRecipes {
	private IncorporeticSkytouchingRecipes() {}
	
	//TODO throw this in an API???
	public static List<RecipeSkytouching> ALL = new LinkedList<>();
	
	public static RecipeSkytouching cygnusSpark;
	
	public static RecipeSkytouching masterCygnusSpark;
	
	public static void init() {
		cygnusSpark = register(new RecipeSkytouching(new ItemStack(IncorporeticCygnusItems.CYGNUS_SPARK), new ItemStack(ModItems.corporeaSpark, 1, 0)));
		
		masterCygnusSpark = register(new RecipeSkytouching(new ItemStack(IncorporeticCygnusItems.MASTER_CYGNUS_SPARK), new ItemStack(ModItems.corporeaSpark, 1, 1)));
	}
	
	public static RecipeSkytouching register(RecipeSkytouching r) {
		ALL.add(r);
		return r;
	}
}
