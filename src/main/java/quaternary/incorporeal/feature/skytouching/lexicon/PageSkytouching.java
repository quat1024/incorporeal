package quaternary.incorporeal.feature.skytouching.lexicon;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.common.lexicon.page.PageRecipe;

public class PageSkytouching extends PageRecipe {
	public PageSkytouching(String translationKey, IRecipeSkytouching recipe) {
		super(translationKey);
		this.recipe = recipe;
	}
	
	public final IRecipeSkytouching recipe;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderRecipe(IGuiLexiconEntry gui, int mx, int my) {
		
	}
}
