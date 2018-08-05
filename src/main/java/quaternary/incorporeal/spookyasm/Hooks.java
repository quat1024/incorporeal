package quaternary.incorporeal.spookyasm;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import quaternary.incorporeal.api.ICorporeaInhibitor;
import quaternary.incorporeal.api.ICustomWrappedInventory;
import vazkii.botania.api.corporea.*;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;
import vazkii.botania.common.core.helper.MathHelper;
import vazkii.botania.common.entity.EntityCorporeaSpark;

import java.util.List;

public final class Hooks {
	private Hooks() {
	}
	
	@SuppressWarnings("unused") //called through asm bullshit
	public static IWrappedInventory invWrapHook(InvWithLocation inv, ICorporeaSpark spork, IWrappedInventory wrap) {
		if(wrap == null) {
			World world = inv.world;
			BlockPos pos = inv.pos;
			TileEntity te = world.getTileEntity(pos);
			
			
			if(te instanceof ICustomWrappedInventory) {				
				wrap = ((ICustomWrappedInventory)te).wrap(inv, spork);
			}
		}
		
		return wrap;
	}
	
	@SuppressWarnings("unused")
	public static List<ICorporeaSpark> nearbyCorporeaSparkHook(List<ICorporeaSpark> allNearby, EntityCorporeaSpark me) {
		BlockPos myPos = me.getPosition();
		World world = me.world;
		if(world.isRemote) return allNearby;
		
		allNearby.removeIf(otherSpork -> {
			BlockPos otherPos;
			if(otherSpork instanceof EntityCorporeaSpark) {
				otherPos = ((EntityCorporeaSpark)otherSpork).getPosition();
			} else {
				InvWithLocation otherInventory = otherSpork.getSparkInventory();
				if(otherInventory == null) return true;
				otherPos = otherInventory.pos.up();
			}
			
			float searchStartX = myPos.getX() + .5f;
			float searchStartY = myPos.getY() - 0.2f;
			float searchStartZ = myPos.getZ() + .5f;
			
			float searchEndX = otherPos.getX() + .5f;
			float searchEndY = otherPos.getY() - 0.2f;
			float searchEndZ = otherPos.getZ() + .5f;
			
			//Shit raycast THE MOVIE
			final float distanceDivider = 1.5f;
			
			float dist = MathHelper.pointDistanceSpace(searchStartX, searchStartY, searchStartZ, searchEndX, searchEndY, searchEndZ);
			
			float searchStepX = (searchEndX - searchStartX) / dist / distanceDivider;
			float searchStepY = (searchEndY - searchStartY) / dist / distanceDivider;
			float searchStepZ = (searchEndZ - searchStartZ) / dist / distanceDivider;
			
			float searchX = searchStartX;
			float searchY = searchStartY;
			float searchZ = searchStartZ;
			
			BlockPos.MutableBlockPos prevPos = new BlockPos.MutableBlockPos(-1, -1, -1);
			BlockPos.MutableBlockPos oldPos = new BlockPos.MutableBlockPos(-1, -1, -1);
			
			for(int i=0; i < dist * distanceDivider; i++) {
				prevPos.setPos(searchX, searchY, searchZ);
				if(!oldPos.equals(prevPos)) {
					oldPos.setPos(searchX, searchY, searchZ);
					BlockPos searchPos = new BlockPos(searchX, searchY, searchZ);
					
					IBlockState state = world.getBlockState(searchPos);
					Block block = state.getBlock();
					if(block instanceof ICorporeaInhibitor) {
						boolean blocksCorporea = ((ICorporeaInhibitor)block).shouldBlockCorporea(world, state, searchPos);
						if(blocksCorporea) return true;
					}
				}
				
				searchX += searchStepX;
				searchY += searchStepY;
				searchZ += searchStepZ;
			}
			
			return false;
		});
		
		return allNearby;
	}
	
	private static final double LOG_2 = Math.log(2d);
	
	@SuppressWarnings("unused")
	public static int retainerComparatorHook(IBlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCorporeaRetainer) {
			//TODO configuration for this to go to the old behavior.
			
			TileCorporeaRetainer retainer = (TileCorporeaRetainer) tile;
			
			if(!retainer.hasPendingRequest()) return 0;
			
			int count = ReflectionHelper.getPrivateValue(TileCorporeaRetainer.class, retainer, "requestCount");
			return count == 0 ? 0 : Math.min(15, (int) Math.floor(Math.log(count) / LOG_2) + 1);
		} else return 0;
	}
}
