package quaternary.incorporeal.feature.cygnusnetwork.tile;

import quaternary.incorporeal.core.TilesModule;

public final class CygnusNetworkTiles extends TilesModule {
	private CygnusNetworkTiles() {
	}
	
	public static void registerTiles() {
		reg(TileCygnusCrystalCube.class, "cygnus_crystal_cube");
		reg(TileCygnusRetainer.class, "cygnus_retainer");
		reg(TileCygnusWord.class, "cygnus_word");
		reg(TileCygnusFunnel.class, "cygnus_funnel");
	}
}
