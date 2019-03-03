package quaternary.incorporeal.client.event;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Incorporeal.MODID)
//hey oshi do a t spin triple
public class BigThinkNoisesHmmmmmmmmmmmmMmMmMmMmMm {
	@SubscribeEvent
	public static void tooltip(ItemTooltipEvent e) {
		ResourceLocation xd = e.getItemStack().getItem().getRegistryName();
		if(xd.getNamespace().equals(Incorporeal.MODID) && xd.getPath().contains("cygnus")) {
			e.getToolTip().add(TextFormatting.DARK_RED + I18n.format("incorporeal.cygnus.wip1"));
			e.getToolTip().add(TextFormatting.DARK_GRAY + I18n.format("incorporeal.cygnus.wip2"));
		}
	}
}
