package quaternary.incorporeal.feature.decorative.block.pieces;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.feature.decorative.block.BlockLokiW;

import java.util.HashMap;
import java.util.Map;

public class PieceBuilder {
	public PieceBuilder(Block block) {
		this(block, block.getMaterial(null), block.getMapColor(null, null, null), block instanceof BlockLokiW);
	}
	
	public PieceBuilder(Block block, Material mat, MapColor color, boolean cutout) {
		Preconditions.checkNotNull(block, "No block!");
		Preconditions.checkNotNull(mat, "No material!");
		Preconditions.checkNotNull(color, "No color!");
		
		this.block = block;
		this.mat = mat;
		this.color = color;
		this.cutout = cutout;
		
		this.res = block.getRegistryName();
	}
	
	private final ResourceLocation res;
	private final Block block;
	private final Material mat;
	private final MapColor color;
	private final boolean cutout;
	
	private final Map<String, Piece<?, ?>> pieces = new HashMap<>();
	
	public PieceBuilder addSlab() {
		return addPiece(new Piece.Slab(block, mat, color, cutout));
	}
	
	public PieceBuilder addStair() {
		return addPiece(new Piece.Stair(block, mat, color, cutout));
	}
	
	public PieceBuilder addFence() {
		return addPiece(new Piece.Fence(block, mat, color, cutout));
	}
	
	public PieceBuilder addWall() {
		return addPiece(new Piece.Wall(block, mat, color, cutout));
	}
	
	public PieceBuilder addPiece(Piece<?, ?> piece) {
		pieces.put(piece.type, piece);
		return this;
	}
	
	public PieceManager build() {
		return new PieceManager(res, pieces);
	}
}
