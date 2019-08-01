package quaternary.incorporeal.feature.corporetics.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.core.sortme.IncorporeticLexicon;
import quaternary.incorporeal.feature.corporetics.tile.TileRedStringLiar;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.block.string.BlockRedString;

import javax.annotation.Nullable;

public class BlockRedStringLiar extends BlockRedString implements ILexiconable {
	public BlockRedStringLiar(String name) {
		//I don't like this practice but I have to do it to extend the vanilla red string block lol oof
		super(name);
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileRedStringLiar();
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return IncorporeticLexicon.redStringLiar;
	}
}
