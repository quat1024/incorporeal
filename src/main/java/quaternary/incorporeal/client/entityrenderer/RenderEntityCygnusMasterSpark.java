package quaternary.incorporeal.client.entityrenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import quaternary.incorporeal.client.IncorporeticClientIcons;
import quaternary.incorporeal.client.event.IncorporeticClientTickHandler;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import vazkii.botania.client.core.handler.MiscellaneousIcons;
import vazkii.botania.client.render.entity.RenderSparkBase;

import java.util.Optional;

public class RenderEntityCygnusMasterSpark extends RenderSparkBase<EntityCygnusMasterSpark> {
	public RenderEntityCygnusMasterSpark(RenderManager manager) {
		super(manager);
	}
	
	@Override
	protected TextureAtlasSprite getBaseIcon(EntityCygnusMasterSpark entity) {
		return IncorporeticClientIcons.masterCygnusSpark;
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
	
	@Override
	protected void renderCallback(EntityCygnusMasterSpark entity, float pticks) {
		CygnusStack stack = entity.getCygnusStack();
		if(stack != null && !stack.isEmpty()) {
			Minecraft mc = Minecraft.getMinecraft();
			Entity renderView = mc.getRenderViewEntity();
			if(renderView == null) return;
			FontRenderer font = mc.fontRenderer;
			
			mc.mcProfiler.startSection("incorporeal-master-spark");
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 1, 0);
			GlStateManager.rotate(180 - IncorporeticClientTickHandler.easedYaw, 0, 1, 0);
			GlStateManager.rotate(- renderView.rotationPitch, 1, 0, 0);
			GlStateManager.scale(1/16d, -1/16d, 1/16d);
			GlStateManager.color(255, 255, 255);
			//big wips lol
			for(int i = 0; i < stack.depth(); i++) {
				stack.peek(i).ifPresent(o -> {
					String toDraw = o.getClass().getSimpleName() + ' ' + o.toString(); //Temp
					font.drawString(toDraw, -font.getStringWidth(toDraw) / 2, 0, 0xFFFFFF);
					GlStateManager.translate(0, -16, 0);
				});
			}
			
			GlStateManager.popMatrix();
			
			mc.mcProfiler.endSection();
		}
	}
}
