package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.lexicon.IncorporealLexiData;
import quaternary.incorporeal.tile.TileCorporeaLiar;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

import javax.annotation.Nullable;

public class BlockCorporeaLiar extends Block implements ILexiconable {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public BlockCorporeaLiar() {
		super(Material.IRON, MapColor.PURPLE);
		
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.UP));
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		((TileCorporeaLiar)world.getTileEntity(pos)).setDirection(state.getValue(FACING));
	}
	
	//Boilerplate!
	//Metadata/blockstate stuff
	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta >= 6) meta = 0;
		return getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing whichWay = state.getValue(FACING);
		return whichWay.ordinal();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	//Facing direction stuff
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
	}
	
	//Tile entity creation stuff
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCorporeaLiar().setDirection(state.getValue(FACING));
	}
	
	//Lexicon stuff
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporealLexiData.corporeaLiarEntry;
	}
}
