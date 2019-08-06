package quaternary.incorporeal.feature.cygnusnetwork.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusDatatypeHelpers;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCygnusTicket extends Item {
	public ItemCygnusTicket() {
		addPropertyOverride(new ResourceLocation(Incorporeal.MODID, "written_ticket"), (stack, worldIn, entityIn) -> {
			return hasCygnusItem(stack) ? 1 : 0;
		});
	}
	
	private static final String TAG_CYGNUS_DATA = "CygnusItem";
	
	public void setCygnusItem(ItemStack stack, Object o) {
		clearCygnusItem(stack);
		if(o != null) {
			NBTTagCompound cygnusData = new NBTTagCompound();
			CygnusDatatypeHelpers.writeToNBT(cygnusData, o);
			ItemNBTHelper.getNBT(stack).setTag(TAG_CYGNUS_DATA, cygnusData);
		}
	}
	
	public boolean hasCygnusItem(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_CYGNUS_DATA);
	}
	
	public Object getCygnusItem(ItemStack stack) {
		return CygnusDatatypeHelpers.readFromNBT(stack.getTagCompound().getCompoundTag(TAG_CYGNUS_DATA));
	}
	
	public void clearCygnusItem(ItemStack stack) {
		if(hasCygnusItem(stack)) {
			stack.getTagCompound().removeTag(TAG_CYGNUS_DATA);
		}
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
}
