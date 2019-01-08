package quaternary.incorporeal.tile.cygnus;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.block.cygnus.BlockCygnusFunnel;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.cygnus.cap.IncorporeticCygnusCapabilities;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.etc.RedstoneDustCygnusFunnelable;
import quaternary.incorporeal.etc.helper.CygnusHelpers;

import javax.annotation.Nullable;
import java.util.List;

public class TileCygnusFunnel extends TileEntity {
	private boolean isPowered;
	private boolean displayBackArrow;
	private boolean displayFrontArrow;
	
	public void updateArrowStatus(BlockPos pos, EnumFacing facing) {
		BlockPos fromPos = pos.offset(facing.getOpposite());
		BlockPos toPos = pos.offset(facing);
		
		displayBackArrow = hasCygnusFunnelable(world, fromPos);
		displayFrontArrow = hasCygnusFunnelable(world, toPos);
	}
	
	public void onNeighborChange() {
		IBlockState state = world.getBlockState(pos);
		EnumFacing facing = state.getValue(BlockCygnusFunnel.FACING);
		boolean shouldPower = world.isBlockPowered(pos);
		
		updateArrowStatus(pos, facing);
		
		if(isPowered != shouldPower) {
			isPowered = shouldPower;
			if(shouldPower) {
				BlockPos fromPos = pos.offset(facing.getOpposite());
				BlockPos toPos = pos.offset(facing);
				
				ICygnusFunnelable source = getCygnusFunnelable(world, fromPos);
				ICygnusFunnelable sink = getCygnusFunnelable(world, toPos);
				
				//while i have this data, might as well update the arrows :P
				displayBackArrow = source != null;
				displayFrontArrow = source != null;
				
				boolean sourceCanGive = source != null && source.canGiveCygnusItem();
				boolean sinkCanAccept = sink != null && sink.canAcceptCygnusItem();
				if(!sourceCanGive && !sinkCanAccept) return;
				
				if(sourceCanGive && sinkCanAccept) {
					//Move data from the source to the sink, no stack needed
					sink.acceptItemFromCygnus(source.giveItemToCygnus());
					return;
				}
				
				//Only 1 action is available (sourcing or sinking). So we will find a Cygnus stack
				//and use that as the other end of the action.
				EntityCygnusMasterSpark master = CygnusHelpers.getMasterSparkForSparkAt(world, pos);
				if(master == null) return; //Or not, since we're not even on a network apparently :p
				
				CygnusStack stack = master.getCygnusStack();
				if(sourceCanGive) {
					Object given = source.giveItemToCygnus();
					if(given != null)	stack.push(given);
				} else {
					stack.pop().ifPresent(sink::acceptItemFromCygnus);
				}
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("Powered", isPowered);
		nbt.setBoolean("DisplayBack", displayBackArrow);
		nbt.setBoolean("DisplayFront", displayFrontArrow);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isPowered = nbt.getBoolean("powered");
		displayBackArrow = nbt.getBoolean("DisplayBack");
		displayFrontArrow = nbt.getBoolean("DisplayFront");
	}
	
	@Nullable
	private static ICygnusFunnelable getCygnusFunnelable(World world, BlockPos pos) {
		//Is it a block (as an interface?)
		IBlockState state = world.getBlockState(pos);
		Block b = state.getBlock();
		if(b instanceof ICygnusFunnelable) {
			return (ICygnusFunnelable) b;
		} else if(b == Blocks.REDSTONE_WIRE) {
			//Not my block to slap an interface on to, let's just hardcode it lmao
			return RedstoneDustCygnusFunnelable.forLevel(state.getValue(BlockRedstoneWire.POWER));
		}
		
		//Is it a tile entity capability?
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null) {
			ICygnusFunnelable capMaybe = tile.getCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, null);
			if(capMaybe != null) return capMaybe;
		}
		
		//Is it an entity capability?
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos));
		for(Entity e : entities) {
			ICygnusFunnelable capMaybe = e.getCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, null);
			if(capMaybe != null) return capMaybe;
		}
		
		//Idk where it is!
		return null;
	}
	
	private static boolean hasCygnusFunnelable(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block b = state.getBlock();
		if(b instanceof ICygnusFunnelable || b == Blocks.REDSTONE_WIRE) {
			return true;
		}
		
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null) {
			return tile.hasCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, null);
		}
		
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos));
		for(Entity e : entities) {
			if(e.hasCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, null)) return true;
		}
		
		//Guess no!
		return false;
	}
}
