package quaternary.incorporeal.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import quaternary.incorporeal.tile.TileRedStringLiar;
import vazkii.botania.common.block.string.BlockRedString;

import javax.annotation.Nullable;

public class BlockRedStringLiar extends BlockRedString {
	public BlockRedStringLiar(String name) {
		//I don't like this practice but I have to do it to extend the vanilla red string block lol oof
		super(name);
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileRedStringLiar();
	}
}
