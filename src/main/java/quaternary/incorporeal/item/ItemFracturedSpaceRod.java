package quaternary.incorporeal.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.entity.EntityFracturedSpaceCollector;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.CrateVariant;
import vazkii.botania.api.wand.ICoordBoundItem;
import vazkii.botania.common.block.BlockOpenCrate;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFracturedSpaceRod extends Item implements IManaUsingItem, ILexiconable, ICoordBoundItem {
	public ItemFracturedSpaceRod() {
		setMaxStackSize(1);
	}
	
	public static final String CRATE_POS_KEY = "CratePos";
	public static final String CRATE_DIMENSION_KEY = "CrateDimension";
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldStack = player.getHeldItem(hand);
		IBlockState hitState = world.getBlockState(pos);
		Block hitBlock = hitState.getBlock();
		
		if(hitBlock instanceof BlockOpenCrate && hitState.getValue(BotaniaStateProps.CRATE_VARIANT) == CrateVariant.OPEN) {
			//Clicked a crate.
			//Remember this position.
			ItemNBTHelper.setCompound(heldStack, CRATE_POS_KEY, NBTUtil.createPosTag(pos));
			ItemNBTHelper.setInt(heldStack, CRATE_DIMENSION_KEY, world.provider.getDimension());
			
			EtcHelpers.sendMessage(player, "incorporeal.fracturedSpace.savedPos", TextFormatting.DARK_PURPLE);
			return EnumActionResult.SUCCESS;
		} else {
			//Didn't click a crate.
			if(facing != EnumFacing.UP) return EnumActionResult.PASS;
			//Sanity check that the crate position is still ok.
			NBTTagCompound cratePosCmp = ItemNBTHelper.getCompound(heldStack, CRATE_POS_KEY, true);
			int crateDimension = ItemNBTHelper.getInt(heldStack, CRATE_DIMENSION_KEY, Integer.MAX_VALUE);
			if(cratePosCmp == null || crateDimension == Integer.MAX_VALUE) {
				EtcHelpers.sendMessage(player, "incorporeal.fracturedSpace.noPos", TextFormatting.RED);
				return EnumActionResult.FAIL;
			}
			
			if(crateDimension != world.provider.getDimension()) {
				EtcHelpers.sendMessage(player, "incorporeal.fracturedSpace.wrongDimension", TextFormatting.RED);
				return EnumActionResult.FAIL;
			}
			
			if(!world.isRemote) {
				//One final server-only sanity check, since this loads the chunk
				BlockPos cratePos = NBTUtil.getPosFromTag(cratePosCmp);
				IBlockState rememberedState = world.getBlockState(cratePos);
				TileEntity rememberedTile = world.getTileEntity(cratePos);
				if(!EtcHelpers.isOpenCrate(rememberedState, rememberedTile)) {
					EtcHelpers.sendMessage(player, "incorporeal.fracturedSpace.noCrateThere", TextFormatting.RED);
					return EnumActionResult.FAIL;
				}
				
				//Spawn the entity.
				EntityFracturedSpaceCollector fsc = new EntityFracturedSpaceCollector(world, cratePos, player);
				fsc.setPosition(pos.getX() + hitX, pos.getY() + 1, pos.getZ() + hitZ);
				world.spawnEntity(fsc);
			}
			
			return EnumActionResult.SUCCESS;
		}
	}
	
	@Override
	public BlockPos getBinding(ItemStack stack) {
		NBTTagCompound cratePosCmp = ItemNBTHelper.getCompound(stack, CRATE_POS_KEY, true);
		if(cratePosCmp == null) return null;
		int dimension = ItemNBTHelper.getInt(stack, CRATE_DIMENSION_KEY, Integer.MAX_VALUE);
		if(dimension == Integer.MAX_VALUE || Incorporeal.PROXY.getClientDimension() != dimension) return null;
		else return NBTUtil.getPosFromTag(cratePosCmp);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag mistake) {
		NBTTagCompound cratePosCmp = ItemNBTHelper.getCompound(stack, CRATE_POS_KEY, true);
		if(cratePosCmp == null) {
			tooltip.add(TextFormatting.RED + I18n.translateToLocal("incorporeal.fracturedSpace.tooltipNotBound"));
		} else {
			tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("incorporeal.fracturedSpace.tooltipBound"));
			int crateDimension = ItemNBTHelper.getInt(stack, CRATE_DIMENSION_KEY, Integer.MAX_VALUE);
			
			if(world != null) { //Grrr
				if(crateDimension == Integer.MAX_VALUE || world.provider.getDimension() != crateDimension) {
					tooltip.add(TextFormatting.RED + I18n.translateToLocal("incorporeal.fracturedSpace.tooltipWrongDimension"));
				}
			}
			
			if(mistake.isAdvanced()) {
				BlockPos pos = NBTUtil.getPosFromTag(cratePosCmp);
				tooltip.add(TextFormatting.DARK_GRAY + I18n.translateToLocalFormatted("incorporeal.fracturedSpace.debug.tooltipPos", pos.getX(), pos.getY(), pos.getZ()));
				tooltip.add(TextFormatting.DARK_GRAY + I18n.translateToLocalFormatted("incorporeal.fracturedSpace.debug.tooltipDim", crateDimension));
			}
		}
	}
	
	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.fracturedSpace;
	}
}
