package quaternary.incorporeal.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;

public class IncorporeticEntities {
	public static void registerEntityEntries(IForgeRegistry<EntityEntry> reg) {
		reg.register(EntityEntryBuilder.create()
						.entity(EntityFracturedSpaceCollector.class)
						.id(new ResourceLocation(Incorporeal.MODID, "fractured_space_collector"), 0)
						.tracker(64, 2, false)
						.name("fractured_space_collector")
						.build());
	}
}
