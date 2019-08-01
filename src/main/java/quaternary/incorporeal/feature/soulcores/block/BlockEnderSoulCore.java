package quaternary.incorporeal.feature.soulcores.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import quaternary.incorporeal.feature.soulcores.tile.TileEnderSoulCore;

import javax.annotation.Nullable;

public class BlockEnderSoulCore extends AbstractBlockSoulCore {
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEnderSoulCore();
	}
}
