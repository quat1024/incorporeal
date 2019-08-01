package quaternary.incorporeal.feature.decorative.client.tesr;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.MathHelper;
import quaternary.incorporeal.feature.decorative.block.BlockUnstableCube;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import quaternary.incorporeal.feature.decorative.tile.TileUnstableCube;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderTileUnstableCube extends TileEntitySpecialRenderer<TileUnstableCube> {
	@Override
	public void render(TileUnstableCube te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(te == null) return;
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		if(!(state.getBlock() instanceof BlockUnstableCube)) return;
		
		GlStateManager.pushMatrix();
		doTransformations(te, partialTicks, x, y, z);
		
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		BlockRendererDispatcher brd = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockModelShapes bms = brd.getBlockModelShapes();
		BlockModelRenderer bmr = brd.getBlockModelRenderer();
		GlStateManager.rotate(-90, 0, 1, 0); //literally just counteract the weird rotation in the below func lmao
		bmr.renderModelBrightness(bms.getModelForState(state), state, 1f, true);
		
		GlStateManager.popMatrix();
	}
	
	//spaghetti emoji
	//this is used in the custom block highlight for this block, that spins with the block
	//so obvs they need the same transformations; might as well break them out into a method to call
	public static void doTransformations(TileUnstableCube te, float partialTicks, double x, double y, double z) {
		float ticks = ClientTickHandler.ticksInGame + partialTicks;
		
		float predictedTileAngle = te.rotationAngle + (te.rotationSpeed * partialTicks);
		
		int hash = MathHelper.hash(MathHelper.hash(te.getPos().hashCode())) % 50000;
		
		GlStateManager.translate(x + .5, y + .5, z + .5);
		GlStateManager.rotate((predictedTileAngle + hash) % 360, 0, 1, 0);
		
		float wobble = ticks + hash;
		float wobbleSin = EtcHelpers.sinDegrees(wobble);
		float wobbleCos = EtcHelpers.cosDegrees(wobble);
		float wobbleAmountDegrees = 15;
		GlStateManager.rotate((float) Math.sin(hash + ticks * 0.02) * 40, 1, 0, 1);
		GlStateManager.rotate(wobbleCos * wobbleAmountDegrees, 1, 0, 0);
		GlStateManager.rotate(wobbleSin * wobbleAmountDegrees, 1, 0, 0);
		GlStateManager.rotate(-wobbleSin * wobbleAmountDegrees, 0, 0, 1);
		GlStateManager.rotate(-wobbleCos * wobbleAmountDegrees, 0, 0, 1);
		
		GlStateManager.translate(-.5, -.5, -.5);
	}
}
