package quaternary.incorporeal.feature.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.api.feature.IClientFeatureTwin;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.core.client.ClientHelpers;
import quaternary.incorporeal.core.client.event.BlockHighlightEventHandler;
import quaternary.incorporeal.feature.decorative.block.DecorativeBlocks;
import quaternary.incorporeal.feature.decorative.block.pieces.BlockSlabPiece;
import quaternary.incorporeal.feature.decorative.block.pieces.Piece;
import quaternary.incorporeal.feature.decorative.client.tesr.RenderTileUnstableCube;
import quaternary.incorporeal.feature.decorative.item.DecorativeItems;
import quaternary.incorporeal.feature.decorative.lexicon.DecorativeLexicon;
import quaternary.incorporeal.feature.decorative.recipe.DecorativeRuneRecipes;
import quaternary.incorporeal.feature.decorative.sound.DecorativeSounds;
import quaternary.incorporeal.feature.decorative.tile.DecorativeTiles;
import quaternary.incorporeal.feature.decorative.tile.TileUnstableCube;
import vazkii.botania.api.state.BotaniaStateProps;

public class DecorativeFeature implements IFeature {
	@Override
	public String name() {
		return "decorative";
	}
	
	@Override
	public String description() {
		return "Various decorative blocks to, uh, decorate stuff with?";
	}
	
	@Override
	public void runeRecipes() {
		DecorativeRuneRecipes.register();
	}
	
	@Override
	public void blocks(IForgeRegistry<Block> blocks) {
		DecorativeBlocks.register(blocks);
	}
	
	@Override
	public void items(IForgeRegistry<Item> items) {
		DecorativeItems.register(items);
	}
	
	@Override
	public void tiles() {
		DecorativeTiles.register();
	}
	
	@Override
	public void sounds(IForgeRegistry<SoundEvent> sounds) {
		DecorativeSounds.register(sounds);
	}
	
	@Override
	public void lexicon() {
		DecorativeLexicon.register();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IClientFeatureTwin client() {
		return new IClientFeatureTwin() {
			@Override
			public void preinit() {
				BlockHighlightEventHandler.ensureRegistered();
			}
			
			@Override
			public void models() {
				ClientHelpers.set16DataValuesPointingAtSameModel(DecorativeItems.UNSTABLE_CUBE);
				ClientHelpers.setSimpleModel(DecorativeItems.RED_STRING_TILE);
				ClientHelpers.setSimpleModel(DecorativeItems.CORPOREA_TILE);
				ClientHelpers.setSimpleModel(DecorativeItems.RED_STRING_FROST);
				ClientHelpers.setSimpleModel(DecorativeItems.CORPOREA_BRICKS);
				ClientHelpers.setSimpleModel(DecorativeItems.FORGOTTEN_SHRINE);
				ClientHelpers.setSimpleModel(DecorativeItems.LOKIW);
				
				DecorativeBlocks.forEachPieceManager(m -> m.forEachItem(ClientHelpers::setSimpleModel));
			}
			
			@Override
			public void statemappers() {
				//Dirty hack...
				DecorativeBlocks.forEachPieceManager(m -> {
					Piece.Slab slabPiece = m.getPiece("slab");
					if(slabPiece != null) {
						ClientHelpers.setIgnoringStateMapper(slabPiece.singleSlabBlock, BlockSlabPiece.DUMMY_VARIANT);
						ClientHelpers.setIgnoringStateMapper(slabPiece.doubleSlabBlock, BlockSlabPiece.DUMMY_VARIANT);
					}
					
					Piece.Fence fencePiece = m.getPiece("fence");
					if(fencePiece != null) {
						ClientHelpers.setIgnoringStateMapper(fencePiece.fenceGateBlock, BlockFenceGate.POWERED);
					}
				});
			}
			
			@Override
			public void tesrs() {
				ClientRegistry.bindTileEntitySpecialRenderer(TileUnstableCube.class, new RenderTileUnstableCube());
			}
			
			@Override
			public void blockColors(ColorHandlerEvent.Block e) {
				BlockColors bc = e.getBlockColors();
				bc.registerBlockColorHandler(((state, world, pos, tintIndex) -> {
					return tintIndex == 0 ? state.getValue(BotaniaStateProps.COLOR).colorValue : 0xFFFFFF;
				}), DecorativeBlocks.UNSTABLE_CUBE);
			}
			
			@Override
			public void itemColors(ColorHandlerEvent.Item e) {
				ItemColors ic = e.getItemColors();
				ic.registerItemColorHandler((stack, tintIndex) -> {
					return tintIndex == 0 ? EnumDyeColor.byMetadata(stack.getMetadata()).colorValue : 0xFFFFFF;
				}, DecorativeItems.UNSTABLE_CUBE);
			}
		};
	}
}
