package quaternary.incorporeal.core.etc;

import net.minecraft.item.ItemStack;
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
//TODO the PR allows extending WrappedIInventory now~
	//I dont feel like dealing with these messy functions though lmaO
public class LyingWrappedInventory extends WrappedInventoryBase {
	public LyingWrappedInventory(InvWithLocation inv, ICorporeaSpark spark, List<ItemStack> spoofedStacks) {
		super(spark);
		this.inv = inv;
		this.spoofedStacks = spoofedStacks;
	}
	
	private final InvWithLocation inv;
	private final List<ItemStack> spoofedStacks;
	
	@Override
	public InvWithLocation getWrappedObject() {
		return inv;
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
		
		for(ItemStack spoof : spoofedStacks) {
			if(!isMatchingItemStack(req.matcher, req.checkNBT, spoof)) continue;
			
			for(int i = 0; i < inv.handler.getSlots(); i++) {
				ItemStack stackAt = inv.handler.getStackInSlot(i);
				if(!stackAt.isEmpty()) {
					//Actually return as if it was a spoofed stack lol
					//Pulled a little sneaky on ya
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
		
		for(ItemStack spoof : spoofedStacks) {
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