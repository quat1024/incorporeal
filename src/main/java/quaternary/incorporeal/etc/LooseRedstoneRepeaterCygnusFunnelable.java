package quaternary.incorporeal.etc;

import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.api.cygnus.ILooseCygnusFunnelable;

import javax.annotation.Nullable;
import java.math.BigInteger;

public class LooseRedstoneRepeaterCygnusFunnelable implements ILooseCygnusFunnelable {
	@Nullable
	@Override
	public ICygnusFunnelable getFor(World world, BlockPos pos, IBlockState state, EnumFacing face) {
		if(state.getBlock() instanceof BlockRedstoneRepeater) {
			return new ICygnusFunnelable() {
				@Override
				public boolean canGiveCygnusItem() {
					return true;
				}
				
				@Override
				public boolean canAcceptCygnusItem() {
					return true;
				}
				
				@Nullable
				@Override
				public Object giveItemToCygnus() {
					return BigInteger.valueOf(state.getValue(BlockRedstoneRepeater.DELAY));
				}
				
				@Override
				public void acceptItemFromCygnus(Object item) {
					if(item instanceof BigInteger) {
						int value = ((BigInteger)item).intValue();
						if(value >= 1 && value <= 4) {
							world.setBlockState(pos, state.withProperty(BlockRedstoneRepeater.DELAY, value));
						}
					}
				}
			};
		} else return null;
	}
}
