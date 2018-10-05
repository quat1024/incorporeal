package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.etc.helper.CygnusHelpers;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;
import quaternary.incorporeal.item.cygnus.ItemCygnusWordCard;
import vazkii.botania.api.state.BotaniaStateProps;

import java.util.Random;
import java.util.function.Consumer;

public class BlockCygnusWord extends BlockCygnusBase implements ICygnusSparkable {
	public BlockCygnusWord(String actionName, Consumer<CygnusStack> action) {
		this.actionName = actionName;
		this.action = action;
		
		setDefaultState(getDefaultState().withProperty(POWERED, false));
	}
	
	public final String actionName;
	public final Consumer<CygnusStack> action;
	
	public static final PropertyBool POWERED = BotaniaStateProps.POWERED;
	
	private ItemCygnusWordCard associatedCard = null;
	
	public void setAssociatedCard(ItemCygnusWordCard associatedCard) {
		this.associatedCard = associatedCard;
	}
	
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
			if(master != null) {
				action.accept(master.cygnusStack);
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = player.getHeldItem(hand);
		
		if(held.getItem() instanceof ItemCygnusWordCard) {
			ItemCygnusWordCard card = (ItemCygnusWordCard) held.getItem();
			world.setBlockState(pos, card.cygnusWord.getDefaultState());
			return true;
		}
		
		return false;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return IncorporeticCygnusItems.WORD_BLANK;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		if(associatedCard == null || (actionName.equals("blank") && player != null && !player.isSneaking())) {
			//This lets you pickblock placeable blank words, but also the blank card, if you want to (by sneaking)
			//Hardcoded to only work on blank words since only those have an ItemBlock associated.
			//Yes this is a kinda lousy hack. What are you gonna do.
			return super.getPickBlock(state, target, world, pos, player);
		} else return new ItemStack(associatedCard);
	}
	
	//Hey look blockstate cruft
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, POWERED);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(POWERED, meta == 1);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}
}
