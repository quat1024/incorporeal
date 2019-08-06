package quaternary.incorporeal;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.incorporeal.api.IIncorporealAPI;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.api.impl.IncorporealAPI;
import quaternary.incorporeal.core.FlowersModule;
import quaternary.incorporeal.core.IncorporeticPacketHandler;
import quaternary.incorporeal.core.etc.proxy.ServerProxy;
import quaternary.incorporeal.core.event.MigrationEvents;
import quaternary.incorporeal.core.sortme.IncorporeticLexicon;
import quaternary.incorporeal.feature.corporetics.item.CorporeticsItems;

@Mod(
	modid = Incorporeal.MODID,
	name = Incorporeal.NAME,
	version = Incorporeal.VERSION,
	dependencies = Incorporeal.DEPENDENCIES,
	guiFactory = "quaternary.incorporeal.core.etc.configjunk.IncorporeticGuiFactory",
	updateJSON = "https://raw.githubusercontent.com/quat1024/incorporeal/master/_static/forge_update.json"
)
public final class Incorporeal {
	public static final String MODID = "incorporeal";
	public static final String NAME = "Incorporeal";
	public static final String VERSION = "GRADLE:VERSION";
	public static final String DEPENDENCIES = "required-after:botania;";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	public static final IIncorporealAPI API = new IncorporealAPI();
	
	@SidedProxy(clientSide = "quaternary.incorporeal.core.etc.proxy.ClientProxy", serverSide = "quaternary.incorporeal.core.etc.proxy.ServerProxy")
	public static ServerProxy PROXY;
	
	public static final CreativeTabs TAB = new CreativeTabs(MODID) {
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack createIcon() {
			if(IncorporeticFeatures.isEnabled(IncorporeticFeatures.CORPORETICS)) {
				return new ItemStack(CorporeticsItems.TICKET_CONJURER);
			} else {
				return new ItemStack(Blocks.RED_FLOWER); //lol
			}
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {
			super.displayAllRelevantItems(list);
			list.addAll(FlowersModule.getAllIncorporeticFlowerStacks());
		}
	};
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		IncorporeticConfig.preinit(e); //load the config and configure enabled features.
		IncorporeticFeatures.forEach(f -> f.preinit(e));
		
		MigrationEvents.register();
		MinecraftForge.EVENT_BUS.register(CommonEvents.class);
		
		PROXY.preinit();
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent e) {
		IncorporeticPacketHandler.init();
		
		IncorporeticFeatures.forEach(f -> f.init(e));
		IncorporeticFeatures.forEach(IFeature::petalRecipes);
		IncorporeticFeatures.forEach(IFeature::runeRecipes);
		
		IncorporeticLexicon.init(); //TODO make sure this is the right home
	}
	
	@Mod.EventHandler
	public static void postinit(FMLPostInitializationEvent e) {
		IncorporeticFeatures.forEach(f -> f.postinit(e));
	}
	
	public static final class CommonEvents {
		private CommonEvents() {
		}
		
		@SubscribeEvent
		public static void blocks(RegistryEvent.Register<Block> e) {
			IncorporeticFeatures.forEach(f -> f.blocks(e.getRegistry()));
			IncorporeticFeatures.forEach(IFeature::tiles);
		}
		
		@SubscribeEvent
		public static void items(RegistryEvent.Register<Item> e) {
			IncorporeticFeatures.forEach(f -> f.items(e.getRegistry()));
		}
		
		@SubscribeEvent
		public static void entityEntries(RegistryEvent.Register<EntityEntry> e) {
			IncorporeticFeatures.forEach(f -> f.entities(e.getRegistry()));
		}
		
		@SubscribeEvent
		public static void sounds(RegistryEvent.Register<SoundEvent> e) {
			IncorporeticFeatures.forEach(f -> f.sounds(e.getRegistry()));
		}
	}
}
