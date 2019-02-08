package quaternary.incorporeal.etc;

import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;

import java.lang.ref.WeakReference;
import java.math.BigInteger;

public class RedstoneRepeaterCygnusFunnelable implements ICygnusFunnelable {
	public RedstoneRepeaterCygnusFunnelable(World world, BlockPos pos) {
		this.worldRef = new WeakReference<>(world);
		this.pos = pos;
	}
	
	//TODO is WeakReference needed here? I generally use it for world references to be safe but hmmMMMM
	private WeakReference<World> worldRef;
	private BlockPos pos;
	
	@Override
	public boolean canAcceptCygnusItem() {
		return true;
	}
	
	@Override
	public void acceptItemFromCygnus(Object item) {
		World world = worldRef.get();
		if(world == null) return;
		
		if(item instanceof BigInteger) {
			int value = ((BigInteger)item).intValue();
			if(value >= 1 && value <= 4) {
				IBlockState state = world.getBlockState(pos);
				if(state.getBlock() == Blocks.POWERED_REPEATER || state.getBlock() == Blocks.UNPOWERED_REPEATER) {
					world.setBlockState(pos, state.withProperty(BlockRedstoneRepeater.DELAY, value));
				}
			}
		}
	}
}
