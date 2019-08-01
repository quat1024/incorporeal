package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockWallPiece extends BlockWall {
	public BlockWallPiece(Block modelBlock, Material mat, MapColor color, boolean cutout) {
		super(modelBlock);
		
		setHardness(modelBlock.blockHardness);
		setResistance(modelBlock.getExplosionResistance(null) * 5f / 3f); //why mojang
		
		actualMaterial = mat;
		actualMapColor = color;
	}
	
	public final Material actualMaterial;
	public final MapColor actualMapColor;
	
	@Override
	public Material getMaterial(IBlockState state) {
		return actualMaterial;
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return actualMapColor;
	}
}
