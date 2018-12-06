package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import quaternary.incorporeal.tile.TileCorporeaSparkTinkerer;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.common.item.ModItems;

import javax.annotation.Nullable;

public class BlockCorporeaSparkTinkerer extends Block implements ILexiconable {	
	public BlockCorporeaSparkTinkerer() {
		super(Material.ROCK, MapColor.PURPLE);
		this.setHardness(3F);
		this.setSoundType(SoundType.METAL);
		
		setDefaultState(getDefaultState().withProperty(BotaniaStateProps.POWERED, true));
	}
	
	//Messages to tile
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldStack = player.getHeldItem(hand);
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileCorporeaSparkTinkerer && heldStack.getItem() == ModItems.dye) { //floral powder
			TileCorporeaSparkTinkerer tinkerer = (TileCorporeaSparkTinkerer) te;
			EnumDyeColor heldColor = EnumDyeColor.byMetadata(heldStack.getMetadata());
			EnumDyeColor networkColor = tinkerer.getNetwork();
			if(networkColor != heldColor) {
				tinkerer.setNetwork(heldColor);
				if(!player.capabilities.isCreativeMode) heldStack.shrink(1);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		boolean worldPower = world.getRedstonePowerFromNeighbors(pos) > 0;
		boolean myPower = state.getValue(BotaniaStateProps.POWERED);
		
		if(worldPower != myPower) {
			world.setBlockState(pos, state.withProperty(BotaniaStateProps.POWERED, worldPower), 2);
			
			if(worldPower) {
				TileEntity tile = world.getTileEntity(pos);
				if(tile instanceof TileCorporeaSparkTinkerer) {
					((TileCorporeaSparkTinkerer) tile).doSwap();
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
	
	//Aabbabababb
	private static final AxisAlignedBB aabb = new AxisAlignedBB(0d, 0d, 0d, 1d, 0.1875d, 1d);
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
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
	
	//Tile
	@Override
	public boolean hasTileEntity(IBlockState blah) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCorporeaSparkTinkerer();
	}
	
	//Entry
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.corporeaTinkerer;
	}
}
