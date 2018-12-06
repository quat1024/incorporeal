package quaternary.incorporeal.etc;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.api.ISimpleRegistry;

import java.util.Set;

public class SimpleRegistry<T> implements ISimpleRegistry<T> {
	BiMap<ResourceLocation, T> registry = HashBiMap.create();
	boolean frozen = false;
	
	@Override
	public void register(ResourceLocation name, T item) {
		if(frozen) throw new IllegalStateException("Too late, this registry is frozen");
		
		registry.put(name, item);
	}
	
	@Override
	public T get(ResourceLocation name) {
		return registry.get(name);
	}
	
	@Override
	public ResourceLocation nameOf(T item) {
		return registry.inverse().get(item);
	}
	
	@Override
	public Set<ResourceLocation> allKeys() {
		return registry.keySet();
	}
	
	@Override
	public Set<T> allValues() {
		return registry.values();
	}
	
	@Override
	public BiMap<ResourceLocation, T> backingMap() {
		return registry;
	}
	
	public void freeze() {
		frozen = true;
	}
}
