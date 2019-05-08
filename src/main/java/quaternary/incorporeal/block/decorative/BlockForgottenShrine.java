package quaternary.incorporeal.block.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.tile.decorative.TileSpiritShrineExt;

import javax.annotation.Nullable;

public class BlockForgottenShrine extends Block {
	public BlockForgottenShrine() {
		super(Material.ROCK);
		setHardness(1f);
		setSoundType(SoundType.STONE);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		updatePoweredStatus(world, pos);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		updatePoweredStatus(world, pos);
	}
	
	private void updatePoweredStatus(World world, BlockPos pos) {
		if(world.isRemote) return;
		
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileSpiritShrineExt) {
			((TileSpiritShrineExt) tile).setPowered(world.isBlockPowered(pos));
		}
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
