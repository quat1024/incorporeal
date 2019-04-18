package quaternary.incorporeal.tile.soulcore;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import quaternary.incorporeal.entity.EntityPotionSoulCoreCollector;

import java.util.List;

public class TilePotionSoulCore extends AbstractTileSoulCore {
	@Override
	protected int getMaxMana() {
		return 3000;
	}
	
	@Override
	public void update() {
		super.update();
		
		List<EntityPotionSoulCoreCollector> nearbyCollectors = world.getEntitiesWithinAABB(EntityPotionSoulCoreCollector.class, new AxisAlignedBB(pos));
		if(nearbyCollectors.size() > 2) {
			//How on earth did this happen? Panic and get rid of them
			nearbyCollectors.forEach(Entity::setDead);
		}
		
		if(nearbyCollectors.isEmpty()) {
			EntityPotionSoulCoreCollector epscc = new EntityPotionSoulCoreCollector(world);
			epscc.setPosition(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
			world.spawnEntity(epscc);
		}
	}
}
