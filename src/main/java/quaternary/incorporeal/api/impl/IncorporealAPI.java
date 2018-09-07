package quaternary.incorporeal.api.impl;

import quaternary.incorporealapi.IIncorporealAPI;
import quaternary.incorporealapi.INaturalDeviceRegistry;

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
}
