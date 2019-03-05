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
		
		public static boolean EVERYONE_ANYONE = true;
	}
	
	public static final class Compat {
		private Compat() {}
		
		public static boolean INFRAREDSTONE = true;
	}
	
	public static final class General {
		private General() {}
		
		public static boolean CORPOREA_KNOWLEDGE_TYPE = false;
	}
	
	public static Configuration config;
	
	static void preinit(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile(), "1");
		config.load();
		
		readConfig();
	}
	
	private static void readConfig() {
		Sanvocalia.EVERYONE_HEARS_MESSAGES = config.getBoolean("everyoneHearsMessages", "sanvocalia", true, "Easter egg spoiler: when the Sanvocalia isn't near any corporea indices but receives a corporea ticket, it will just dump the corporea request into chat, to reference the all-too-often occurrence of players accidentally standing too far away from their corporea indexes in multiplayer and telling everyone \"5 stone\".\n\nIf this is false, only the person who placed the Sanvocalia will see these messages.");
		
		SoulCore.EVERYONE_ANYONE = config.getBoolean("anyoneCanPlaceAnyones", "soulcore", true, "If false, players can only place soul cores around their own skull; if true, anyone can place soul cores around anyone's skull.");
		
		Compat.INFRAREDSTONE = config.getBoolean("infraredstone", "compat", true, "If InfraRedstone is available, special features will be made available.");
		
		//default this to false since it's WIP
		General.CORPOREA_KNOWLEDGE_TYPE = config.getBoolean("corporeaKnowledgeType", "general", false, "Should Incorporeal move corporea into its own knowledge type? This is because the Ender Artefacts chapter was pretty much taken over by corporea-related things, and it was getting a little out of hand.");
		
		//TODO config versioning system to remove etc.variableRetainerStrength since it was merged into 356
		
		if(config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public static void configChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equals(Incorporeal.MODID)) {
			readConfig();
		}
	}
}
