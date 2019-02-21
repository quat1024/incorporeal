package quaternary.incorporeal.api;

import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.api.cygnus.ILooseCygnusFunnelable;

import java.util.function.Consumer;
import java.util.function.Predicate;

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
	
	/**
	 * @return A place to register your natural devices; things that can be grown from the redstone root.
	 */
	INaturalDeviceRegistry getNaturalDeviceRegistry();
	
	/**
	 * @return A registry of Cygnus datatypes.
	 */
	ISimpleRegistry<ICygnusDatatype<?>> getCygnusDatatypeRegistry();
	
	/**
	 * @return A registry of Cygnus actions; things that can transform a Cygnus stack.
	 */
	ISimpleRegistry<Consumer<ICygnusStack>> getCygnusStackActionRegistry();
	
	/**
	 * @return A registry of Cygnus conditions; things that tell a true-false piece of information about a Cygnus stack.
	 */
	ISimpleRegistry<Predicate<ICygnusStack>> getCygnusStackConditionRegistry();
	
	/**
	 * Register a loose cygnus funnelable.
	 * Don't use these 
	 * @param loose
	 */
	void registerLooseFunnelable(ILooseCygnusFunnelable loose);
}
