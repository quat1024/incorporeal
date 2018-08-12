package quaternary.incorporeal.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticBlocks;
import quaternary.incorporeal.client.tesr.RenderItemSoulCore;
import quaternary.incorporeal.client.tesr.RenderTileCorporeaSparkTinkerer;
import quaternary.incorporeal.client.tesr.RenderTileSoulCore;
import quaternary.incorporeal.flower.SubTileSanvocalia;
import quaternary.incorporeal.flower.SubTileSweetAlexum;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.tile.TileCorporeaSparkTinkerer;
import quaternary.incorporeal.tile.soulcore.TileCorporeaSoulCore;
import quaternary.incorporeal.tile.soulcore.TileEnderSoulCore;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.subtile.SubTileEntity;

import java.util.Collections;

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
		setSimpleModel(IncorporeticItems.CORPOREA_LIAR);
		setSimpleModel(IncorporeticItems.NATURAL_REPEATER);
		setSimpleModel(IncorporeticItems.NATURAL_COMPARATOR);
		setSimpleModel(IncorporeticItems.CORPOREA_RETAINER_DECREMENTER);
		
		setSimpleModel(IncorporeticItems.CORPOREA_TICKET);
		setSimpleModel(IncorporeticItems.TICKET_CONJURER);
		setSimpleModel(IncorporeticItems.FRACTURED_SPACE_ROD);
		
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
		
		//Tile Entity Special Renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileCorporeaSparkTinkerer.class, new RenderTileCorporeaSparkTinkerer());
		
		RenderTileSoulCore<TileEnderSoulCore> enderRender = new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/tesr/ender_soul_core.png"));
		RenderTileSoulCore<TileCorporeaSoulCore> corporeaRender = new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "textures/tesr/corporea_soul_core.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnderSoulCore.class, enderRender);
		ClientRegistry.bindTileEntitySpecialRenderer(TileCorporeaSoulCore.class, corporeaRender);
		
		//Teisrs
		setTEISRModel(IncorporeticItems.ENDER_SOUL_CORE, new RenderItemSoulCore(enderRender));
		setTEISRModel(IncorporeticItems.CORPOREA_SOUL_CORE, new RenderItemSoulCore(corporeaRender));
	}
	
	private static void setSimpleModel(Item i) {
		ModelResourceLocation mrl = new ModelResourceLocation(i.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(i, 0, mrl);
	}
	
	private static void setTEISRModel(Item i, TileEntityItemStackRenderer rend) {
		ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(Incorporeal.MODID, "dummy_builtin_blocktransforms"), "inventory");
		ModelLoader.setCustomModelResourceLocation(i, 0, mrl);
		
		i.setTileEntityItemStackRenderer(rend);
	}
	
	private static void setIgnoreAllStateMapper(Block b) {
		ModelLoader.setCustomStateMapper(b, new IgnoreAllStateMapper(b));
	}
	
	private static void setFlowerModel(Class<? extends SubTileEntity> flower, String name) {
		ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(Incorporeal.MODID, name), "normal");
		BotaniaAPIClient.registerSubtileModel(flower, mrl);
	}
}