package quaternary.incorporeal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.incorporeal.lexicon.IncorporealLexiData;
import quaternary.incorporeal.tile.*;
import quaternary.incorporeal.tile.flower.SubTileSanvocalia;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;

@Mod(modid = Incorporeal.MODID, name = Incorporeal.NAME, version = Incorporeal.VERSION, dependencies = Incorporeal.DEPENDENCIES)

public class Incorporeal {
	public static final String MODID = "incorporeal";
	public static final String NAME = "Incorporeal";
	public static final String VERSION = "0";
	public static final String DEPENDENCIES = "required-after:botania;";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	@Mod.EventBusSubscriber(modid = Incorporeal.MODID)
	public static class CommonEvents {
		@Mod.EventHandler
		public static void init(FMLInitializationEvent e) {
			IncorporealLexiData.init();
		}
		
		@SubscribeEvent
		public static void blocks(RegistryEvent.Register<Block> e) {
			IForgeRegistry<Block> reg = e.getRegistry();
			
			for(Block b : Stuff.BLOCKS) {
				reg.register(b);
			}
			
			GameRegistry.registerTileEntity(TileCorporeaLiar.class, Incorporeal.MODID + ":liar");
			GameRegistry.registerTileEntity(TileCorporeaSolidifier.class, Incorporeal.MODID + ":solidifier");
			GameRegistry.registerTileEntity(TileCorporeaLiquifier.class, Incorporeal.MODID + ":liquifier");
			GameRegistry.registerTileEntity(TileCorporeaSparkTinkerer.class, Incorporeal.MODID + ":tinkerer");
			
			BotaniaAPI.registerSubTile("sanvocalia", SubTileSanvocalia.class);
			BotaniaAPI.registerMiniSubTile("sanvocalia", SubTileSanvocalia.class, "sanvocaliaChibi");
		}
		
		@SubscribeEvent
		public static void items(RegistryEvent.Register<Item> e) {
			IForgeRegistry<Item> reg = e.getRegistry();
			
			for(Item i : Stuff.ITEMS) {
				reg.register(i);
			}
		}
	}
	
	@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Incorporeal.MODID)
	public static class ClientEvents {
		@Mod.EventHandler
		public static void models(ModelRegistryEvent e) {
			//BotaniaAPIClient.registerSubtileModel("sanvocalia", etc etc)
		}
	}
}
