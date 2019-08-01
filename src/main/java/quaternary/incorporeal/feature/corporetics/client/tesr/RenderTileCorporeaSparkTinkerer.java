package quaternary.incorporeal.feature.corporetics.client.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import quaternary.incorporeal.feature.corporetics.tile.TileCorporeaSparkTinkerer;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.handler.MiscellaneousIcons;
import vazkii.botania.client.core.proxy.ClientProxy;

/** Majority of this class is based on a butchered copy of RenderSparkBase while basically just hardcoding the method calls to RenderCorporeaSpark. This isn't a pretty class x) */
public class RenderTileCorporeaSparkTinkerer extends TileEntitySpecialRenderer<TileCorporeaSparkTinkerer> {
	@Override
	public void render(TileCorporeaSparkTinkerer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + .5, y + .5, z + .5);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.05F);
		
		double time = ClientTickHandler.ticksInGame + partialTicks + (MathHelper.hash(te.getPos().hashCode()) % 120000);
		
		float a = 0.8F;
		
		GlStateManager.color(1F, 1F, 1F, (0.7F + 0.3F * (float) (Math.sin(time / 5.0) + 0.5) * 2) * a);
		
		float scale = 0.75F + 0.1F * (float) Math.sin(time / 10);
		GlStateManager.scale(scale, scale, scale);
		
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		//bindEntityTexture(tEntity);
		Tessellator tessellator = Tessellator.getInstance();
		
		GlStateManager.pushMatrix();
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		float r = 180.0F - renderManager.playerViewY;
		GlStateManager.rotate(r, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-renderManager.playerViewX, 1F, 0F, 0F);
		//renderIcon(tessellator, MiscellaneousIcons.INSTANCE.corporeaWorldIcon);
		
		TextureAtlasSprite spinningIcon = MiscellaneousIcons.INSTANCE.corporeaIconStar;
		if(spinningIcon != null) {
			GlStateManager.translate(-0.02F + (float) Math.sin(time / 20) * 0.2F, 0.24F + (float) Math.cos(time / 20) * 0.2F, 0.005F);
			//GlStateManager.scale(0.2F, 0.2F, 0.2F);
			colorSpinningIcon(te, a);
			renderIcon(tessellator, spinningIcon);
		}
		GlStateManager.popMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		
		GlStateManager.disableBlend();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}
	
	private static final VertexFormat meme = ClientProxy.POSITION_TEX_LMAP_NORMAL;
	
	private void renderIcon(Tessellator t, TextureAtlasSprite icon) {
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		
		t.getBuffer().begin(GL11.GL_QUADS, meme);
		t.getBuffer().pos(0.0F - f5, 0.0F - f6, 0.0D).tex(f, f3).lightmap(240, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
		t.getBuffer().pos(f4 - f5, 0.0F - f6, 0.0D).tex(f1, f3).lightmap(240, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
		t.getBuffer().pos(f4 - f5, f4 - f6, 0.0D).tex(f1, f2).lightmap(240, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
		t.getBuffer().pos(0.0F - f5, f4 - f6, 0.0D).tex(f, f2).lightmap(240, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
		t.draw();
	}
	
	private static void colorSpinningIcon(TileCorporeaSparkTinkerer tinkerer, float a) {
		int hex = tinkerer.getNetwork().colorValue;
		int r = (hex & 0xFF0000) >> 16;
		int g = (hex & 0xFF00) >> 8;
		int b = hex & 0xFF;
		GlStateManager.color(r / 255F, g / 255F, b / 255F, a);
	}
}
