package quaternary.incorporeal.feature.corporetics.tile;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;
import quaternary.incorporeal.api.ICustomWrappedInventory;
import quaternary.incorporeal.core.etc.LyingWrappedInventory;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.corporea.IWrappedInventory;
import vazkii.botania.api.corporea.InvWithLocation;
import vazkii.botania.common.block.tile.string.TileRedString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileRedStringLiar extends TileRedString implements ICustomWrappedInventory {
	@Override
	public boolean acceptBlock(BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile == null) return false;
		else return tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) || tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, getOrientation().getOpposite());
	}
	
	@Override
	public boolean hasCapability(Capability<?> cap, @Nullable EnumFacing facing) {
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(cap, facing);
	}
	
	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> cap, @Nullable EnumFacing facing) {
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			TileEntity boundTile = getTileAtBinding();
			//not bound to anything? welp
			if(boundTile == null) return (T) EmptyHandler.INSTANCE;
			
			EnumFacing myFacing = getOrientation();
			//try facing
			IItemHandler boundHandler = boundTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, myFacing);
			if(boundHandler != null) return (T) boundHandler;
			
			//try faceless
			boundHandler = boundTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if(boundHandler != null) return (T) boundHandler;
			
			//welp
			return (T) EmptyHandler.INSTANCE;
		} else return super.getCapability(cap, facing);
	}
	
	//Basically copied from TileCorporeaFunnel; doing a similar thing to it anyways
	public List<ItemStack> getSpoofedStacks() {
		List<ItemStack> stacks = new ArrayList<>();
		
		for(EnumFacing whichWay : EnumFacing.HORIZONTALS) {
			List<EntityItemFrame> frames = world.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos.offset(whichWay), pos.offset(whichWay).add(1,1,1)));
			for(EntityItemFrame frame : frames) {
				EnumFacing orientation = frame.facingDirection;
				if(orientation == whichWay) {
					ItemStack frameStack = frame.getDisplayedItem();
					if(!frameStack.isEmpty()) {
						stacks.add(frameStack.copy());
					}
				}
			}
		}
		
		return stacks;
	}
	
	@Override
	public IWrappedInventory wrap(InvWithLocation inv, ICorporeaSpark spork) {
		return new LyingWrappedInventory(inv, spork, getSpoofedStacks());
	}
}
