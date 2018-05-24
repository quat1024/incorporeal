package quaternary.incorporeal.spookyasm;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.ICorporeaInhibitor;
import quaternary.incorporeal.block.BlockCorporeaInhibitor;
import quaternary.incorporeal.etc.ICustomWrappedInventory;
import vazkii.botania.api.corporea.*;
import vazkii.botania.common.core.helper.MathHelper;
import vazkii.botania.common.entity.EntityCorporeaSpark;

import java.util.ArrayList;
import java.util.List;

public class Hooks {
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
	
	public static List<ICorporeaSpark> nearbyCorporeaSparkHook(List<ICorporeaSpark> allNearby, EntityCorporeaSpark me) {
		BlockPos myPos = me.getPosition();
		World world = me.world;
		if(world.isRemote) return allNearby;
		
		allNearby.removeIf(otherSpork -> {
			InvWithLocation otherInventory = otherSpork.getSparkInventory();
			if(otherInventory == null) return true;
			BlockPos otherPos = otherInventory.pos.down();
			
			int searchStartX = Math.min(myPos.getX(), otherPos.getX());
			int searchStartY = Math.min(myPos.getY(), otherPos.getY());
			int searchStartZ = Math.min(myPos.getZ(), otherPos.getZ());
			
			int searchEndX = Math.max(myPos.getX(), otherPos.getX());
			int searchEndY = Math.max(myPos.getY(), otherPos.getY());
			int searchEndZ = Math.max(myPos.getZ(), otherPos.getZ());
			
			double dist = MathHelper.pointDistanceSpace(searchStartX, searchStartY, searchStartZ, searchEndX, searchEndY, searchEndZ);
			
			//Shit raycast THE MOVIE
			double searchStepX = (searchEndX - searchStartX) / dist;
			double searchStepY = (searchEndY - searchStartY) / dist;
			double searchStepZ = (searchEndZ - searchStartZ) / dist;
			
			double searchX = searchStartX;
			double searchY = searchStartY;
			double searchZ = searchStartZ;
			
			Incorporeal.LOGGER.info("ADDA");
			for(int i=0; i < dist; i++) {
				Incorporeal.LOGGER.info("Start {} {} {} End {} {} {} Pos {} {} {}", searchStartX, searchStartY, searchStartZ, searchEndX, searchEndY, searchEndZ, searchX, searchY, searchZ);
				IBlockState state = world.getBlockState(new BlockPos(searchX, searchY, searchZ));
				Block block = state.getBlock();
				if(block instanceof ICorporeaInhibitor) {
					if(((ICorporeaInhibitor)block).shouldBlockCorporea(world, state)) return true;
				}
				
				searchX += searchStepX;
				searchY += searchStepY;
				searchZ += searchStepZ;
			}
			Incorporeal.LOGGER.info("ASDASDASDASDASDASd");
			
			return false;
		});
		
		return allNearby;
	}
}
