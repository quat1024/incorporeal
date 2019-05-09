package quaternary.incorporeal.item;

import com.google.common.base.Preconditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticBlocks;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public class IncorporeticItems {
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String TICKET_CONJURER = "ticket_conjurer";
		public static final String CORPOREA_TICKET = "corporea_ticket";
		public static final String FRACTURED_SPACE_ROD = "fractured_space_rod";
		public static final String SOUL_CORE_FRAME = "soul_core_frame";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.TICKET_CONJURER)
	public static ItemTicketConjurer TICKET_CONJURER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_TICKET)
	public static ItemCorporeaTicket CORPOREA_TICKET = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.FRACTURED_SPACE_ROD)
	public static ItemFracturedSpaceRod FRACTURED_SPACE_ROD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.SOUL_CORE_FRAME)
	public static Item SOUL_CORE_FRAME = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_INHIBITOR)
	public static ItemBlock CORPOREA_INHIBITOR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_SOLIDIFIER)
	public static ItemBlock CORPOREA_SOLIDIFIER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_SPARK_TINKERER)
	public static ItemBlock CORPOREA_SPARK_TINKERER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_RETAINER_DECREMENTER)
	public static ItemBlock CORPOREA_RETAINER_DECREMENTER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.FRAME_TINKERER)
	public static ItemBlock FRAME_TINKERER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.ENDER_SOUL_CORE)
	public static ItemBlock ENDER_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_SOUL_CORE)
	public static ItemBlock CORPOREA_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.POTION_SOUL_CORE)
	public static ItemBlock POTION_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.RED_STRING_LIAR)
	public static ItemBlock RED_STRING_LIAR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.NATURAL_REPEATER)
	public static ItemBlock NATURAL_REPEATER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.NATURAL_COMPARATOR)
	public static ItemBlock NATURAL_COMPARATOR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.UNSTABLE_CUBE)
	public static ItemBlock UNSTABLE_CUBE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.RED_STRING_TILE)
	public static ItemBlock RED_STRING_TILE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_TILE)
	public static ItemBlock CORPOREA_TILE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.RED_STRING_FROST)
	public static ItemBlock RED_STRING_FROST = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_BRICKS)
	public static ItemBlock CORPOREA_BRICKS = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.FORGOTTEN_SHRINE)
	public static ItemBlock FORGOTTEN_SHRINE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.LOKIW)
	public static ItemBlock LOKIW = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerItems(IForgeRegistry<Item> reg) {
		reg.registerAll(
			TICKET_CONJURER = createItem(new ItemTicketConjurer(), RegistryNames.TICKET_CONJURER),
			CORPOREA_TICKET = createItem(new ItemCorporeaTicket(), RegistryNames.CORPOREA_TICKET),
			FRACTURED_SPACE_ROD = createItem(new ItemFracturedSpaceRod(), RegistryNames.FRACTURED_SPACE_ROD),
			SOUL_CORE_FRAME = createItem(new Item(), RegistryNames.SOUL_CORE_FRAME),
			
			CORPOREA_INHIBITOR = createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_INHIBITOR)),
			CORPOREA_SOLIDIFIER = createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_SOLIDIFIER)),
			CORPOREA_SPARK_TINKERER = createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_SPARK_TINKERER)),
			CORPOREA_RETAINER_DECREMENTER = createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_RETAINER_DECREMENTER)),
			FRAME_TINKERER = createItemBlock(new ItemBlock(IncorporeticBlocks.FRAME_TINKERER)),
			
			ENDER_SOUL_CORE = createItemBlock(new ItemBlock(IncorporeticBlocks.ENDER_SOUL_CORE)),
			CORPOREA_SOUL_CORE = createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_SOUL_CORE)),
			POTION_SOUL_CORE = createItemBlock(new ItemBlock(IncorporeticBlocks.POTION_SOUL_CORE)),
			
			RED_STRING_LIAR = createItemBlock(new ItemBlock(IncorporeticBlocks.RED_STRING_LIAR)),
			
			NATURAL_REPEATER = createItemBlock(new ItemBlock(IncorporeticBlocks.NATURAL_REPEATER)),
			NATURAL_COMPARATOR = createItemBlock(new ItemBlock(IncorporeticBlocks.NATURAL_COMPARATOR)),
			
			UNSTABLE_CUBE = createItemBlock(new ItemCloth(IncorporeticBlocks.UNSTABLE_CUBE)),
			RED_STRING_TILE = createItemBlock(new ItemBlock(IncorporeticBlocks.RED_STRING_TILE)),
			CORPOREA_TILE = createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_TILE)),
			RED_STRING_FROST = createItemBlock(new ItemBlock(IncorporeticBlocks.RED_STRING_FROST)),
			CORPOREA_BRICKS = createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_BRICKS)),
			FORGOTTEN_SHRINE = createItemBlock(new ItemBlock(IncorporeticBlocks.FORGOTTEN_SHRINE)),
			LOKIW = createItemBlock(new ItemBlock(IncorporeticBlocks.LOKIW))
		);
		
		IncorporeticCygnusItems.registerItems(reg);
		IncorporeticFluffItems.registerItems(reg);
	}
	
	protected static <T extends Item> T createItem(T item, String name) {
		item.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		item.setTranslationKey(Incorporeal.MODID + "." + name);
		item.setCreativeTab(Incorporeal.TAB);
		return item;
	}
	
	protected static <T extends ItemBlock> T createItemBlock(T itemBlock) {
		itemBlock.setRegistryName(Preconditions.checkNotNull(itemBlock.getBlock().getRegistryName()));
		return itemBlock;
	}
}
