package quaternary.incorporeal.client.tesr.cygnus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
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
import quaternary.incorporeal.tile.cygnus.TileCygnusCrystalCube;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderTileCygnusCrystalCube extends TileEntitySpecialRenderer<TileCygnusCrystalCube> {
	//HUGE portions copied from RenderTileCorporeaCrystalCube, understandably since they are similar, lol
	
	private EntityItem entity = null;
	private RenderEntityItem itemRenderer = null;
	
	@Override
	public void render(TileCygnusCrystalCube cube, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		ItemStack stack = ItemStack.EMPTY;
		if (cube != null) {
			if(entity == null)
				entity = new EntityItem(cube.getWorld(), cube.getPos().getX(), cube.getPos().getY(), cube.getPos().getZ(), new ItemStack(Blocks.STONE));
			
			if(itemRenderer == null)
				itemRenderer = new RenderEntityItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()) {
					@Override
					public boolean shouldBob() {
						return false;
					}
				};
			
			entity.age = ClientTickHandler.ticksInGame;
			stack = ItemStack.EMPTY; //TODO
			entity.setItem(stack);
		}
		
		double time = ClientTickHandler.ticksInGame + partialTicks;
		double worldTicks = cube == null || cube.getWorld() == null ? 0 : time;
		
		Minecraft mc = Minecraft.getMinecraft();
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(0.5F, 1.5F, 0.5F);
		GlStateManager.scale(1F, -1F, -1F);
		GlStateManager.translate(0F, (float) Math.sin(worldTicks / 20.0 * 1.55) * 0.025F, 0F);
		if(!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			float s = stack.getItem() instanceof ItemBlock ? 0.7F : 0.5F;
			GlStateManager.translate(0F, 0.8F, 0F);
			GlStateManager.scale(s, s, s);
			GlStateManager.rotate(180F, 0F, 0F, 1F);
			itemRenderer.doRender(entity, 0, 0, 0, 1F, partialTicks);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.color(1F, 1F, 1F);
		
		if(!stack.isEmpty()) {
			int count = 1;
			String countStr = "" + count;
			int color = 0xA0FFFFFF;
			int colorShade = (color & 16579836) >> 2 | color & -16777216;
			
			float s = 1F / 64F;
			GlStateManager.scale(s, s, s);
			GlStateManager.disableLighting();
			int l = mc.fontRenderer.getStringWidth(countStr);
			
			GlStateManager.translate(0F, 55F, 0F);
			float tr = -16.5F;
			for(int i = 0; i < 4; i++) {
				GlStateManager.rotate(90F, 0F, 1F, 0F);
				GlStateManager.translate(0F, 0F, tr);
				mc.fontRenderer.drawString(countStr, -l / 2, 0, color);
				GlStateManager.translate(0F, 0F, 0.1F);
				mc.fontRenderer.drawString(countStr, -l / 2 + 1, 1, colorShade);
				GlStateManager.translate(0F, 0F, -tr - 0.1F);
			}
			GlStateManager.enableLighting();
		}
		
		GlStateManager.scale(1F, -1F, -1F);
		GlStateManager.enableRescaleNormal();
		GlStateManager.popMatrix();
		
		renderAnimatedModel(cube, x, y, z, partialTicks);
	}
	
	// Copied from AnimationTESR
	private static BlockRendererDispatcher blockRenderer;
	
	private void renderAnimatedModel(TileCygnusCrystalCube te, double x, double y, double z, float partialTick) {
		// From FastTESR.render
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		
		if (Minecraft.isAmbientOcclusionEnabled())
		{
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		}
		else
		{
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}
		
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		
		// Inlined AnimationTESR.renderTileEntityFast
		if(!te.hasCapability(CapabilityAnimation.ANIMATION_CAPABILITY, null))
		{
			return;
		}
		if(blockRenderer == null) blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockPos pos = te.getPos();
		IBlockAccess world = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), pos);
		IBlockState state = world.getBlockState(pos);
		if(state.getPropertyKeys().contains(Properties.StaticProperty))
		{
			state = state.withProperty(Properties.StaticProperty, false);
		}
		if(state instanceof IExtendedBlockState)
		{
			IExtendedBlockState exState = (IExtendedBlockState)state;
			if(exState.getUnlistedNames().contains(Properties.AnimationProperty))
			{
				float time = Animation.getWorldTime(getWorld(), partialTick);
				Pair<IModelState, Iterable<Event>> pair = te.getCapability(CapabilityAnimation.ANIMATION_CAPABILITY, null).apply(time);
				// handleEvents(te, time, pair.getRight());
				
				IBakedModel model = blockRenderer.getBlockModelShapes().getModelForState(exState.getClean());
				exState = exState.withProperty(Properties.AnimationProperty, pair.getLeft());
				
				worldRenderer.setTranslation(x - pos.getX(), y - pos.getY(), z - pos.getZ());
				
				blockRenderer.getBlockModelRenderer().renderModel(world, model, exState, pos, worldRenderer, false);
			}
		}
		// End inline AnimationTESR.renderTileEntityFast
		
		worldRenderer.setTranslation(0, 0, 0);
		
		tessellator.draw();
		
		RenderHelper.enableStandardItemLighting();
	}
}
