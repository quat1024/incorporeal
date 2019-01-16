package quaternary.incorporeal.client.model;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import quaternary.incorporeal.Incorporeal;

public class MashedModelLoader implements ICustomModelLoader {
	private static final String MASHED_PATH = "magic/mashed_model";
	
	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		if(!modelLocation.getNamespace().equals(Incorporeal.MODID)) return false;
		else return modelLocation.getPath().endsWith(MASHED_PATH);
	}
	
	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return new MashedModel();
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		
	}
}
