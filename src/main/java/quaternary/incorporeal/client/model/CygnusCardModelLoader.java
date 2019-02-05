package quaternary.incorporeal.client.model;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import quaternary.incorporeal.Incorporeal;

public class CygnusCardModelLoader implements ICustomModelLoader {
	ResourceLocation yea = new ResourceLocation(Incorporeal.MODID, "magic/cygnus_card_glue");
	
	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getNamespace().equals(yea.getNamespace()) && modelLocation.getPath().endsWith(yea.getPath());
	}
	
	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return new CygnusCardModel();
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		CygnusCardModel.dumpCache();
	}
}
