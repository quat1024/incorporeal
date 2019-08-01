package quaternary.incorporeal.core;

import com.google.common.base.Preconditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;

public class ItemsModule extends RegistryModule {
	public static <T extends Item> T name(T item, String name) {
		item.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		item.setTranslationKey(Incorporeal.MODID + "." + name);
		item.setCreativeTab(Incorporeal.TAB);
		return item;
	}
	
	public static <T extends ItemBlock> T itemBlock(T itemBlock) {
		itemBlock.setRegistryName(Preconditions.checkNotNull(itemBlock.getBlock().getRegistryName()));
		return itemBlock;
	}
}
