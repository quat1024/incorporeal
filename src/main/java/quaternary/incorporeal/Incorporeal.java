package quaternary.incorporeal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.incorporeal.api.IIncorporealAPI;
import quaternary.incorporeal.api.impl.IncorporealAPI;
import quaternary.incorporeal.block.IncorporeticBlocks;
import quaternary.incorporeal.compat.crafttweaker.CTSkytouching;
import quaternary.incorporeal.compat.infraredstone.InfraRedstoneCompat;
import quaternary.incorporeal.cygnus.CygnusDatatypeHelpers;
import quaternary.incorporeal.cygnus.CygnusRegistries;
import quaternary.incorporeal.cygnus.IncorporeticCygnusActions;
import quaternary.incorporeal.cygnus.IncorporeticCygnusConditions;
import quaternary.incorporeal.cygnus.IncorporeticCygnusDatatypes;
import quaternary.incorporeal.cygnus.cap.IncorporeticCygnusCapabilities;
import quaternary.incorporeal.entity.IncorporeticEntities;
import quaternary.incorporeal.etc.CygnusStackDataSerializer;
import quaternary.incorporeal.etc.DispenserBehaviorRedstoneRoot;
import quaternary.incorporeal.etc.EnumDyeColorDataSerializer;
import quaternary.incorporeal.etc.IncorporeticNaturalDevices;
import quaternary.incorporeal.etc.IncorporeticSounds;
import quaternary.incorporeal.etc.LooseRedstoneDustCygnusFunnelable;
import quaternary.incorporeal.etc.LooseRedstoneRepeaterCygnusFunnelable;
import quaternary.incorporeal.etc.helper.DespacitoHelper;
import quaternary.incorporeal.etc.proxy.ServerProxy;
import quaternary.incorporeal.flower.IncorporeticFlowers;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import quaternary.incorporeal.net.IncorporeticPacketHandler;
import quaternary.incorporeal.recipe.IncorporeticPetalRecipes;
import quaternary.incorporeal.recipe.IncorporeticRuneRecipes;
import quaternary.incorporeal.recipe.skytouch.IncorporeticSkytouchingRecipes;
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
		public ItemStack createIcon() {
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
		IncorporeticCygnusCapabilities.preinit(e);
		
		IncorporeticCygnusActions.registerCygnusActions();
		IncorporeticCygnusConditions.registerCygnusConditions();
		IncorporeticCygnusDatatypes.registerCygnusDatatypes();
		
		CygnusStackDataSerializer.preinit(e); //yeet
		EnumDyeColorDataSerializer.preinit(e); //ya yeet
		
		PROXY.preinit();
		
		if(IncorporeticConfig.Compat.INFRAREDSTONE && Loader.isModLoaded("infraredstone")) {
			InfraRedstoneCompat.preinit(e);
		}
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent e) {
		//TODO can this be moved to postinit?
		CygnusRegistries.freezeRegistries();
		CygnusDatatypeHelpers.init();
		
		IncorporeticPacketHandler.init();
		
		IncorporeticPetalRecipes.init();
		IncorporeticRuneRecipes.init();
		IncorporeticSkytouchingRecipes.init();
		
		IncorporeticNaturalDevices.init();
		
		IncorporeticLexicon.init();
		
		//TODO find a better home for this?
		CygnusRegistries.LOOSE_FUNNELABLES.add(new LooseRedstoneDustCygnusFunnelable());
		CygnusRegistries.LOOSE_FUNNELABLES.add(new LooseRedstoneRepeaterCygnusFunnelable());
		
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.manaResource, new DispenserBehaviorRedstoneRoot());
		
		if(Loader.isModLoaded("crafttweaker")) {
			CTSkytouching.init();
		}
	}
	
	@Mod.EventHandler
	public static void postinit(FMLPostInitializationEvent e) {
		if(IncorporeticConfig.Compat.INFRAREDSTONE && Loader.isModLoaded("infraredstone")) {
			InfraRedstoneCompat.postinit(e);
		}
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
		
		@SubscribeEvent
		public static void sounds(RegistryEvent.Register<SoundEvent> e) {
			IncorporeticSounds.registerSounds(e.getRegistry());
		}
	}
}
