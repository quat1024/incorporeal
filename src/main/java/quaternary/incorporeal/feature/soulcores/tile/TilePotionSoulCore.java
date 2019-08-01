package quaternary.incorporeal.feature.soulcores.tile;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import quaternary.incorporeal.feature.soulcores.entity.EntityPotionSoulCoreCollector;

import java.util.List;

public class TilePotionSoulCore extends AbstractTileSoulCore {
	@Override
	protected int getMaxMana() {
		return 3000;
	}
	
	@Override
	public void update() {
		super.update();
		if(world.isRemote) return;
		
		List<EntityPotionSoulCoreCollector> nearbyCollectors = world.getEntitiesWithinAABB(EntityPotionSoulCoreCollector.class, new AxisAlignedBB(pos));
		boolean hasPlayer = findPlayer().isPresent();
		
		if(nearbyCollectors.size() > 2 || !hasPlayer) {
			nearbyCollectors.forEach(Entity::setDead);
			nearbyCollectors.clear();
		}
		
		if(nearbyCollectors.isEmpty() && hasPlayer) {
			EntityPotionSoulCoreCollector epscc = new EntityPotionSoulCoreCollector(world);
			epscc.setPosition(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
			world.spawnEntity(epscc);
		}
	}
}
