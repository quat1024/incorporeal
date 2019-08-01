package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;

public class PieceManager {
	public PieceManager(ResourceLocation res, Map<String, Piece<?, ?>> pieces) {
		this.res = res;
		this.pieces = pieces;
	}
	
	private final ResourceLocation res;
	private final Map<String, Piece<?, ?>> pieces;
	
	@Nullable
	public <T extends Piece<?, ?>> T getPiece(String type) {
		return (T) pieces.get(type);
	}
	
	public void forEachBlock(Consumer<Block> func) {
		pieces.values().forEach(piece -> piece.forEachBlock(func));
	}
	
	public void forEachItem(Consumer<Item> func) {
		pieces.values().forEach(piece -> piece.forEachItem(func));
	}
	
	public void nameBlocks() {
		pieces.values().forEach(Piece::nameBlocks);
	}
	
	public void nameItems() {
		pieces.values().forEach(Piece::nameItems);
	}
}
