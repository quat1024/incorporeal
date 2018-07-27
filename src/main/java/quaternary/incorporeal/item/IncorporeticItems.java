package quaternary.incorporeal.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticBlocks;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public class IncorporeticItems {
	public static class RegistryNames {
		public static final String PORTABLE_INDEX = "portable_index";
		public static final String CORPOREA_TICKET = "corporea_ticket";
		public static final String FRACTURED_SPACE_ROD = "fractured_space_rod";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.PORTABLE_INDEX)
	public static final Item PORTABLE_INDEX = Items.AIR;
	
	@GameRegistry.ObjectHolder(RegistryNames.CORPOREA_TICKET)
	public static final Item CORPOREA_TICKET = Items.AIR;
	
	@GameRegistry.ObjectHolder(RegistryNames.FRACTURED_SPACE_ROD)
	public static final Item FRACTURED_SPACE_ROD = Items.AIR;
	
	//IDEA balks at the idea of injecting values into final fields in incorporeticblocks
	//That's what objectholder does, though, so make it shut up.
	@SuppressWarnings("ConstantConditions")
	public static void registerItems(IForgeRegistry<Item> reg) {
		reg.register(createItem(new ItemPortableCorporeaIndex(), RegistryNames.PORTABLE_INDEX));
		reg.register(createItem(new ItemCorporeaTicket(), RegistryNames.CORPOREA_TICKET));
		reg.register(createItem(new ItemFracturedSpaceRod(), RegistryNames.FRACTURED_SPACE_ROD));
		
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_INHIBITOR)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_LIAR)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_SOLIDIFIER)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.CORPOREA_SPARK_TINKERER)));
		reg.register(createItemBlock(new ItemBlock(IncorporeticBlocks.FRAME_TINKERER)));
		reg.register(createItemBlock(new ItemSoulCore(IncorporeticBlocks.ENDER_SOUL_CORE)));
		reg.register(createItemBlock(new ItemSoulCore(IncorporeticBlocks.CORPOREA_SOUL_CORE)));
	}
	
	private static <T extends Item> T createItem(T item, String name) {
		item.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		item.setUnlocalizedName(Incorporeal.MODID + "." + name);
		//TODO creative tab.
		return item;
	}
	
	private static <T extends ItemBlock> T createItemBlock(T itemBlock) {
		itemBlock.setRegistryName(itemBlock.getBlock().getRegistryName());
		return itemBlock;
	}
}
