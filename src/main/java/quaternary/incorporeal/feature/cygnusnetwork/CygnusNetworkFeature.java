package quaternary.incorporeal.feature.cygnusnetwork;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.IncorporeticFeatures;
import quaternary.incorporeal.api.feature.IClientFeatureTwin;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.core.client.ClientHelpers;
import quaternary.incorporeal.core.client.event.PostRenderGameOverlayEventHandler;
import quaternary.incorporeal.feature.cygnusnetwork.etc.CygnusStackDataSerializer;
import quaternary.incorporeal.feature.cygnusnetwork.etc.LooseRedstoneDustCygnusFunnelable;
import quaternary.incorporeal.feature.cygnusnetwork.etc.LooseRedstoneRepeaterCygnusFunnelable;
import quaternary.incorporeal.feature.cygnusnetwork.block.CygnusNetworkBlocks;
import quaternary.incorporeal.feature.cygnusnetwork.cap.CygnusAttachCapabilitiesEventHandler;
import quaternary.incorporeal.feature.cygnusnetwork.cap.IncorporeticCygnusCapabilities;
import quaternary.incorporeal.feature.cygnusnetwork.client.entityrenderer.RenderEntityCygnusMasterSpark;
import quaternary.incorporeal.feature.cygnusnetwork.client.entityrenderer.RenderEntityCygnusRegularSpark;
import quaternary.incorporeal.feature.cygnusnetwork.client.event.CameraEaseHandler;
import quaternary.incorporeal.feature.cygnusnetwork.client.event.CorporeticIcons;
import quaternary.incorporeal.feature.cygnusnetwork.client.event.ScrollEventHandler;
import quaternary.incorporeal.feature.cygnusnetwork.client.model.CygnusWordModelLoader;
import quaternary.incorporeal.feature.cygnusnetwork.client.tesr.RenderTileCygnusCrystalCube;
import quaternary.incorporeal.feature.cygnusnetwork.client.tesr.RenderTileCygnusRetainer;
import quaternary.incorporeal.feature.cygnusnetwork.entity.CygnusNetworkEntities;
import quaternary.incorporeal.feature.cygnusnetwork.entity.EntityCygnusMasterSpark;
import quaternary.incorporeal.feature.cygnusnetwork.entity.EntityCygnusRegularSpark;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;
import quaternary.incorporeal.feature.cygnusnetwork.item.ItemCygnusCard;
import quaternary.incorporeal.feature.cygnusnetwork.recipe.CygnusSkytouchingRecipes;
import quaternary.incorporeal.feature.cygnusnetwork.tile.CygnusNetworkTiles;
import quaternary.incorporeal.feature.cygnusnetwork.tile.TileCygnusCrystalCube;
import quaternary.incorporeal.feature.cygnusnetwork.tile.TileCygnusRetainer;

public class CygnusNetworkFeature implements IFeature {
	@Override
	public String name() {
		return "cygnusNetwork";
	}
	
	@Override
	public String description() {
		return "Various data storage and processing utilities.";
	}
	
	@Override
	public void preinit(FMLPreInitializationEvent e) {
		IncorporeticCygnusCapabilities.register(e);
		IncorporeticCygnusActions.registerCygnusActions();
		IncorporeticCygnusConditions.registerCygnusConditions();
		IncorporeticCygnusDatatypes.registerCygnusDatatypes();
		CygnusStackDataSerializer.register(e);
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		CygnusRegistries.freezeRegistries();
		CygnusDatatypeHelpers.register();
		CygnusRegistries.LOOSE_FUNNELABLES.add(new LooseRedstoneDustCygnusFunnelable());
		CygnusRegistries.LOOSE_FUNNELABLES.add(new LooseRedstoneRepeaterCygnusFunnelable());
		
		CygnusAttachCapabilitiesEventHandler.register();
		
		if(IncorporeticFeatures.isEnabled(IncorporeticFeatures.SKYTOUCHING)) {
			CygnusSkytouchingRecipes.register();
		}
	}
	
	@Override
	public void blocks(IForgeRegistry<Block> blocks) {
		CygnusNetworkBlocks.register(blocks);
	}
	
	@Override
	public void items(IForgeRegistry<Item> items) {
		CygnusNetworkItems.register(items);
	}
	
	@Override
	public void entities(IForgeRegistry<EntityEntry> entities) {
		CygnusNetworkEntities.register(entities);
	}
	
	@Override
	public void tiles() {
		CygnusNetworkTiles.register();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IClientFeatureTwin client() {
		return new IClientFeatureTwin() {
			@Override
			public void preinit() {
				RenderingRegistry.registerEntityRenderingHandler(EntityCygnusRegularSpark.class, RenderEntityCygnusRegularSpark::new);
				RenderingRegistry.registerEntityRenderingHandler(EntityCygnusMasterSpark.class, RenderEntityCygnusMasterSpark::new);
				ModelLoaderRegistry.registerLoader(new CygnusWordModelLoader());
				
				CameraEaseHandler.register();
				ScrollEventHandler.register();
				CorporeticIcons.register();
				
				PostRenderGameOverlayEventHandler.ensureRegistered();
			}
			
			@Override
			public void models() {
				ClientHelpers.setSimpleModel(CygnusNetworkItems.MASTER_CYGNUS_SPARK);
				ClientHelpers.setSimpleModel(CygnusNetworkItems.CYGNUS_SPARK);
				ClientHelpers.setSimpleModel(CygnusNetworkItems.CYGNUS_TICKET);
				
				ClientHelpers.setSimpleModel(CygnusNetworkItems.WORD);
				ClientHelpers.setSimpleModel(CygnusNetworkItems.CRYSTAL_CUBE);
				ClientHelpers.setSimpleModel(CygnusNetworkItems.FUNNEL);
				ClientHelpers.setSimpleModel(CygnusNetworkItems.RETAINER);
				
				setCygnusCardMeshDefinition(CygnusNetworkItems.WORD_CARD, "word_card");
				setCygnusCardMeshDefinition(CygnusNetworkItems.CRYSTAL_CUBE_CARD, "crystal_cube_card");
			}
			
			@Override
			public void statemappers() {
				ClientHelpers.setIgnoreAllStateMapper(CygnusNetworkBlocks.WORD);
			}
			
			@Override
			public void tesrs() {
				ClientRegistry.bindTileEntitySpecialRenderer(TileCygnusRetainer.class, new RenderTileCygnusRetainer());
				
				ClientRegistry.bindTileEntitySpecialRenderer(TileCygnusCrystalCube.class, new RenderTileCygnusCrystalCube());
			}
			
			private <T> void setCygnusCardMeshDefinition(ItemCygnusCard<T> card, String path) {
				String path2 = "cygnus/" + path + "/";
				
				ModelLoader.setCustomMeshDefinition(card, stack -> {
					ResourceLocation valueName = card.readValueName(stack);
					return new ModelResourceLocation(
						new ResourceLocation(
							valueName.getNamespace(),
							path2 + valueName.getPath()
						), "inventory"
					);
				});
				
				ModelLoader.registerItemVariants(card,
					card.registry.allKeys().stream()
						.map(res -> new ResourceLocation(res.getNamespace(), path2 + res.getPath()))
						.toArray(ResourceLocation[]::new)
				);
			}
		};
	}
}
