package quaternary.incorporeal.feature.decorative.client.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.feature.soulcores.tile.AbstractTileSoulCore;

public class RenderItemSoulCore extends TileEntityItemStackRenderer {
	public RenderItemSoulCore(TileEntitySpecialRenderer<? extends AbstractTileSoulCore> coreRenderer) {
		this.coreRenderer = coreRenderer;
	}
	
	private final TileEntitySpecialRenderer<? extends AbstractTileSoulCore> coreRenderer;
	
	@Override
	public void renderByItem(ItemStack stack) {
		coreRenderer.render(null, 0, 0, 0, Minecraft.getMinecraft().getRenderPartialTicks(), 0, 0);
	}
}
