package quaternary.incorporeal.client.tesr.cygnus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
			GlStateManager.translate(x + .5, y + .5, z + .5);
			GlStateManager.scale(-1 / 32f, -1 / 32f, 1 / 32f);
			
			RenderHelpers.useFullbrightLightmap();
			
			FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
			
			int maxWidth = 16;
			
			int width = fr.getStringWidth(message) / 2;
			double scale = 1;
			if(width > maxWidth) {
				scale = (double) maxWidth / width;
			}
			
			scale -= .02; //dont worry about it
			
			for(int i = 0; i < 4; i++) {
				GlStateManager.pushMatrix();
				//rotate to the correct side of the box
				GlStateManager.rotate(i * 90, 0, 1, 0);
				//translate to the outside of the box
				GlStateManager.translate(0, 0, -16.01);
				//downscale as needed to fit the text on screen
				GlStateManager.scale(scale, scale, scale);
				//translate left half the width of the message and up half the height
				//(missing /2 is because of... idk, actually, probably the font scale?)
				GlStateManager.translate(-width + 1, -3.5, 0);
				fr.drawString(message, 0, 0, 0xFFFFFF);
				GlStateManager.popMatrix();
			}
			
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}
}
