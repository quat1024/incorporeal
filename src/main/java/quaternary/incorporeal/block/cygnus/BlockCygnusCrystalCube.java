package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.block.IncorporeticStateProps;
import quaternary.incorporeal.block.properties.UnlistedSimpleRegistryProperty;
import quaternary.incorporeal.item.cygnus.ItemCygnusCrystalCubeCard;
import quaternary.incorporeal.tile.cygnus.TileCygnusCrystalCube;
import vazkii.botania.api.state.BotaniaStateProps;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class BlockCygnusCrystalCube extends BlockCygnusBase implements ICygnusSparkable {
	public static final PropertyBool POWERED = BotaniaStateProps.POWERED;
	public static final UnlistedSimpleRegistryProperty<Predicate<ICygnusStack>> UNLISTED_CONDITION = IncorporeticStateProps.UNLISTED_CONDITION;
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean acceptsCygnusSpark(World world, IBlockState state, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusCrystalCube) {
			return ((TileCygnusCrystalCube) tile).isEnabled() ? 15 : 0;
		} else return 0;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCygnusCrystalCube();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = player.getHeldItem(hand);
		TileEntity tile = world.getTileEntity(pos);
		
		if(held.getItem() instanceof ItemCygnusCrystalCubeCard && tile instanceof TileCygnusCrystalCube) {
			ItemCygnusCrystalCubeCard card = (ItemCygnusCrystalCubeCard) held.getItem();
			TileCygnusCrystalCube cube = (TileCygnusCrystalCube) tile;
			
			cube.setCondition(card.readValue(held));
			
			return true;
		}
		
		return false;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(POWERED).add(UNLISTED_CONDITION).build();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(POWERED, meta == 1);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return super.getExtendedState(state, world, pos);
	}
}
