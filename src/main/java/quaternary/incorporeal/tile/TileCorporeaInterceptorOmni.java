package quaternary.incorporeal.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.BlockCorporeaInterceptorOmni;
import vazkii.botania.api.corporea.ICorporeaInterceptor;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.corporea.InvWithLocation;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;

import java.util.List;

public class TileCorporeaInterceptorOmni extends TileCorporeaBase implements ICorporeaInterceptor {
	@Override
	public void interceptRequest(Object request, int count, ICorporeaSpark spark, ICorporeaSpark source, List<ItemStack> stacks, List<InvWithLocation> inventories, boolean doit) {
		//Not sure what the difference here is, but the corporea interceptor uses the latter, not this one
	}
	
	@Override
	public void interceptRequestLast(Object request, int count, ICorporeaSpark spark, ICorporeaSpark source, List<ItemStack> stacks, List<InvWithLocation> inventories, boolean doit) {
		if(!doit || count <= 0) return; //So things like crystal cubes, "count x" requests, etc don't trigger a pulse.
		IBlockState state = world.getBlockState(pos);
		if(!state.getValue(BlockCorporeaInterceptorOmni.POWERED)) {
			//Based on copypasta from TileCorporeaInterceptor of course.
			world.setBlockState(pos, state.withProperty(BlockCorporeaInterceptorOmni.POWERED, true), 3);
			world.scheduleUpdate(pos, getBlockType(), 2);
			
			//Notify nearby corporea retainers.
			TileEntity requestor = source.getSparkInventory().world.getTileEntity(source.getSparkInventory().pos);
			for(EnumFacing dir : EnumFacing.HORIZONTALS) {
				TileEntity tile = world.getTileEntity(pos.offset(dir));
				if(tile != null && tile instanceof TileCorporeaRetainer && requestor != null) {
					((TileCorporeaRetainer) tile).setPendingRequest(requestor.getPos(), request, count);
				}
			}
		}
	}
}
