package quaternary.incorporeal.client.tesr;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.tile.soulcore.AbstractTileSoulCore;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.helper.ShaderHelper;

import java.util.Map;

public class RenderTileSoulCore<T extends AbstractTileSoulCore> extends TileEntitySpecialRenderer<T> {
	public RenderTileSoulCore(ResourceLocation cubesLocation) {
		this.cubesLocation = cubesLocation;
	}
	
	private final ResourceLocation cubesLocation;
	private final ModelHumanoidHead head = new ModelHumanoidHead();
	
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		
		int hash = te == null ? 0 : MathHelper.hash(MathHelper.hash(te.getPos().hashCode())) % 1500000;
		float ticks = ClientTickHandler.ticksInGame + partialTicks;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + .5, y + .5, z + .5);
		
		//Wibble wobble
		float rotateY = (hash + ticks) * 2 % 360;
		GlStateManager.rotate(rotateY, 0, 1, 0);
		
		float verticalBob = EtcHelpers.sinDegrees((hash + ticks) * 4);
		GlStateManager.translate(0, 0.1 * verticalBob, 0);
		
		if(te != null) {
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
			
			ResourceLocation skullLocation = getSkullLocation(te);
			bindTexture(skullLocation);
			
			head.render(null, 0, 0, 0, 0, 0, 1 / 16f);
			
			GlStateManager.popMatrix();
		}
		
		//Draw cubes
		bindTexture(cubesLocation);
		Tessellator t = Tessellator.getInstance();
		BufferBuilder b = t.getBuffer();
		
		GlStateManager.rotate(MathHelper.sin(ticks / 50f) * 40, 0, 1, 0);
		
		float wobble2 = (hash + ticks) * 3;
		float wobble2Sin = EtcHelpers.sinDegrees(wobble2);
		float wobble2Cos = EtcHelpers.cosDegrees(wobble2);
		float wobble2AmountDegrees = 10f;
		GlStateManager.rotate(-wobble2Cos * wobble2AmountDegrees, 1, 0, 0);
		GlStateManager.rotate(-wobble2Sin * wobble2AmountDegrees, 1, 0, 0);
		GlStateManager.rotate(wobble2Cos * wobble2AmountDegrees, 0, 0, 1);
		GlStateManager.rotate(wobble2Sin * wobble2AmountDegrees, 0, 0, 1);
		
		GlStateManager.scale(.4, .4, .4);
		for(float outerX = -1f; outerX < 3f; outerX += 2f) {
			for(float outerY = -1f; outerY < 3f; outerY += 2f) {
				for(float outerZ = -1f; outerZ < 3f; outerZ += 2f) {
					float innerX = outerX / 3f;
					float innerY = outerY / 3f;
					float innerZ = outerZ / 3f;
					
					b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
					
					//top
					b.pos(outerX, outerY, outerZ).tex(1, 1).endVertex();
					b.pos(outerX, outerY, innerZ).tex(1, 0).endVertex();
					b.pos(innerX, outerY, innerZ).tex(0, 0).endVertex();
					b.pos(innerX, outerY, outerZ).tex(0, 1).endVertex();
					
					//bottom
					b.pos(outerX, innerY, outerZ).tex(1, 1).endVertex();
					b.pos(outerX, innerY, innerZ).tex(1, 0).endVertex();
					b.pos(innerX, innerY, innerZ).tex(0, 0).endVertex();
					b.pos(innerX, innerY, outerZ).tex(0, 1).endVertex();
					
					//z out
					b.pos(outerX, outerY, outerZ).tex(1, 1).endVertex();
					b.pos(outerX, innerY, outerZ).tex(1, 0).endVertex();
					b.pos(innerX, innerY, outerZ).tex(0, 0).endVertex();
					b.pos(innerX, outerY, outerZ).tex(0, 1).endVertex();
					
					//z in
					b.pos(outerX, outerY, innerZ).tex(1, 1).endVertex();
					b.pos(outerX, innerY, innerZ).tex(1, 0).endVertex();
					b.pos(innerX, innerY, innerZ).tex(0, 0).endVertex();
					b.pos(innerX, outerY, innerZ).tex(0, 1).endVertex();
					
					//x out
					b.pos(outerX, outerY, outerZ).tex(1, 1).endVertex();
					b.pos(outerX, outerY, innerZ).tex(1, 0).endVertex();
					b.pos(outerX, innerY, innerZ).tex(0, 0).endVertex();
					b.pos(outerX, innerY, outerZ).tex(0, 1).endVertex();
					
					//x in
					b.pos(innerX, outerY, outerZ).tex(1, 1).endVertex();
					b.pos(innerX, outerY, innerZ).tex(1, 0).endVertex();
					b.pos(innerX, innerY, innerZ).tex(0, 0).endVertex();
					b.pos(innerX, innerY, outerZ).tex(0, 1).endVertex();
					
					t.draw();
				}
			}
		}
		
		GlStateManager.popMatrix();
	}
	
	private ResourceLocation getSkullLocation(T te) {
		ResourceLocation skullLocation = DefaultPlayerSkin.getDefaultSkinLegacy();
						
		if(te != null) {
			GameProfile skullProfile = te.getOwnerProfile();
			if(skullProfile != null) {
				Minecraft mc = Minecraft.getMinecraft();
				Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> skin = mc.getSkinManager().loadSkinFromCache(skullProfile);
				
				if(skin.containsKey(MinecraftProfileTexture.Type.SKIN)) {
					skullLocation = mc.getSkinManager().loadSkin(skin.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
				} else {
					skullLocation = DefaultPlayerSkin.getDefaultSkin(skullProfile.getId());
				}
			}
		}
		
		return skullLocation;
	}
}
