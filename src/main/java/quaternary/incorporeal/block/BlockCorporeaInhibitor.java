package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import quaternary.incorporeal.api.ICorporeaInhibitor;

public class BlockCorporeaInhibitor extends Block implements ICorporeaInhibitor {
	public BlockCorporeaInhibitor() {
		super(Material.ROCK);
	}
	
	@Override
	public boolean shouldBlockCorporea(World world, IBlockState state) {
		return true;
	}
}
