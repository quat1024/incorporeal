package quaternary.incorporeal.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class IncorporeticClientIcons {
	private IncorporeticClientIcons() {}
	
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
	
	private static TextureAtlasSprite registerSprite(TextureMap map, String path) {
		return map.registerSprite(new ResourceLocation(Incorporeal.MODID, path));
	}
}
