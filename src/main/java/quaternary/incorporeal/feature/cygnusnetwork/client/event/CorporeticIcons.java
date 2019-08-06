package quaternary.incorporeal.feature.cygnusnetwork.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.core.client.IconsModule;

public class CorporeticIcons extends IconsModule {
	private CorporeticIcons() {
	}
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(CorporeticIcons.class);
	}
	
	public static TextureAtlasSprite cygnusSpark = null;
	public static TextureAtlasSprite masterCygnusSpark = null;
	
	@SubscribeEvent
	public static void textureStitch(TextureStitchEvent.Pre e) {
		TextureMap map = e.getMap();
		if(map == Minecraft.getMinecraft().getTextureMapBlocks()) {
			cygnusSpark = registerSprite(map, "item/cygnus/spark");
			masterCygnusSpark = registerSprite(map, "item/cygnus/master_spark");
		}
	}
}
