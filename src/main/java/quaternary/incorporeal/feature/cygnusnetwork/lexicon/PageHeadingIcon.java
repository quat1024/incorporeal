package quaternary.incorporeal.feature.cygnusnetwork.lexicon;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.common.lexicon.page.PageRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class PageHeadingIcon extends PageRecipe {
	public PageHeadingIcon(ItemStack headingIcon, String descriptionKey) {
		this(headingIcon, null, descriptionKey);
	}
	
	public PageHeadingIcon(ItemStack headingIcon, String headingKey, String descriptionKey) {
		super(descriptionKey);
		this.headingIcon = headingIcon;
		this.headingKey = headingKey;
	}
	
	public final ItemStack headingIcon;
	public final String headingKey;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
		//draw the heading text
		//extra 20px horizontal padding for the icon
		String txt = headingKey == null ? headingIcon.getDisplayName() : headingKey;
		PageText.renderText(gui.getLeft() + 16 + 20, gui.getTop() + 9, gui.getWidth() - 30, gui.getHeight(), txt);
		
		//draw the heading icon using this helper from pagerecipe
		renderItem(gui, gui.getLeft() + 16, gui.getTop() + 16, headingIcon, false);
		
		//draw the caption
		int width = gui.getWidth() - 30;
		int height = gui.getHeight();
		int x = gui.getLeft() + 16;
		int y = gui.getTop() + 25;
		PageText.renderText(x, y, width, height, getUnlocalizedName());
	}
}
