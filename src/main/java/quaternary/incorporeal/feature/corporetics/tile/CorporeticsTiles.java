package quaternary.incorporeal.feature.corporetics.tile;

import quaternary.incorporeal.core.TilesModule;

public final class CorporeticsTiles extends TilesModule {
	private CorporeticsTiles() {}
	
	public static void registerTiles() {
		reg(TileCorporeaSolidifier.class, "solidifier");
		reg(TileCorporeaSparkTinkerer.class, "corporea_tinkerer");
		reg(TileRedStringLiar.class, "red_string_liar");
	}
}
