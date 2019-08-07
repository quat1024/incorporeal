package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFenceGatePiece extends BlockFenceGate {
	public BlockFenceGatePiece(Block block, Material mat, MapColor color, boolean cutout) {
		super(BlockPlanks.EnumType.OAK);
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
