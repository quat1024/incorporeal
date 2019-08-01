package quaternary.incorporeal.feature.compat.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;

public class SkytouchRecipeCategory implements IRecipeCategory<SkytouchRecipeWrapper> {
	public SkytouchRecipeCategory(IJeiHelpers helpers) {
		//this.helpers = helpers;
		
		bg = helpers.getGuiHelper().createDrawable(new ResourceLocation(Incorporeal.MODID, "textures/jei/skytouching.png"), 0, 0, WIDTH, HEIGHT);
	}
	
	public static final String UID = "incorporeal.skytouch";
	
	private static final int WIDTH = 166;
	private static final int HEIGHT = 65;
	
	//private final IJeiHelpers helpers;
	private final IDrawable bg;
	
	@Override
	public String getUid() {
		return UID;
	}
	
	@Override
	public String getTitle() {
		return I18n.format("incorporeal.jei.category.skytouch");
	}
	
	@Override
	public String getModName() {
		return Incorporeal.NAME;
	}
	
	@Override
	public IDrawable getBackground() {
		return bg;
	}
	
	@Override
	public void setRecipe(IRecipeLayout layout, SkytouchRecipeWrapper w, IIngredients ing) {
		IGuiItemStackGroup g = layout.getItemStacks();
		
		final int ITEM_WIDTH = 18;
		
		g.init(0, true, 0, 2 * HEIGHT / 3);
		g.set(0, ing.getInputs(VanillaTypes.ITEM).get(0));
		
		g.init(1, false, WIDTH - ITEM_WIDTH - 1, 2 * HEIGHT / 3);
		g.set(1, ing.getOutputs(VanillaTypes.ITEM).get(0));
	}
}
