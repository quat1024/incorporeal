package quaternary.incorporeal;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public final class IncorporeticConfig {
	private IncorporeticConfig() {}
	
	public static final class Sanvocalia {
		private Sanvocalia() {}
		
		public static boolean EVERYONE_HEARS_MESSAGES = true;
	}
	
	public static final class SoulCore {
		private SoulCore() {}
		
		public static boolean DEBUG_BLOODCORE_ENTITIES = false;
	}
	
	public static final class Compat {
		private Compat() {}
		
		public static boolean INFRAREDSTONE = true;
	}
	
	public static Configuration config;
	
	static void preinit(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile(), "2");
		config.load();
		
		if("1".equals(config.getLoadedConfigVersion())) {
			//Too lazy to make a proper updater...
			config.removeCategory(config.getCategory("soulcore"));
			config.removeCategory(config.getCategory("etc"));
			config.removeCategory(config.getCategory("general"));
		}
		
		readConfig();
	}
	
	private static void readConfig() {
		//features
		IncorporeticFeatures.forEachIncludingDisabled(f -> {
			if(!f.canDisable()) return;
			
			String name = f.name();
			
			String category = "features";
			if(!f.subcategory().isEmpty()) category += '.' + f.subcategory();
			
			String description = f.description();
			if(!f.requiredModIDs().isEmpty()) {
				StringBuilder oof = new StringBuilder("\nRequires: ");
				for(String modID : f.requiredModIDs()) {
					oof.append(modID);
					oof.append(' ');
				}
				description += oof.toString();
			}
			
			boolean isEnabled = config.get(category, name, description).setRequiresMcRestart(true).getBoolean();
			
			if(isEnabled) IncorporeticFeatures.enableFeature(f);
		});
		
		Sanvocalia.EVERYONE_HEARS_MESSAGES = config.getBoolean("everyoneHearsMessages", IncorporeticFeatures.CORPORETICS.name(), true, "Easter egg spoiler: when the Sanvocalia isn't near any corporea indices but receives a corporea ticket, it will just dump the corporea request into chat, to reference the all-too-often occurrence of players accidentally standing too far away from their corporea indexes in multiplayer and telling everyone \"5 stone\".\n\nIf this is false, only the person who placed the Sanvocalia will see these messages.");
		
		SoulCore.DEBUG_BLOODCORE_ENTITIES = config.getBoolean("debugBloodcoreEntities", IncorporeticFeatures.SOUL_CORES.name(), false, "If this is enabled, the invisible \"incorporeal:potion_soul_core_collector\" entities that Blood Soul Cores create to absorb thrown potion effects will appear as a small white box. If you see these lingering after you remove a bloodcore, let me know. Client-only.");
		
		if(config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public static void configChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equals(Incorporeal.MODID)) {
			readConfig();
		}
	}
}
