package quaternary.incorporeal.item.cygnus;

import com.google.common.base.Preconditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.cygnus.BlockCygnusWord;
import quaternary.incorporeal.block.cygnus.IncorporeticCygnusBlocks;
import quaternary.incorporeal.etc.helper.EtcHelpers;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public final class IncorporeticCygnusItems {
	private IncorporeticCygnusItems() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		static final String cardSuffix = "_card";
		
		public static final String CARD_BLANK = IncorporeticCygnusBlocks.RegistryNames.WORD_BLANK + cardSuffix;
		
		public static final String CARD_DUPLICATE = IncorporeticCygnusBlocks.RegistryNames.WORD_DUPLICATE + cardSuffix;
		
		public static final String CARD_NUMBER_ADD = IncorporeticCygnusBlocks.RegistryNames.WORD_NUMBER_ADD + cardSuffix;
		public static final String CARD_NUMBER_SUBTRACT = IncorporeticCygnusBlocks.RegistryNames.WORD_NUMBER_SUBTRACT + cardSuffix;
		public static final String CARD_NUMBER_MULTIPLY = IncorporeticCygnusBlocks.RegistryNames.WORD_NUMBER_MULTIPLY + cardSuffix;
		public static final String CARD_NUMBER_DIVIDE = IncorporeticCygnusBlocks.RegistryNames.WORD_NUMBER_DIVIDE + cardSuffix;
		
		public static final String CARD_STACK_SET_COUNT = IncorporeticCygnusBlocks.RegistryNames.WORD_STACK_SET_COUNT + cardSuffix;
		public static final String CARD_STACK_SET_ITEM = IncorporeticCygnusBlocks.RegistryNames.WORD_STACK_SET_ITEM + cardSuffix;
		public static final String CARD_STACK_EXTRACT_COUNT = IncorporeticCygnusBlocks.RegistryNames.WORD_STACK_EXTRACT_COUNT + cardSuffix;
		
		public static final String MASTER_CYGNUS_SPARK = "master_cygnus_spark";
		public static final String CYGNUS_SPARK = "cygnus_spark";
	}
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.WORD_BLANK)
	public static final ItemBlock WORD_BLANK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CARD_BLANK)
	public static final ItemCygnusWordCard CARD_BLANK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CARD_DUPLICATE)
	public static final ItemCygnusWordCard CARD_DUPLICATE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CARD_NUMBER_ADD)
	public static final ItemCygnusWordCard CARD_NUMBER_ADD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CARD_NUMBER_SUBTRACT)
	public static final ItemCygnusWordCard CARD_NUMBER_SUBTRACT = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CARD_NUMBER_MULTIPLY)
	public static final ItemCygnusWordCard CARD_NUMBER_MULTIPLY = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CARD_NUMBER_DIVIDE)
	public static final ItemCygnusWordCard CARD_NUMBER_DIVIDE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CARD_STACK_SET_COUNT)
	public static final ItemCygnusWordCard CARD_STACK_SET_COUNT = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CARD_STACK_SET_ITEM)
	public static final ItemCygnusWordCard CARD_STACK_SET_ITEM = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CARD_STACK_EXTRACT_COUNT)
	public static final ItemCygnusWordCard CARD_STACK_EXTRACT_COUNT = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.MASTER_CYGNUS_SPARK)
	public static final ItemCygnusSpark MASTER_CYGNUS_SPARK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CYGNUS_SPARK)
	public static final ItemCygnusSpark CYGNUS_SPARK = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerItems(IForgeRegistry<Item> reg) {
		registerCard(IncorporeticCygnusBlocks.WORD_BLANK, reg);
		registerCard(IncorporeticCygnusBlocks.WORD_DUPLICATE, reg);
		registerCard(IncorporeticCygnusBlocks.WORD_NUMBER_ADD, reg);
		registerCard(IncorporeticCygnusBlocks.WORD_NUMBER_SUBTRACT, reg);
		registerCard(IncorporeticCygnusBlocks.WORD_NUMBER_MULTIPLY, reg);
		registerCard(IncorporeticCygnusBlocks.WORD_NUMBER_DIVIDE, reg);
		registerCard(IncorporeticCygnusBlocks.WORD_STACK_SET_COUNT, reg);
		registerCard(IncorporeticCygnusBlocks.WORD_STACK_SET_ITEM, reg);
		registerCard(IncorporeticCygnusBlocks.WORD_STACK_EXTRACT_COUNT, reg);
		
		registerItemBlock(new ItemBlock(IncorporeticCygnusBlocks.WORD_BLANK), reg);
		
		registerItem(new ItemCygnusSpark(true), RegistryNames.MASTER_CYGNUS_SPARK, reg);
		registerItem(new ItemCygnusSpark(false), RegistryNames.CYGNUS_SPARK, reg);
	}
	
	private static void registerCard(BlockCygnusWord word, IForgeRegistry<Item> reg) {
		ItemCygnusWordCard card = new ItemCygnusWordCard(word);
		String name = Preconditions.checkNotNull(word.getRegistryName()).getResourcePath() + RegistryNames.cardSuffix;
		card.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		card.setUnlocalizedName(Incorporeal.MODID + '.' + name);
		card.setCreativeTab(Incorporeal.TAB);
		
		reg.register(card);
		
		word.setAssociatedCard(card);
	}
	
	private static void registerItem(Item item, String name, IForgeRegistry<Item> reg) {
		item.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		item.setUnlocalizedName(Incorporeal.MODID + "." + name);
		item.setCreativeTab(Incorporeal.TAB);
		reg.register(item);
	}
	
	private static void registerItemBlock(ItemBlock itemBlock, IForgeRegistry<Item> reg) {
		itemBlock.setRegistryName(Preconditions.checkNotNull(itemBlock.getBlock().getRegistryName()));
		itemBlock.setCreativeTab(Incorporeal.TAB);
		reg.register(itemBlock);
	}
}
