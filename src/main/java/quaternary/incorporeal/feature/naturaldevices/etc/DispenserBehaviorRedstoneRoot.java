package quaternary.incorporeal.feature.naturaldevices.etc;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.feature.naturaldevices.block.NaturalDevicesBlocks;
import vazkii.botania.common.block.dispenser.BehaviourSeeds;

import javax.annotation.Nonnull;

public class DispenserBehaviorRedstoneRoot extends BehaviourSeeds {
	public DispenserBehaviorRedstoneRoot() {
		super(NaturalDevicesBlocks.REDSTONE_ROOT_CROP);
	}
	
	private final BehaviorDefaultDispenseItem defaultBehavior = new BehaviorDefaultDispenseItem();
	
	@Nonnull
	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		//Check that it's actually a redstone root
		if(stack.getMetadata() == 6) return super.dispenseStack(source, stack);
		else return defaultBehavior.dispense(source, stack);
	}
}
