package quaternary.incorporeal.feature.cygnusnetwork.client.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;
import quaternary.incorporeal.feature.cygnusnetwork.tile.TileCygnusCrystalCube;
import vazkii.botania.client.core.handler.ClientTickHandler;

import java.util.function.Predicate;

public class RenderTileCygnusCrystalCube extends TileEntitySpecialRenderer<TileCygnusCrystalCube> {
	//HUGE portions copied from RenderTileCorporeaCrystalCube, understandably since they are similar, lol
	
	private EntityItem entity = null;
	private RenderEntityItem itemRenderer = null;
	private ItemStack stack = new ItemStack(CygnusNetworkItems.CRYSTAL_CUBE_CARD);
	
	@Override
	public void render(TileCygnusCrystalCube cube, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (cube != null) {
			if(entity == null)
				entity = new EntityItem(cube.getWorld(), cube.getPos().getX(), cube.getPos().getY(), cube.getPos().getZ(), stack);
			
			if(itemRenderer == null)
				itemRenderer = new RenderEntityItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()) {
					@Override
					public boolean shouldBob() {
						return false;
					}
				};
			
			entity.age = ClientTickHandler.ticksInGame;
			
			Predicate<ICygnusStack> cond = cube.getCondition();
			CygnusNetworkItems.CRYSTAL_CUBE_CARD.set(stack, cond);
			
			entity.setItem(stack);
		}
		
		double time = ClientTickHandler.ticksInGame + partialTicks;
		double worldTicks = cube == null || cube.getWorld() == null ? 0 : time;
		
		Minecraft mc = Minecraft.getMinecraft();
		
		GlStateManager.pushMatrix();
		GlStateManager.disableRescaleNormal();
		//GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x + 0.5f, y + 0.56f, z + 10/16f); //Magic Constant. Do not touch
		if(!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.rotate(270f, 1f, 0f, 0f);
			mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
			
			GlStateManager.popMatrix();
		}
		
		GlStateManager.color(1F, 1F, 1F);
		
		//GlStateManager.scale(1F, -1F, -1F);
		GlStateManager.enableRescaleNormal();
		GlStateManager.popMatrix();
	}
}
