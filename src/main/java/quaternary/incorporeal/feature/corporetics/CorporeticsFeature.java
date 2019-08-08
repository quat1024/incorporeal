package quaternary.incorporeal.feature.corporetics;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.api.feature.IClientFeatureTwin;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.core.client.ClientHelpers;
import quaternary.incorporeal.core.client.entityrenderer.RenderEntityNothing;
import quaternary.incorporeal.core.client.event.PostRenderGameOverlayEventHandler;
import quaternary.incorporeal.feature.corporetics.block.BlockCorporeaInhibitor;
import quaternary.incorporeal.feature.corporetics.block.BlockFrameScrew;
import quaternary.incorporeal.feature.corporetics.block.CorporeticsBlocks;
import quaternary.incorporeal.feature.corporetics.client.tesr.RenderTileCorporeaSparkTinkerer;
import quaternary.incorporeal.feature.corporetics.entity.CorporeticsEntities;
import quaternary.incorporeal.feature.corporetics.entity.EntityFracturedSpaceCollector;
import quaternary.incorporeal.feature.corporetics.event.CorporeaIndexInputHandler;
import quaternary.incorporeal.feature.corporetics.flower.CorporeticFlowers;
import quaternary.incorporeal.feature.corporetics.flower.SubTileSanvocalia;
import quaternary.incorporeal.feature.corporetics.flower.SubTileSweetAlexum;
import quaternary.incorporeal.feature.corporetics.item.CorporeticsItems;
import quaternary.incorporeal.feature.corporetics.item.ItemTicketConjurer;
import quaternary.incorporeal.feature.corporetics.lexicon.CorporeticsLexicon;
import quaternary.incorporeal.feature.corporetics.recipe.CorporeticsPetalRecipes;
import quaternary.incorporeal.feature.corporetics.tile.CorporeticsTiles;
import quaternary.incorporeal.feature.corporetics.tile.TileCorporeaSparkTinkerer;

public class CorporeticsFeature implements IFeature {
	@Override
	public String name() {
		return "corporetics";
	}
	
	@Override
	public String description() {
		return "The appetizer, and the main course. All sorts of wacky corporea-related doodads and trinkets.";
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		//per-block and item events... maybe break these out into a dedi event class? :thinking:
		ItemTicketConjurer.registerChatEvent();
		BlockCorporeaInhibitor.registerTickEvent();
		CorporeaIndexInputHandler.register();
	}
	
	@Override
	public void petalRecipes() {
		CorporeticsPetalRecipes.register();
	}
	
	@Override
	public void lexicon() {
		CorporeticsLexicon.register();
	}
	
	@Override
	public void blocks(IForgeRegistry<Block> reg) {
		CorporeticsBlocks.register(reg);
	}
	
	@Override
	public void items(IForgeRegistry<Item> reg) {
		CorporeticsItems.register(reg);
		
		//I think this is a good spot to register flowers...
		CorporeticFlowers.register();
	}
	
	@Override
	public void entities(IForgeRegistry<EntityEntry> entities) {
		CorporeticsEntities.register(entities);
	}
	
	@Override
	public void tiles() {
		CorporeticsTiles.register();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IClientFeatureTwin client() {
		return new IClientFeatureTwin() {
			@Override
			public void preinit() {
				RenderingRegistry.registerEntityRenderingHandler(EntityFracturedSpaceCollector.class, RenderEntityNothing::new);
				
				PostRenderGameOverlayEventHandler.ensureRegistered();
			}
			
			@Override
			public void models() {
				//Basic stuff
				ClientHelpers.setSimpleModel(CorporeticsItems.FRAME_TINKERER);
				ClientHelpers.setSimpleModel(CorporeticsItems.FRAME_SCREW);
				ClientHelpers.setSimpleModel(CorporeticsItems.CORPOREA_INHIBITOR);
				ClientHelpers.setSimpleModel(CorporeticsItems.CORPOREA_SPARK_TINKERER);
				ClientHelpers.setSimpleModel(CorporeticsItems.CORPOREA_SOLIDIFIER);
				ClientHelpers.setSimpleModel(CorporeticsItems.CORPOREA_RETAINER_DECREMENTER);
				
				ClientHelpers.setSimpleModel(CorporeticsItems.CORPOREA_TICKET);
				ClientHelpers.setSimpleModel(CorporeticsItems.TICKET_CONJURER);
				ClientHelpers.setSimpleModel(CorporeticsItems.FRACTURED_SPACE_ROD);
				
				//Flowers
				ClientHelpers.setFlowerModel(SubTileSanvocalia.class, "sanvocalia");
				ClientHelpers.setFlowerModel(SubTileSanvocalia.Mini.class, "sanvocalia_chibi");
				ClientHelpers.setFlowerModel(SubTileSweetAlexum.class, "sweet_alexum");
				ClientHelpers.setFlowerModel(SubTileSweetAlexum.Mini.class, "sweet_alexum_chibi");
			}
			
			@Override
			public void statemappers() {
				ClientHelpers.setIgnoreAllStateMapper(CorporeticsBlocks.FRAME_TINKERER);
				ClientHelpers.setIgnoreAllStateMapper(CorporeticsBlocks.CORPOREA_SPARK_TINKERER);
				ClientHelpers.setIgnoreAllStateMapper(CorporeticsBlocks.CORPOREA_RETAINER_DECREMENTER);
				
				ClientHelpers.setIgnoringStateMapper(CorporeticsBlocks.FRAME_SCREW, BlockFrameScrew.POWERED);
				ClientHelpers.setIgnoringStateMapper(CorporeticsBlocks.FRAME_SCREW_REVERSED, BlockFrameScrew.POWERED);
			}
			
			@Override
			public void tesrs() {
				ClientRegistry.bindTileEntitySpecialRenderer(TileCorporeaSparkTinkerer.class, new RenderTileCorporeaSparkTinkerer());
			}
		};
	}
}
