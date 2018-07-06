package quaternary.incorporeal.flower;

import com.google.common.base.Predicate;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import quaternary.incorporeal.etc.helper.CorporeaHelper2;
import quaternary.incorporeal.item.ItemCorporeaTicket;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import vazkii.botania.api.corporea.*;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;

import java.util.*;
import java.util.stream.Collectors;

//A horrible pun based off of the real life Sanvitalia flower
public class SubTileSanvocalia extends SubTileFunctional implements ILexiconable {
	public static final String NAME = "sanvocalia";
	public static final String NAME_CHIBI = "sanvocalia_chibi";
	
	@GameRegistry.ObjectHolder("incorporeal:corporea_ticket")
	public static final Item CORPOREA_TICKET = Items.AIR;
	
	static final Predicate<EntityItem> ITEM_IS_VALID_TICKET = (entity) -> {
		ItemStack stack = (entity).getItem();
		return stack.getItem() == CORPOREA_TICKET && ItemCorporeaTicket.isRequestable(stack);
	};
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(mana <= 20) return;
		
		World w = supertile.getWorld();
		BlockPos pos = supertile.getPos();
		
		if(w.isRemote || redstoneSignal > 0) return;
		
		AxisAlignedBB itemDetectionBox = new AxisAlignedBB(pos.add(-getRange(), 0, -getRange()), pos.add(getRange(), 1, getRange()));
		List<EntityItem> nearbyTickets = w.getEntitiesWithinAABB(EntityItem.class, itemDetectionBox, ITEM_IS_VALID_TICKET);
		
		if(nearbyTickets.isEmpty()) return;
		
		EntityItem ticket = nearbyTickets.get(0);
		CorporeaRequest ticketsRequest = ItemCorporeaTicket.getRequestFromTicket(ticket.getItem());
		
		//Definitely cache nearby indices and stuff, this is expensive
		List<TileCorporeaIndex> nearbyIndices = getNearbyIndicesReflect(w, pos);
		if(nearbyIndices.isEmpty()) {
			//No indexes nearby? Post the message to chat
			//This is a nod to when players accidentally type corporea requests into chat, lol
			TextComponentTranslation txt = new TextComponentTranslation("chat.type.text", "Sanvocalia", CorporeaHelper2.requestToString(ticketsRequest));
			for(EntityPlayerMP player : w.getMinecraftServer().getPlayerList().getPlayers()) {
				player.sendMessage(txt);
			}
		} else {
			Collections.shuffle(nearbyIndices);
			TileCorporeaIndex index = nearbyIndices.get(0);
			
			ICorporeaSpark spork = index.getSpark();
			CorporeaHelper2.spawnRequest(w, ticketsRequest, spork, index.getPos());
		}
		mana -= 20;		
		ticket.setDead();
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
	
	public static List<TileCorporeaIndex> getNearbyIndicesReflect(World w, BlockPos pos) {
		Set<TileCorporeaIndex> indices = ReflectionHelper.getPrivateValue(TileCorporeaIndex.class, null, "serverIndexes");
		
		return indices.stream().filter(tile -> {
			if(tile.getWorld().provider.getDimension() != w.provider.getDimension()) return false;
			
			return Math.abs(pos.getX() - tile.getPos().getX()) <= 2 && Math.abs(pos.getZ() - tile.getPos().getZ()) <= 2 && tile.getPos().getY() == pos.getY();
		}).collect(Collectors.toList());
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.sanvocalia;
	}
}
