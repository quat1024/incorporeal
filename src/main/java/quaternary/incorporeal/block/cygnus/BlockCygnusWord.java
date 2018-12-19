package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
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
import net.minecraftforge.common.property.IExtendedBlockState;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.block.IncorporeticStateProps;
import quaternary.incorporeal.block.properties.UnlistedSimpleRegistryProperty;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.etc.helper.CygnusHelpers;
import quaternary.incorporeal.item.cygnus.ItemCygnusWordCard;
import quaternary.incorporeal.tile.cygnus.TileCygnusWord;
import vazkii.botania.api.state.BotaniaStateProps;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class BlockCygnusWord extends BlockCygnusBase implements ICygnusSparkable {
	public BlockCygnusWord() {
		setDefaultState(getDefaultState().withProperty(POWERED, false));
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public static final PropertyBool POWERED = BotaniaStateProps.POWERED;
	public static final UnlistedSimpleRegistryProperty<Consumer<ICygnusStack>> UNLISTED_ACTION = IncorporeticStateProps.UNLISTED_ACTION;
	
	@Override
	public boolean acceptsCygnusSpark(World world, IBlockState state, BlockPos pos) {
		return true;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		boolean isPowered = state.getValue(POWERED);
		boolean shouldPower = world.isBlockPowered(pos);
		if(isPowered != shouldPower) {
			world.setBlockState(pos, state.withProperty(POWERED, shouldPower));
		}
		
		if(!isPowered && shouldPower) {
			EntityCygnusMasterSpark master = CygnusHelpers.getMasterSparkForSparkAt(world, pos);
			TileEntity tile = world.getTileEntity(pos);
			if(master != null && tile instanceof TileCygnusWord) {
				((TileCygnusWord)tile).accept(master.getCygnusStack());
			}
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCygnusWord();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = player.getHeldItem(hand);
		TileEntity tile = world.getTileEntity(pos);
		
		if(held.getItem() instanceof ItemCygnusWordCard && tile instanceof TileCygnusWord) {
			ItemCygnusWordCard card = (ItemCygnusWordCard) held.getItem();
			TileCygnusWord word = (TileCygnusWord) tile;
			
			word.setAction(card.readValue(held));
			
			return true;
		}
		
		return false;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(POWERED).add(UNLISTED_ACTION).build();
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
		IExtendedBlockState stateExt = (IExtendedBlockState) state;
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusWord) {
			stateExt = stateExt.withProperty(UNLISTED_ACTION, ((TileCygnusWord)tile).getAction());
		}
		return stateExt;
	}
}
