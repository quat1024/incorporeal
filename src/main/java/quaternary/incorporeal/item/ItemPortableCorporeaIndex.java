package quaternary.incorporeal.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.*;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.lang3.text.WordUtils;
import quaternary.incorporeal.Incorporeal;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.common.advancements.CorporeaRequestTrigger;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;
import vazkii.botania.common.entity.EntityCorporeaSpark;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class ItemPortableCorporeaIndex extends Item {
	//Reee
	static final Map<Pattern, TileCorporeaIndex.IRegexStacker> patterns = ReflectionHelper.getPrivateValue(TileCorporeaIndex.class, null, "patterns");
	
	//Based on code from Botania's TileCorporeaIndex
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onChat(ServerChatEvent e) {
		EntityPlayerMP player = e.getPlayer();
		WorldServer w = e.getPlayer().getServerWorld();
		
		//The stack used for requests of the form "5 of this"
		ItemStack thisOrderStack;
		
		//Make sure the player is actually holding the portable corporea index item
		if(player.getHeldItemMainhand().getItem() instanceof ItemPortableCorporeaIndex) {
			thisOrderStack = player.getHeldItemOffhand();
		} else if (player.getHeldItemOffhand().getItem() instanceof ItemPortableCorporeaIndex) {
			thisOrderStack = player.getHeldItemMainhand();
		} else {
			//They're not holding me, just bail
			return;
		}
		
		//Do the slightly more expensive check for nearby corporea sparks after the easy item one
		AxisAlignedBB aabb = new AxisAlignedBB(player.getPosition()).grow(2.5);
		
		List<EntityCorporeaSpark> nearbySparks = w.getEntitiesWithinAABB(EntityCorporeaSpark.class, aabb);
		if(nearbySparks.isEmpty()) return;
		if(nearbySparks.size() > 1) Collections.shuffle(nearbySparks);
		
		EntityCorporeaSpark spark = nearbySparks.get(0);
		String chatMessage = e.getMessage().toLowerCase().trim();
		
		String itemName = "";
		int itemCount = 0;
		boolean foundMatch = false;
		
		//I don't really know what i'm doing, basically straight up copied from tilecorporeaindex
		for(Pattern pattern : patterns.keySet()) {
			Matcher matcher = pattern.matcher(chatMessage);
			if(matcher.matches()) {
				TileCorporeaIndex.IRegexStacker stacker = patterns.get(pattern);
				itemCount = stacker.getCount(matcher);
				itemName = stacker.getName(matcher);
				foundMatch = true;
				break;
			}
		}
		
		//Handle "2 of this" style requests
		if(itemName.equals("this") || itemName.equals("these")) {
			if(thisOrderStack.isEmpty()) {
				//Just kidding, they weren't holding anything else
				foundMatch = false;
			} else {
				itemName = thisOrderStack.getDisplayName();
			}
		}
		
		itemName = itemName.toLowerCase().trim();
		
		if(foundMatch) {
			//Perform the corporea request. First, request and spawn the items in world.
			//Based off of TileCorporeaIndex.doCorporeaRequest but requests the items at the user's feet
			List<ItemStack> requestedStacks = CorporeaHelper.requestItem(itemName, itemCount, spark, true);
			spark.onItemsRequested(requestedStacks);
			
			for(ItemStack stack : requestedStacks) {
				if(!stack.isEmpty()) {
					//At the player position
					EntityItem item = new EntityItem(w, player.posX, player.posY, player.posZ, stack);
					w.spawnEntity(item);
				}
			}
			
			//Next, send feedback and potentially earn advancements for this request.
			player.sendMessage(new TextComponentTranslation("botaniamisc.requestMsg", itemCount, WordUtils.capitalizeFully(itemName), CorporeaHelper.lastRequestMatches, CorporeaHelper.lastRequestExtractions).setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
			CorporeaRequestTrigger.INSTANCE.trigger(player, w, player.getPosition(), CorporeaHelper.lastRequestExtractions);
			
			//Cancel the chat message so it doesn't leak into multiplayer chat
			e.setCanceled(true);
		}
	}
}
