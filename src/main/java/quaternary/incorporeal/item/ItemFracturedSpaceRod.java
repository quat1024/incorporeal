package quaternary.incorporeal.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import quaternary.incorporeal.entity.EntityFracturedSpaceCollector;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.CrateVariant;
import vazkii.botania.common.block.BlockOpenCrate;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemFracturedSpaceRod extends Item implements IManaUsingItem, ILexiconable {
	public ItemFracturedSpaceRod() {
		setMaxStackSize(1);
	}
	
	public static final String CRATE_POS_KEY = "CratePos";
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldStack = player.getHeldItem(hand);
		IBlockState hitState = world.getBlockState(pos);
		Block hitBlock = hitState.getBlock();
		
		if(hitBlock instanceof BlockOpenCrate && hitState.getValue(BotaniaStateProps.CRATE_VARIANT) == CrateVariant.OPEN) {
			//Clicked a crate.
			//Remember this position.
			ItemNBTHelper.setCompound(heldStack, CRATE_POS_KEY, NBTUtil.createPosTag(pos));
			
			TextComponentTranslation txt = new TextComponentTranslation("incorporeal.etc.savedPos");
			txt.getStyle().setColor(TextFormatting.DARK_PURPLE);
			player.sendStatusMessage(txt, true);
			
			return EnumActionResult.SUCCESS;
		} else {
			//Didn't click a crate.
			if(facing != EnumFacing.UP) return EnumActionResult.PASS;
			//Spawn the entity that does the item collection.
			NBTTagCompound cratePosCmp = ItemNBTHelper.getCompound(heldStack, CRATE_POS_KEY, true);
			if(cratePosCmp == null) {
				TextComponentTranslation txt = new TextComponentTranslation("incorporeal.etc.noPos");
				txt.getStyle().setColor(TextFormatting.RED);
				player.sendStatusMessage(txt, true);
				return EnumActionResult.FAIL;
			}
			
			if(!world.isRemote) {
				BlockPos cratePos = NBTUtil.getPosFromTag(cratePosCmp);
				EntityFracturedSpaceCollector fsc = new EntityFracturedSpaceCollector(world, cratePos, player);
				fsc.setPosition(pos.getX() + hitX, pos.getY() + 1, pos.getZ() + hitZ);
				world.spawnEntity(fsc);
			}
			return EnumActionResult.SUCCESS;
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
