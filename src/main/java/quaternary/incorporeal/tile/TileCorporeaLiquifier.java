package quaternary.incorporeal.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import quaternary.incorporeal.item.ItemCorporeaTicket;
import vazkii.botania.api.corporea.*;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;
import vazkii.botania.common.network.PacketBotaniaEffect;
import vazkii.botania.common.network.PacketHandler;

import java.util.List;

public class TileCorporeaLiquifier extends TileCorporeaBase implements ITickable {
	//TODO remove next Botania update
	@Override
	public int getSizeInventory() {
		return 0;
	}
	
	@Override
	public void update() {
		if(!world.isRemote && getSpark() != null) {
			ICorporeaSpark sparky = getSpark();
			AxisAlignedBB itemDetection = new AxisAlignedBB(pos).grow(1);
			
			List<EntityItem> nearbyItems = world.getEntitiesWithinAABB(EntityItem.class, itemDetection,
			(EntityItem e) -> (!e.getItem/*Stack*/().isEmpty() && e.getItem/*Stack*/().getItem() instanceof ItemCorporeaTicket));
			
			for(EntityItem ticket : nearbyItems) {
				CorporeaRequest req = ItemCorporeaTicket.getRequestFromTicket(ticket.getItem/*Stack*/());
				
				List<ItemStack> requestedStacks = CorporeaHelper.requestItem(req.matcher, req.count, sparky, req.checkNBT, true);
				sparky.onItemsRequested(requestedStacks);
				
				for(ItemStack aStack : requestedStacks) {
					EntityItem newItem = new EntityItem(world, ticket.posX, ticket.posY, ticket.posZ, aStack);
					newItem.motionX = -ticket.motionX;
					newItem.motionY = -ticket.motionY;
					newItem.motionZ = -ticket.motionZ;
					
					world.spawnEntity(newItem);
					
					//poof!
					PacketHandler.sendToNearby(newItem.world, new BlockPos(newItem), new PacketBotaniaEffect(PacketBotaniaEffect.EffectType.ITEM_SMOKE, newItem.posX, newItem.posY, newItem.posZ, newItem.getEntityId(), 3));
				}
				
				ticket.setDead();
			}
		}
	}
}
