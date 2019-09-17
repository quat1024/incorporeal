package quaternary.incorporeal.api;

import net.minecraft.item.ItemStack;
import quaternary.incorporeal.api.cygnus.ICygnusAction;
import quaternary.incorporeal.api.cygnus.ICygnusCondition;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.api.cygnus.ILooseCygnusFunnelable;

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
	 * Note: Method signature changed in 1.2.4 (api version 2) in a non-backwards compatible way.
	 */
	ISimpleRegistry<ICygnusAction> getCygnusStackActionRegistry();
	
	/**
	 * @return A registry of Cygnus conditions; things that tell a true-false piece of information about a Cygnus stack.
	 * Note: Method signature changed in 1.2.4 (api version 2) in a non-backwards compatible way.
	 */
	ISimpleRegistry<ICygnusCondition> getCygnusStackConditionRegistry();
	
	/**
	 * Register a loose cygnus funnelable.
	 * Don't use these if a regular funnelable would suffice.
	 */
	void registerLooseFunnelable(ILooseCygnusFunnelable loose);
	
	/**
	 * Register a skytouching recipe.
	 * Call this in init.
	 *
	 * @param out The item that will be crafted.
	 * @param in The item that needs to be thrown into the air.
	 * @param minY The minimum Y-height the item needs to be thrown to.
	 * @param maxY The Y-height that will return the maximum bonus.
	 * @param multiplier The output multiplier that players will earn for sending an item all the way up to maxY. 
	 */
	void registerSkytouchingRecipe(ItemStack out, ItemStack in, int minY, int maxY, int multiplier);
	
	/**
	 * Register a skytouching recipe.
	 * Call this in init.
	 * 
	 * The default parameters will be used. These are:
	 *  - minimum Y: 260
	 *  - maximum Y: 300
	 *  - maximum multiplier: 4x
	 * @param out The item that will be crafted.
	 * @param in The item that needs to be thrown into the air.
	 */
	void registerSkytouchingRecipe(ItemStack out, ItemStack in);
}
