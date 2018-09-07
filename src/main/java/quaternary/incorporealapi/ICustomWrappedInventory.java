package quaternary.incorporealapi;

import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.corporea.IWrappedInventory;
import vazkii.botania.api.corporea.InvWithLocation;

/**
 * ASM hackery in Botania's InternalMethodHandler#wrapInventory causes wrap to be called on any TileEntities that implement this interface.
 * 
 * @author quaternary
 * @since 1.1
 */
public interface ICustomWrappedInventory {
	/**
	 * @return the IWrappedInventory that will be used in the corporea system. 
	 */
	IWrappedInventory wrap(InvWithLocation inv, ICorporeaSpark spork);
}
