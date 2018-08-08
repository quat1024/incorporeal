package quaternary.incorporeal.client.tesr;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import quaternary.incorporeal.tile.soulcore.AbstractTileSoulCore;

import java.util.Map;

public abstract class AbstractRenderTileSoulCore <T extends AbstractTileSoulCore> extends TileEntitySpecialRenderer<T> {
	abstract protected ResourceLocation getCubeResloc();
	
	private final ResourceLocation cubesLocation = getCubeResloc();
	private final ModelHumanoidHead head = new ModelHumanoidHead();
	
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		ResourceLocation skullLocation = getSkullLocation(te);
		bindTexture(skullLocation);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + .5, y, z + .5);
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		
		//following 3 lines are apparently needed
		GlStateManager.enableRescaleNormal();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		
		head.render(null, 0, 0, 0, 0, 0, 0.0625f);
		
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
