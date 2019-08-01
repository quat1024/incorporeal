package quaternary.incorporeal.core.etc;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class LyingItemHandler implements IItemHandler {
	public LyingItemHandler(IItemHandler other, List<ItemStack> fakeStacks) {
		this.other = other;
		this.fakeStacks = fakeStacks;
	}
	
	public LyingItemHandler(IItemHandler other, ItemStack... otherStacks) {
		this(other, ImmutableList.copyOf(otherStacks));
	}
	
	IItemHandler other;
	List<ItemStack> fakeStacks;
	
	@Override
	public int getSlots() {
		return other.getSlots();
	}
	
	@Nonnull
	@Override
	public ItemStack getStackInSlot(int slot) {
		return null;
	}
	
	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		return null;
	}
	
	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return null;
	}
	
	@Override
	public int getSlotLimit(int slot) {
		return 0;
	}
}
