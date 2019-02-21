package quaternary.incorporeal.compat.infraredstone;

import com.elytradev.infraredstone.block.CableInRedScaffold;
import com.elytradev.infraredstone.block.CableInfraRedstone;
import com.elytradev.infraredstone.block.ModBlocks;
import com.elytradev.infraredstone.logic.InRedLogic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.api.cygnus.ILooseCygnusFunnelable;

import javax.annotation.Nullable;
import java.math.BigInteger;

public class LooseInRedWireFunnelable implements ILooseCygnusFunnelable {
	@Override
	@Nullable
	public ICygnusFunnelable getFor(World world, BlockPos pos, IBlockState state, EnumFacing face) {
		if(state.getBlock() instanceof CableInfraRedstone || state.getBlock() instanceof CableInRedScaffold) {
			BlockPos funnelPos = pos.offset(face.getOpposite());
			
			return new ICygnusFunnelable() {
				@Override
				public boolean canGiveCygnusItem() {
					return true;
				}
				
				@Nullable
				@Override
				public Object giveItemToCygnus() {
					//TODO This doesn't actually work
					Incorporeal.LOGGER.info(pos);
					Incorporeal.LOGGER.info(face);
					Incorporeal.LOGGER.info(world.getBlockState(pos));
					return BigInteger.valueOf(InRedLogic.findIRValue(world, funnelPos, face));
				}
			};
		} else return null;
	}
}
