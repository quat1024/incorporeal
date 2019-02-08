package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.tile.cygnus.TileCygnusFunnel;
import vazkii.botania.api.state.BotaniaStateProps;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Random;

public class BlockCygnusFunnel extends BlockCygnusBase implements ICygnusSparkable {
	public static final PropertyEnum<EnumFacing> FACING = BotaniaStateProps.FACING;
	public static final PropertyEnum<ArrowLight> ARROW_LIGHT = PropertyEnum.create("arrow_light", ArrowLight.class);
	
	public BlockCygnusFunnel() {
		setDefaultState(
			getDefaultState()
				.withProperty(FACING, EnumFacing.UP)
				.withProperty(ARROW_LIGHT, ArrowLight.OFF)
		);
	}
	
	@Override
	public boolean acceptsCygnusSpark(World world, IBlockState state, BlockPos pos) {
		return true;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos updaterPos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusFunnel) {
			TileCygnusFunnel funnel = (TileCygnusFunnel) tile;
			funnel.onNeighborChange();
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer).getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusFunnel) {
			((TileCygnusFunnel)tile).updateArrowStatus(pos, state.getValue(FACING));
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCygnusFunnel();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, ARROW_LIGHT);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta % 6));
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = EtcHelpers.getTileEntityThreadsafe(world, pos);
		if(tile instanceof TileCygnusFunnel) {
			TileCygnusFunnel funnel = (TileCygnusFunnel) tile; 
			return state.withProperty(
				ARROW_LIGHT,
				ArrowLight.OFF.wrap(funnel.isBackLit(), funnel.isFrontLit())
			);
		} else return state;
	}
	
	//basically wrapper for 2 bools to make writing the blockstate a bit easier.
	public enum ArrowLight implements IStringSerializable {
		OFF,
		FRONT,
		BACK,
		BOTH;
		
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
		
		public ArrowLight wrap(boolean back, boolean front) {
			if(back) return front ? BOTH : BACK;
			else return front ? FRONT : OFF;
		}
	}
}
