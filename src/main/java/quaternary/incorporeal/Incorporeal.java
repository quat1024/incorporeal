package quaternary.incorporeal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
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
import quaternary.incorporeal.etc.helper.DespacitoHelper;
import quaternary.incorporeal.flower.IncorporeticFlowers;
import quaternary.incorporeal.flower.IncorporeticPetalRecipes;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import quaternary.incorporeal.tile.IncorporeticTiles;
import vazkii.botania.common.item.ModItems;

@Mod(
				modid = Incorporeal.MODID,
				name = Incorporeal.NAME,
				version = Incorporeal.VERSION,
				dependencies = Incorporeal.DEPENDENCIES,
				guiFactory = "quaternary.incorporeal.etc.configjunk.IncorporeticGuiFactory"
)
public final class Incorporeal {	
	public static final String MODID = "incorporeal";
	public static final String NAME = "Incorporeal";
	public static final String VERSION = "GRADLE:VERSION";
	public static final String DEPENDENCIES = "required-after:botania;";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	public static final boolean DEV_ENV = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	
	static {
		//TODO remove for obvious reasons.
		if(!DEV_ENV) {
			FMLLog.bigWarning("[Incorporeal] You are stupid");
			throw new RuntimeException("Incorporeal is not ready to be played yet. I'm ashamed I even have to add this error message");
		}
	}
	
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
			//list.addAll(IncorporeticFlowers.getAllIncorporeticFlowerStacks());
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
		DespacitoHelper.init();
		
		//TODO find a better home for these?
		IncorporealNaturalDeviceRegistry.addNaturalDevice((rand) -> {
			return IncorporeticBlocks.NATURAL_REPEATER.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(rand.nextInt(4)));
		}, 80);
		
		IncorporealNaturalDeviceRegistry.addNaturalDevice((rand) -> {
			return IncorporeticBlocks.NATURAL_COMPARATOR.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(rand.nextInt(4)));
		}, 20);
		
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
}
