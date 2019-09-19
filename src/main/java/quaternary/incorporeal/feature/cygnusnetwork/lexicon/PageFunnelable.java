package quaternary.incorporeal.feature.cygnusnetwork.lexicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.common.lexicon.page.PageRecipe;
import vazkii.botania.common.lexicon.page.PageText;

//Basically a copy-paste of PageImage.
//Changed the layout though.
public class PageFunnelable extends PageRecipe {
	public PageFunnelable(ItemStack headingIcon, String resource, String descriptionKey) {
		this(headingIcon, null, resource, descriptionKey);
	}
	
	public PageFunnelable(ItemStack headingIcon, String headingKey, String resource, String descriptionKey) {
		super(descriptionKey);
		this.headingIcon = headingIcon;
		this.headingKey = headingKey;
		
		if(resource != null) this.resource = new ResourceLocation(resource);
		else this.resource = null;
	}
	
	public final ItemStack headingIcon;
	public final String headingKey;
	public final ResourceLocation resource;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
		//draw the heading text
		//extra 20px horizontal padding for the icon
		String txt = headingKey == null ? headingIcon.getDisplayName() : headingKey;
		PageText.renderText(gui.getLeft() + 16 + 20, gui.getTop() + 9, gui.getWidth() - 30, gui.getHeight(), txt);
		
		//draw the heading icon using this helper from pagerecipe
		renderItem(gui, gui.getLeft() + 16, gui.getTop() + 16, headingIcon, false);
		
		if(resource != null) {
			//draw the image
			TextureManager render = Minecraft.getMinecraft().renderEngine;
			render.bindTexture(resource);
			
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(1F, 1F, 1F, 1F);
			//move it down compared to original
			((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop() + 40, 0, 0, gui.getWidth(), gui.getHeight());
			GlStateManager.disableBlend();
		}
		
		//draw the caption
		int width = gui.getWidth() - 30;
		int height = gui.getHeight();
		int x = gui.getLeft() + 16;
		int y = gui.getTop() + height - (resource == null ? 160 : 80); //move it up compared to original
		PageText.renderText(x, y, width, height, getUnlocalizedName());
	}
}
