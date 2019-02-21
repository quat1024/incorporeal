package quaternary.incorporeal.client.tesr.cygnus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.animation.Animation;
import net.minecraftforge.common.animation.Event;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.Properties;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;
import quaternary.incorporeal.item.cygnus.ItemCygnusCard;
import quaternary.incorporeal.tile.cygnus.TileCygnusCrystalCube;
import vazkii.botania.client.core.handler.ClientTickHandler;

import java.util.function.Predicate;

public class RenderTileCygnusCrystalCube extends TileEntitySpecialRenderer<TileCygnusCrystalCube> {
	//HUGE portions copied from RenderTileCorporeaCrystalCube, understandably since they are similar, lol
	
	private EntityItem entity = null;
	private RenderEntityItem itemRenderer = null;
	private ItemStack stack = new ItemStack(IncorporeticCygnusItems.CRYSTAL_CUBE_CARD);
	
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
			IncorporeticCygnusItems.CRYSTAL_CUBE_CARD.set(stack, cond);
			
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
