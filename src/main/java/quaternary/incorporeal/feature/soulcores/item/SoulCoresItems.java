package quaternary.incorporeal.feature.soulcores.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.ItemsModule;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import quaternary.incorporeal.feature.soulcores.block.SoulCoresBlocks;

public final class SoulCoresItems extends ItemsModule {
	private SoulCoresItems() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String SOUL_CORE_FRAME = "soul_core_frame";
	}
	
	public static Item SOUL_CORE_FRAME = EtcHelpers.definitelyIsntNullISwear();
	public static ItemBlock ENDER_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	public static ItemBlock CORPOREA_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	public static ItemBlock POTION_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerItems(IForgeRegistry<Item> items) {
		items.registerAll(
			SOUL_CORE_FRAME = name(new Item(), RegistryNames.SOUL_CORE_FRAME),
			ENDER_SOUL_CORE = itemBlock(new ItemBlock(SoulCoresBlocks.ENDER_SOUL_CORE)),
			CORPOREA_SOUL_CORE = itemBlock(new ItemBlock(SoulCoresBlocks.CORPOREA_SOUL_CORE)),
			POTION_SOUL_CORE = itemBlock(new ItemBlock(SoulCoresBlocks.POTION_SOUL_CORE))
		);
	}
}
