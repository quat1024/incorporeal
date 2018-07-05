package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import quaternary.incorporeal.lexicon.IncorporeticLexiData;
import quaternary.incorporeal.tile.TileFrameTinkerer;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.state.BotaniaStateProps;

import javax.annotation.Nullable;

public class BlockFrameTinkerer extends Block implements ILexiconable {
	public BlockFrameTinkerer() {
		super(Material.ROCK);
		
		setDefaultState(getDefaultState().withProperty(BotaniaStateProps.POWERED, false));
	}
	
	//Inserting item
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(hand != EnumHand.MAIN_HAND) return false;
		
		ItemStack heldStack = player.getHeldItem(hand);
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileFrameTinkerer) {
			TileFrameTinkerer tinkerer = (TileFrameTinkerer) tile;
			
			ItemStack tinkererStack = tinkerer.getItemCopy();
			tinkerer.copyAndSetItem(heldStack);
			player.setHeldItem(hand, tinkererStack);
			
			return true;
		}
		return false;
	}
	
	//Powering
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		boolean worldPower = world.isBlockIndirectlyGettingPowered(pos) > 0;
		boolean myPower = state.getValue(BotaniaStateProps.POWERED);
		if(myPower != worldPower) {
			world.setBlockState(pos, state.withProperty(BotaniaStateProps.POWERED, worldPower));
			
			if(worldPower) {
				TileEntity tile = world.getTileEntity(pos);
				if(tile instanceof TileFrameTinkerer) {
					((TileFrameTinkerer)tile).doSwap();
				}
			}
		}
	}
	
	//State boilerplate
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BotaniaStateProps.POWERED);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BotaniaStateProps.POWERED, meta == 1);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BotaniaStateProps.POWERED) ? 1 : 0;
	}
	
	AxisAlignedBB aabb = new AxisAlignedBB(0d, 0d, 0d, 1d, 0.1875d, 1d);
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return aabb;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	//Tile
	@Override
	public boolean hasTileEntity(IBlockState blah) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileFrameTinkerer();
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexiData.frameTinkererEntry;
	}
}
