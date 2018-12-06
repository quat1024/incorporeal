package quaternary.incorporeal.block.properties;

import net.minecraftforge.common.property.IUnlistedProperty;
import quaternary.incorporeal.api.ISimpleRegistry;
import quaternary.incorporeal.etc.SimpleRegistry;

public class UnlistedSimpleRegistryCondition<T> implements IUnlistedProperty<T> {
	public UnlistedSimpleRegistryCondition(String name, Class<T> tClass, ISimpleRegistry<T> simpleRegistry) {
		this.name = name;
		this.tClass = tClass;
		this.simpleRegistry = simpleRegistry;
	}
	
	public static <T> UnlistedSimpleRegistryCondition<T> create(String name, Class<T> tClass, ISimpleRegistry<T> simpleRegistry) {
		return new UnlistedSimpleRegistryCondition<>(name, tClass, simpleRegistry);
	}
	
	private String name;
	private Class<T> tClass;
	private ISimpleRegistry<T> simpleRegistry;
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean isValid(T value) {
		return simpleRegistry.allValues().contains(value);
	}
	
	@Override
	public Class<T> getType() {
		return tClass;
	}
	
	@Override
	public String valueToString(T value) {
		return simpleRegistry.nameOf(value).toString();
	}
}
