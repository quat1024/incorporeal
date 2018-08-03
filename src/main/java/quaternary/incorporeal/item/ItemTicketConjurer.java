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
import org.apache.commons.lang3.tuple.Pair;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class ItemTicketConjurer extends Item {
	static final Map<Pattern, TileCorporeaIndex.IRegexStacker> patterns;
	static final List<Pair<Pattern, TileCorporeaIndex.IRegexStacker>> patternsHack;
	
	static {
		patterns = ReflectionHelper.getPrivateValue(TileCorporeaIndex.class, null, "patterns");
		
		//For some reason it's picking the shortest ones first or smtn
		//meaning it prefers to parse "5 stone" as "1x '5 stone'" instead of, well, "5x stone"
		//Use a wacky hack to choose the longest ones first
		//That's generally going to be the most specific one
		
		patternsHack = new ArrayList<>(patterns.size());
		for(Map.Entry<Pattern, TileCorporeaIndex.IRegexStacker> entry : patterns.entrySet()) {
			patternsHack.add(Pair.of(entry.getKey(), entry.getValue()));
		}
		patternsHack.sort((a, b) -> {
			return b.getLeft().toString().length() - a.getLeft().toString().length();
		});
	}
	
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
		
		for(Pair<Pattern, TileCorporeaIndex.IRegexStacker> pair : patternsHack) {
			Pattern pattern = pair.getLeft();
			Matcher matcher = pattern.matcher(chatMessage);
			if(matcher.matches()) {
				TileCorporeaIndex.IRegexStacker stacker = pair.getRight();
				itemCount = stacker.getCount(matcher);
				itemName = stacker.getName(matcher);
				foundMatch = true;
				break;
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
