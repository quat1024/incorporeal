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
		
		public static final String WORD_CARD = "cygnus_word_card";
		public static final String CRYSTAL_CUBE_CARD = "cygnus_crystal_cube_card";
		
		public static final String MASTER_CYGNUS_SPARK = "master_cygnus_spark";
		public static final String CYGNUS_SPARK = "cygnus_spark";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_CARD)
	public static final ItemCygnusWordCard WORD_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CRYSTAL_CUBE_CARD)
	public static final ItemCygnusCrystalCubeCard CRYSTAL_CUBE_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.MASTER_CYGNUS_SPARK)
	public static final ItemCygnusSpark MASTER_CYGNUS_SPARK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CYGNUS_SPARK)
	public static final ItemCygnusSpark CYGNUS_SPARK = EtcHelpers.definitelyIsntNullISwear();
	
	//Itemblocks
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.WORD)
	public static final ItemBlock WORD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.CRYSTAL_CUBE)
	public static final ItemBlock CRYSTAL_CUBE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.FUNNEL)
	public static final ItemBlock FUNNEL = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.RETAINER)
	public static final ItemBlock RETAINER = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerItems(IForgeRegistry<Item> reg) {
		registerItem(new ItemCygnusWordCard(), RegistryNames.WORD_CARD, reg);
		registerItem(new ItemCygnusCrystalCubeCard(), RegistryNames.CRYSTAL_CUBE_CARD, reg);
		
		registerItem(new ItemCygnusSpark(true), RegistryNames.MASTER_CYGNUS_SPARK, reg);
		registerItem(new ItemCygnusSpark(false), RegistryNames.CYGNUS_SPARK, reg);
		
		registerItemBlock(new ItemBlock(IncorporeticCygnusBlocks.WORD), reg);
		registerItemBlock(new ItemBlock(IncorporeticCygnusBlocks.CRYSTAL_CUBE), reg);
		registerItemBlock(new ItemBlock(IncorporeticCygnusBlocks.FUNNEL), reg);
		registerItemBlock(new ItemBlock(IncorporeticCygnusBlocks.RETAINER), reg);
	}
	
	private static void registerItem(Item item, String name, IForgeRegistry<Item> reg) {
		item.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		item.setTranslationKey(Incorporeal.MODID + "." + name);
		item.setCreativeTab(Incorporeal.TAB);
		reg.register(item);
	}
	
	private static void registerItemBlock(ItemBlock itemBlock, IForgeRegistry<Item> reg) {
		itemBlock.setRegistryName(Preconditions.checkNotNull(itemBlock.getBlock().getRegistryName()));
		itemBlock.setCreativeTab(Incorporeal.TAB);
		reg.register(itemBlock);
	}
}
