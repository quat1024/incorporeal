package quaternary.incorporeal.etc.proxy;

import net.minecraft.client.Minecraft;

public class ClientProxy extends ServerProxy {
	@Override
	public int getClientDimension() {
		if(Minecraft.getMinecraft().world != null) {
			return Minecraft.getMinecraft().world.provider.getDimension();
		} else return Integer.MAX_VALUE;
	}
}
