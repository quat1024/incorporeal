package quaternary.incorporeal.feature.soulcores.tile;

import quaternary.incorporeal.core.TilesModule;

public final class SoulCoresTiles extends TilesModule {
	private SoulCoresTiles() {}
	
	public static void registerTiles() {
		reg(TileEnderSoulCore.class, "ender_soul_core");
		reg(TileCorporeaSoulCore.class, "corporea_soul_core");
		reg(TilePotionSoulCore.class, "potion_soul_core");
	}
}
