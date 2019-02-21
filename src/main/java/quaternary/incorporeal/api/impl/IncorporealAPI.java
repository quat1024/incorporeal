package quaternary.incorporeal.api.impl;

import quaternary.incorporeal.api.IIncorporealAPI;
import quaternary.incorporeal.api.INaturalDeviceRegistry;
import quaternary.incorporeal.api.ISimpleRegistry;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.api.cygnus.ILooseCygnusFunnelable;
import quaternary.incorporeal.cygnus.CygnusRegistries;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class IncorporealAPI implements IIncorporealAPI {
	@Override
	public int apiVersion() {
		return 0;
	}
	
	private static final INaturalDeviceRegistry naturalDeviceRegistry = new IncorporealNaturalDeviceRegistry();
	
	@Override
	public INaturalDeviceRegistry getNaturalDeviceRegistry() {
		return naturalDeviceRegistry;
	}
	
	@Override
	public ISimpleRegistry<ICygnusDatatype<?>> getCygnusDatatypeRegistry() {
		return CygnusRegistries.DATATYPES;
	}
	
	@Override
	public ISimpleRegistry<Consumer<ICygnusStack>> getCygnusStackActionRegistry() {
		return CygnusRegistries.ACTIONS;
	}
	
	@Override
	public ISimpleRegistry<Predicate<ICygnusStack>> getCygnusStackConditionRegistry() {
		return CygnusRegistries.CONDITIONS;
	}
	
	@Override
	public void registerLooseFunnelable(ILooseCygnusFunnelable loose) {
		CygnusRegistries.LOOSE_FUNNELABLES.add(loose);
	}
}
