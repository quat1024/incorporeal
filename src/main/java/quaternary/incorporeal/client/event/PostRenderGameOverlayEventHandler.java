package quaternary.incorporeal.client.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.cygnus.CygnusDatatypeHelpers;
import quaternary.incorporeal.cygnus.CygnusRegistries;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.item.ItemTicketConjurer;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;
import quaternary.incorporeal.tile.cygnus.TileCygnusCrystalCube;
import quaternary.incorporeal.tile.cygnus.TileCygnusRetainer;
import quaternary.incorporeal.tile.cygnus.TileCygnusWord;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class PostRenderGameOverlayEventHandler {
	private PostRenderGameOverlayEventHandler() {}
	
	@SubscribeEvent
	public static void onDrawScreen(RenderGameOverlayEvent.Post e) {
		if(e.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			Minecraft mc = Minecraft.getMinecraft();
			ItemStack heldMain = mc.player.getHeldItemMainhand();
			ItemStack heldOff = mc.player.getHeldItemOffhand();
			ScaledResolution res = e.getResolution();
			
			if(mc.currentScreen instanceof GuiChat && (heldMain.getItem() instanceof ItemTicketConjurer || heldOff.getItem() instanceof ItemTicketConjurer)) {
				drawConjurerOverlay(mc, res);
			}
			
			RayTraceResult trace = mc.objectMouseOver;
			if(trace != null) {
				BlockPos hitPos = null;
				IBlockState hitState = null;
				TileEntity hitTile = null;
				if(trace.typeOfHit == RayTraceResult.Type.BLOCK) {
					hitPos = trace.getBlockPos();
					hitState = mc.world.getBlockState(hitPos);
					hitTile = mc.world.getTileEntity(hitPos);
				}
				
				if(hitState != null) {
					if(hitTile instanceof TileCygnusCrystalCube) {
						drawCygnusCrystalCubeOverlay(res, (TileCygnusCrystalCube) hitTile);
					} else if(hitTile instanceof TileCygnusRetainer) {
						drawCygnusRetainerOverlay(res, (TileCygnusRetainer) hitTile);
					} else if(hitTile instanceof TileCygnusWord) {
						drawCygnusWordOverlay(res, (TileCygnusWord) hitTile);
					}
				}
			}
		}
	}
	
	private static void drawConjurerOverlay(Minecraft mc, ScaledResolution res) {
		//Based on copy-paste from renderNearIndexDisplay in Botania HUDHandler
		//(change) change the text
		String txt0 = I18n.format("incorporeal.etc.holdingTicketConjurer0");
		String txt1 = TextFormatting.GRAY + I18n.format("incorporeal.etc.holdingTicketConjurer1");
		String txt2 = TextFormatting.GRAY + I18n.format("incorporeal.etc.holdingTicketConjurer2");
		
		int l = Math.max(mc.fontRenderer.getStringWidth(txt0), Math.max(mc.fontRenderer.getStringWidth(txt1), mc.fontRenderer.getStringWidth(txt2))) + 20;
		int x = res.getScaledWidth() - l - 20;
		
		//(change) move it up if botania will render theirs
		//TODO would be nice if both of them actually worked together :)
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
	
	private static ItemStack cygnusCrystalCubeCardStack = new ItemStack(IncorporeticCygnusItems.CRYSTAL_CUBE_CARD);
	
	private static void drawCygnusCrystalCubeOverlay(ScaledResolution res, TileCygnusCrystalCube tile) {
		//copy paste from HudHander of course
		Minecraft mc = Minecraft.getMinecraft();
		Profiler profiler = mc.profiler;
		profiler.startSection("cygnusCrystalCubeOverlay");
		
		IncorporeticCygnusItems.CRYSTAL_CUBE_CARD.set(cygnusCrystalCubeCardStack, tile.getCondition());
		ResourceLocation cond = CygnusRegistries.CONDITIONS.nameOf(tile.getCondition());
		//i need to standardize this
		String name = I18n.format(cond.getNamespace() + ".cygnus.condition." + cond.getPath());
		
		int strlen = mc.fontRenderer.getStringWidth(name);
		int w = res.getScaledWidth();
		int h = res.getScaledHeight();
		Gui.drawRect(w / 2 + 8, h / 2 - 12, w / 2 + strlen + 32, h / 2 + 10, 0x44000000);
		Gui.drawRect(w / 2 + 6, h / 2 - 14, w / 2 + strlen + 34, h / 2 + 12, 0x44000000);
		mc.fontRenderer.drawStringWithShadow(name, (float) (w / 2 + 30), (float) (h / 2 - 6), 0x85c6cc);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableRescaleNormal();
		mc.getRenderItem().renderItemAndEffectIntoGUI(cygnusCrystalCubeCardStack, w / 2 + 10, h / 2 - 10);
		RenderHelper.disableStandardItemLighting();
		
		profiler.endSection();
	}
	
	private static void drawCygnusRetainerOverlay(ScaledResolution res, TileCygnusRetainer tile) {
		Minecraft mc = Minecraft.getMinecraft();
		Profiler profiler = mc.profiler;
		profiler.startSection("cygnusRetainerOverlay");
		
		List<String> lines = new ArrayList<>();
		
		if(tile.hasRetainedObject()) {
			Object o = tile.getRetainedObject();
			ICygnusDatatype<?> type = CygnusDatatypeHelpers.forClass(o.getClass());
			String typeName = I18n.format(type.getTranslationKey());
			
			lines.add(TextFormatting.GREEN + net.minecraft.util.text.translation.I18n.translateToLocalFormatted(
				EtcHelpers.vowelizeTranslationKey("incorporeal.cygnus.retainer.some", typeName),
				typeName
			));
			
			lines.addAll(type.describeUnchecked(o));
		} else {
			lines.add(TextFormatting.RED + I18n.format("incorporeal.cygnus.retainer.none"));
		}
		
		//find box w/h
		int boxWidth = 0;
		for(String s : lines) {
			int swidth = mc.fontRenderer.getStringWidth(s);
			if(swidth > boxWidth) boxWidth = swidth;
		}
		
		//Take away 1 since the box starts a little above the crosshair
		//Yeah idk it works don't @ me
		int boxHeight = (lines.size() - 1) * 12;
		
		//draw box
		int w = res.getScaledWidth();
		int h = res.getScaledHeight();
		//Magic Numbers. Do not touch
		Gui.drawRect(w / 2 + 8, h / 2 - 12, w / 2 + boxWidth + 12, h / 2 + boxHeight, 0x44000000);
		Gui.drawRect(w / 2 + 6, h / 2 - 14, w / 2 + boxWidth + 14, h / 2 + boxHeight + 2, 0x44000000);
		
		//draw text
		int y = -10;
		for(String s : lines) {
			mc.fontRenderer.drawStringWithShadow(s, w / 2 + 10, h / 2 + y, 0xFFFFFF);
			y += 12;
		}
		
		profiler.endSection();
	}
	
	private static final ItemStack cygnusWordCardStack = new ItemStack(IncorporeticCygnusItems.WORD_CARD);
	
	private static void drawCygnusWordOverlay(ScaledResolution res, TileCygnusWord tile) {
		//copy paste from HudHander of course
		Minecraft mc = Minecraft.getMinecraft();
		Profiler profiler = mc.profiler;
		profiler.startSection("cygnusWordOverlay");
		
		IncorporeticCygnusItems.WORD_CARD.set(cygnusWordCardStack, tile.getAction());
		ResourceLocation cond = CygnusRegistries.ACTIONS.nameOf(tile.getAction());
		//i need to standardize this
		String name = I18n.format(cond.getNamespace() + ".cygnus.action." + cond.getPath());
		
		int strlen = mc.fontRenderer.getStringWidth(name);
		int w = res.getScaledWidth();
		int h = res.getScaledHeight();
		Gui.drawRect(w / 2 + 8, h / 2 - 12, w / 2 + strlen + 32, h / 2 + 10, 0x44000000);
		Gui.drawRect(w / 2 + 6, h / 2 - 14, w / 2 + strlen + 34, h / 2 + 12, 0x44000000);
		mc.fontRenderer.drawStringWithShadow(name, (float) (w / 2 + 30), (float) (h / 2 - 6), 0x6fcc8e);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableRescaleNormal();
		mc.getRenderItem().renderItemAndEffectIntoGUI(cygnusWordCardStack, w / 2 + 10, h / 2 - 10);
		RenderHelper.disableStandardItemLighting();
		
		profiler.endSection();
	}
}
