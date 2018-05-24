package quaternary.incorporeal.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public interface ICorporeaInhibitor {
	/** If true, corporea sparks cannot connect through this block. */
	boolean shouldBlockCorporea(World world, IBlockState state);
}
