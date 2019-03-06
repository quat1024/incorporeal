package quaternary.incorporeal.item.cygnus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.IScrollableItem;
import quaternary.incorporeal.api.ISimpleRegistry;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public abstract class ItemCygnusCard<T> extends Item implements IScrollableItem {
	public ItemCygnusCard(ISimpleRegistry<T> registry, String tagName, T defaultValue) {
		this.registry = registry;
		this.tagName = tagName;
		this.tagNameLower = tagName.toLowerCase(Locale.ROOT);
		this.defaultValue = defaultValue;
	}
	
	public final ISimpleRegistry<T> registry;
	public final String tagName;
	public final String tagNameLower; //Used for the lang key
	public final T defaultValue;
	
	public T readValue(ItemStack stack) {
		T thing = registry.get(readValueName(stack));
		if(thing == null) return defaultValue;
		else return thing;
	}
	
	public ResourceLocation readValueName(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null) return registry.nameOf(defaultValue);
		
		String s = nbt.getString(tagName);
		if(s.isEmpty()) return registry.nameOf(defaultValue);
		
		return new ResourceLocation(s);
	}
	
	public String langKeyForValue(ItemStack stack) {
		ResourceLocation thingName = readValueName(stack);
		
		return thingName.getNamespace() + ".cygnus." + tagNameLower + "." + thingName.getPath();
	}
	
	public ItemStack set(ItemStack stack, T thing) {
		ItemNBTHelper.setString(stack, tagName, registry.nameOf(thing).toString());
		return stack;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == Incorporeal.TAB) {
			for(T thing : registry.allValues()) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString(tagName, registry.nameOf(thing).toString());
				
				ItemStack stack = new ItemStack(this);
				stack.setTagCompound(nbt);
				
				items.add(stack);
			}
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String mainName = stack.getItem().getTranslationKey(stack) + ".name";
		String valueName = I18n.translateToLocal(langKeyForValue(stack));
		
		return I18n.translateToLocalFormatted(mainName, valueName);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag mistake) {
		tooltip.add(TextFormatting.LIGHT_PURPLE + I18n.translateToLocalFormatted("incorporeal.etc.sneakscroll", Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName()));
		
		if(mistake.isAdvanced()) {
			tooltip.add(readValueName(stack).toString());
		}
	}
	
	@Override
	public ItemStack scrollChange(ItemStack current, int dwheel) {
		T value = readValue(current);
		T valueNext;
		if(dwheel < 0) valueNext = registry.next(value);
		else valueNext = registry.prev(value);
		
		return set(current.copy(), valueNext);
	}
}
