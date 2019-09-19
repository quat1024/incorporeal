package quaternary.incorporeal.api.cygnus;

import quaternary.incorporeal.api.IDocumentableComponent;
import quaternary.incorporeal.api.IIncorporealAPI;

import javax.annotation.Nullable;

/**
 * Something with special interactions with the Cygnus funnel.
 * Can be applied to Entities or TileEntities as a capabilty, or to blocks as an interface.
 * 
 * Please document your Cygnus funnelables. Note that the decentralized nature of funnelables
 * (they are not registered to a central location) means that I can't pick up automatically
 * on their documentation pages.
 * 
 * @see ILooseCygnusFunnelable
 * @see IIncorporealAPI#documentCygnusFunnelable(IDocumentableComponent...) 
 * 
 * @since 1.1
 * @author quaternary
 */
public interface ICygnusFunnelable {
	/**
	 * @return whether or not this can have Cygnus data pulled from it
	 */
	default boolean canGiveCygnusItem() {
		return false;
	}
	
	/**
	 * @return whether or not a Cygnus funnel can push data into this
	 */
	default boolean canAcceptCygnusItem() {
		return false;
	}
	
	/**
	 * @return the Cygnus item currently contained by this (don't forget to clear it as a side effect, if appropriate)
	 */
	default @Nullable Object giveItemToCygnus() {
		return null;
	}
	
	/**
	 * @param item The item that the Cygnus network is currently pushing in to this
	 */
	default void acceptItemFromCygnus(Object item) {
		
	}
}
