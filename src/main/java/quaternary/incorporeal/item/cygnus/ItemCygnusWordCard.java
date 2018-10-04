package quaternary.incorporeal.item.cygnus;

import net.minecraft.item.Item;
import quaternary.incorporeal.block.cygnus.BlockCygnusWord;

public class ItemCygnusWordCard extends Item {
	public ItemCygnusWordCard(BlockCygnusWord cygnusWord) {
		this.cygnusWord = cygnusWord;
	}
	
	public BlockCygnusWord cygnusWord;
}
