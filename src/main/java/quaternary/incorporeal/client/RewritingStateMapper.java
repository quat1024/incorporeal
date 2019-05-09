package quaternary.incorporeal.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;

public class RewritingStateMapper extends StateMapperBase {
	public RewritingStateMapper(Block b) {
		res = b.getRegistryName();
	}
	
	private final ResourceLocation res;
	
	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return new ModelResourceLocation(res, getPropertyString(state.getProperties()));
	}
}
