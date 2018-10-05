package quaternary.incorporeal.etc.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.entity.cygnus.AbstractEntityCygnusSparkBase;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.entity.cygnus.EntityCygnusRegularSpark;

import javax.annotation.Nullable;
import java.util.List;

public final class CygnusHelpers {
	private CygnusHelpers() {}
	
	public static boolean isSparkable(World world, BlockPos pos, boolean master) {
		if(master) return true;
		else {
			IBlockState state = world.getBlockState(pos);
			if(state.getBlock() instanceof ICygnusSparkable) {
				return ((ICygnusSparkable)state.getBlock()).acceptsCygnusSpark(world, state, pos);
			} else return false; //TODO tile entities too?
		}
	}
	
	public static void spawnSparkAt(World world, BlockPos pos, boolean master) {
		AbstractEntityCygnusSparkBase spork = master ? new EntityCygnusMasterSpark(world) : new EntityCygnusRegularSpark(world);
		spork.setPosition(pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5);
		world.spawnEntity(spork);
	}
	
	@Nullable
	public static AbstractEntityCygnusSparkBase getSparkAt(World world, BlockPos pos) {
		List<AbstractEntityCygnusSparkBase> sporks = world.getEntitiesWithinAABB(AbstractEntityCygnusSparkBase.class, new AxisAlignedBB(pos.up()));
		return sporks.isEmpty() ? null : sporks.get(0);
	}
	
	public static boolean isSparked(World world, BlockPos pos) {
		return getSparkAt(world, pos) != null;
	}
	
	public static EntityCygnusMasterSpark getMasterSparkForSparkAt(World world, BlockPos pos) {
		AbstractEntityCygnusSparkBase spork = getSparkAt(world, pos);
		return spork == null ? null : spork.getMasterSpark();
	}
}
