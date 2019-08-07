package quaternary.incorporeal.feature.cygnusnetwork.entity;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.EntitiesModule;

public class CygnusNetworkEntities extends EntitiesModule {
	public static void register(IForgeRegistry<EntityEntry> entities) {
		entities.registerAll(
			builder(MASTER_CYGNUS_SPARK_ID, EntityCygnusMasterSpark.class)
				.tracker(64, 10, false)
				.build(),
			
			builder(CYGNUS_SPARK_ID, EntityCygnusRegularSpark.class)
				.tracker(64, 10, false)
				.build()
		);
	}
}
