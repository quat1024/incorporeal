package quaternary.incorporeal.tile;

import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.incorporeal.Incorporeal;

public class IncorporeticTiles {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileCorporeaLiar.class, Incorporeal.MODID + ":liar");
		GameRegistry.registerTileEntity(TileCorporeaSolidifier.class, Incorporeal.MODID + ":solidifier");
		GameRegistry.registerTileEntity(TileCorporeaSparkTinkerer.class, Incorporeal.MODID + ":corporea_tinkerer");
		GameRegistry.registerTileEntity(TileFrameTinkerer.class, Incorporeal.MODID + ":frame_tinkerer");
	}
}
