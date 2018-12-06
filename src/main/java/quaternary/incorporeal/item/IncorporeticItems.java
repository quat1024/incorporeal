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
@SuppressWarnings("unused")
public final class IncorporeticItems {
	private IncorporeticItems() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String TICKET_CONJURER = "ticket_conjurer";
		public static final String CORPOREA_TICKET = "corporea_ticket";
		public static final String FRACTURED_SPACE_ROD = "fractured_space_rod";
		public static final String SOUL_CORE_FRAME = "soul_core_frame";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.TICKET_CONJURER)
	public static final ItemTicketConjurer TICKET_CONJURER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_TICKET)
	public static final ItemCorporeaTicket CORPOREA_TICKET = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.FRACTURED_SPACE_ROD)
	public static final ItemFracturedSpaceRod FRACTURED_SPACE_ROD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.SOUL_CORE_FRAME)
	public static final Item SOUL_CORE_FRAME = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_INHIBITOR)
	public static final ItemBlock CORPOREA_INHIBITOR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_LIAR)
	public static final ItemBlock CORPOREA_LIAR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_SOLIDIFIER)
	public static final ItemBlock CORPOREA_SOLIDIFIER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_SPARK_TINKERER)
	public static final ItemBlock CORPOREA_SPARK_TINKERER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_RETAINER_DECREMENTER)
	public static final ItemBlock CORPOREA_RETAINER_DECREMENTER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.FRAME_TINKERER)
	public static final ItemBlock FRAME_TINKERER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.ENDER_SOUL_CORE)
	public static final ItemSoulCore ENDER_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_SOUL_CORE)
	public static final ItemSoulCore CORPOREA_SOUL_CORE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.NATURAL_REPEATER)
	public static final ItemBlock NATURAL_REPEATER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.NATURAL_COMPARATOR)
	public static final ItemBlock NATURAL_COMPARATOR = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.DECORATIVE_UNSTABLE_CUBE)
	public static final ItemBlock DECORATIVE_UNSTABLE_CUBE = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerItems(IForgeRegistry<Item> reg) {
		reg.register(createItem(new ItemTicketConjurer(), RegistryNames.TICKET_CONJURER));
		reg.register(createItem(new ItemCorporeaTicket(), RegistryNames.CORPOREA_TICKET));
		reg.register(createItem(new ItemFracturedSpaceRod(), RegistryNames.FRACTURED_SPACE_ROD));
		reg.register(createItem(new Item(), RegistryNames.SOUL_CORE_FRAME));
		
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_INHIBITOR)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_LIAR)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_SOLIDIFIER)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_SPARK_TINKERER)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_RETAINER_DECREMENTER)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.FRAME_TINKERER)));
		
		reg.register(createItemBlock(new ItemSoulCore(IncorporeticBlocks.ENDER_SOUL_CORE)));
		reg.register(createItemBlock(new ItemSoulCore(IncorporeticBlocks.CORPOREA_SOUL_CORE)));
		
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.NATURAL_REPEATER)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.NATURAL_COMPARATOR)));
		
		reg.register(createItemBlock(new ItemCloth(IncorporeticBlocks.DECORATIVE_UNSTABLE_CUBE)));
		
		IncorporeticCygnusItems.registerItems(reg);
	}
	
	private static <T extends Item> T createItem(T item, String name) {
		item.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		item.setTranslationKey(Incorporeal.MODID + "." + name);
		item.setCreativeTab(Incorporeal.TAB);
		return item;
	}
	
	private static <T extends ItemBlock> T createItemBlock(T itemBlock) {
		itemBlock.setRegistryName(Preconditions.checkNotNull(itemBlock.getBlock().getRegistryName()));
		return itemBlock;
	}
}
