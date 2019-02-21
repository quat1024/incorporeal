package quaternary.incorporeal.compat.infraredstone;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import quaternary.incorporeal.Incorporeal;

public class InfraRedstoneCompat {
	public static void preinit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(InRedAttachCapabilitiesEventHandler.class);
	}
	
	public static void postinit(FMLPostInitializationEvent e) {
		Incorporeal.API.registerLooseFunnelable(new LooseInRedWireFunnelable());
	}
}
