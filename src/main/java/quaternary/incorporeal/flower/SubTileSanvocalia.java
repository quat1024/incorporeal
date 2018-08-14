package quaternary.incorporeal.flower;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import quaternary.incorporeal.IncorporeticConfig;
import quaternary.incorporeal.etc.helper.CorporeaHelper2;
import quaternary.incorporeal.item.ItemCorporeaTicket;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import vazkii.botania.api.corporea.CorporeaRequest;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

//A horrible pun based off of the real life Sanvitalia flower
public class SubTileSanvocalia extends SubTileFunctional implements ILexiconable {
	public static final Predicate<EntityItem> ITEM_IS_VALID_TICKET = (entity) -> {
		ItemStack stack = (entity).getItem();
		return stack.getItem() instanceof ItemCorporeaTicket && ItemCorporeaTicket.isRequestable(stack);
	};
	
	@Nullable
	private UUID owner;
	private String customName = "Sanvocalia"; 
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		World w = supertile.getWorld();
		BlockPos pos = supertile.getPos();
		
		if(w.isRemote || redstoneSignal > 0) return;
		
		AxisAlignedBB itemDetectionBox = new AxisAlignedBB(pos.add(-getRange(), 0, -getRange()), pos.add(getRange(), 1, getRange()));
		List<EntityItem> nearbyTickets = w.getEntitiesWithinAABB(EntityItem.class, itemDetectionBox, ITEM_IS_VALID_TICKET::test);
		
		if(nearbyTickets.isEmpty()) return;
		
		EntityItem ticket = nearbyTickets.get(0);
		CorporeaRequest ticketsRequest = ItemCorporeaTicket.getRequestFromTicket(ticket.getItem());
		
		List<TileCorporeaIndex> nearbyIndices = CorporeaHelper2.getNearbyIndicesReflect(w, pos, getRange());
		
		if(nearbyIndices.isEmpty()) {
			//No indexes nearby? Post the message to chat
			//This is a nod to when players accidentally type corporea requests into chat, lol
			TextComponentTranslation txt = new TextComponentTranslation("chat.type.text", customName, CorporeaHelper2.requestToString(ticketsRequest));
			MinecraftServer server = w.getMinecraftServer();
			if(server != null && mana >= 100) {
				for(EntityPlayerMP player : w.getMinecraftServer().getPlayerList().getPlayers()) {
					if(IncorporeticConfig.Sanvocalia.EVERYONE_HEARS_MESSAGES || player.getUniqueID().equals(owner)) {
						player.sendMessage(txt);
					}
				}
				
				mana -= 100;
				ticket.setDead();
				sync();
			}
		} else {
			//Read the request to all nearby indices
			boolean did = false;
			for(TileCorporeaIndex index : nearbyIndices) {
				if(mana < 20) break;
				CorporeaHelper2.spawnRequest(w, ticketsRequest, index.getSpark(), index.getPos());
				mana -= 20;
				did = true;
			}
			
			if(did) {
				ticket.setDead();
				sync();
			}
		}
	}
	
	public int getRange() {
		return 3;
	}
	
	public static class Mini extends SubTileSanvocalia {
		public Mini() {}
		
		@Override
		public int getRange() {
			return 1;
		}
	}
	
	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(supertile.getPos(), getRange());
	}
	
	@Override
	public int getMaxMana() {
		return 200;
	}
	
	@Override
	public int getColor() {
		return 0xed9625;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
		if(entity != null) {
			owner = entity.getUniqueID();
		} else {
			owner = null;
		}
		
		if(stack.hasDisplayName()) {
			customName = stack.getDisplayName();
		}
	}
	
	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		owner = NBTUtil.getUUIDFromTag(cmp.getCompoundTag("Owner"));
		customName = cmp.getString("CustomName");
	}
	
	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		cmp.setTag("Owner", NBTUtil.createUUIDTag(owner));
		cmp.setString("CustomName", customName);
		super.writeToPacketNBT(cmp);
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.sanvocalia;
	}
}
