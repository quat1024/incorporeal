package quaternary.incorporeal.client.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.LinkedList;
import java.util.List;

public class MashedBakedModel implements IBakedModel {
	public MashedBakedModel(List<IBakedModel> otherModels) {
		this.otherModels = otherModels.toArray(new IBakedModel[0]);
	}
	
	private final IBakedModel[] otherModels;
	
	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		List<BakedQuad> quads = new LinkedList<>();
		for(IBakedModel otherModel : otherModels) {
			quads.addAll(otherModel.getQuads(state, side, rand));
		}
		return quads;
	}
	
	@Override
	public boolean isAmbientOcclusion() {
		return otherModels[0].isAmbientOcclusion();
	}
	
	@Override
	public boolean isGui3d() {
		return otherModels[0].isGui3d();
	}
	
	@Override
	public boolean isBuiltInRenderer() {
		return otherModels[0].isBuiltInRenderer();
	}
	
	@Override
	public TextureAtlasSprite getParticleTexture() {
		return otherModels[0].getParticleTexture();
	}
	
	@Override
	public ItemOverrideList getOverrides() {
		return otherModels[0].getOverrides();
	}
	
	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return otherModels[0].getItemCameraTransforms();
	}
	
	@Override
	public boolean isAmbientOcclusion(IBlockState state) {
		return otherModels[0].isAmbientOcclusion(state);
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		return Pair.of(this, otherModels[0].handlePerspective(cameraTransformType).getRight());
	}
}
