package quaternary.incorporeal.client.model;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.cygnus.CygnusRegistries;
import quaternary.incorporeal.cygnus.CygnusStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class CygnusCardModel implements IModel {
	public static ResourceLocation FRAME_LOCATION = new ResourceLocation(Incorporeal.MODID, "item/special/cygnus_word_card_base");
	
	static Map<Object, IBakedModel> cardBakedModels = new HashMap<>();
	static Collection<ResourceLocation> cardTextures = new ArrayList<>();
	static IModel frameModel;
	
	static void dumpCache() {
		frameModel = null;
		cardBakedModels.clear();
	}
	
	private static void initModelsIfNeeded() {
		if(frameModel == null) {
			frameModel = ModelLoaderRegistry.getModelOrMissing(FRAME_LOCATION);
		}
	}
	
	private static void bakeModelsIfNeeded(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		if(cardBakedModels.isEmpty()) {
			for(Consumer<ICygnusStack> action : CygnusRegistries.ACTIONS.allValues()) {
				ResourceLocation actionName = CygnusRegistries.ACTIONS.nameOf(action);
				
				ResourceLocation actionTexture = new ResourceLocation(
					actionName.getNamespace(),
					"block/cygnus/word/" + actionName.getPath()
				);
				
				cardTextures.add(actionTexture);
				
				frameModel.retexture(ImmutableMap.of("center", actionTexture.toString()));
				cardBakedModels.put(action, frameModel.bake(state, format, bakedTextureGetter));
			}
			
			for(Predicate<ICygnusStack> condition : CygnusRegistries.CONDITIONS.allValues()) {
				ResourceLocation actionName = CygnusRegistries.CONDITIONS.nameOf(condition);
				
				ResourceLocation actionTexture = new ResourceLocation(
					actionName.getNamespace(),
					"item/cygnus/condition/" + actionName.getPath()
				);
				
				cardTextures.add(actionTexture);
				
				frameModel = ModelLoaderRegistry.getModelOrMissing(FRAME_LOCATION);
				frameModel.retexture(ImmutableMap.of("center", actionTexture.toString()));
				
				cardBakedModels.put(condition, frameModel.bake(state, format, bakedTextureGetter));
			}
		}
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		bakeModelsIfNeeded(state, format, bakedTextureGetter);
		
		return new CygnusCardBakedModel();
	}
	
	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of(FRAME_LOCATION);
	}
}
