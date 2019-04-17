package quaternary.incorporeal.client.tesr;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.tile.soulcore.AbstractTileSoulCore;
import vazkii.botania.client.core.handler.ClientTickHandler;

import java.util.Map;

public class RenderTileSoulCore<T extends AbstractTileSoulCore> extends TileEntitySpecialRenderer<T> {
	public RenderTileSoulCore(ResourceLocation cubesLocation) {
		this.cubesLocation = cubesLocation;
	}
	
	private final ResourceLocation cubesLocation;
	private final ModelHumanoidHead head = new ModelHumanoidHead();
	
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		//Fix weird bug w/ the soul core frame teisr, TODO what is actually causing this? that it's not actually a block/tile?
		if(rendererDispatcher == null) setRendererDispatcher(TileEntityRendererDispatcher.instance);
		
		int hash = te == null ? 0 : MathHelper.hash(MathHelper.hash(te.getPos().hashCode())) % 1500000;
		float ticks = ClientTickHandler.ticksInGame + partialTicks;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + .5, y + .5, z + .5);
		
		//Wibble wobble
		float rotateY = (hash + ticks) * 2 % 360;
		GlStateManager.rotate(rotateY, 0, 1, 0);
		
		float verticalBob = EtcHelpers.sinDegrees((hash + ticks) * 4);
		GlStateManager.translate(0, 0.1 * verticalBob, 0);
		
		if(te == null) { //Rendering a TEISR
			GlStateManager.scale(0.9, 0.9, 0.9);
		} else { //Rendering an actual tile entity
			GlStateManager.pushMatrix();
			
			float wobble = (hash + ticks) * 5;
			float wobbleSin = EtcHelpers.sinDegrees(wobble);
			float wobbleCos = EtcHelpers.cosDegrees(wobble);
			float wobbleAmountDegrees = 10f;
			GlStateManager.rotate(wobbleCos * wobbleAmountDegrees, 1, 0, 0);
			GlStateManager.rotate(wobbleSin * wobbleAmountDegrees, 1, 0, 0);
			GlStateManager.rotate(-wobbleCos * wobbleAmountDegrees, 0, 0, 1);
			GlStateManager.rotate(-wobbleSin * wobbleAmountDegrees, 0, 0, 1);
			
			//Draw skull doot
			GlStateManager.translate(0, -1 / 4f, 0);
			GlStateManager.scale(-1.0F, -1.0F, 1.0F);
			GlStateManager.enableRescaleNormal();
			GlStateManager.disableCull();
			GlStateManager.enableAlpha();
			
			if(te.hasOwnerProfile()) {
				ResourceLocation skullLocation = getSkullLocation(te);
				bindTexture(skullLocation);
				head.render(null, 0, 0, 0, 0, 0, 1 / 16f);
			}
			
			GlStateManager.popMatrix();
		}
		
		//Draw cubes
		bindTexture(cubesLocation);
		
		GlStateManager.rotate(((-ticks + hash) / 5f) % 360, 0, 1, 0);
		GlStateManager.rotate(MathHelper.sin((ticks + hash) / 50f) * 40, 0, 1, 0);
		
		float wobble2 = (hash + ticks) * 3;
		float wobble2Sin = EtcHelpers.sinDegrees(wobble2);
		float wobble2Cos = EtcHelpers.cosDegrees(wobble2);
		float wobble2AmountDegrees = 10f;
		GlStateManager.rotate(-wobble2Cos * wobble2AmountDegrees, 1, 0, 0);
		GlStateManager.rotate(-wobble2Sin * wobble2AmountDegrees, 1, 0, 0);
		GlStateManager.rotate(wobble2Cos * wobble2AmountDegrees, 0, 0, 1);
		GlStateManager.rotate(wobble2Sin * wobble2AmountDegrees, 0, 0, 1);
		
		GlStateManager.enableRescaleNormal();
		GlStateManager.disableLighting();
		
		Tessellator t = Tessellator.getInstance();
		BufferBuilder b = t.getBuffer();
		
		for(int i = 0; i < 8; i++) {
			drawBox(t, b, 0.1f, 0.1f, 0.1f, 0.35f);
			GlStateManager.scale(1, -1, -1);
			if(i % 2 == 0) GlStateManager.rotate(90, 0, 1, 0);
		}
		
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
	
	private static void drawBox(Tessellator t, BufferBuilder b, float x1, float y1, float z1, float size) {
		float x2 = x1 + size;
		float y2 = y1 + size;
		float z2 = z1 + size;
		
		b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		
		//low y
		b.pos(x1, y1, z1).tex(1, 1).endVertex();
		b.pos(x2, y1, z1).tex(0, 1).endVertex();
		b.pos(x2, y1, z2).tex(0, 0).endVertex();
		b.pos(x1, y1, z2).tex(1, 0).endVertex();
		
		//high y
		b.pos(x1, y2, z1).tex(0, 0).endVertex();
		b.pos(x1, y2, z2).tex(1, 0).endVertex();
		b.pos(x2, y2, z2).tex(1, 1).endVertex();
		b.pos(x2, y2, z1).tex(0, 1).endVertex();
		
		//low z
		b.pos(x1, y1, z1).tex(1, 1).endVertex();
		b.pos(x1, y2, z1).tex(1, 0).endVertex();
		b.pos(x2, y2, z1).tex(0, 0).endVertex();
		b.pos(x2, y1, z1).tex(0, 1).endVertex();
		
		//high z
		b.pos(x1, y1, z2).tex(0, 0).endVertex();
		b.pos(x2, y1, z2).tex(1, 0).endVertex();
		b.pos(x2, y2, z2).tex(1, 1).endVertex();
		b.pos(x1, y2, z2).tex(0, 1).endVertex();
		
		//low x
		b.pos(x1, y1, z1).tex(1, 1).endVertex();
		b.pos(x1, y1, z2).tex(1, 0).endVertex();
		b.pos(x1, y2, z2).tex(0, 0).endVertex();
		b.pos(x1, y2, z1).tex(0, 1).endVertex();
		
		//high x
		b.pos(x2, y1, z1).tex(0, 0).endVertex();
		b.pos(x2, y2, z1).tex(1, 0).endVertex();
		b.pos(x2, y2, z2).tex(1, 1).endVertex();
		b.pos(x2, y1, z2).tex(0, 1).endVertex();
		
		t.draw();
	}
	
	private ResourceLocation getSkullLocation(T te) {
		ResourceLocation skullLocation = DefaultPlayerSkin.getDefaultSkinLegacy();
						
		if(te != null) {
			GameProfile skullProfile = te.getOwnerProfile();
			Minecraft mc = Minecraft.getMinecraft();
			Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> skin = mc.getSkinManager().loadSkinFromCache(skullProfile);
				
			if(skin.containsKey(MinecraftProfileTexture.Type.SKIN)) {
					skullLocation = mc.getSkinManager().loadSkin(skin.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
			} else {
				skullLocation = DefaultPlayerSkin.getDefaultSkin(skullProfile.getId());
			}
		}
		
		return skullLocation;
	}
}
