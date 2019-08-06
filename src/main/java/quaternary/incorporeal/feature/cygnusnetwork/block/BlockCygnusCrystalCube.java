package quaternary.incorporeal.feature.cygnusnetwork.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.feature.cygnusnetwork.item.ItemCygnusCrystalCubeCard;
import quaternary.incorporeal.feature.cygnusnetwork.tile.TileCygnusCrystalCube;

import javax.annotation.Nullable;

public class BlockCygnusCrystalCube extends BlockCygnusBase implements ICygnusSparkable {
	private static final AxisAlignedBB AABB = new AxisAlignedBB(3 / 16d, 0, 3 / 16d, 13 / 16d, 12 / 16d, 13 / 16d);
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
}
