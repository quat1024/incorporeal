package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockCygnusBase extends Block {
	public BlockCygnusBase() {
		super(Material.ROCK, MapColor.GREEN);
		setHardness(1f);
		setSoundType(SoundType.METAL);
	}
}
