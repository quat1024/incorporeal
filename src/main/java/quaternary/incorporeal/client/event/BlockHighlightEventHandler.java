package quaternary.incorporeal.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.decorative.BlockUnstableCube;
import quaternary.incorporeal.client.tesr.decorative.RenderTileUnstableCube;
import quaternary.incorporeal.tile.decorative.TileUnstableCube;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class BlockHighlightEventHandler {
	private BlockHighlightEventHandler() {}
	
	private static final AxisAlignedBB UNSTABLE_CUBE_HIGHLIGHT_AABB = new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 0.75, 0.75).grow(0.002);
	
	@SubscribeEvent
	public static void renderBlockHighlight(DrawBlockHighlightEvent e) {
		RayTraceResult ray = e.getTarget();
		if(ray.typeOfHit == RayTraceResult.Type.BLOCK && e.getPlayer().world.getBlockState(ray.getBlockPos()).getBlock() instanceof BlockUnstableCube) {
			BlockPos pos = ray.getBlockPos();
			TileEntity tile = e.getPlayer().world.getTileEntity(pos);
			if(!(tile instanceof TileUnstableCube)) return;
			
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.glLineWidth(2.0F);
			GlStateManager.disableTexture2D();
			GlStateManager.depthMask(false);
			
			float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
			EntityPlayer player = e.getPlayer();
			
			//based on the weird stuff in renderglobal#drawselectionbox
			double x = pos.getX() - (player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks);
			double y = pos.getY() - (player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks);
			double z = pos.getZ() - (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks);
			
			GlStateManager.pushMatrix();
			RenderTileUnstableCube.doTransformations(((TileUnstableCube)tile), partialTicks, x, y, z);
			RenderGlobal.drawSelectionBoundingBox(UNSTABLE_CUBE_HIGHLIGHT_AABB, 0, 0, 0, 0.4f);
			GlStateManager.popMatrix();
			
			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			
			e.setCanceled(true);
		}
	}
}
