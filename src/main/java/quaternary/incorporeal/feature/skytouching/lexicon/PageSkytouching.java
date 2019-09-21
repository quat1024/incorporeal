package quaternary.incorporeal.feature.skytouching.lexicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.common.lexicon.page.PageRecipe;

public class PageSkytouching extends PageRecipe {
	public PageSkytouching(String translationKey, IRecipeSkytouching recipe) {
		super(translationKey);
		this.recipe = recipe;
	}
	
	public final IRecipeSkytouching recipe;
	
	private static final ResourceLocation SKYTOUCHING_BASE = new ResourceLocation(Incorporeal.MODID, "textures/lexicon/pages/skytouching.png");
	private int ticks = 0;
	private int inputIndex = 0;
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateScreen() {
		//pasted from PageCraftingRecipe, basically
		if(GuiScreen.isShiftKeyDown()) return;
		ticks++;
		if(ticks % 20 == 0) {
			inputIndex++;
			if(inputIndex >= recipe.getGenericInputs().size()) inputIndex = 0;
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderRecipe(IGuiLexiconEntry gui, int mx, int my) {
		int yOffset = 98;
		
		ItemStack input = recipe.getGenericInputs().get(inputIndex);
		renderItem(gui, gui.getLeft() + 23, gui.getTop() + yOffset, input, true);
		
		ItemStack output = recipe.getGenericOutput();
		renderItem(gui, gui.getLeft() + gui.getWidth() - 39, gui.getTop() + yOffset, output, false);
		
		//Pasted from PageCraftingRecipe
		Minecraft.getMinecraft().renderEngine.bindTexture(SKYTOUCHING_BASE);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight());
	}
}
