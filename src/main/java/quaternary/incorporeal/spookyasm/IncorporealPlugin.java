package quaternary.incorporeal.spookyasm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"quaternary.incorporeal.spookyasm"})
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("Incorporeal Tweaker")
public class IncorporealPlugin implements IFMLLoadingPlugin {
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"quaternary.incorporeal.spookyasm.IncorporealTransformer"};
	}
	
	@Override
	public String getModContainerClass() {
		return "quaternary.incorporeal.spookyasm.IncorporealCoreModContainer";
	}
	
	@Nullable
	@Override
	public String getSetupClass() {
		return null;
	}
	
	@Override
	public void injectData(Map<String, Object> data) {
		
	}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
