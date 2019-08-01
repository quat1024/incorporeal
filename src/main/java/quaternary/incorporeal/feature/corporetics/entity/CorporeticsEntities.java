package quaternary.incorporeal.feature.corporetics.entity;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.EntitiesModule;

public class CorporeticsEntities extends EntitiesModule {
	public static void registerEntities(IForgeRegistry<EntityEntry> entities) {
		entities.register(
			builder(FRACTURED_SPACE_COLLECTOR_ID, EntityFracturedSpaceCollector.class)
				.tracker(64, 2, false)
				.build()
		);
	}
}
