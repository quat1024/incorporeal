package quaternary.incorporeal.feature.cygnusnetwork.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.api.IScrollableItem;
import quaternary.incorporeal.core.IncorporeticPacketHandler;
import quaternary.incorporeal.feature.cygnusnetwork.net.MessageScrollItem;

public final class ScrollEventHandler {
	private ScrollEventHandler() {
	}
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(ScrollEventHandler.class);
	}
	
	@SubscribeEvent
	public static void mouseInput(MouseEvent e) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		if(player.isSneaking() && e.getDwheel() != 0) {
			Item selectedItem = player.inventory.getCurrentItem().getItem();
			if(selectedItem instanceof IScrollableItem) {
				IncorporeticPacketHandler.sendToServer(new MessageScrollItem(e.getDwheel()));
				e.setCanceled(true);
			}
		}
	}
}
