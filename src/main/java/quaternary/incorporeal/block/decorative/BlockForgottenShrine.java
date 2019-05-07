package quaternary.incorporeal.block.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import quaternary.incorporeal.tile.decorative.TileSpiritShrineExt;
import vazkii.botania.common.block.tile.TileSpiritShrine;

import javax.annotation.Nullable;

public class BlockForgottenShrine extends Block {
	public BlockForgottenShrine() {
		super(Material.ROCK);
		setHardness(1f);
		setSoundType(SoundType.STONE);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileSpiritShrineExt();
	}
}
