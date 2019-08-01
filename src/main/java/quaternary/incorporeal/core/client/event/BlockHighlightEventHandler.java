package quaternary.incorporeal.core.client.event;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.feature.decorative.block.BlockUnstableCube;
import quaternary.incorporeal.feature.soulcores.block.AbstractBlockSoulCore;
import quaternary.incorporeal.feature.soulcores.client.tesr.RenderTileSoulCore;
import quaternary.incorporeal.feature.decorative.client.tesr.RenderTileUnstableCube;
import quaternary.incorporeal.feature.decorative.tile.TileUnstableCube;
import quaternary.incorporeal.feature.soulcores.tile.AbstractTileSoulCore;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class BlockHighlightEventHandler {
	private BlockHighlightEventHandler() {}
	
	private static final AxisAlignedBB UNSTABLE_CUBE_HIGHLIGHT_AABB = new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 0.75, 0.75).grow(0.002);
	private static final AxisAlignedBB SOUL_CORE_HIGHLIGHT_AABB = new AxisAlignedBB(0.05, 0.05, 0.05, 0.95, 0.95, 0.95).grow(0.002);
	
	@SubscribeEvent
	public static void renderBlockHighlight(DrawBlockHighlightEvent e) {
		RayTraceResult ray = e.getTarget();
		if(ray.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos pos = ray.getBlockPos();
			World world = e.getPlayer().world;
			TileEntity tile = world.getTileEntity(pos);
			Block b = world.getBlockState(pos).getBlock();
			
			if(b instanceof BlockUnstableCube && tile instanceof TileUnstableCube) {
				highlight(e, (x, y, z, partial) -> {
					RenderTileUnstableCube.doTransformations(((TileUnstableCube) tile), partial, x, y, z);
					RenderGlobal.drawSelectionBoundingBox(UNSTABLE_CUBE_HIGHLIGHT_AABB, 0, 0, 0, 0.4f);
				});
			} else if(b instanceof AbstractBlockSoulCore && tile instanceof AbstractTileSoulCore) {
				highlight(e, (x, y, z, partial) -> {
					RenderTileSoulCore.performHighlightTransformations((AbstractTileSoulCore) tile, partial, x, y, z);
					//something...
					GlStateManager.translate(-.5, -.5, -.5);
					RenderGlobal.drawSelectionBoundingBox(SOUL_CORE_HIGHLIGHT_AABB, 0, 0, 0, 0.4f);
				});
			}
		}
	}
	
	private static void highlight(DrawBlockHighlightEvent e, IHighlightHandler h) {
		//Setup
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.glLineWidth(2.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		
		//Transform
		float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
		EntityPlayer player = e.getPlayer();
		BlockPos pos = e.getTarget().getBlockPos();
		//based on the weird stuff in renderglobal#drawselectionbox
		double x = pos.getX() - (player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks);
		double y = pos.getY() - (player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks);
		double z = pos.getZ() - (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks);
		
		//Draw
		GlStateManager.pushMatrix();
		h.apply(x, y, z, Minecraft.getMinecraft().getRenderPartialTicks());
		GlStateManager.popMatrix();
		
		//Cleanup
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		
		//Cancel
		e.setCanceled(true);
	}
	
	private interface IHighlightHandler {
		void apply(double x, double y, double z, float partialTicks);
	}
}
