package quaternary.incorporeal.api.recipe;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * A "skytouching recipe".
 * You put the thing above the height limit and it turns into something else.
 * How about that.
 */
public interface IRecipeSkytouching {
	/**
	 * Does this recipe match?
	 * @param ent The input item entity in the world.
	 * @return whether getOutputs should be called and the visual effect played 
	 */
	boolean matches(EntityItem ent);
	
	/**
	 * What items to replace this item with?
	 * @param ent The input item entity in the world.
	 * @return The items that will be spawned in the world
	 */
	List<ItemStack> getOutputs(EntityItem ent);
	
	
	/**
	 * @return A "generic" form of the recipe's input items. Used for JEI, crafttweaker remove, etc.
	 * List is available because of things like ore dictionary inputs.
	 */
	List<ItemStack> getGenericInputs();
	
	/**
	 * @return A "generic" form of the recipe's output item. Used for JEI, etc. Do not call this method to look for an item to spawn in the world
	 */
	ItemStack getGenericOutput();
	
	/**
	 * @return The minimum Y value that the input needs to be passed upwards to
	 */
	int getMinY();
	
	/**
	 * @return The y value that the input needs to be passed upwards to for the highest multiplier
	 */
	int getMaxY();
	
	/**
	 * @return The multiplier obtained by sending the item up to the highest y value
	 */
	int getMultiplier();
}
