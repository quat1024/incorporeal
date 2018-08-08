package quaternary.incorporeal.etc;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.tile.TileCorporeaLiar;
import vazkii.botania.api.corporea.CorporeaRequest;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.corporea.InvWithLocation;
import vazkii.botania.common.integration.corporea.WrappedInventoryBase;

import java.util.ArrayList;
import java.util.List;

/** An IWrappedInventory that completely ignores the item passed in to "getCount" and substitutes its own. Extraction works as normal, so it will return items that do not match the item passed in to getCount! Used in the corporea liar to provide the "spoofing" functionality.
 * 
 * While it would be useful to extend Botania's WrappedIInventory, its constructor is private.
 * Most of this class is therefore just copied from it. */
public class LyingWrappedInventory extends WrappedInventoryBase {
	private final InvWithLocation inv;
	
	public LyingWrappedInventory(InvWithLocation inv, ICorporeaSpark spark) {
		super(spark);
		this.inv = inv;
	}
	
	@Override
	public InvWithLocation getWrappedObject() {
		return inv;
	}
	
	private List<ItemStack> getSpoofedStacks() {
		World w = inv.world;
		BlockPos p = inv.pos;
		TileEntity te = w.getTileEntity(p);
		if(te instanceof TileCorporeaLiar) {
			return ((TileCorporeaLiar)te).getSpoofedStack();
		}
		return ImmutableList.of(ItemStack.EMPTY);
	}
	
	@Override
	public List<ItemStack> countItems(CorporeaRequest request) {
		return doMockedRequest(request);
	}
	
	@Override
	public List<ItemStack> extractItems(CorporeaRequest request) {	
		return doRealRequest(request);
	}
	
	private List<ItemStack> doMockedRequest(CorporeaRequest req) {
		List<ItemStack> stacks = new ArrayList<>();
		
		for(ItemStack spoof : getSpoofedStacks()) {
			if(!isMatchingItemStack(req.matcher, req.checkNBT, spoof)) continue;
			
			for(int i = 0; i < inv.handler.getSlots(); i++) {
				ItemStack stackAt = inv.handler.getStackInSlot(i);
				if(!stackAt.isEmpty()) {
					ItemStack copy = new ItemStack(spoof.getItem(), stackAt.getCount(), spoof.getMetadata());
					
					req.extractedItems += Math.min(stackAt.getCount(), req.count == -1 ? stackAt.getCount() : req.count);
					req.foundItems += copy.getCount();
					
					stacks.add(copy);
				}
			}
		}
		
		return stacks;
	}
	
	//This barely works.
	private List<ItemStack> doRealRequest(CorporeaRequest req) {
		List<ItemStack> stacks = new ArrayList<>();
		
		for(ItemStack spoof : getSpoofedStacks()) {
			if(!isMatchingItemStack(req.matcher, req.checkNBT, spoof)) continue;
			
			for(int i = inv.handler.getSlots() - 1; i >= 0; i--) {
				ItemStack stackAt = inv.handler.getStackInSlot(i);
				
				int rem = Math.min(stackAt.getCount(), req.count == -1 ? stackAt.getCount() : req.count);
				req.foundItems += stackAt.getCount();
				
				if(rem > 0) {
					stacks.add(inv.handler.extractItem(i, rem, false));
					if(spark != null)
						spark.onItemExtracted(stackAt);
				}
				
				req.extractedItems += rem;
				
				if(req.count != -1) {
					req.count -= rem;
				}
			}
		}
		
		return stacks;
	}
}