package quaternary.incorporeal.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.entity.cygnus.EntityCygnusRegularSpark;

public final class IncorporeticEntities {
	private IncorporeticEntities() {
	}
	
	public static void registerEntityEntries(IForgeRegistry<EntityEntry> reg) {
		int id = 0;
		
		reg.register(EntityEntryBuilder.create()
			.entity(EntityFracturedSpaceCollector.class)
			.id(new ResourceLocation(Incorporeal.MODID, "fractured_space_collector"), id++)
			.tracker(64, 2, false)
			.name(Incorporeal.MODID + '.' + "fractured_space_collector")
			.build()
		);
		
		reg.register(EntityEntryBuilder.create()
			.entity(EntityCygnusMasterSpark.class)
			.id(new ResourceLocation(Incorporeal.MODID, "master_cygnus_spark"), id++)
			.tracker(64, 10, false) //TODO recheck these tracker settings. It's stationary
			.name(Incorporeal.MODID + '.' + "master_cygnus_spark")
			.build()
		);
		
		reg.register(EntityEntryBuilder.create()
			.entity(EntityCygnusRegularSpark.class)
			.id(new ResourceLocation(Incorporeal.MODID, "cygnus_spark"), id++)
			.tracker(64, 10, false)
			.name(Incorporeal.MODID + '.' + "cygnus_spark")
			.build()
		);
		
		reg.register(EntityEntryBuilder.create()
			.entity(EntityPotionSoulCoreCollector.class)
			.id(new ResourceLocation(Incorporeal.MODID, "potion_soul_core_collector"), id++)
			.tracker(10, 20, false)
			.name(Incorporeal.MODID + '.' + "potion_soul_core_collector")
			.build());
	}
}
