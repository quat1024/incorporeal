package quaternary.incorporeal.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.MouseHelper;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.IScrollableItem;
import quaternary.incorporeal.net.IncorporeticPacketHandler;
import quaternary.incorporeal.net.MessageScrollItem;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public class ScrollEventHandler {
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
