package quaternary.incorporeal.api;

import com.google.common.collect.BiMap;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

/**
 * A simple resourcelocation -> something bimap.
 * This is used internally inside Incorporeal, because IForgeRegistries are
 * not always appropriate and are a little bit overkill.
 * 
 * Note that the ResourceLocation is supplied separately, not as some kind of property on the item.
 * This is intentional; Incorporeal's registries often contain things like plain Java
 * Consumers and Predicates.
 * 
 * Largely self explanatory.
 * 
 * @param <T> the thing in the registry
 * @since 1.1
 * @author quaternary
 */
public interface ISimpleRegistry<T> extends Iterable<T> {
	/**
	 * @throws IllegalStateException if this registry is frozen (call this in your mod's preinit, after Incorporeal gets a turn!)
	 */
	T register(ResourceLocation name, T item);
	T get(ResourceLocation name);
	ResourceLocation nameOf(T item);
	Set<ResourceLocation> allKeys();
	Set<T> allValues();
	BiMap<ResourceLocation, T> backingMap();
	T next(T cur);
	T prev(T cur);
}
