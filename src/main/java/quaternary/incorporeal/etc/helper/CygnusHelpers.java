package quaternary.incorporeal.etc.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.entity.cygnus.AbstractEntityCygnusSparkBase;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.entity.cygnus.EntityCygnusRegularSpark;

public final class CygnusHelpers {
	private CygnusHelpers() {}
	
	public static boolean isSparkable(World world, BlockPos pos, boolean master) {
		if(master) return true;
		else {
			IBlockState state = world.getBlockState(pos);
			if(state.getBlock() instanceof ICygnusSparkable) {
				return ((ICygnusSparkable)state.getBlock()).acceptsSpark(world, state, pos);
			} else return false; //TODO tile entities too?
		}
	}
	
	public static void spawnSparkAt(World world, BlockPos pos, boolean master) {
		AbstractEntityCygnusSparkBase spork = master ? new EntityCygnusMasterSpark(world) : new EntityCygnusRegularSpark(world);
		spork.setPosition(pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5);
		world.spawnEntity(spork);
	}
	
	public static boolean isSparked(World world, BlockPos pos) {
		return !world.getEntitiesWithinAABB(AbstractEntityCygnusSparkBase.class, new AxisAlignedBB(pos.up())).isEmpty();
	}
}
