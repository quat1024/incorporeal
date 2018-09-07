package quaternary.incorporeal.etc.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientProxy extends ServerProxy {
	@Override
	public int getClientDimension() {
		World world = Minecraft.getMinecraft().world;
		return world == null ? Integer.MAX_VALUE : world.provider.getDimension();
	}
}
