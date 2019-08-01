package quaternary.incorporeal.core;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import quaternary.incorporeal.Incorporeal;

public class EntitiesModule {
	//Store the IDs in here because disabling a module should not change the numeric IDs.
	//cygnus
	protected static final String MASTER_CYGNUS_SPARK_ID = "master_cygnus_spark";
	protected static final String CYGNUS_SPARK_ID = "cygnus_spark";
	
	protected static final String FRACTURED_SPACE_COLLECTOR_ID = "fractured_space_collector";
	
	protected static final String POTION_SOUL_CORE_COLLECTOR_ID = "potion_soul_core_collector";
	
	protected static int globalID = 0;
	
	protected static <T extends Entity> EntityEntryBuilder<T> builder(String id, Class<? extends T> classs) {
		return EntityEntryBuilder.<T>create()
			.entity(classs)
			.id(new ResourceLocation(Incorporeal.MODID, id), globalID++)
			.name(Incorporeal.MODID + '.' + id);
	}
}
