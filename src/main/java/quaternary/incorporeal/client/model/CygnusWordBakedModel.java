package quaternary.incorporeal.client.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.common.property.IExtendedBlockState;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.block.cygnus.BlockCygnusWord;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CygnusWordBakedModel extends BakedModelWrapper<IBakedModel> {
	public CygnusWordBakedModel(Map<Consumer<ICygnusStack>, IBakedModel> actionBakedModels) {
		//Pass one of these models to super
		//bakedmodelwrapper takes care of e.g. particle textures and whatever
		//I can assume they're all the same, i don't really care
		super(actionBakedModels.values().iterator().next());
		
		this.actionBakedModels = actionBakedModels;
	}
	
	private final Map<Consumer<ICygnusStack>, IBakedModel> actionBakedModels;
	
	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if(state instanceof IExtendedBlockState) {
			IExtendedBlockState ext = (IExtendedBlockState) state;
			
			Consumer<ICygnusStack> action = ext.getValue(BlockCygnusWord.UNLISTED_ACTION);
			IBakedModel actionBakedModel = actionBakedModels.get(action);
			if(actionBakedModel != null) {
				return actionBakedModel.getQuads(state, side, rand);
			}
		}
		
		return new LinkedList<>();
	}
}
