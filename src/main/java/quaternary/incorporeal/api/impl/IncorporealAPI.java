package quaternary.incorporeal.api.impl;

import quaternary.incorporeal.api.IIncorporealAPI;
import quaternary.incorporeal.api.INaturalDeviceRegistry;
import quaternary.incorporeal.api.cygnus.ICygnusDatatypeInfoRegistry;
import quaternary.incorporeal.api.impl.cygnus.CygnusDatatypeInfoRegistry;

public class IncorporealAPI implements IIncorporealAPI {
	@Override
	public int apiVersion() {
		return 0;
	}
	
	private static final INaturalDeviceRegistry naturalDeviceRegistry = new IncorporealNaturalDeviceRegistry();
	private static final ICygnusDatatypeInfoRegistry cygnusSerializerRegistry = new CygnusDatatypeInfoRegistry();
	
	@Override
	public INaturalDeviceRegistry getNaturalDeviceRegistry() {
		return naturalDeviceRegistry;
	}
	
	@Override
	public ICygnusDatatypeInfoRegistry getCygnusDatatypeInfoRegistry() {
		return cygnusSerializerRegistry;
	}
}
