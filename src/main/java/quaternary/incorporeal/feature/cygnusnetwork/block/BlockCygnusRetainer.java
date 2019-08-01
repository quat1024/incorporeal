package quaternary.incorporeal.feature.cygnusnetwork.block;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.tile.TileCygnusRetainer;
import vazkii.botania.api.wand.IWandable;

import javax.annotation.Nullable;

public class BlockCygnusRetainer extends BlockCygnusBase implements IWandable {
	public BlockCygnusRetainer() {
		setDefaultState(getDefaultState().withProperty(LIT, false));
	}
	
	public static final PropertyBool LIT = PropertyBool.create("lit");
	
	//iwandable
	@Override
	public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing facing) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusRetainer) {
			((TileCygnusRetainer)tile).wand();
			return true;
		}
		
		return false;
	}
	
	//comparator
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusRetainer) {
			return ((TileCygnusRetainer)tile).getComparator();
		} else return 0;
	}
	
	//TE
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCygnusRetainer();
	}
	
	//blockstate junk
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LIT);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = EtcHelpers.getTileEntityThreadsafe(world, pos);
		if(tile instanceof TileCygnusRetainer) {
			return state.withProperty(LIT, ((TileCygnusRetainer)tile).hasRetainedObject());
		} else return state;
	}
}
