package quaternary.incorporeal.api.impl;

import quaternary.incorporeal.api.IIncorporealAPI;
import quaternary.incorporeal.api.INaturalDeviceRegistry;
import quaternary.incorporeal.api.cygnus.ICygnusSerializerRegistry;
import quaternary.incorporeal.api.impl.cygnus.CygnusSerializerRegistry;

public class IncorporealAPI implements IIncorporealAPI {
	@Override
	public int apiVersion() {
		return 0;
	}
	
	private static final INaturalDeviceRegistry naturalDeviceRegistry = new IncorporealNaturalDeviceRegistry();
	private static final ICygnusSerializerRegistry cygnusSerializerRegistry = new CygnusSerializerRegistry();
	
	@Override
	public INaturalDeviceRegistry getNaturalDeviceRegistry() {
		return naturalDeviceRegistry;
	}
	
	@Override
	public ICygnusSerializerRegistry getCygnusSerializerRegistry() {
		return cygnusSerializerRegistry;
	}
}
