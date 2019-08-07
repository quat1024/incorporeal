package quaternary.incorporeal.feature.decorative.block;

import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.BlocksModule;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;
import quaternary.incorporeal.feature.decorative.block.pieces.PieceBuilder;
import quaternary.incorporeal.feature.decorative.block.pieces.PieceManager;

import java.util.function.Consumer;

public final class DecorativeBlocks extends BlocksModule {
	private DecorativeBlocks() {
	}
	
	public static final class RegistryNames {
		private RegistryNames() {
		}
		
		public static final String UNSTABLE_CUBE = "unstable_cube";
		public static final String RED_STRING_TILE = "red_string_deco";
		public static final String CORPOREA_TILE = "corporea_deco";
		public static final String RED_STRING_FROST = "red_string_frost_deco";
		public static final String CORPOREA_BRICKS = "corporea_brick_deco";
		public static final String FORGOTTEN_SHRINE = "forgotten_shrine";
		
		public static final String LOKIW = "lokiw";
	}
	
	public static BlockUnstableCube UNSTABLE_CUBE = null;
	
	public static BlockRedStringDeco RED_STRING_TILE = null;
	public static BlockCorporeaDeco CORPOREA_TILE = null;
	public static BlockRedStringDeco RED_STRING_FROST = null;
	public static BlockCorporeaDeco CORPOREA_BRICKS = null;
	
	public static BlockForgottenShrine FORGOTTEN_SHRINE = null;
	
	public static PieceManager redStringTilePieces;
	public static PieceManager corporeaBrickPieces;
	public static PieceManager corporeaTilePieces;
	public static PieceManager lokiwPieces;
	
	//holy artifact
	public static BlockLokiW LOKIW = null;
	
	public static void registerBlocks(IForgeRegistry<Block> blocks) {
		blocks.registerAll(
			UNSTABLE_CUBE = name(new BlockUnstableCube(), RegistryNames.UNSTABLE_CUBE),
			RED_STRING_TILE = name(new BlockRedStringDeco(), RegistryNames.RED_STRING_TILE),
			CORPOREA_TILE = name(new BlockCorporeaDeco(), RegistryNames.CORPOREA_TILE),
			RED_STRING_FROST = name(new BlockRedStringFrost(), RegistryNames.RED_STRING_FROST),
			CORPOREA_BRICKS = name(new BlockCorporeaDeco(), RegistryNames.CORPOREA_BRICKS),
			
			FORGOTTEN_SHRINE = name(new BlockForgottenShrine(), RegistryNames.FORGOTTEN_SHRINE),
			
			LOKIW = name(new BlockLokiW(), RegistryNames.LOKIW)
		);
		
		redStringTilePieces = new PieceBuilder(RED_STRING_TILE).addStair().addSlab().build();
		corporeaBrickPieces = new PieceBuilder(CORPOREA_BRICKS).addStair().addSlab().addWall().build();
		corporeaTilePieces = new PieceBuilder(CORPOREA_TILE).addStair().addSlab().build();
		lokiwPieces = new PieceBuilder(LOKIW).addStair().addSlab().addFence().addWall().build();
		
		forEachPieceManager((m) -> {
			m.nameBlocks();
			m.forEachBlock(blocks::register);
		});
	}
	
	public static void forEachPieceManager(Consumer<PieceManager> act) {
		EtcHelpers.forEach(act, redStringTilePieces, corporeaBrickPieces, corporeaTilePieces, lokiwPieces);
	}
}
