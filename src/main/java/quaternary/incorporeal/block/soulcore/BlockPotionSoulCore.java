package quaternary.incorporeal.block.soulcore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import quaternary.incorporeal.tile.soulcore.TilePotionSoulCore;

import javax.annotation.Nullable;

public class BlockPotionSoulCore extends AbstractBlockSoulCore {
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TilePotionSoulCore();
	}
}
