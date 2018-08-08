package quaternary.incorporeal.item;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.items.ItemHandlerHelper;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class ItemTicketConjurer extends Item {
	static final Map<Pattern, TileCorporeaIndex.IRegexStacker> patterns = ReflectionHelper.getPrivateValue(TileCorporeaIndex.class, null, "patterns");
	
	//Based on code from Botania's TileCorporeaIndex
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onChat(ServerChatEvent e) {
		EntityPlayerMP player = e.getPlayer();
		WorldServer w = e.getPlayer().getServerWorld();
		
		//The stack used for requests of the form "5 of this"
		ItemStack thisOrderStack;
		
		//Make sure the player is actually holding the portable corporea index item
		if(player.getHeldItemMainhand().getItem() instanceof ItemTicketConjurer) {
			thisOrderStack = player.getHeldItemOffhand();
		} else if (player.getHeldItemOffhand().getItem() instanceof ItemTicketConjurer) {
			thisOrderStack = player.getHeldItemMainhand();
		} else {
			//They're not holding me, just bail
			return;
		}
		
		String chatMessage = e.getMessage().toLowerCase().trim();
		
		String itemName = "";
		int itemCount = 0;
		boolean foundMatch = false;
		
		for(Map.Entry<Pattern, TileCorporeaIndex.IRegexStacker> pair : patterns.entrySet()) {
			Pattern pattern = pair.getKey();
			Matcher matcher = pattern.matcher(chatMessage);
			if(matcher.matches()) {
				TileCorporeaIndex.IRegexStacker stacker = pair.getValue();
				itemCount = stacker.getCount(matcher);
				itemName = stacker.getName(matcher);
				foundMatch = true;
			}
		}
		
		itemName = itemName.toLowerCase().trim();
		
		//Handle "2 of this" style requests
		if(itemName.equals("this") || itemName.equals("these")) {
			if(thisOrderStack.isEmpty()) {
				//Just kidding, they weren't holding anything else
				foundMatch = false;
			} else {
				itemName = thisOrderStack.getDisplayName();
			}
		}
		
		if(foundMatch) {
			//Give them a ticket!
			ItemStack ticket = ItemCorporeaTicket.createFromRequest(itemName, itemCount);
			ItemHandlerHelper.giveItemToPlayer(player, ticket);
			
			//Cancel the chat message so it doesn't leak into multiplayer chat
			e.setCanceled(true);
		}
	}
}
