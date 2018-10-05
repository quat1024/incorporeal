package quaternary.incorporeal.api.cygnus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICygnusSparkable {
	boolean acceptsCygnusSpark(World world, IBlockState state, BlockPos pos);
}
