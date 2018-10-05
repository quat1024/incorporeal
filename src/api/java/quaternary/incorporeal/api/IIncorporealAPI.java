package quaternary.incorporeal.api;

import quaternary.incorporeal.api.cygnus.ICygnusSerializerRegistry;

/**
 * Incorporeal's API.
 * 
 * The main instance of this API is available in Incorporeal#API.
 * If you'd like to soft-depend on this API, you may be interested
 * in FMLPostInitializationEvent#buildSoftDependProxy.
 * 
 * @author quaternary
 * @since 1.1
 */
public interface IIncorporealAPI {
	/**
	 * The version number of this API.
	 * Will be increased by at least one when any new additions or breaking changes occur.
	 */
	int apiVersion();
	
	INaturalDeviceRegistry getNaturalDeviceRegistry();
	
	ICygnusSerializerRegistry getCygnusSerializerRegistry();
}
