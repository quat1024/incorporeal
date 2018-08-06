package quaternary.incorporeal.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticBlocks;
import quaternary.incorporeal.flower.SubTileSanvocalia;
import quaternary.incorporeal.item.IncorporeticItems;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.subtile.SubTileEntity;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class ClientEvents {
	private ClientEvents() {}
	
	@SubscribeEvent
	public static void models(ModelRegistryEvent e) {
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.FRAME_TINKERER));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_INHIBITOR));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_SPARK_TINKERER));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_SOLIDIFIER));
		
		setSimpleModel(IncorporeticItems.CORPOREA_TICKET);
		setSimpleModel(IncorporeticItems.TICKET_CONJURER);
		
		setIgnoreAllStateMapper(IncorporeticBlocks.FRAME_TINKERER);
		setIgnoreAllStateMapper(IncorporeticBlocks.CORPOREA_SPARK_TINKERER);
		
		setFlowerModel(SubTileSanvocalia.class, "sanvocalia");
		setFlowerModel(SubTileSanvocalia.Mini.class, "sanvocalia_chibi");
	}
	
	private static void setSimpleModel(Item i) {
		ModelResourceLocation mrl = new ModelResourceLocation(i.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(i, 0, mrl);
	}
	
	private static void setIgnoreAllStateMapper(Block b) {
		ModelLoader.setCustomStateMapper(b, new IgnoreAllStateMapper(b));
	}
	
	private static void setFlowerModel(Class<? extends SubTileEntity> flower, String name) {
		ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(Incorporeal.MODID, name), "normal");
		BotaniaAPIClient.registerSubtileModel(flower, mrl);
	}
	
	@SubscribeEvent
	public static void blockColors(ColorHandlerEvent.Block e) {
		//TODO
	}
	
	@SubscribeEvent
	public static void itemColors(ColorHandlerEvent.Item e) {
		//TODO
	}
}
