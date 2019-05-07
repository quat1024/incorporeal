package quaternary.incorporeal.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.tile.cygnus.TileCygnusCrystalCube;
import quaternary.incorporeal.tile.cygnus.TileCygnusFunnel;
import quaternary.incorporeal.tile.cygnus.TileCygnusRetainer;
import quaternary.incorporeal.tile.cygnus.TileCygnusWord;
import quaternary.incorporeal.tile.decorative.TileSpiritShrineExt;
import quaternary.incorporeal.tile.decorative.TileUnstableCube;
import quaternary.incorporeal.tile.soulcore.TileCorporeaSoulCore;
import quaternary.incorporeal.tile.soulcore.TileEnderSoulCore;
import quaternary.incorporeal.tile.soulcore.TilePotionSoulCore;

public final class IncorporeticTiles {
	private IncorporeticTiles() { }
	
	public static void registerTileEntities() {
		reg(TileCorporeaSolidifier.class, "solidifier");
		reg(TileCorporeaSparkTinkerer.class, "corporea_tinkerer");
		reg(TileEnderSoulCore.class, "ender_soul_core");
		reg(TileCorporeaSoulCore.class, "corporea_soul_core");
		reg(TilePotionSoulCore.class, "potion_soul_core");
		reg(TileUnstableCube.class, "unstable_cube");
		reg(TileSpiritShrineExt.class, "forgotten_shrine");
		reg(TileRedStringLiar.class, "red_string_liar");
		
		reg(TileCygnusCrystalCube.class, "cygnus_crystal_cube");
		reg(TileCygnusRetainer.class, "cygnus_retainer");
		reg(TileCygnusWord.class, "cygnus_word");
		reg(TileCygnusFunnel.class, "cygnus_funnel");
	}
	
	private static void reg(Class<? extends TileEntity> c, String name) {
		GameRegistry.registerTileEntity(c, new ResourceLocation(Incorporeal.MODID, name));
	}
}
