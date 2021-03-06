package quaternary.incorporeal.feature.cygnusnetwork.block;

import net.minecraftforge.common.property.IUnlistedProperty;
import quaternary.incorporeal.api.ISimpleRegistry;

public class UnlistedSimpleRegistryProperty<T> implements IUnlistedProperty<T> {
	public UnlistedSimpleRegistryProperty(String name, Class<T> tClass, ISimpleRegistry<T> simpleRegistry) {
		this.name = name;
		this.tClass = tClass;
		this.simpleRegistry = simpleRegistry;
	}
	
	public static <T> UnlistedSimpleRegistryProperty<T> create(String name, Class<T> tClass, ISimpleRegistry<T> simpleRegistry) {
		return new UnlistedSimpleRegistryProperty<>(name, tClass, simpleRegistry);
	}
	
	private final String name;
	private final Class<T> tClass;
	private final ISimpleRegistry<T> simpleRegistry;
	
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
