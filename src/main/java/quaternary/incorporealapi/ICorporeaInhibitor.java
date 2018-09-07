package quaternary.incorporealapi;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * ASM hackery in EntitySpark causes shouldBlockCorporea to be called on any Blocks that implement this interface.
 * 
 * @author quaternary
 * @since 1.1
 */
public interface ICorporeaInhibitor {
	/**
	 * @return Are corporea sparks disallowed from forming connections that pass through this block?
	 */
	boolean shouldBlockCorporea(World world, IBlockState state, BlockPos pos);
}
