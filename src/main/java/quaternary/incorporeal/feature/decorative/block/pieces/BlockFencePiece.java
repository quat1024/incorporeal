package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFencePiece extends BlockFence {
	public BlockFencePiece(Block block, Material mat, MapColor color, boolean cutout) {
		super(mat, color);
		this.cutout = cutout;
		
		setSoundType(block.getSoundType());
	}
	
	private final boolean cutout;
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return cutout ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
	}
}
