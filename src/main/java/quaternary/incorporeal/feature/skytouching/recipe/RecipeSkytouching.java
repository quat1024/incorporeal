package quaternary.incorporeal.feature.skytouching.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;

import java.util.ArrayList;
import java.util.List;

public class RecipeSkytouching implements IRecipeSkytouching {
	public RecipeSkytouching(ItemStack out, ItemStack in, int minY, int maxY, int multiplier) {
		this.out = out;
		this.in = in;
		this.minY = minY;
		this.maxY = maxY;
		this.multiplier = multiplier;
	}
	
	public RecipeSkytouching(ItemStack out, ItemStack in) {
		this(out, in, DEFAULT_MINY, DEFAULT_MAXY, DEFAULT_MULTIPLIER);
	}
	
	public static final int DEFAULT_MINY = 260;
	public static final int DEFAULT_MAXY = 300;
	public static final int DEFAULT_MULTIPLIER = 4;
	
	//the output
	private final ItemStack out;
	//the input
	private final ItemStack in;
	//the minimum Y level this item has to reach to be transformed one-to-one
	private final int minY;
	//the minimum Y level this item has to reach to be transformed one-to-"multiplier"
	private final int maxY;
	//the maximum bonus multiplier that can be awarded for hitting maxY
	private final int multiplier;
	
	@Override
	public boolean matches(EntityItem ent) {
		//TODO: Nbt stuff I guess
		return ent.posY >= minY && ItemStack.areItemsEqualIgnoreDurability(ent.getItem(), in);
	}
	
	@Override
	public List<ItemStack> getOutputs(EntityItem ent) {
		//calculate the height bonus
		double bonus = EtcHelpers.rangeRemap(ent.posY, minY, maxY, 1, multiplier);
		//figure out how many items to dispense
		int itemsToDispense = ent.getItem().getCount() * ((int) Math.round(out.getCount() * bonus));
		
		List<ItemStack> result = new ArrayList<>();
		
		if(itemsToDispense <= out.getMaxStackSize()) {
			ItemStack outC = out.copy();
			outC.setCount(itemsToDispense);
			result.add(outC);
		} else {
			while(itemsToDispense > 0) {
				int dispensedItems = Math.min(itemsToDispense, out.getMaxStackSize());
				
				ItemStack outC = out.copy();
				outC.setCount(dispensedItems);
				result.add(outC);
				
				itemsToDispense -= dispensedItems;
			}
		}
		
		//:voldethonk:
		ItemStack container = ent.getItem().getItem().getContainerItem(ent.getItem());
		if(!container.isEmpty()) {
			result.add(container.copy());
		}
		
		return result;
	}
	
	@Override
	public List<ItemStack> getGenericInputs() {
		return ImmutableList.of(in);
	}
	
	@Override
	public ItemStack getGenericOutput() {
		return out;
	}
	
	@Override
	public int getMinY() {
		return minY;
	}
	
	@Override
	public int getMaxY() {
		return maxY;
	}
	
	@Override
	public int getMultiplier() {
		return multiplier;
	}
}
