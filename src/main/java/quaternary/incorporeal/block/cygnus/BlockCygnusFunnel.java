package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.cygnus.cap.IncorporeticCygnusCapabilities;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.etc.RedstoneDustCygnusFunnelable;
import quaternary.incorporeal.etc.helper.CygnusHelpers;
import quaternary.incorporeal.tile.cygnus.TileCygnusFunnel;
import vazkii.botania.api.state.BotaniaStateProps;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockCygnusFunnel extends BlockCygnusBase implements ICygnusSparkable {
	public static final PropertyEnum<EnumFacing> FACING = BotaniaStateProps.FACING;
	public static final PropertyBool POWERED = BotaniaStateProps.POWERED;
	
	public BlockCygnusFunnel() {
		setDefaultState(
			getDefaultState()
				.withProperty(FACING, EnumFacing.UP)
				.withProperty(POWERED, false)
		);
		
		setTickRandomly(true);
	}
	
	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusFunnel) {
			//basically since entities can provide cygnus funnelables
			//and they can move away of their own volition, without causing a block update to this
			//randomly rechecking is a good balance between "keeping old data until a block update"
			//and "making the tile tickable"
			((TileCygnusFunnel)tile).updateArrowStatus(pos, state.getValue(FACING));
		}
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWERED);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex() + (state.getValue(POWERED) ? 6 : 0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta % 6)).withProperty(POWERED, meta >= 6);
	}
}
