package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import quaternary.incorporeal.tile.cygnus.TileCygnusRetainer;

import javax.annotation.Nullable;

public class BlockCygnusRetainer extends BlockCygnusBase {
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCygnusRetainer();
	}
}
