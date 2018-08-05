package quaternary.incorporeal.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticBlocks;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID, value = Side.CLIENT)
public final class ClientEvents {
	private ClientEvents() {}
	
	@SubscribeEvent
	public static void models(ModelRegistryEvent e) {
		//TODO item models
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.FRAME_TINKERER));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_INHIBITOR));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_SPARK_TINKERER));
		setSimpleModel(Item.getItemFromBlock(IncorporeticBlocks.CORPOREA_SOLIDIFIER));
		
		setIgnoreAllStateMapper(IncorporeticBlocks.FRAME_TINKERER);
		setIgnoreAllStateMapper(IncorporeticBlocks.CORPOREA_SPARK_TINKERER);
	}
	
	private static void setSimpleModel(Item i) {
		ModelResourceLocation mrl = new ModelResourceLocation(i.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(i, 0, mrl);
	}
	
	private static void setIgnoreAllStateMapper(Block b) {
		ModelLoader.setCustomStateMapper(b, new IgnoreAllStateMapper(b));
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
