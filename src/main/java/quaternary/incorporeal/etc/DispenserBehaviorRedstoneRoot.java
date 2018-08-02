package quaternary.incorporeal.etc;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.block.IncorporeticBlocks;
import vazkii.botania.common.block.dispenser.BehaviourSeeds;

import javax.annotation.Nonnull;

public class DispenserBehaviorRedstoneRoot extends BehaviourSeeds {
	public DispenserBehaviorRedstoneRoot() {
		super(IncorporeticBlocks.REDSTONE_ROOT_CROP);
	}
	
	@Nonnull
	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		//Check that it's actually a redstone root
		if(stack.getMetadata() == 6) return super.dispenseStack(source, stack);
		else return stack;
	}
}
