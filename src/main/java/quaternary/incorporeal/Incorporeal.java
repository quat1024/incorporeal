package quaternary.incorporeal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.incorporeal.api.IIncorporealAPI;
import quaternary.incorporeal.api.impl.IncorporealAPI;
import quaternary.incorporeal.block.IncorporeticBlocks;
import quaternary.incorporeal.cygnus.types.CygnusErrorType;
import quaternary.incorporeal.cygnus.types.CygnusBigIntegerType;
import quaternary.incorporeal.cygnus.types.CygnusItemStackType;
import quaternary.incorporeal.entity.IncorporeticEntities;
import quaternary.incorporeal.etc.DispenserBehaviorRedstoneRoot;
import quaternary.incorporeal.etc.IncorporeticRuneRecipes;
import quaternary.incorporeal.etc.helper.DespacitoHelper;
import quaternary.incorporeal.etc.proxy.ServerProxy;
import quaternary.incorporeal.flower.IncorporeticFlowers;
import quaternary.incorporeal.flower.IncorporeticPetalRecipes;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import quaternary.incorporeal.net.IncorporeticPacketHandler;
import quaternary.incorporeal.tile.IncorporeticTiles;
import vazkii.botania.common.item.ModItems;

@Mod(
				modid = Incorporeal.MODID,
				name = Incorporeal.NAME,
				version = Incorporeal.VERSION,
				dependencies = Incorporeal.DEPENDENCIES,
				guiFactory = "quaternary.incorporeal.etc.configjunk.IncorporeticGuiFactory",
				updateJSON = "https://raw.githubusercontent.com/quat1024/incorporeal/master/_static/forge_update.json"
)
public final class Incorporeal {	
	public static final String MODID = "incorporeal";
	public static final String NAME = "Incorporeal";
	public static final String VERSION = "GRADLE:VERSION";
	public static final String DEPENDENCIES = "required-after:botania;";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	public static final IIncorporealAPI API = new IncorporealAPI();
	
	@SidedProxy(clientSide = "quaternary.incorporeal.etc.proxy.ClientProxy", serverSide = "quaternary.incorporeal.etc.proxy.ServerProxy")
	public static ServerProxy PROXY;
	
	public static final CreativeTabs TAB = new CreativeTabs(MODID) {
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(IncorporeticItems.TICKET_CONJURER);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {
			super.displayAllRelevantItems(list);
			list.addAll(IncorporeticFlowers.getAllIncorporeticFlowerStacks());
		}
	};
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		IncorporeticConfig.preinit(e);
		
		PROXY.entityRendererBullshit();
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent e) {
		DespacitoHelper.init();
		
		IncorporeticPacketHandler.init();
		
		IncorporeticPetalRecipes.init();
		IncorporeticRuneRecipes.init();
		
		IncorporeticLexicon.init();
		
		//TODO find a better home for these?
		API.getNaturalDeviceRegistry().registerNaturalDevice((rand) -> {
			return IncorporeticBlocks.NATURAL_REPEATER.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(rand.nextInt(4)));
		}, 80);
		
		API.getNaturalDeviceRegistry().registerNaturalDevice((rand) -> {
			return IncorporeticBlocks.NATURAL_COMPARATOR.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(rand.nextInt(4)));
		}, 20);
		
		API.getCygnusDatatypeInfoRegistry().registerDatatype(new CygnusBigIntegerType());
		API.getCygnusDatatypeInfoRegistry().registerDatatype(new CygnusItemStackType());
		API.getCygnusDatatypeInfoRegistry().registerDatatype(new CygnusErrorType());
		
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.manaResource, new DispenserBehaviorRedstoneRoot());
	}
	
	@Mod.EventBusSubscriber(modid = Incorporeal.MODID)
	public static final class CommonEvents {
		private CommonEvents() {}
		
		@SubscribeEvent
		public static void blocks(RegistryEvent.Register<Block> e) {
			IncorporeticBlocks.registerBlocks(e.getRegistry());
			IncorporeticTiles.registerTileEntities();
		}
		
		@SubscribeEvent
		public static void items(RegistryEvent.Register<Item> e) {
			IncorporeticItems.registerItems(e.getRegistry());
			
			IncorporeticFlowers.registerFlowers(); //good spot?
		}
		
		@SubscribeEvent
		public static void entityEntries(RegistryEvent.Register<EntityEntry> e) {
			IncorporeticEntities.registerEntityEntries(e.getRegistry());
		}
	}
}
