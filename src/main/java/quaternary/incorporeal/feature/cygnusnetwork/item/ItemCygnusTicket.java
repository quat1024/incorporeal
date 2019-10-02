package quaternary.incorporeal.feature.cygnusnetwork.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusDatatypeHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.lexicon.CygnusNetworkLexicon;
import quaternary.incorporeal.feature.cygnusnetwork.tile.TileCygnusRetainer;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCygnusTicket extends Item implements ILexiconable {
	public ItemCygnusTicket() {
		addPropertyOverride(new ResourceLocation(Incorporeal.MODID, "written_ticket"), (stack, worldIn, entityIn) -> {
			return hasCygnusItem(stack) ? 1 : 0;
		});
	}
	
	private static final String TAG_CYGNUS_DATA = "CygnusItem";
	
	public static void setCygnusItem(ItemStack stack, Object o) {
		if(o != null) {
			NBTTagCompound cygnusData = new NBTTagCompound();
			CygnusDatatypeHelpers.writeToNBT(cygnusData, o);
			ItemNBTHelper.getNBT(stack).setTag(TAG_CYGNUS_DATA, cygnusData);
		}
	}
	
	public static boolean hasCygnusItem(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_CYGNUS_DATA);
	}
	
	public static Object getCygnusItem(ItemStack stack) {
		return CygnusDatatypeHelpers.readFromNBT(stack.getTagCompound().getCompoundTag(TAG_CYGNUS_DATA));
	}
	
	public static void clearCygnusItem(ItemStack stack) {
		if(hasCygnusItem(stack)) {
			stack.getTagCompound().removeTag(TAG_CYGNUS_DATA);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldStack = player.getHeldItem(hand);
		
		TileEntity hitTile = world.getTileEntity(pos);
		if(hitTile instanceof TileCygnusRetainer) {
			TileCygnusRetainer retainer = (TileCygnusRetainer) hitTile;
			
			if(hasCygnusItem(heldStack)) {
				//Try setting the retainer to the contents of the ticket
				retainer.acceptItemFromCygnus(getCygnusItem(heldStack));
				return EnumActionResult.SUCCESS;
			} else if(retainer.hasRetainedObject()) {
				//Try setting the ticket to the contents of the retainer
				setCygnusItem(heldStack, retainer.getRetainedObject());
				player.setHeldItem(hand, heldStack); //force an update
				return EnumActionResult.SUCCESS;
			}
		}
		
		if(player.isSneaking()) {
			clearCygnusItem(heldStack);
			player.setHeldItem(hand, heldStack); //force an update
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag mistake) {
		if(world != null) {
			if(hasCygnusItem(stack)) {
				Object o = getCygnusItem(stack);
				ICygnusDatatype<?> type = CygnusDatatypeHelpers.forClass(o.getClass());
				String typeName = I18n.translateToLocal(type.getTranslationKey());
				
				tooltip.add(TextFormatting.GREEN + I18n.translateToLocalFormatted(
					EtcHelpers.vowelizeTranslationKey("incorporeal.cygnus.retainer.some", typeName),
					typeName
				));
				
				tooltip.addAll(type.describeUnchecked(o));
			} else {
				tooltip.add(TextFormatting.RED + I18n.translateToLocal("incorporeal.cygnus.retainer.none"));
			}
		}
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return CygnusNetworkLexicon.CYGNUS_TICKET;
	}
}
