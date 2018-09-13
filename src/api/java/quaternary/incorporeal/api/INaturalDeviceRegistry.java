package quaternary.incorporeal.api;

import net.minecraft.block.state.IBlockState;

import java.util.Random;
import java.util.function.Function;

/**
 * Incorporeal's natural device registry is used to figure out what blockstates
 * the "plantable redstone root" crop can grow into when it becomes fully grown.
 * 
 * It is preferred that any blocks made obtainable through this process are not very "good"
 * redstone components. Don't just throw in any old component. Incorporeal's convention
 * is that components take 20 ticks (1 second) to respond to input, are only placable on dirt
 * or dirt-like blocks, and generally have a smaller range of configuration options (the repeater,
 * for example, does not have a configurable delay).
 * 
 * However, at the same time, the components should be interesting in their own right. That repeater
 * is a quite useful pulse extender, for example. Oh, and of course, it's a Botania addon. No GUIs.
 * 
 * @apiNote If the block cannot be placed on farmland, it will simply break as an item when a redstone root crop grows into it.
 *
 * @author quaternary
 * @since 1.1
 */
public interface INaturalDeviceRegistry {
	/**
	 * Register a new blockstate with this natural device registry.
	 * 
	 * @param deviceStateFactory A function that returns the blockstate to place in the world.
	 *                           Will be called only if this device is chosen in pullRandomDevice. 
	 *                           If your device can be placed in different orientations, it's preferred
	 *                           to use the Random to choose a random north, south, east, or west direction.
	 * @param weight How likely is it that this blockstate will be chosen over others?
	 *               Incorporeal's defaults are 80 for the repeater and 20 for the comparator.
	 */
	void registerNaturalDevice(Function<Random, IBlockState> deviceStateFactory, double weight);
	
	/**
	 * Choose a random blockstate from the pool.
	 * 
	 * @param rand The randomizer to use to decide on the device type and angle.
	 * @return The blockstate that will replace the redstone root crop.
	 */
	IBlockState pullRandomDevice(Random rand);
}
