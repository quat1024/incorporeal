package quaternary.incorporeal.client;

import net.minecraft.client.renderer.OpenGlHelper;

public final class RenderHelpers {
	private RenderHelpers() {}
	
	public static void useFullbrightLightmap() {
		/*
		//I just hardcode the result of this calculation. The 15s would be from World#getCombinedLight
		int ambLight = 15 << 20 | 15 << 4;
		int lu = ambLight % 65536;
		int lv = ambLight / 65536;
		*/
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
	}
}
