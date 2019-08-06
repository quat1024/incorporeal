package quaternary.incorporeal.core.event;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.feature.corporetics.block.CorporeticsBlocks;
import quaternary.incorporeal.feature.corporetics.item.CorporeticsItems;

public final class MigrationEvents {
	private MigrationEvents() {
	}
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(MigrationEvents.class);
	}
	
	@SubscribeEvent
	public static void missingBlocks(RegistryEvent.MissingMappings<Block> e) {
		for(RegistryEvent.MissingMappings.Mapping<Block> map : e.getMappings()) {
			if(map.key.getPath().equals("corporea_liar")) {
				map.remap(CorporeticsBlocks.RED_STRING_LIAR);
			}
		}
	}
	
	@SubscribeEvent
	public static void missingItems(RegistryEvent.MissingMappings<Item> e) {
		for(RegistryEvent.MissingMappings.Mapping<Item> map : e.getMappings()) {
			if(map.key.getPath().equals("corporea_liar")) {
				map.remap(CorporeticsItems.RED_STRING_LIAR);
			}
		}
	}
}
