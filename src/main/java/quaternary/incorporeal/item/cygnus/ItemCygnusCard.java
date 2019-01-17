package quaternary.incorporeal.item.cygnus;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.ISimpleRegistry;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemCygnusCard<T> extends Item {
	public ItemCygnusCard(ISimpleRegistry<T> registry, String tagName, T defaultValue) {
		this.registry = registry;
		this.tagName = tagName;
		this.defaultValue = defaultValue;
	}
	
	private final ISimpleRegistry<T> registry;
	private final String tagName;
	private final T defaultValue;
	
	public T readValue(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null) return defaultValue;
		
		String s = nbt.getString(tagName);
		if(s.isEmpty()) return defaultValue;
		
		T thing = registry.get(new ResourceLocation(s));
		if(thing == null) return defaultValue;
		else return thing;
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		//TODO remove this is debuging stuff.
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			tooltip.add(nbt.getString(tagName));
		}
	}
}
