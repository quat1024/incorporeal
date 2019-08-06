package quaternary.incorporeal.core.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;

public abstract class IconsModule {
	protected static TextureAtlasSprite registerSprite(TextureMap map, String path) {
		return map.registerSprite(new ResourceLocation(Incorporeal.MODID, path));
	}
}
