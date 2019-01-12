package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.tile.cygnus.TileCygnusRetainer;

import javax.annotation.Nullable;

public class BlockCygnusRetainer extends BlockCygnusBase {
	public BlockCygnusRetainer() {
		setDefaultState(getDefaultState().withProperty(LIT, false));
	}
	
	public static final PropertyBool LIT = PropertyBool.create("lit");
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCygnusRetainer();
	}
	
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
