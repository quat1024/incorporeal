package quaternary.incorporeal.api.cygnus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This interface is used, in lieu of a capability, to determine if a block can receive a Cygnus spark.
 * Implement this interface <i>on instances of Block</i>.
 * This method was chosen because not all Cygnus-operable blocks really need to be tile entities,
 * and as of this point in time, plain blocks cannot receive capabilities in Forge.
 * 
 * If you want it as a capability too, yell at me.
 * 
 * @author quaternary
 * @since 1.1
 */
public interface ICygnusSparkable {
	/**
	 * Can this blockstate accept a Cygnus spark?
	 * Called when attempting to place a Cygnus spark on this block,
	 * and every tick by the spark itself, to determine if the spark needs to break off.
	 * 
	 * @return if this blockstate can accept a Cygnus spark. 
	 */
	boolean acceptsCygnusSpark(World world, IBlockState state, BlockPos pos);
}
