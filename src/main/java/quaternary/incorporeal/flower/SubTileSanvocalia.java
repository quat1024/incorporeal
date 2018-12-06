package quaternary.incorporeal.flower;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import quaternary.incorporeal.IncorporeticConfig;
import quaternary.incorporeal.etc.helper.CorporeaHelper2;
import quaternary.incorporeal.item.ItemCorporeaTicket;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import quaternary.incorporeal.net.IncorporeticPacketHandler;
import quaternary.incorporeal.net.MessageSparkleLine;
import vazkii.botania.api.corporea.CorporeaRequest;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

//A horrible pun based off of the real life Sanvitalia flower
public class SubTileSanvocalia extends SubTileFunctional implements ILexiconable {
	@Nullable
	private UUID owner;
	private String customName = "Sanvocalia"; 
	private int cooldown;
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(cooldown > 0) {
			cooldown--;
			return;
		}
		
		World w = supertile.getWorld();
		BlockPos pos = supertile.getPos();
		
		if(w.isRemote || redstoneSignal > 0) return;
		
		AxisAlignedBB itemDetectionBox = new AxisAlignedBB(pos.add(-getRange(), 0, -getRange()), pos.add(getRange() + 1, 1, getRange() + 1));
		List<EntityItem> nearbyTickets = w.getEntitiesWithinAABB(EntityItem.class, itemDetectionBox, (ent) -> {
			if(ent == null) return false;
			
			ItemStack stack = ent.getItem/*Stack*/();
			return ItemCorporeaTicket.hasRequest(stack);
		});
		
		if(nearbyTickets.isEmpty()) return;
		
		EntityItem ticket = nearbyTickets.get(w.rand.nextInt(nearbyTickets.size()));
		CorporeaRequest ticketsRequest = ItemCorporeaTicket.getRequest(ticket.getItem/*Stack*/());
		assert ticketsRequest != null; //it already passed the predicate above
		
		List<TileCorporeaIndex> nearbyIndices = CorporeaHelper2.getNearbyIndicesReflect(w, pos, getRange());
		
		if(nearbyIndices.isEmpty()) {
			//No indexes nearby? Post the message to chat
			//This is a nod to when players accidentally type corporea requests into chat, lol
			MinecraftServer server = w.getMinecraftServer();
			if(server != null && mana >= 100) {
				TextComponentTranslation txt = new TextComponentTranslation("chat.type.text", customName, CorporeaHelper2.requestToString(ticketsRequest));
				
				for(EntityPlayerMP player : w.getMinecraftServer().getPlayerList().getPlayers()) {
					if(IncorporeticConfig.Sanvocalia.EVERYONE_HEARS_MESSAGES || player.getUniqueID().equals(owner)) {
						player.sendMessage(txt);
					}
				}
				
				mana -= 100;
				consumeTicket(ticket, null);
				sync();
			}
		} else {
			//Read the request to all nearby indices
			boolean did = false;
			Set<BlockPos> indexPositions = new HashSet<>();
			for(TileCorporeaIndex index : nearbyIndices) {
				if(mana < 20) break;
				CorporeaHelper2.spawnRequest(w, ticketsRequest, index.getSpark(), index.getPos());
				mana -= 20;
				indexPositions.add(index.getPos());
				did = true;
			}
			
			if(did) {
				consumeTicket(ticket, indexPositions);
				sync();
			}
		}
	}
	
	//Only call serverside
	private void consumeTicket(EntityItem ticket, @Nullable Collection<BlockPos> indexPositions) {
		Vec3d ticketPos = ticket.getPositionVector();
		ItemStack ticketStack = ticket.getItem/*Stack*/();
		WorldServer world = (WorldServer) getWorld();
		BlockPos pos = getPos();
		
		IncorporeticPacketHandler.sendToAllTracking(
						new MessageSparkleLine(ticketPos, new Vec3d(pos).add(.5, .5, .5), 12), world, pos
		);
		if(indexPositions != null) {
			for(BlockPos p : indexPositions) {
				IncorporeticPacketHandler.sendToAllTracking(
								new MessageSparkleLine(new Vec3d(p).add(.5, .5, .5), ticketPos, 12), world, pos
				);
			}
		}
		
		SoundEvent sound = world.rand.nextDouble() < 0.1 ? SoundEvents.ENTITY_PLAYER_BURP : SoundEvents.ENTITY_GENERIC_EAT;
		world.playSound(null, pos, sound, SoundCategory.BLOCKS, .5f, 1);
		world.spawnParticle(EnumParticleTypes.ITEM_CRACK, false, ticket.posX, ticket.posY, ticket.posZ, 20, 0.1D, 0.1D, 0.1D, 0.05D, Item.getIdFromItem(ticketStack.getItem()), ticketStack.getItemDamage());
		
		if(ticketStack.getCount() > 1) {
			ticketStack.shrink(1);
			ticket.setItem(ticketStack); //forces a sync (look inside setItem)
		} else {
			ticket.setDead();
		}
		
		cooldown = 3;
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
		if(cmp.hasKey("Owner")) {
			owner = NBTUtil.getUUIDFromTag(cmp.getCompoundTag("Owner"));
		} else owner = null;
		customName = cmp.getString("CustomName");
		cooldown = cmp.getInteger("TicketCooldown");
	}
	
	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		if(owner != null) {
			cmp.setTag("Owner", NBTUtil.createUUIDTag(owner));
		}
		cmp.setString("CustomName", customName);
		cmp.setInteger("TicketCooldown", cooldown);
		super.writeToPacketNBT(cmp);
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.sanvocalia;
	}
}
