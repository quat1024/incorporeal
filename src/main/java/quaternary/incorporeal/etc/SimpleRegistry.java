package quaternary.incorporeal.etc;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.api.ISimpleRegistry;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SimpleRegistry<T> implements ISimpleRegistry<T> {
	private BiMap<ResourceLocation, T> registry = HashBiMap.create();
	//used for next/previous operations like scrolling a cygnus card
	//yes this is dumb
	private List<T> valuesOrdered = new LinkedList<>();
	private boolean frozen = false;
	
	@Override
	public void register(ResourceLocation name, T item) {
		if(frozen) throw new IllegalStateException("Too late, this registry is frozen");
		
		registry.put(name, item);
		valuesOrdered.add(item);
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
	
	@Override
	public T next(T cur) {
		if(cur == null) return valuesOrdered.get(0);
		
		int i = indexOf(cur) + 1;
		if(i == valuesOrdered.size()) i = 0;
		return valuesOrdered.get(i);
	}
	
	@Override
	public T prev(T cur) {
		if(cur == null) return valuesOrdered.get(0);
		
		int i = indexOf(cur) - 1;
		if(i == -1) i = valuesOrdered.size() - 1;
		return valuesOrdered.get(i);
	}
	
	private int indexOf(T thing) {
		Iterator<T> it = valuesOrdered.iterator();
		int i = 0;
		while(it.hasNext()) {
			if(thing.equals(it.next())) return i;
			i++;
		}
		return 0;
	}
	
	public void freeze() {
		frozen = true;
	}
}
