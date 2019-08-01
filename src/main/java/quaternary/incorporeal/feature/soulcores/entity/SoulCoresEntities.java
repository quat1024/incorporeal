package quaternary.incorporeal.feature.soulcores.entity;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.EntitiesModule;

public class SoulCoresEntities extends EntitiesModule {
	public static void registerEntities(IForgeRegistry<EntityEntry> entities) {
		entities.register(
			builder(POTION_SOUL_CORE_COLLECTOR_ID, EntityPotionSoulCoreCollector.class)
				.tracker(10, 20, false)
				.build()
		);
	}
}
