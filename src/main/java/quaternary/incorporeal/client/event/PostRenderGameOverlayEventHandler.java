package quaternary.incorporeal.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.item.ItemTicketConjurer;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class PostRenderGameOverlayEventHandler {
	private PostRenderGameOverlayEventHandler() {}
	
	@SubscribeEvent
	public static void onDrawScreen(RenderGameOverlayEvent.Post e) {
		if(e.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			Minecraft mc = Minecraft.getMinecraft();
			ItemStack heldMain = mc.player.getHeldItemMainhand();
			ItemStack heldOff = mc.player.getHeldItemOffhand();
			
			if(mc.currentScreen instanceof GuiChat && (heldMain.getItem() instanceof ItemTicketConjurer || heldOff.getItem() instanceof ItemTicketConjurer)) {
				//Based on copy-paste from renderNearIndexDisplay in Botania HUDHandler
				ScaledResolution res = e.getResolution();
				
				//(change) change the text
				String txt0 = I18n.format("incorporeal.etc.holdingTicketConjurer0");
				String txt1 = TextFormatting.GRAY + I18n.format("incorporeal.etc.holdingTicketConjurer1");
				String txt2 = TextFormatting.GRAY + I18n.format("incorporeal.etc.holdingTicketConjurer2");
				
				int l = Math.max(mc.fontRenderer.getStringWidth(txt0), Math.max(mc.fontRenderer.getStringWidth(txt1), mc.fontRenderer.getStringWidth(txt2))) + 20;
				int x = res.getScaledWidth() - l - 20;
				
				//(change) move it up if botania will render theirs
				//TODO would be nice if both of them actually worked :)
				TileCorporeaIndex.getInputHandler();
				int y = res.getScaledHeight() - (TileCorporeaIndex.InputHandler.getNearbyIndexes(mc.player).isEmpty() ? 60 : 110);
				
				Gui.drawRect(x - 6, y - 6, x + l + 6, y + 37, 0x44000000);
				Gui.drawRect(x - 4, y - 4, x + l + 4, y + 35, 0x44000000);
				net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
				GlStateManager.enableRescaleNormal();
				//(change) change the itemstack
				mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(IncorporeticItems.TICKET_CONJURER), x, y + 10);
				net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
				
				mc.fontRenderer.drawStringWithShadow(txt0, x + 20, y, 0xFFFFFF);
				mc.fontRenderer.drawStringWithShadow(txt1, x + 20, y + 14, 0xFFFFFF);
				mc.fontRenderer.drawStringWithShadow(txt2, x + 20, y + 24, 0xFFFFFF);
			}
		}
	}
}
