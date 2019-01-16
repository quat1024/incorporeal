package quaternary.incorporeal.client.model;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MashedModel implements IModel {
	//TODO this is the big garbage code fix it lol
	
	public MashedModel() {}
	
	public MashedModel(List<IModel> otherModels) {
		this.otherModels = otherModels;
	}
	
	private List<IModel> otherModels = null;
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		if(otherModels == null) {
			return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getMissingModel();
		} else {
			List<IBakedModel> bakeybakey = new ArrayList<>(otherModels.size());
			otherModels.forEach(m -> {
				bakeybakey.add(m.bake(state, format, bakedTextureGetter));
			});
			
			return new MashedBakedModel(bakeybakey);
		}
	}
	
	@Override
	public Collection<ResourceLocation> getDependencies() {
		List<ResourceLocation> tex = new LinkedList<>();
		if(otherModels != null)	otherModels.forEach(m -> tex.addAll(m.getDependencies()));
		return tex;
	}
	
	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		LinkedList<IModel> otherModels_ = new LinkedList<>();
		
		String primaryPath = customData.get("main");
		if(primaryPath != null) addModel(otherModels_, primaryPath);
		
		for(Map.Entry<String, String> entry : customData.entrySet()) {
			if(entry.getKey().equals("main")) continue;
			addModel(otherModels_, entry.getValue());
		}
		
		return new MashedModel(otherModels_);
	}
	
	private void addModel(List<IModel> otherModels, String path) {
		ResourceLocation lol = new ResourceLocation(path.replaceAll("\"", ""));
		otherModels.add(ModelLoaderRegistry.getModelOrLogError(lol, "[mashedmodel] can't find " + lol.toString()));
	}
}
