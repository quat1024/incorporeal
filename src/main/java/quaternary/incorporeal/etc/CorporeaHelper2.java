package quaternary.incorporeal.etc;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.entity.EntityCorporeaSpark;

import java.util.List;

public class CorporeaHelper2 {
	public static EntityCorporeaSpark getSparkEntityForBlock(World world, BlockPos pos) {
		List<EntityCorporeaSpark> sparks = world.getEntitiesWithinAABB(EntityCorporeaSpark.class, new AxisAlignedBB(pos.up(), pos.add(1, 2, 1)));
		return sparks.isEmpty() ? null : sparks.get(0);
	}
}
