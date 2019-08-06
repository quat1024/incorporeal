package quaternary.incorporeal.feature.compat.infraredstone;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import quaternary.incorporeal.IncorporeticFeatures;

public final class InfraRedstoneCompat {
	private InfraRedstoneCompat() {
	}
	
	public static void preinit(FMLPreInitializationEvent e) {
		if(IncorporeticFeatures.isEnabled(IncorporeticFeatures.CYGNUS_NETWORK)) {
			MinecraftForge.EVENT_BUS.register(InRedAttachCapabilitiesEventHandler.class);
		}
	}
	
	public static void postinit(FMLPostInitializationEvent e) {
		if(IncorporeticFeatures.isEnabled(IncorporeticFeatures.CYGNUS_NETWORK)) {
			//Incorporeal.API.registerLooseFunnelable(new LooseInRedWireFunnelable());
		}
	}
}
