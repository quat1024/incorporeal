package quaternary.incorporeal.feature.decorative.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.feature.decorative.tile.TileSpiritShrineExt;

import javax.annotation.Nullable;
import java.util.List;

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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("incorporeal.wip"));
	}
}
