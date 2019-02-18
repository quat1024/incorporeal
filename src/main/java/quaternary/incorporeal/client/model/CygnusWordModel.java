package quaternary.incorporeal.client.model;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.ISimpleRegistry;
import quaternary.incorporeal.api.cygnus.ICygnusStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class CygnusWordModel implements IModel {
	public CygnusWordModel() {
		initModelsIfNeeded();
	}
	
	private static Set<ResourceLocation> actionResources;
	private static Map<Consumer<ICygnusStack>, IModel> actionModels;
	private static Map<Consumer<ICygnusStack>, IBakedModel> actionBakedModels;
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		initModelsIfNeeded();
		bakeModelsIfNeeded(state, format, bakedTextureGetter);
		
		return new CygnusWordBakedModel(actionBakedModels);
	}
	
	@Override
	public Collection<ResourceLocation> getDependencies() {
		return actionResources;
	}
	
	///
	
	public static void initModelsIfNeeded() {
		if(actionResources == null) {
			actionResources = new HashSet<>();
			actionModels = new HashMap<>();
			
			ISimpleRegistry<Consumer<ICygnusStack>> actions = Incorporeal.API.getCygnusStackActionRegistry();
			
			for(Map.Entry<ResourceLocation, Consumer<ICygnusStack>> entry : actions.backingMap().entrySet()) {
				ResourceLocation actionName = entry.getKey();
				ResourceLocation actionModelLocation = new ResourceLocation(
					actionName.getNamespace(),
					"block/cygnus/word/" + actionName.getPath()
				);
				
				IModel actionModel = ModelLoaderRegistry.getModelOrLogError(actionModelLocation, "Cannot load model for " + actionName + " (location '" + actionModelLocation + "')");
				
				actionModels.put(entry.getValue(), actionModel);
				actionResources.addAll(actionModel.getDependencies());
			}
		}
	}
	
	public static void bakeModelsIfNeeded(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		if(actionBakedModels == null) {
			actionBakedModels = new HashMap<>();
			actionModels.forEach((action, model) -> {
				actionBakedModels.put(action, model.bake(state, format, bakedTextureGetter));
			});
		}
	}
	
	public static void dumpModelCache() {
		actionResources = null;
		actionModels = null;
		actionBakedModels = null;
	}
}
