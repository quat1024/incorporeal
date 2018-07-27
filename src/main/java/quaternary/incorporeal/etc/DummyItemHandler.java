package quaternary.incorporeal.etc;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

/**
 * Fake item handler used for when players are not currently in this dimension
 * Better than just not providing a handler at all, so things like corporea sparks don't fall off.
 * */
public class DummyItemHandler implements IItemHandler {
	@Override
	public int getSlots() {
		return 0;
	}
	
	@Nonnull
	@Override
	public ItemStack getStackInSlot(int slot) {
		return ItemStack.EMPTY;
	}
	
	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		return stack;
	}
	
	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public int getSlotLimit(int slot) {
		return 0;
	}
}
