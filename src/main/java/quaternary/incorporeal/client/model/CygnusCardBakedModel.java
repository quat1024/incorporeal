package quaternary.incorporeal.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import quaternary.incorporeal.item.cygnus.ItemCygnusCard;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class CygnusCardBakedModel implements IBakedModel {
	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		return new LinkedList<>();
	}
	
	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}
	
	@Override
	public boolean isGui3d() {
		return true;
	}
	
	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}
	
	@Override
	public TextureAtlasSprite getParticleTexture() {
		return null;
	}
	
	
	
	@Override
	public ItemOverrideList getOverrides() {
		return new ItemOverrideList(ImmutableList.of()) {
			@Override
			public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				if(stack.getItem() instanceof ItemCygnusCard<?>) {
					return CygnusCardModel.cardBakedModels.get(((ItemCygnusCard<?>)stack.getItem()).readValue(stack));
				} else return originalModel;
			}
		};
	}
}
