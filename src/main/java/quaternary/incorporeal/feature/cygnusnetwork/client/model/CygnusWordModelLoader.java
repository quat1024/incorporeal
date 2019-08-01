package quaternary.incorporeal.feature.cygnusnetwork.client.model;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import quaternary.incorporeal.Incorporeal;

public class CygnusWordModelLoader implements ICustomModelLoader {
	private static final String WORD_PATH = "magic/redirecting_cygnus_word";
	
	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		if(!modelLocation.getNamespace().equals(Incorporeal.MODID)) return false;
		else return modelLocation.getPath().endsWith(WORD_PATH);
	}
	
	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return new CygnusWordModel();
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		CygnusWordModel.dumpModelCache();
	}
}
