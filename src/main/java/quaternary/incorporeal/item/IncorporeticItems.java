package quaternary.incorporeal.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticBlocks;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
@SuppressWarnings("unused")
public final class IncorporeticItems {
	private IncorporeticItems() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String TICKET_CONJURER = "ticket_conjurer";
		public static final String CORPOREA_TICKET = "corporea_ticket";
		public static final String FRACTURED_SPACE_ROD = "fractured_space_rod";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.TICKET_CONJURER)
	public static final ItemTicketConjurer TICKET_CONJURER = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_TICKET)
	public static final ItemCorporeaTicket CORPOREA_TICKET = null;
	
	@GameRegistry.ObjectHolder(RegistryNames.FRACTURED_SPACE_ROD)
	public static final ItemFracturedSpaceRod FRACTURED_SPACE_ROD = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_INHIBITOR)
	public static final ItemBlock CORPOREA_INHIBITOR = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_LIAR)
	public static final ItemBlock CORPOREA_LIAR = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_SOLIDIFIER)
	public static final ItemBlock CORPOREA_SOLIDIFIER = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_SPARK_TINKERER)
	public static final ItemBlock CORPOREA_SPARK_TINKERER = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_RETAINER_DECREMENTER)
	public static final ItemBlock CORPOREA_RETAINER_DECREMENTER = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.FRAME_TINKERER)
	public static final ItemBlock FRAME_TINKERER = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.ENDER_SOUL_CORE)
	public static final ItemSoulCore ENDER_SOUL_CORE = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.CORPOREA_SOUL_CORE)
	public static final ItemSoulCore CORPOREA_SOUL_CORE = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.NATURAL_REPEATER)
	public static final ItemBlock NATURAL_REPEATER = null;
	
	@GameRegistry.ObjectHolder(IncorporeticBlocks.RegistryNames.NATURAL_COMPARATOR)
	public static final ItemBlock NATURAL_COMPARATOR = null;
	
	//IDEA balks at the idea of injecting values into final fields in incorporeticblocks
	//That's what objectholder does, though, so make it shut up.
	@SuppressWarnings("ConstantConditions")
	public static void registerItems(IForgeRegistry<Item> reg) {
		reg.register(createItem(new ItemTicketConjurer(), RegistryNames.TICKET_CONJURER));
		reg.register(createItem(new ItemCorporeaTicket(), RegistryNames.CORPOREA_TICKET));
		reg.register(createItem(new ItemFracturedSpaceRod(), RegistryNames.FRACTURED_SPACE_ROD));
		
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
	}
	
	private static <T extends Item> T createItem(T item, String name) {
		item.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		item.setUnlocalizedName(Incorporeal.MODID + "." + name);
		item.setCreativeTab(Incorporeal.TAB);
		return item;
	}
	
	private static <T extends ItemBlock> T createItemBlock(T itemBlock) {
		itemBlock.setRegistryName(itemBlock.getBlock().getRegistryName());
		return itemBlock;
	}
}
