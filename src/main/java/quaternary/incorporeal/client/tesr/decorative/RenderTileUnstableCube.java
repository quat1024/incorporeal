package quaternary.incorporeal.client.tesr.decorative;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import quaternary.incorporeal.block.decorative.BlockUnstableCube;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.tile.decorative.TileUnstableCube;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderTileUnstableCube extends TileEntitySpecialRenderer<TileUnstableCube> {
	ModelResourceLocation tinyPlanetBoi = new ModelResourceLocation(new ResourceLocation("botania", "tinyplanetblock"), "normal");
	
	@Override
	public void render(TileUnstableCube te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(te == null) return;
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		if(!(state.getBlock() instanceof BlockUnstableCube)) return;
		
		float ticks = ClientTickHandler.ticksInGame + partialTicks;
		
		float predictedTileAngle = te.rotationAngle + (te.rotationSpeed * partialTicks);
		
		int hash = MathHelper.hash(MathHelper.hash(te.getPos().hashCode())) % 50000;
		
		GlStateManager.pushMatrix();
		
		GlStateManager.translate(x + .5, y + .5, z + .5);
		GlStateManager.rotate((predictedTileAngle + hash) % 360, 0, 1, 0);
		
		//float verticalBob = EtcHelpers.sinDegrees((hash + ticks) * 4);
		//GlStateManager.translate(0, .1 * verticalBob, 0);
		
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
		
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		BlockRendererDispatcher brd = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockModelShapes bms = brd.getBlockModelShapes();
		BlockModelRenderer bmr = brd.getBlockModelRenderer();
		bmr.renderModelBrightnessColor(bms.getModelForState(state), 1f, 1f, 1f, 1f);
		
		GlStateManager.popMatrix();
	}
}
