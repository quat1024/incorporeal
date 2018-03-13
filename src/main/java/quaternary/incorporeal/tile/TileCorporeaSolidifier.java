package quaternary.incorporeal.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import quaternary.incorporeal.item.ItemCorporeaTicket;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;

//MUST extend TileCorporeaRetainer because the interceptor uses an instanceof check
public class TileCorporeaSolidifier extends TileCorporeaRetainer {
	//Called from the corporea interceptor.
	//Obviously I'm not going to actually set a request, I'm just stealing it
	@Override
	public void setPendingRequest(BlockPos requesterPos, Object request, int requestCount) {
		ItemStack ticketStack = ItemCorporeaTicket.createFromRequest(request, requestCount);
		
		EntityItem ticketEnt = new EntityItem(world, pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5, ticketStack);
		world.spawnEntity(ticketEnt);
	}
	
	//TileCorporeaRetainer saves a bunch of shit to NBT regarding the stored corporea request.
	//The corporea solidifier does not store requests, but because I have to extend it, fun stuff happens
	@Override
	public void writePacketNBT(NBTTagCompound cmp) {
		//No-op (same as TileMod.writePacketNBT)
	}
	
	@Override
	public void readPacketNBT(NBTTagCompound cmp) {
		//No-op (same as TileMod.readPacketNBT)
	}
}
