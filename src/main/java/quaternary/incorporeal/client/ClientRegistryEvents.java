package quaternary.incorporeal.client;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.IncorporeticConfig;
import quaternary.incorporeal.block.IncorporeticBlocks;
import quaternary.incorporeal.block.cygnus.IncorporeticCygnusBlocks;
import quaternary.incorporeal.client.entityrenderer.RenderEntityCygnusMasterSpark;
import quaternary.incorporeal.client.entityrenderer.RenderEntityCygnusRegularSpark;
import quaternary.incorporeal.client.entityrenderer.RenderEntityNothing;
import quaternary.incorporeal.client.model.CygnusWordModelLoader;
import quaternary.incorporeal.client.tesr.RenderItemSoulCore;
import quaternary.incorporeal.client.tesr.RenderTileCorporeaSparkTinkerer;
import quaternary.incorporeal.client.tesr.RenderTileSoulCore;
import quaternary.incorporeal.client.tesr.cygnus.RenderTileCygnusCrystalCube;
import quaternary.incorporeal.client.tesr.cygnus.RenderTileCygnusRetainer;
import quaternary.incorporeal.client.tesr.decorative.RenderTileUnstableCube;
import quaternary.incorporeal.entity.EntityFracturedSpaceCollector;
import quaternary.incorporeal.entity.EntityPotionSoulCoreCollector;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.entity.cygnus.EntityCygnusRegularSpark;
import quaternary.incorporeal.flower.SubTileSanvocalia;
import quaternary.incorporeal.flower.SubTileSweetAlexum;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;
import quaternary.incorporeal.item.cygnus.ItemCygnusCard;
import quaternary.incorporeal.tile.TileCorporeaSparkTinkerer;
import quaternary.incorporeal.tile.cygnus.TileCygnusCrystalCube;
import quaternary.incorporeal.tile.cygnus.TileCygnusRetainer;
import quaternary.incorporeal.tile.decorative.TileUnstableCube;
import quaternary.incorporeal.tile.soulcore.TileCorporeaSoulCore;
import quaternary.incorporeal.tile.soulcore.TileEnderSoulCore;
import quaternary.incorporeal.tile.soulcore.TilePotionSoulCore;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.subtile.SubTileEntity;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class ClientRegistryEvents {
	private ClientRegistryEvents() {}
	
	@SubscribeEvent
	public static void models(ModelRegistryEvent e) {
		//Item models
		setSimpleModel(IncorporeticItems.FRAME_TINKERER);
		setSimpleModel(IncorporeticItems.CORPOREA_INHIBITOR);
		setSimpleModel(IncorporeticItems.CORPOREA_SPARK_TINKERER);
		setSimpleModel(IncorporeticItems.CORPOREA_SOLIDIFIER);
		setSimpleModel(IncorporeticItems.NATURAL_REPEATER);
		setSimpleModel(IncorporeticItems.NATURAL_COMPARATOR);
		setSimpleModel(IncorporeticItems.CORPOREA_RETAINER_DECREMENTER);
		
		setSimpleModel(IncorporeticItems.CORPOREA_TICKET);
		setSimpleModel(IncorporeticItems.TICKET_CONJURER);
		setSimpleModel(IncorporeticItems.FRACTURED_SPACE_ROD);
		
		setSimpleModel(IncorporeticItems.DECORATIVE_RED_STRING);
		setSimpleModel(IncorporeticItems.DECORATIVE_CORPOREA);
		
		setSimpleModel(IncorporeticCygnusItems.MASTER_CYGNUS_SPARK);
		setSimpleModel(IncorporeticCygnusItems.CYGNUS_SPARK);
		setSimpleModel(IncorporeticCygnusItems.CYGNUS_TICKET);
		
		setSimpleModel(IncorporeticCygnusItems.WORD);
		setSimpleModel(IncorporeticCygnusItems.CRYSTAL_CUBE);
		setSimpleModel(IncorporeticCygnusItems.FUNNEL);
		setSimpleModel(IncorporeticCygnusItems.RETAINER);
		
		setCygnusCardMeshDefinition(IncorporeticCygnusItems.WORD_CARD, "word_card");
		setCygnusCardMeshDefinition(IncorporeticCygnusItems.CRYSTAL_CUBE_CARD, "crystal_cube_card");
		
		set16DataValuesPointingAtSameModel(IncorporeticItems.DECORATIVE_UNSTABLE_CUBE);
		
		setFlowerModel(SubTileSanvocalia.class, "sanvocalia");
		setFlowerModel(SubTileSanvocalia.Mini.class, "sanvocalia_chibi");
		setFlowerModel(SubTileSweetAlexum.class, "sweet_alexum");
		setFlowerModel(SubTileSweetAlexum.Mini.class, "sweet_alexum_chibi");
		
		//Statemappers
		setIgnoreAllStateMapper(IncorporeticBlocks.FRAME_TINKERER);
		setIgnoreAllStateMapper(IncorporeticBlocks.CORPOREA_SPARK_TINKERER);
		setIgnoreAllStateMapper(IncorporeticBlocks.CORPOREA_RETAINER_DECREMENTER);
		setIgnoreAllStateMapper(IncorporeticBlocks.ENDER_SOUL_CORE);
		setIgnoreAllStateMapper(IncorporeticBlocks.CORPOREA_SOUL_CORE);
		setIgnoreAllStateMapper(IncorporeticBlocks.DECORATIVE_UNSTABLE_CUBE);
		setIgnoreAllStateMapper(IncorporeticCygnusBlocks.WORD);
		
		//Tile Entity Special Renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileCorporeaSparkTinkerer.class, new RenderTileCorporeaSparkTinkerer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileUnstableCube.class, new RenderTileUnstableCube());
		
		RenderTileSoulCore<TileEnderSoulCore> enderRender = new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/entity/ender_soul_core.png"));
		RenderTileSoulCore<TileCorporeaSoulCore> corporeaRender = new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/entity/corporea_soul_core.png"));
		RenderTileSoulCore<TilePotionSoulCore> potionRender = new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/entity/potion_soul_core.png"));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnderSoulCore.class, enderRender);
		ClientRegistry.bindTileEntitySpecialRenderer(TileCorporeaSoulCore.class, corporeaRender);
		ClientRegistry.bindTileEntitySpecialRenderer(TilePotionSoulCore.class, potionRender);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileCygnusRetainer.class, new RenderTileCygnusRetainer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileCygnusCrystalCube.class, new RenderTileCygnusCrystalCube());
		
		//Teisrs
		setTEISRModel(IncorporeticItems.ENDER_SOUL_CORE, new RenderItemSoulCore(enderRender));
		setTEISRModel(IncorporeticItems.CORPOREA_SOUL_CORE, new RenderItemSoulCore(corporeaRender));
		setTEISRModel(IncorporeticItems.POTION_SOUL_CORE, new RenderItemSoulCore(potionRender));
		setTEISRModel(IncorporeticItems.SOUL_CORE_FRAME, new RenderItemSoulCore(new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/entity/soul_core_frame.png"))));
	}
	
	@SubscribeEvent
	public static void blockColors(ColorHandlerEvent.Block e) {
		BlockColors bc = e.getBlockColors();
		bc.registerBlockColorHandler(((state, world, pos, tintIndex) -> {
			return tintIndex == 0 ? state.getValue(BotaniaStateProps.COLOR).colorValue : 0xFFFFFF;
		}), IncorporeticBlocks.DECORATIVE_UNSTABLE_CUBE);
	}
	
	@SubscribeEvent
	public static void itemColors(ColorHandlerEvent.Item e) {
		ItemColors ic = e.getItemColors();
		ic.registerItemColorHandler((stack, tintIndex) -> {
			return tintIndex == 0 ? EnumDyeColor.byMetadata(stack.getMetadata()).colorValue : 0xFFFFFF;
		}, IncorporeticItems.DECORATIVE_UNSTABLE_CUBE);
	}
	
	public static void preinit() {
		RenderingRegistry.registerEntityRenderingHandler(EntityCygnusRegularSpark.class, RenderEntityCygnusRegularSpark::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCygnusMasterSpark.class, RenderEntityCygnusMasterSpark::new);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFracturedSpaceCollector.class, RenderEntityNothing::new);
		
		if(!IncorporeticConfig.SoulCore.DEBUG_BLOODCORE_ENTITIES) {
			RenderingRegistry.registerEntityRenderingHandler(EntityPotionSoulCoreCollector.class, RenderEntityNothing::new);
		}
		
		//Block model loader things
		ModelLoaderRegistry.registerLoader(new CygnusWordModelLoader());
	}
	
	private static void setSimpleModel(Item i) {
		ResourceLocation res = Preconditions.checkNotNull(i.getRegistryName());
		ModelResourceLocation mrl = new ModelResourceLocation(res, "inventory");
		ModelLoader.setCustomModelResourceLocation(i, 0, mrl);
	}
	
	private static void setTEISRModel(Item i, TileEntityItemStackRenderer rend) {
		ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(Incorporeal.MODID, "dummy_builtin_blocktransforms"), "inventory");
		ModelLoader.setCustomModelResourceLocation(i, 0, mrl);
		
		i.setTileEntityItemStackRenderer(rend);
	}
	
	private static void set16DataValuesPointingAtSameModel(Item i) {
		ResourceLocation res = Preconditions.checkNotNull(i.getRegistryName());
		
		for(int color = 0; color < 16; color++) {
			ModelResourceLocation mrl = new ModelResourceLocation(res, "inventory");
			ModelLoader.setCustomModelResourceLocation(i, color, mrl);
		}
	}
	
	private static void setIgnoreAllStateMapper(Block b) {
		ModelLoader.setCustomStateMapper(b, new IgnoreAllStateMapper(b));
	}
	
	private static void setIgnoringStateMapper(Block b, IProperty<?>... props) {
		StateMap.Builder builder = new StateMap.Builder();
		for(int i = 0; i < props.length; i++) {
			builder.ignore(props[i]);
		}
		ModelLoader.setCustomStateMapper(b, builder.build());
	}
	
	private static void setFlowerModel(Class<? extends SubTileEntity> flower, String name) {
		ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(Incorporeal.MODID, name), "normal");
		BotaniaAPIClient.registerSubtileModel(flower, mrl);
	}
	
	private static <T> void setCygnusCardMeshDefinition(ItemCygnusCard<T> card, String path) {
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
}