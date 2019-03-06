package quaternary.incorporeal.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import quaternary.incorporeal.recipe.skytouch.RecipeSkytouching;

public class SkytouchRecipeWrapper implements IRecipeWrapper {
	public SkytouchRecipeWrapper(RecipeSkytouching recipe) {
		this.recipe = recipe;
	}
	
	public final RecipeSkytouching recipe;
	
	@Override
	public void getIngredients(IIngredients ing) {
		ing.setInput(VanillaTypes.ITEM, recipe.in);
		ing.setOutput(VanillaTypes.ITEM, recipe.out);
	}
	
	@Override
	public void drawInfo(Minecraft mc, int width, int height, int mouseX, int mouseY) {
		drawCenteredString(mc.fontRenderer, I18n.format("incorporeal.jei.skytouch.heightlimit"), width / 2, height / 3 + 2, 0xa7a7a7);
	}
	
	private static void drawCenteredString(FontRenderer font, String s, int x, int y, int color) {
		font.drawString(s, x - font.getStringWidth(s) / 2, y, color);
	}
}
