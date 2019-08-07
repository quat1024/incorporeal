package quaternary.incorporeal.feature.soulcores;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.IncorporeticConfig;
import quaternary.incorporeal.api.feature.IClientFeatureTwin;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.core.client.ClientHelpers;
import quaternary.incorporeal.core.client.entityrenderer.RenderEntityNothing;
import quaternary.incorporeal.core.client.event.BlockHighlightEventHandler;
import quaternary.incorporeal.feature.decorative.block.DecorativeBlocks;
import quaternary.incorporeal.feature.decorative.client.tesr.RenderItemSoulCore;
import quaternary.incorporeal.feature.soulcores.block.SoulCoresBlocks;
import quaternary.incorporeal.feature.soulcores.client.tesr.RenderTileSoulCore;
import quaternary.incorporeal.feature.soulcores.entity.EntityPotionSoulCoreCollector;
import quaternary.incorporeal.feature.soulcores.entity.SoulCoresEntities;
import quaternary.incorporeal.feature.soulcores.item.SoulCoresItems;
import quaternary.incorporeal.feature.soulcores.lexicon.SoulCoresLexicon;
import quaternary.incorporeal.feature.soulcores.recipe.SoulCoresRuneRecipes;
import quaternary.incorporeal.feature.soulcores.sound.SoulCoresSounds;
import quaternary.incorporeal.feature.soulcores.tile.SoulCoresTiles;
import quaternary.incorporeal.feature.soulcores.tile.TileCorporeaSoulCore;
import quaternary.incorporeal.feature.soulcores.tile.TileEnderSoulCore;
import quaternary.incorporeal.feature.soulcores.tile.TilePotionSoulCore;

public class SoulCoresFeature implements IFeature {
	@Override
	public String name() {
		return "soulCore";
	}
	
	@Override
	public String description() {
		return "Haunted blocks that are out for your soul...";
	}
	
	@Override
	public void blocks(IForgeRegistry<Block> blocks) {
		SoulCoresBlocks.register(blocks);
	}
	
	@Override
	public void items(IForgeRegistry<Item> items) {
		SoulCoresItems.register(items);
	}
	
	@Override
	public void entities(IForgeRegistry<EntityEntry> entities) {
		SoulCoresEntities.register(entities);
	}
	
	@Override
	public void tiles() {
		SoulCoresTiles.register();
	}
	
	@Override
	public void sounds(IForgeRegistry<SoundEvent> sounds) {
		SoulCoresSounds.register(sounds);
	}
	
	@Override
	public void runeRecipes() {
		SoulCoresRuneRecipes.register();
	}
	
	@Override
	public void lexicon() {
		SoulCoresLexicon.register();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IClientFeatureTwin client() {
		return new IClientFeatureTwin() {
			@Override
			public void preinit() {
				if(!IncorporeticConfig.SoulCore.DEBUG_BLOODCORE_ENTITIES) {
					RenderingRegistry.registerEntityRenderingHandler(EntityPotionSoulCoreCollector.class, RenderEntityNothing::new);
				}
				
				BlockHighlightEventHandler.ensureRegistered();
			}
			
			@Override
			public void statemappers() {
				ClientHelpers.setIgnoreAllStateMapper(SoulCoresBlocks.ENDER_SOUL_CORE);
				ClientHelpers.setIgnoreAllStateMapper(SoulCoresBlocks.CORPOREA_SOUL_CORE);
				ClientHelpers.setIgnoreAllStateMapper(DecorativeBlocks.UNSTABLE_CUBE);
			}
			
			@Override
			public void tesrs() {
				RenderTileSoulCore<TileEnderSoulCore> enderRender = new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/entity/ender_soul_core.png"));
				RenderTileSoulCore<TileCorporeaSoulCore> corporeaRender = new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/entity/corporea_soul_core.png"));
				RenderTileSoulCore<TilePotionSoulCore> potionRender = new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/entity/potion_soul_core.png"));
				
				ClientRegistry.bindTileEntitySpecialRenderer(TileEnderSoulCore.class, enderRender);
				ClientRegistry.bindTileEntitySpecialRenderer(TileCorporeaSoulCore.class, corporeaRender);
				ClientRegistry.bindTileEntitySpecialRenderer(TilePotionSoulCore.class, potionRender);
				
				//And teirs.
				ClientHelpers.setTEISRModel(SoulCoresItems.ENDER_SOUL_CORE, new RenderItemSoulCore(enderRender));
				ClientHelpers.setTEISRModel(SoulCoresItems.CORPOREA_SOUL_CORE, new RenderItemSoulCore(corporeaRender));
				ClientHelpers.setTEISRModel(SoulCoresItems.POTION_SOUL_CORE, new RenderItemSoulCore(potionRender));
				ClientHelpers.setTEISRModel(SoulCoresItems.SOUL_CORE_FRAME, new RenderItemSoulCore(new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/entity/soul_core_frame.png"))));
			}
		};
	}
}
