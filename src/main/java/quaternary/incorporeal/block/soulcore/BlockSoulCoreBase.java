package quaternary.incorporeal.block.soulcore;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockSoulCoreBase extends Block {
	public BlockSoulCoreBase() {
		super(Material.CIRCUITS);
		setHardness(1f);
		setSoundType(SoundType.METAL);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public abstract TileEntity createTileEntity(World world, IBlockState state);
}
