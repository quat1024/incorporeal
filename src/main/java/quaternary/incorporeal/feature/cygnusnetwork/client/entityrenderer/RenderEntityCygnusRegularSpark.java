package quaternary.incorporeal.feature.cygnusnetwork.client.entityrenderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import quaternary.incorporeal.feature.cygnusnetwork.client.event.CorporeticIcons;
import quaternary.incorporeal.feature.cygnusnetwork.entity.EntityCygnusRegularSpark;
import vazkii.botania.client.core.handler.MiscellaneousIcons;
import vazkii.botania.client.render.entity.RenderSparkBase;

public class RenderEntityCygnusRegularSpark extends RenderSparkBase<EntityCygnusRegularSpark> {
	public RenderEntityCygnusRegularSpark(RenderManager manager) {
		super(manager);
	}
	
	@Override
	protected TextureAtlasSprite getBaseIcon(EntityCygnusRegularSpark entity) {
		return CorporeticIcons.cygnusSpark;
	}
	
	@Override
	protected TextureAtlasSprite getSpinningIcon(EntityCygnusRegularSpark entity) {
		return MiscellaneousIcons.INSTANCE.corporeaIconStar;
	}
	
	@Override
	protected void colorSpinningIcon(EntityCygnusRegularSpark entity, float a) {
		int rgb = entity.getTint().colorValue;
		float r = ((rgb >> 16) & 0xFF) / 255f;
		float g = ((rgb >> 8) & 0xFF) / 255f;
		float b = (rgb & 0xFF) / 255f;
		GlStateManager.color(r, g, b, a);
	}
}
