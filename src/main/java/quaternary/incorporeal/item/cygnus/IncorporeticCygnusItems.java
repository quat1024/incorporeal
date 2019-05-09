package quaternary.incorporeal.item.cygnus;

import com.google.common.base.Preconditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.cygnus.IncorporeticCygnusBlocks;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.item.IncorporeticItems;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public class IncorporeticCygnusItems extends IncorporeticItems {
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String WORD_CARD = "cygnus_word_card";
		public static final String CRYSTAL_CUBE_CARD = "cygnus_crystal_cube_card";
		
		public static final String MASTER_CYGNUS_SPARK = "master_cygnus_spark";
		public static final String CYGNUS_SPARK = "cygnus_spark";
		
		public static final String CYGNUS_TICKET = "cygnus_ticket";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_CARD)
	public static ItemCygnusWordCard WORD_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CRYSTAL_CUBE_CARD)
	public static ItemCygnusCrystalCubeCard CRYSTAL_CUBE_CARD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.MASTER_CYGNUS_SPARK)
	public static ItemCygnusSpark MASTER_CYGNUS_SPARK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CYGNUS_SPARK)
	public static ItemCygnusSpark CYGNUS_SPARK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CYGNUS_TICKET)
	public static ItemCygnusTicket CYGNUS_TICKET = EtcHelpers.definitelyIsntNullISwear();
	
	//Itemblocks
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.WORD)
	public static ItemBlock WORD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.CRYSTAL_CUBE)
	public static ItemBlock CRYSTAL_CUBE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.FUNNEL)
	public static ItemBlock FUNNEL = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(IncorporeticCygnusBlocks.RegistryNames.RETAINER)
	public static ItemBlock RETAINER = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerItems(IForgeRegistry<Item> reg) {
		reg.registerAll(
			WORD_CARD = createItem(new ItemCygnusWordCard(), RegistryNames.WORD_CARD),
			CRYSTAL_CUBE_CARD = createItem(new ItemCygnusCrystalCubeCard(), RegistryNames.CRYSTAL_CUBE_CARD),
		
			MASTER_CYGNUS_SPARK = createItem(new ItemCygnusSpark(true), RegistryNames.MASTER_CYGNUS_SPARK),
			CYGNUS_SPARK = createItem(new ItemCygnusSpark(false), RegistryNames.CYGNUS_SPARK),
		
			CYGNUS_TICKET = createItem(new ItemCygnusTicket(), RegistryNames.CYGNUS_TICKET),
		
			WORD = createItemBlock(new ItemBlock(IncorporeticCygnusBlocks.WORD)),
			CRYSTAL_CUBE = createItemBlock(new ItemBlock(IncorporeticCygnusBlocks.CRYSTAL_CUBE)),
			FUNNEL = createItemBlock(new ItemBlock(IncorporeticCygnusBlocks.FUNNEL)),
			RETAINER = createItemBlock(new ItemBlock(IncorporeticCygnusBlocks.RETAINER))
		);
	}
}
