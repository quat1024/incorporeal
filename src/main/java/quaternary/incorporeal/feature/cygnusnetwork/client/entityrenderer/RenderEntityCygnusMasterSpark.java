package quaternary.incorporeal.feature.cygnusnetwork.client.entityrenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import quaternary.incorporeal.core.client.RenderHelpers;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusDatatypeHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.client.event.CameraEaseHandler;
import quaternary.incorporeal.feature.cygnusnetwork.client.event.CorporeticIcons;
import quaternary.incorporeal.feature.cygnusnetwork.entity.EntityCygnusMasterSpark;
import vazkii.botania.client.core.handler.MiscellaneousIcons;
import vazkii.botania.client.render.entity.RenderSparkBase;
import vazkii.botania.common.item.equipment.bauble.ItemMonocle;

import javax.annotation.Nonnull;

public class RenderEntityCygnusMasterSpark extends RenderSparkBase<EntityCygnusMasterSpark> {
	public RenderEntityCygnusMasterSpark(RenderManager manager) {
		super(manager);
	}
	
	@Override
	public void doRender(@Nonnull EntityCygnusMasterSpark ent, double x, double y, double z, float yaw, float partialTicks) {
		Minecraft mc = Minecraft.getMinecraft();
		
		float distanceTo = vazkii.botania.common.core.helper.MathHelper.pointDistanceSpace(ent.posX, ent.posY, ent.posZ, mc.player.posX, mc.player.posY, mc.player.posZ);
		
		if(distanceTo > 40) {
			return;
		}
		
		//render the spark
		super.doRender(ent, x, y, z, yaw, partialTicks);
		
		//Translate again.
		//There's a renderCallback method in sparks, but it bring along some pulsating scaling I don't like.
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		
		CygnusStack stack = ent.getCygnusStack();
		if(stack != null && !stack.isEmpty()) {
			boolean disableDepth = ItemMonocle.hasMonocle(mc.player);
			FontRenderer font = mc.fontRenderer;
			
			GlStateManager.translate(0, 1, 0);
			GlStateManager.rotate(180 - CameraEaseHandler.easedYaw, 0, 1, 0);
			GlStateManager.rotate(-CameraEaseHandler.easedPitch * 0.85f, 1, 0, 0);
			
			float scale = EtcHelpers.rangeRemap(distanceTo, 5, 25, 1 / 32f, 1 / 12f);
			GlStateManager.scale(scale, -scale, scale);
			
			GlStateManager.color(255, 255, 255);
			
			RenderHelpers.useFullbrightLightmap();
			//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 208f, 240f);
			
			if(disableDepth) {
				GlStateManager.disableDepth();
			}
			
			GlStateManager.enableBlend();
			
			//low end of opacity is 0x03 since it kinda renders without transparency below that???
			int alpha = ((int) EtcHelpers.rangeRemap(distanceTo, 20, 40, 255, 3)) & 0xFF;
			int textColor = 0xFFFFFF | (alpha << 24);
			
			for(int i = stack.depth() - 1; i >= 0; i--) {
				stack.peek(i).ifPresent(o -> {
					String toDraw = CygnusDatatypeHelpers.forClass(o.getClass()).toStringUnchecked(o);
					font.drawString(toDraw, -font.getStringWidth(toDraw) / 2, 0, textColor);
					GlStateManager.translate(0, -14, 0);
				});
			}
			
			if(disableDepth) {
				GlStateManager.enableDepth();
			}
		}
		
		GlStateManager.popMatrix();
	}
	
	@Override
	protected TextureAtlasSprite getBaseIcon(EntityCygnusMasterSpark entity) {
		return CorporeticIcons.masterCygnusSpark;
	}
	
	@Override
	protected TextureAtlasSprite getSpinningIcon(EntityCygnusMasterSpark entity) {
		return MiscellaneousIcons.INSTANCE.corporeaIconStar;
	}
	
	@Override
	protected void colorSpinningIcon(EntityCygnusMasterSpark entity, float a) {
		int rgb = entity.getTint().colorValue;
		float r = ((rgb >> 16) & 0xFF) / 255f;
		float g = ((rgb >> 8) & 0xFF) / 255f;
		float b = (rgb & 0xFF) / 255f;
		GlStateManager.color(r, g, b, a);
	}
}
