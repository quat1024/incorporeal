package quaternary.incorporeal.api;

import net.minecraft.item.ItemStack;

public interface IScrollableItem {
	ItemStack scrollChange(ItemStack current, int dwheel);
}
