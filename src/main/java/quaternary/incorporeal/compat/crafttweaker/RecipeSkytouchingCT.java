package quaternary.incorporeal.compat.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.recipe.skytouch.RecipeSkytouching;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeSkytouchingCT implements IRecipeSkytouching {
	public RecipeSkytouchingCT(IItemStack out, IIngredient ingredient, int minY, int maxY, int multiplier) {
		this.out = out;
		this.ingredient = ingredient;
		this.minY = minY;
		this.maxY = maxY;
		this.multiplier = multiplier;
	}
	
	public RecipeSkytouchingCT(IItemStack out, IIngredient ingredient) {
		this(out, ingredient, RecipeSkytouching.DEFAULT_MINY, RecipeSkytouching.DEFAULT_MAXY, RecipeSkytouching.DEFAULT_MULTIPLIER);
	}
	
	private final IItemStack out;
	private final IIngredient ingredient;
	private final int minY;
	private final int maxY;
	private final int multiplier;
	
	@Override
	public boolean matches(EntityItem ent) {
		return ent.posY >= minY && ingredient.matches(CraftTweakerMC.getIItemStack(ent.getItem()));
	}
	
	@Override
	public List<ItemStack> getOutputs(EntityItem ent) {
		//lots of this is cut and paste from regular recipeskytouching
		//calculate the height bonus
		double bonus = EtcHelpers.rangeRemap(ent.posY, minY, maxY, 1, multiplier);
		//figure out how many items to dispense
		int itemsToDispense = ent.getItem().getCount() * ((int) Math.round(out.getAmount() * bonus));
		
		ItemStack outStack = CraftTweakerMC.getItemStack(out);
		List<ItemStack> result = new ArrayList<>();
		
		if(itemsToDispense <= out.getMaxStackSize()) {
			ItemStack outC = outStack.copy();
			outC.setCount(itemsToDispense);
			result.add(outC);
		} else {
			while(itemsToDispense > 0) {
				int dispensedItems = Math.min(itemsToDispense, out.getMaxStackSize());
				
				ItemStack outC = outStack.copy();
				outC.setCount(dispensedItems);
				result.add(outC);
				
				itemsToDispense -= dispensedItems;
			}
		}
		
		if(ingredient.hasNewTransformers()) {
			IItemStack istack = ingredient.getItems().get(0);
			istack = ingredient.applyNewTransform(istack);
			
			result.add(CraftTweakerMC.getItemStack(istack));
		}
		
		result.removeIf(ItemStack::isEmpty);
		
		return result;
	}
	
	@Override
	public List<ItemStack> getGenericInputs() {
		return ingredient.getItems().stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList());
	}
	
	@Override
	public ItemStack getGenericOutput() {
		return CraftTweakerMC.getItemStack(out);
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
