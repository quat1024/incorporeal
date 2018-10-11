package quaternary.incorporeal.item.cygnus;

import net.minecraft.item.Item;
import quaternary.incorporeal.block.cygnus.BlockCygnusCrystalCube;

public class ItemCygnusCrystalCubeCard extends Item {
	public ItemCygnusCrystalCubeCard(BlockCygnusCrystalCube cygnusCube) {
		this.cygnusCube = cygnusCube;
	}
	
	public final BlockCygnusCrystalCube cygnusCube;
}
