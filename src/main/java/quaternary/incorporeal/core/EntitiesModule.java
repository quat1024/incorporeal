package quaternary.incorporeal.core;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.incorporeal.Incorporeal;

public class EntitiesModule {
	//cygnus
	protected static final Pair<Integer, String> MASTER_CYGNUS_SPARK_ID = Pair.of(0, "master_cygnus_spark");
	protected static final Pair<Integer, String> CYGNUS_SPARK_ID = Pair.of(1, "cygnus_spark");
	
	protected static final Pair<Integer, String> FRACTURED_SPACE_COLLECTOR_ID = Pair.of(2, "fractured_space_collector");
	
	protected static final Pair<Integer, String> POTION_SOUL_CORE_COLLECTOR_ID = Pair.of(3, "potion_soul_core_collector");
	
	protected static <T extends Entity> EntityEntryBuilder<T> builder(Pair<Integer, String> id, Class<? extends T> classs) {
		return EntityEntryBuilder.<T>create()
			.entity(classs)
			.id(new ResourceLocation(Incorporeal.MODID, id.getRight()), id.getLeft())
			.name(Incorporeal.MODID + '.' + id.getRight());
	}
}
