package quaternary.incorporeal.item.cygnus;

import com.google.common.base.Preconditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.cygnus.BlockCygnusCrystalCube;
import quaternary.incorporeal.block.cygnus.BlockCygnusWord;
import quaternary.incorporeal.block.cygnus.IncorporeticCygnusBlocks;
import quaternary.incorporeal.etc.helper.EtcHelpers;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public final class IncorporeticCygnusItems {
	private IncorporeticCygnusItems() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		static final String cardSuffix = "_card";
		
		public static final String WORD_BLANK_CARD = IncorporeticCygnusBlocks.RegistryNames.WORD_BLANK + cardSuffix;
		
		public static final String WORD_DUPLICATE_CARD = IncorporeticCygnusBlocks.RegistryNames.WORD_DUPLICATE + cardSuffix;
		
		public static final String WORD_NUMBER_ADD_CARD = IncorporeticCygnusBlocks.RegistryNames.WORD_NUMBER_ADD + cardSuffix;
		public static final String WORD_NUMBER_SUBTRACT_CARD = IncorporeticCygnusBlocks.RegistryNames.WORD_NUMBER_SUBTRACT + cardSuffix;
		public static final String WORD_NUMBER_MULTIPLY_CARD = IncorporeticCygnusBlocks.RegistryNames.WORD_NUMBER_MULTIPLY + cardSuffix;
		public static final String WORD_NUMBER_DIVIDE_CARD = IncorporeticCygnusBlocks.RegistryNames.WORD_NUMBER_DIVIDE + cardSuffix;
		
		public static final String WORD_REQUEST_SET_COUNT_CARD = IncorporeticCygnusBlocks.RegistryNames.WORD_REQUEST_SET_COUNT + cardSuffix;
		public static final String WORD_REQUEST_SET_ITEM_CARD = IncorporeticCygnusBlocks.RegistryNames.WORD_REQUEST_SET_ITEM + cardSuffix;
		public static final String WORD_REQUEST_GET_COUNT_CARD = IncorporeticCygnusBlocks.RegistryNames.WORD_REQUEST_GET_COUNT + cardSuffix;
		
		public static final String CUBE_BLANK_CARD = IncorporeticCygnusBlocks.RegistryNames.CUBE_BLANK + cardSuffix;
		
		public static final String CUBE_EMPTY_STACK_CARD = IncorporeticCygnusBlocks.RegistryNames.CUBE_EMPTY_STACK + cardSuffix;
		public static final String CUBE_FULL_STACK_CARD = IncorporeticCygnusBlocks.RegistryNames.CUBE_FULL_STACK + cardSuffix;
		public static final String CUBE_EQUAL_VALUE_CARD = IncorporeticCygnusBlocks.RegistryNames.CUBE_EQUAL_VALUE + cardSuffix;
		public static final String CUBE_EQUAL_TYPE_CARD = IncorporeticCygnusBlocks.RegistryNames.CUBE_EQUAL_TYPE + cardSuffix;
		
		public static final String CUBE_LESS_CARD = IncorporeticCygnusBlocks.RegistryNames.CUBE_LESS + cardSuffix;
		public static final String CUBE_GREATER_CARD = IncorporeticCygnusBlocks.RegistryNames.CUBE_GREATER + cardSuffix;
		
		public static final String CUBE_ERRORED_CARD = IncorporeticCygnusBlocks.RegistryNames.CUBE_ERRORED + cardSuffix;
		
		public static final String MASTER_CYGNUS_SPARK = "master_cygnus_spark";
		public static final String CYGNUS_SPARK = "cygnus_spark";
	}
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.WORD_BLANK)
	public static final ItemBlock WORD_BLANK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_BLANK_CARD)
	public static final ItemCygnusWordCard WORD_BLANK_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_DUPLICATE_CARD)
	public static final ItemCygnusWordCard WORD_DUPLICATE_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_NUMBER_ADD_CARD)
	public static final ItemCygnusWordCard WORD_NUMBER_ADD_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_NUMBER_SUBTRACT_CARD)
	public static final ItemCygnusWordCard WORD_NUMBER_SUBTRACT_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_NUMBER_MULTIPLY_CARD)
	public static final ItemCygnusWordCard WORD_NUMBER_MULTIPLY_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_NUMBER_DIVIDE_CARD)
	public static final ItemCygnusWordCard WORD_NUMBER_DIVIDE_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_REQUEST_SET_COUNT_CARD)
	public static final ItemCygnusWordCard WORD_REQUEST_SET_COUNT_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_REQUEST_SET_ITEM_CARD)
	public static final ItemCygnusWordCard WORD_REQUEST_SET_ITEM_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_REQUEST_GET_COUNT_CARD)
	public static final ItemCygnusWordCard WORD_REQUEST_GET_COUNT_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.CUBE_BLANK)
	public static final ItemBlock CUBE_BLANK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_BLANK_CARD)
	public static final ItemCygnusCrystalCubeCard CUBE_BLANK_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_EMPTY_STACK_CARD)
	public static final ItemCygnusCrystalCubeCard CUBE_EMPTY_STACK_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_FULL_STACK_CARD)
	public static final ItemCygnusCrystalCubeCard CUBE_FULL_STACK_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_EQUAL_VALUE_CARD)
	public static final ItemCygnusCrystalCubeCard CUBE_EQUAL_VALUE_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_EQUAL_TYPE_CARD)
	public static final ItemCygnusCrystalCubeCard CUBE_EQUAL_TYPE_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_LESS_CARD)
	public static final ItemCygnusCrystalCubeCard CUBE_LESS_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_GREATER_CARD)
	public static final ItemCygnusCrystalCubeCard CUBE_GREATER_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_ERRORED_CARD)
	public static final ItemCygnusCrystalCubeCard CUBE_ERRORED_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.MASTER_CYGNUS_SPARK)
	public static final ItemCygnusSpark MASTER_CYGNUS_SPARK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CYGNUS_SPARK)
	public static final ItemCygnusSpark CYGNUS_SPARK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.CYGNUS_FUNNEL)
	public static final ItemBlock CYGNUS_FUNNEL = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.CYGNUS_RETAINER)
	public static final ItemBlock CYGNUS_RETAINER = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerItems(IForgeRegistry<Item> reg) {
		registerItemBlock(new ItemBlock(IncorporeticCygnusBlocks.WORD_BLANK), reg);
		registerWordCard(IncorporeticCygnusBlocks.WORD_BLANK, reg);
		registerWordCard(IncorporeticCygnusBlocks.WORD_DUPLICATE, reg);
		registerWordCard(IncorporeticCygnusBlocks.WORD_NUMBER_ADD, reg);
		registerWordCard(IncorporeticCygnusBlocks.WORD_NUMBER_SUBTRACT, reg);
		registerWordCard(IncorporeticCygnusBlocks.WORD_NUMBER_MULTIPLY, reg);
		registerWordCard(IncorporeticCygnusBlocks.WORD_NUMBER_DIVIDE, reg);
		registerWordCard(IncorporeticCygnusBlocks.WORD_REQUEST_SET_COUNT, reg);
		registerWordCard(IncorporeticCygnusBlocks.WORD_REQUEST_SET_ITEM, reg);
		registerWordCard(IncorporeticCygnusBlocks.WORD_REQUEST_GET_COUNT, reg);
		
		registerItemBlock(new ItemBlock(IncorporeticCygnusBlocks.CUBE_BLANK), reg);
		registerCrystalCard(IncorporeticCygnusBlocks.CUBE_BLANK, reg);
		registerCrystalCard(IncorporeticCygnusBlocks.CUBE_EMPTY_STACK , reg);
		registerCrystalCard(IncorporeticCygnusBlocks.CUBE_FULL_STACK, reg);
		registerCrystalCard(IncorporeticCygnusBlocks.CUBE_EQUAL_VALUE, reg);
		registerCrystalCard(IncorporeticCygnusBlocks.CUBE_EQUAL_TYPE, reg);
		registerCrystalCard(IncorporeticCygnusBlocks.CUBE_LESS, reg);
		registerCrystalCard(IncorporeticCygnusBlocks.CUBE_GREATER, reg);
		registerCrystalCard(IncorporeticCygnusBlocks.CUBE_ERRORED, reg);
		
		registerItem(new ItemCygnusSpark(true), RegistryNames.MASTER_CYGNUS_SPARK, reg);
		registerItem(new ItemCygnusSpark(false), RegistryNames.CYGNUS_SPARK, reg);
		
		registerItemBlock(new ItemBlock(IncorporeticCygnusBlocks.CYGNUS_FUNNEL), reg);
		registerItemBlock(new ItemBlock(IncorporeticCygnusBlocks.CYGNUS_RETAINER), reg);
	}
	
	private static void registerWordCard(BlockCygnusWord word, IForgeRegistry<Item> reg) {
		ItemCygnusWordCard card = new ItemCygnusWordCard(word);
		String name = Preconditions.checkNotNull(word.getRegistryName()).getResourcePath() + RegistryNames.cardSuffix;
		registerItem(card, name, reg);
		word.setAssociatedCard(card);
	}
	
	private static void registerCrystalCard(BlockCygnusCrystalCube cube, IForgeRegistry<Item> reg) {
		ItemCygnusCrystalCubeCard card = new ItemCygnusCrystalCubeCard(cube);
		String name = Preconditions.checkNotNull(cube.getRegistryName()).getResourcePath() + RegistryNames.cardSuffix;
		registerItem(card, name, reg);
		cube.setAssociatedCard(card);
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
