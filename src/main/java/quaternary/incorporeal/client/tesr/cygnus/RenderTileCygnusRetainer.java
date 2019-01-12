package quaternary.incorporeal.client.tesr.cygnus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import quaternary.incorporeal.client.RenderHelpers;
import quaternary.incorporeal.cygnus.CygnusDatatypeHelpers;
import quaternary.incorporeal.tile.cygnus.TileCygnusRetainer;

public class RenderTileCygnusRetainer extends TileEntitySpecialRenderer<TileCygnusRetainer> {
	@Override
	public void render(TileCygnusRetainer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(te.hasRetainedObject()) {
			Object o = te.getRetainedObject();
			String message = CygnusDatatypeHelpers.forClass(o.getClass()).toStringUnchecked(o);
			
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.translate(x + .5, y + .5 + 1.8/16f, z + .5);
			GlStateManager.scale(-1/32f, -1/32f, 1/32f);
			
			RenderHelpers.useFullbrightLightmap();
			
			FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
			for(int i = 0; i < 4; i++) {
				GlStateManager.pushMatrix();
				GlStateManager.rotate(i * 90, 0, 1, 0);
				GlStateManager.translate(0, 0, -16.5);
				fr.drawString(message, -fr.getStringWidth(message) / 2, 0, 0xFFFFFF);
				GlStateManager.popMatrix();
			}
			
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}
}
