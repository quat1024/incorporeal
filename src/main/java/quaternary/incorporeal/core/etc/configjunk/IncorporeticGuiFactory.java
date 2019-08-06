package quaternary.incorporeal.core.etc.configjunk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.IncorporeticConfig;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("SameReturnValue")
public class IncorporeticGuiFactory implements IModGuiFactory {
	@Override
	public void initialize(Minecraft minecraftInstance) {
		//No-op
	}
	
	@Override
	public boolean hasConfigGui() {
		return true;
	}
	
	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		GuiScreen parent = parentScreen;
		return new GuiConfig(parent, getConfigElements(), Incorporeal.MODID, false, false, Incorporeal.NAME + " Config!");
	}
	
	//Adapted from Choonster's TestMod3. They say they adapted it from EnderIO "a while back".
	//http://www.minecraftforge.net/forum/topic/39880-110solved-make-config-options-show-up-in-gui/
	private static List<IConfigElement> getConfigElements() {
		Configuration c = IncorporeticConfig.config;
		//Don't look!
		return c.getCategoryNames().stream().filter(name -> !c.getCategory(name).isChild()).map(name -> new ConfigElement(c.getCategory(name).setLanguageKey(Incorporeal.MODID + ".config." + name))).collect(Collectors.toList());
	}
	
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
}
