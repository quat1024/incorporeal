package quaternary.incorporeal.feature.cygnusnetwork.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.ItemsModule;
import quaternary.incorporeal.feature.cygnusnetwork.block.CygnusNetworkBlocks;

public class CygnusNetworkItems extends ItemsModule {
	public static final class RegistryNames {
		private RegistryNames() {
		}
		
		public static final String WORD_CARD = "cygnus_word_card";
		public static final String CRYSTAL_CUBE_CARD = "cygnus_crystal_cube_card";
		
		public static final String MASTER_CYGNUS_SPARK = "master_cygnus_spark";
		public static final String CYGNUS_SPARK = "cygnus_spark";
		
		public static final String CYGNUS_TICKET = "cygnus_ticket";
	}
	
	public static ItemCygnusWordCard WORD_CARD = null;
	public static ItemCygnusCrystalCubeCard CRYSTAL_CUBE_CARD = null;
	public static ItemCygnusSpark MASTER_CYGNUS_SPARK = null;
	public static ItemCygnusSpark CYGNUS_SPARK = null;
	public static ItemCygnusTicket CYGNUS_TICKET = null;
	
	public static ItemBlock WORD = null;
	public static ItemBlock CRYSTAL_CUBE = null;
	public static ItemBlock FUNNEL = null;
	public static ItemBlock RETAINER = null;
	
	public static void registerItems(IForgeRegistry<Item> reg) {
		reg.registerAll(
			WORD_CARD = name(new ItemCygnusWordCard(), RegistryNames.WORD_CARD),
			CRYSTAL_CUBE_CARD = name(new ItemCygnusCrystalCubeCard(), RegistryNames.CRYSTAL_CUBE_CARD),
			
			MASTER_CYGNUS_SPARK = name(new ItemCygnusSpark(true), RegistryNames.MASTER_CYGNUS_SPARK),
			CYGNUS_SPARK = name(new ItemCygnusSpark(false), RegistryNames.CYGNUS_SPARK),
			
			CYGNUS_TICKET = name(new ItemCygnusTicket(), RegistryNames.CYGNUS_TICKET),
			
			WORD = itemBlock(new ItemBlock(CygnusNetworkBlocks.WORD)),
			CRYSTAL_CUBE = itemBlock(new ItemBlock(CygnusNetworkBlocks.CRYSTAL_CUBE)),
			FUNNEL = itemBlock(new ItemBlock(CygnusNetworkBlocks.FUNNEL)),
			RETAINER = itemBlock(new ItemBlock(CygnusNetworkBlocks.RETAINER))
		);
	}
}