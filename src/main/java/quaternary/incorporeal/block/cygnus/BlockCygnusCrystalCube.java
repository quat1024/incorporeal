package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.item.cygnus.ItemCygnusCrystalCubeCard;
import quaternary.incorporeal.item.cygnus.ItemCygnusWordCard;
import quaternary.incorporeal.tile.cygnus.TileCygnusCrystalCube;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Predicate;

public class BlockCygnusCrystalCube extends BlockCygnusBase implements ICygnusSparkable {
	public BlockCygnusCrystalCube(String conditionName, Predicate<CygnusStack> condition) {
		this.conditionName = conditionName;
		this.condition = condition;
	}
	
	public final String conditionName;
	public final Predicate<CygnusStack> condition;
	
	private ItemCygnusCrystalCubeCard associatedCard;
	
	public void setAssociatedCard(ItemCygnusCrystalCubeCard associatedCard) {
		this.associatedCard = associatedCard;
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
		
		if(held.getItem() instanceof ItemCygnusCrystalCubeCard) {
			ItemCygnusCrystalCubeCard card = (ItemCygnusCrystalCubeCard) held.getItem();
			world.setBlockState(pos, card.cygnusCube.getDefaultState());
			return true;
		}
		
		return false;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		//TODO
		return null;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		if(associatedCard == null || (conditionName.equals("blank") && player != null && !player.isSneaking())) {
			return super.getPickBlock(state, target, world, pos, player);
		} else return new ItemStack(associatedCard);
	}
}
