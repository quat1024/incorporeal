package quaternary.incorporeal.feature.cygnusnetwork.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.api.cygnus.ILooseCygnusFunnelable;
import quaternary.incorporeal.core.etc.helper.CygnusHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusRegistries;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.block.BlockCygnusFunnel;
import quaternary.incorporeal.feature.cygnusnetwork.cap.IncorporeticCygnusCapabilities;
import quaternary.incorporeal.feature.cygnusnetwork.entity.EntityCygnusMasterSpark;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class TileCygnusFunnel extends TileEntity implements ITickable {
	private boolean isPowered;
	private boolean backLit;
	private boolean frontLit;
	
	@Override
	public void update() {
		if(!world.isRemote && world.getTotalWorldTime() % 20 == 0) {
			updateArrowStatus(pos, world.getBlockState(pos).getValue(BlockCygnusFunnel.FACING));
		}
	}
	
	public void updateArrowStatus(BlockPos pos, EnumFacing facing) {
		BlockPos fromPos = pos.offset(facing.getOpposite());
		BlockPos toPos = pos.offset(facing);
		
		boolean oldBackLit = backLit;
		boolean oldFrontLit = frontLit;
		
		ICygnusFunnelable backFunnelable = getCygnusFunnelable(world, fromPos, facing);
		backLit = backFunnelable != null && backFunnelable.canGiveCygnusItem();
		
		ICygnusFunnelable frontFunnelable = getCygnusFunnelable(world, toPos, facing.getOpposite());
		frontLit = frontFunnelable != null && frontFunnelable.canAcceptCygnusItem();
		
		if(oldBackLit != backLit || oldFrontLit != frontLit) {
			IBlockState memes = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, memes, memes, 3);
		}
	}
	
	public void onNeighborChange() {
		IBlockState state = world.getBlockState(pos);
		EnumFacing facing = state.getValue(BlockCygnusFunnel.FACING);
		boolean shouldPower = world.isBlockPowered(pos);
		
		if(isPowered != shouldPower) {
			isPowered = shouldPower;
			markDirty();
			if(shouldPower) {
				BlockPos fromPos = pos.offset(facing.getOpposite());
				BlockPos toPos = pos.offset(facing);
				
				ICygnusFunnelable source = getCygnusFunnelable(world, fromPos, facing);
				ICygnusFunnelable sink = getCygnusFunnelable(world, toPos, facing.getOpposite());
				
				//while i have this data, might as well update the arrows :P
				backLit = source != null;
				frontLit = source != null;
				
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
					Optional.ofNullable(source.giveItemToCygnus()).ifPresent(stack::push);
				} else {
					stack.pop().ifPresent(sink::acceptItemFromCygnus);
				}
			}
		}
	}
	
	public boolean isBackLit() {
		return backLit;
	}
	
	public boolean isFrontLit() {
		return frontLit;
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 6969, getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		IBlockState yes = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, yes, yes, 3);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("Powered", isPowered);
		nbt.setBoolean("DisplayBack", backLit);
		nbt.setBoolean("DisplayFront", frontLit);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isPowered = nbt.getBoolean("powered");
		backLit = nbt.getBoolean("DisplayBack");
		frontLit = nbt.getBoolean("DisplayFront");
	}
	
	@Nullable
	private static ICygnusFunnelable getCygnusFunnelable(World world, BlockPos pos, EnumFacing face) {
		//Is it a block (as an interface?)
		IBlockState state = world.getBlockState(pos);
		Block b = state.getBlock();
		if(b instanceof ICygnusFunnelable) {
			return (ICygnusFunnelable) b;
		}
		
		//Is it a tile entity capability?
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null) {
			//try face
			ICygnusFunnelable capMaybe = tile.getCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, face);
			if(capMaybe != null) return capMaybe;
			//try null
			capMaybe = tile.getCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, null);
			if(capMaybe != null) return capMaybe;
		}
		
		//Is it an entity capability?
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos));
		for(Entity e : entities) {
			//try face
			ICygnusFunnelable capMaybe = e.getCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, face);
			if(capMaybe != null) return capMaybe;
			//try null
			capMaybe = e.getCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, null);
			if(capMaybe != null) return capMaybe;
		}
		
		//Maybe it's a loose funnelable? (last resort)
		for(ILooseCygnusFunnelable loose : CygnusRegistries.LOOSE_FUNNELABLES) {
			ICygnusFunnelable f = loose.getFor(world, pos, state, face);
			if(f != null) return f;
		}
		
		//Idk where it is!
		return null;
	}
}
