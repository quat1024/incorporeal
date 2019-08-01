package quaternary.incorporeal.core.etc.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import quaternary.incorporeal.core.client.ClientRegistryEvents;

public class ClientProxy extends ServerProxy {
	@Override
	public int getClientDimension() {
		World world = Minecraft.getMinecraft().world;
		return world == null ? Integer.MAX_VALUE : world.provider.getDimension();
	}
	
	@Override
	public void preinit() {
		ClientRegistryEvents.preinit();
	}
}
