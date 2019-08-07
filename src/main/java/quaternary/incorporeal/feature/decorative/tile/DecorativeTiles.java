package quaternary.incorporeal.feature.decorative.tile;

import quaternary.incorporeal.core.TilesModule;

public final class DecorativeTiles extends TilesModule {
	private DecorativeTiles() {
	}
	
	public static void register() {
		reg(TileUnstableCube.class, "unstable_cube");
		reg(TileSpiritShrineExt.class, "forgotten_shrine");
	}
}
