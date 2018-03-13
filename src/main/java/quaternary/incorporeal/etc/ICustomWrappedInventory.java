package quaternary.incorporeal.etc;

import vazkii.botania.api.corporea.*;

/** For tile entities that can return a custom IWrappedInventory object. 
 * Called through ASM hackery in InternalMethodHandler#wrapInventory. 
 * All I want for Christmas is an InventoryWrapEvent~ */
public interface ICustomWrappedInventory {
	IWrappedInventory wrap(InvWithLocation inv, ICorporeaSpark spork);
}
