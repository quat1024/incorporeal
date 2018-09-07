package quaternary.incorporeal.client.tesr.decorative;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.tile.decorative.TileLivingwoodChest;

public class RenderItemLivingwoodChest extends TileEntityItemStackRenderer {
	public RenderItemLivingwoodChest(RenderTileLivingwoodChest tesr) {
		this.tesr = tesr;
	}
	
	private final RenderTileLivingwoodChest tesr;
	
	private final TileLivingwoodChest chestTile = new TileLivingwoodChest();
	
	@Override
	public void renderByItem(ItemStack stack) {
		tesr.render(chestTile, 0, 0, 0, 0, -1, 1);
	}
}
