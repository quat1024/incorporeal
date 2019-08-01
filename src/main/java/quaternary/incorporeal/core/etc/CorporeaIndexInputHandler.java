package quaternary.incorporeal.core.etc;

import com.mojang.authlib.GameProfile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.feature.soulcores.tile.TileCorporeaSoulCore;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.CorporeaIndexRequestEvent;
import vazkii.botania.api.corporea.InvWithLocation;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public final class CorporeaIndexInputHandler {
	private CorporeaIndexInputHandler() {}
	
	@SubscribeEvent
	public static void corporeaInput(CorporeaIndexRequestEvent e) {
		World world = e.requester.world;
		
		//Look for any corporea soul cores on the network
		List<GameProfile> soulCoreProfiles = new ArrayList<>();
		for(InvWithLocation inv : CorporeaHelper.getInventoriesOnNetwork(e.indexSpark)) {
			TileEntity tile = world.getTileEntity(inv.pos);
			if(tile instanceof TileCorporeaSoulCore) {
				TileCorporeaSoulCore csoul = (TileCorporeaSoulCore) tile;
				GameProfile prof = csoul.getOwnerProfile();
				if(prof != null) soulCoreProfiles.add(prof);
			}
		}
		
		//No soul cores on this network? Ok.
		if(soulCoreProfiles.isEmpty()) return;
		
		//Some soul cores on this network? Check for a matching one
		GameProfile playerProfile = e.requester.getGameProfile();
		for(GameProfile profile : soulCoreProfiles) {
			if(profile.equals(playerProfile)) return;
		}
		
		//Couldn't find a matching one? Display an error message and cancel the event
		e.requester.sendMessage(new TextComponentTranslation("incorporeal.etc.noSoulCore").setStyle(new Style().setColor(TextFormatting.RED)));
		e.setCanceled(true);
	}
}
