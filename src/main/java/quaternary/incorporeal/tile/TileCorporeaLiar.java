package quaternary.incorporeal.tile;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import quaternary.incorporeal.api.ICustomWrappedInventory;
import quaternary.incorporeal.block.BlockCorporeaLiar;
import quaternary.incorporeal.etc.LyingWrappedInventory;
import vazkii.botania.api.corporea.*;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TileCorporeaLiar extends TileCorporeaBase implements ICustomWrappedInventory {
	private EnumFacing spoofingDirection;
	
	public TileCorporeaLiar() {
	}	
	
	public TileCorporeaLiar setDirection(EnumFacing whichWay) {
		spoofingDirection = whichWay;
		return this;
	}
	
	private void updateDirection() {
		spoofingDirection = world.getBlockState(pos).getValue(BlockCorporeaLiar.FACING);
	}
	
	//Basically copied from TileCorporeaFunnel; doing a similar thing to it anyways.
	public List<ItemStack> getSpoofedStack() {
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
	
	//TODO: I don't want to be able to interact with the spoofed inventory with like, a hopper?
	//But the only way corporea sparks can find inventories is through capabilities.
	//THINKING ASM
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, EnumFacing side) {
		if(cap != CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return false;
		
		if(spoofingDirection == null) updateDirection();
		
		TileEntity te = world.getTileEntity(pos.offset(spoofingDirection));
		if(te == null) return false;
		return te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, spoofingDirection);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(@Nonnull Capability<T> cap, EnumFacing side) {
		if(cap != CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return null;
		
		if(spoofingDirection == null) updateDirection();
		
		TileEntity te = world.getTileEntity(pos.offset(spoofingDirection));
		if(te == null) return null; //UH OH
		
		IItemHandler wrappedHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, spoofingDirection);
		return (T) wrappedHandler;
	}
	
	@Override
	public IWrappedInventory wrap(InvWithLocation inv, ICorporeaSpark spork) {
		return new LyingWrappedInventory(inv, spork);
	}
}
