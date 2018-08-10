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
import quaternary.incorporeal.client.tesr.RenderTileSoulCore;
import quaternary.incorporeal.flower.SubTileSanvocalia;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.tile.soulcore.TileEnderSoulCore;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.subtile.SubTileEntity;

import java.util.Collections;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class ClientEvents {
	private ClientEvents() {}
	
	@SubscribeEvent
	public static void models(ModelRegistryEvent e) {
		//TODO this is a lot of Item.getItemFromBlock; should i store ItemBlocks somewhere?
		//Item models
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.FRAME_TINKERER));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_INHIBITOR));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_SPARK_TINKERER));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_SOLIDIFIER));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_INTERCEPTOR_OMNI));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.NATURAL_REPEATER));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.NATURAL_COMPARATOR));
		
		setSimpleModel(IncorporeticItems.CORPOREA_TICKET);
		setSimpleModel(IncorporeticItems.TICKET_CONJURER);
		
		setFlowerModel(SubTileSanvocalia.class, "sanvocalia");
		setFlowerModel(SubTileSanvocalia.Mini.class, "sanvocalia_chibi");
		
		//Statemappers
		setIgnoreAllStateMapper(IncorporeticBlocks.FRAME_TINKERER);
		setIgnoreAllStateMapper(IncorporeticBlocks.CORPOREA_SPARK_TINKERER);
		setIgnoreAllStateMapper(IncorporeticBlocks.CORPOREA_INTERCEPTOR_OMNI);
		
		setInvisibleStateMapper(IncorporeticBlocks.ENDER_SOUL_CORE);
		setInvisibleStateMapper(IncorporeticBlocks.CORPOREA_SOUL_CORE);
		
		//Tile Entity Special Renderers
		RenderTileSoulCore<TileEnderSoulCore> enderRender = new RenderTileSoulCore<>(new ResourceLocation(Incorporeal.MODID, "tempblah"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnderSoulCore.class, enderRender);
		
		//Teisrs
		setTEISRModel(Item.getItemFromBlock(IncorporeticBlocks.ENDER_SOUL_CORE), new RenderItemSoulCore(enderRender));
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
	
	private static void setInvisibleStateMapper(Block b) {
		ModelLoader.setCustomStateMapper(b, (block) -> Collections.emptyMap());
	}
	
	private static void setFlowerModel(Class<? extends SubTileEntity> flower, String name) {
		ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(Incorporeal.MODID, name), "normal");
		BotaniaAPIClient.registerSubtileModel(flower, mrl);
	}
}