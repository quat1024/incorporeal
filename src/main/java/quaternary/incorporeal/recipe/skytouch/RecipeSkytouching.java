package quaternary.incorporeal.recipe.skytouch;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.etc.helper.EtcHelpers;

import java.util.ArrayList;
import java.util.List;

public class RecipeSkytouching {
	public RecipeSkytouching(ItemStack out, ItemStack in, int minY, int maxY, int multiplier) {
		this.out = out;
		this.in = in;
		this.minY = minY;
		this.maxY = maxY;
		this.multiplier = multiplier;
		
		if(minY < LOWEST_SKYTOUCHING_RECIPE_Y) LOWEST_SKYTOUCHING_RECIPE_Y = minY;
	}
	
	public RecipeSkytouching(ItemStack out, ItemStack in) {
		this(out, in, 260, 300, 4);
	}
	
	//The lowest Y value for a skytouching recipe.
	//Used to quickly filter out item entities below this area.
	public static int LOWEST_SKYTOUCHING_RECIPE_Y = Integer.MAX_VALUE;
	
	//the output
	public final ItemStack out;
	//the input
	public final ItemStack in;
	//the minimum Y level this item has to reach to be transformed one-to-one
	public final int minY;
	//the minimum Y level this item has to reach to be transformed one-to-"multiplier"
	public final int maxY;
	//the maximum bonus multiplier that can be awarded for hitting maxY
	public final int multiplier;
	
	public boolean matches(ItemStack stack, double entY) {
		//TODO: Nbt stuff I guess
		return entY >= minY && ItemStack.areItemsEqualIgnoreDurability(stack, in);
	}
	
	//why a list? well, it might go over the max stack size for the output item! :D
	public List<ItemStack> getOutputs(ItemStack supplied, double entY) {
		//calculate the height bonus
		double bonus = EtcHelpers.rangeRemap(entY, minY, maxY, 1, multiplier);
		//figure out how many items to dispense
		int itemsToDispense = supplied.getCount() * ((int) Math.round(out.getCount() * bonus));
		
		//does it fit in one itemstack?
		if(itemsToDispense <= out.getMaxStackSize()) {
			ItemStack outC = out.copy();
			outC.setCount(itemsToDispense);
			return ImmutableList.of(outC);
		}
		
		//nope, it doesn't
		List<ItemStack> result = new ArrayList<>(2);
		
		while(itemsToDispense > 0) {
			int dispensedItems = Math.min(itemsToDispense, out.getMaxStackSize());
			
			ItemStack outC = out.copy();
			outC.setCount(dispensedItems);
			result.add(outC);
			
			itemsToDispense -= dispensedItems;
		}
		
		return result;
	}
}
