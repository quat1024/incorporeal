package quaternary.incorporeal.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.tile.soulcore.TileCorporeaSoulCore;
import quaternary.incorporeal.tile.soulcore.TileEnderSoulCore;

public class IncorporeticTiles {
	public static void registerTileEntities() {
		reg(TileCorporeaLiar.class, "liar");
		reg(TileCorporeaSolidifier.class, "solidifier");
		reg(TileCorporeaSparkTinkerer.class, "corporea_tinkerer");
		reg(TileFrameTinkerer.class, "frame_tinkerer");
		reg(TileEnderSoulCore.class, "ender_soul_core");
		reg(TileCorporeaSoulCore.class, "corporea_soul_core");
	}
	
	private static void reg(Class<? extends TileEntity> c, String name) {
		GameRegistry.registerTileEntity(c, new ResourceLocation(Incorporeal.MODID, name));
	}
}
