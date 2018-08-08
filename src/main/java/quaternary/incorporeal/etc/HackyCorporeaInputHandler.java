package quaternary.incorporeal.etc;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.tile.soulcore.TileCorporeaSoulCore;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.ICorporeaAutoCompleteController;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.corporea.InvWithLocation;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This is the worst class I've ever written in my whole life. Ever. There's no topping this.
 * 
 * This class is patched - *at runtime* - to extend TileCorporeaIndex$InputHandler.
 * It's final, so I can't extend it. The final modifier on that class is also removed at runtime.
 * Don't move this class without fixing the path in TerribleHorribleNoGoodVeryBadAwfulCorporeaIndexInputHandlerTweak.java.
 * */
@Mod.EventBusSubscriber(modid = Incorporeal.MODID)
@SuppressWarnings("unused")
public class HackyCorporeaInputHandler extends SacrificialGoat implements ICorporeaAutoCompleteController {
	public HackyCorporeaInputHandler() {
		//The same sideeffecty constructor as the superclass has.
		//Well, that it *did*, until I asm patched it out...
		CorporeaHelper.registerAutoCompleteController(this);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onChatMessage(ServerChatEvent event) {
		//Find a corporea spark.
		List<TileCorporeaIndex> nearbyIndices = TileCorporeaIndex.InputHandler.getNearbyIndexes(event.getPlayer());
		ICorporeaSpark firstSpark = null;
		for(TileCorporeaIndex index : nearbyIndices) {
			ICorporeaSpark spork = index.getSpark();
			if(spork != null) {
				firstSpark = spork;
				break;
			}
		}
		
		if(firstSpark == null) return; //Without calling super; they're not near an index anyway.
		
		List<UUID> soulCoreUUIDs = new ArrayList<>();
		
		//Look for corporea sparks on the same network attacked to corporea soul cores.
		List<InvWithLocation> nearbyInv = CorporeaHelper.getInventoriesOnNetwork(firstSpark);
		for(InvWithLocation inv : nearbyInv) {
			TileEntity tile = event.getPlayer().world.getTileEntity(inv.pos);
			if(tile instanceof TileCorporeaSoulCore) {
				soulCoreUUIDs.add(((TileCorporeaSoulCore) tile).getOwnerUUID());
			}
		}
		
		//If there's none on the network, assume it's already ok to use the network
		if(soulCoreUUIDs.isEmpty()) {
			super.onChatMessage(event);
			return;
		}
		
		//If there are some, their UUID must match at least one in order to use the index
		UUID playerID = event.getPlayer().getUniqueID();
		boolean ok = false;
		for(UUID uuid : soulCoreUUIDs) {
			if(uuid.equals(playerID)) {
				ok = true;
				break;
			}
		}
		
		if(ok) {
			super.onChatMessage(event);
		} else {
			event.getPlayer().sendMessage(new TextComponentTranslation("incorporeal.etc.noSoulCore").setStyle(new Style().setColor(TextFormatting.RED)));
			event.setCanceled(true);
		}
	}
	
	@Override
	public boolean shouldAutoComplete() {
		//This will reroute to the regular inputhandler after the patching
		//I need this so I can still properly implement ICorporeaAutoCompleteController.
		return super.shouldAutoComplete();
	}
}
