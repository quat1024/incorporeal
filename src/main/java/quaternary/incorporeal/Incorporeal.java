package quaternary.incorporeal;

import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.incorporeal.api.IncorporealNaturalDeviceRegistry;
import quaternary.incorporeal.block.IncorporeticBlocks;
import quaternary.incorporeal.entity.IncorporeticEntities;
import quaternary.incorporeal.etc.DispenserBehaviorRedstoneRoot;
import quaternary.incorporeal.flower.IncorporeticFlowers;
import quaternary.incorporeal.flower.IncorporeticPetalRecipes;
import quaternary.incorporeal.flower.SubTileSanvocalia;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import quaternary.incorporeal.tile.IncorporeticTiles;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.signature.BasicSignature;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockFloatingSpecialFlower;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

@SuppressWarnings("ALL")
@Mod(modid = Incorporeal.MODID, name = Incorporeal.NAME, version = Incorporeal.VERSION, dependencies = Incorporeal.DEPENDENCIES)
public final class Incorporeal {	
	public static final String MODID = "incorporeal";
	public static final String NAME = "Incorporeal";
	public static final String VERSION = "0";
	public static final String DEPENDENCIES = "required-after:botania;";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	public static final CreativeTabs TAB = new CreativeTabs(MODID) {
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(IncorporeticItems.CORPOREA_TICKET);
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
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent e) {
		IncorporeticPetalRecipes.init();
		IncorporeticLexicon.init();
		
		//TODO find a better home for these?
		//TODO replace with custom "natural" crops too
		IncorporealNaturalDeviceRegistry.addNaturalDevice((rand) -> {
			return Blocks.UNPOWERED_COMPARATOR.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(rand.nextInt(4)));
		}, 3);
		
		IncorporealNaturalDeviceRegistry.addNaturalDevice((rand) -> {
			return Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(rand.nextInt(4)));
		}, 5);
		
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.manaResource, new DispenserBehaviorRedstoneRoot());
	}
	
	@Mod.EventBusSubscriber(modid = Incorporeal.MODID)
	public static final class CommonEvents {
		private CommonEvents() {}
		
		@SubscribeEvent
		public static void blocks(RegistryEvent.Register<Block> e) {
			IncorporeticBlocks.registerBlocks(e.getRegistry());
			IncorporeticTiles.registerTileEntities();
			IncorporeticFlowers.registerFlowers(); //good spot?
		}
		
		@SubscribeEvent
		public static void items(RegistryEvent.Register<Item> e) {
			IncorporeticItems.registerItems(e.getRegistry());
		}
		
		@SubscribeEvent
		public static void entityEntries(RegistryEvent.Register<EntityEntry> e) {
			IncorporeticEntities.registerEntityEntries(e.getRegistry());
		}
	}
	
	@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Incorporeal.MODID)
	public static final class ClientEvents {
		private ClientEvents() {}
		
		@SubscribeEvent
		public static void models(ModelRegistryEvent e) {
			//TODO BotaniaAPIClient.registerSubtileModel("sanvocalia", etc etc)
		}
	}
}
