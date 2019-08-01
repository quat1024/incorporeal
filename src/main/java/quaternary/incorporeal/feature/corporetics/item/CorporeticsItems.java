package quaternary.incorporeal.feature.corporetics.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.ItemsModule;
import quaternary.incorporeal.feature.corporetics.block.CorporeticsBlocks;

public final class CorporeticsItems extends ItemsModule {
	private CorporeticsItems() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String TICKET_CONJURER = "ticket_conjurer";
		public static final String CORPOREA_TICKET = "corporea_ticket";
		public static final String FRACTURED_SPACE_ROD = "fractured_space_rod";
	}
	
	
	public static ItemTicketConjurer TICKET_CONJURER = totallyNotNull();
	public static ItemCorporeaTicket CORPOREA_TICKET = totallyNotNull();
	public static ItemFracturedSpaceRod FRACTURED_SPACE_ROD = totallyNotNull();
	
	public static ItemBlock CORPOREA_INHIBITOR = totallyNotNull();
	public static ItemBlock CORPOREA_SOLIDIFIER = totallyNotNull();
	public static ItemBlock CORPOREA_SPARK_TINKERER = totallyNotNull();
	public static ItemBlock CORPOREA_RETAINER_DECREMENTER = totallyNotNull();
	public static ItemBlock FRAME_TINKERER = totallyNotNull();
	public static ItemBlock RED_STRING_LIAR = totallyNotNull();
	
	public static void registerItems(IForgeRegistry<Item> items) {
		items.registerAll(
			TICKET_CONJURER = name(new ItemTicketConjurer(), RegistryNames.TICKET_CONJURER),
			CORPOREA_TICKET = name(new ItemCorporeaTicket(), RegistryNames.CORPOREA_TICKET),
			FRACTURED_SPACE_ROD = name(new ItemFracturedSpaceRod(), RegistryNames.FRACTURED_SPACE_ROD),
			
			CORPOREA_INHIBITOR = itemBlock(new ItemBlock(CorporeticsBlocks.CORPOREA_INHIBITOR)),
			CORPOREA_SOLIDIFIER = itemBlock(new ItemBlock(CorporeticsBlocks.CORPOREA_SOLIDIFIER)),
			CORPOREA_SPARK_TINKERER = itemBlock(new ItemBlock(CorporeticsBlocks.CORPOREA_SPARK_TINKERER)),
			CORPOREA_RETAINER_DECREMENTER = itemBlock(new ItemBlock(CorporeticsBlocks.CORPOREA_RETAINER_DECREMENTER)),
			FRAME_TINKERER = itemBlock(new ItemBlock(CorporeticsBlocks.FRAME_TINKERER)),
			RED_STRING_LIAR = itemBlock(new ItemBlock(CorporeticsBlocks.RED_STRING_LIAR))
		);
	}
}
