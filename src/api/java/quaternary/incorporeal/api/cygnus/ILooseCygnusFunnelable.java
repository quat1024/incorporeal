package quaternary.incorporeal.api.cygnus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.api.IDocumentableComponent;
import quaternary.incorporeal.api.IIncorporealAPI;

import javax.annotation.Nullable;

/**
 * Loose cygnus funnelables are used for circumstances when the Forge capability system falls short.
 * For example, if you want to add a funnelable to a normal, non-TE block but do not or cannot
 * add an interface to it, a loose funnelable is appropriate. They're "loose" since they're not
 * attached to anything in the same way capabilities are, and just go into a pile.
 * 
 * Loose funnelables are checked last, after all the normal ones. Use them as a last resort.
 * 
 * Because loose funnelables are centrally registered, you do not need to use IIncorporealAPI#documentCygnusFunnelable for these (doing so will result in the page being added twice to the lexicon).
 * 
 * @see IIncorporealAPI#registerLooseFunnelable
 * @see IDocumentableComponent
 * 
 * @since 1.1
 * @author quaternary
 */
public interface ILooseCygnusFunnelable extends IDocumentableComponent {
	/**
	 * @param face the direction to the Cygnus funnel, i.e. <pre>world.getBlockState(pos.offset(face)).getBlock() == IncorporeticCygnusBlocks.FUNNEL</pre>
	 * @return an ICygnusFunnelable if appropriate for this location, or <pre>null/pre> if not
	 */
	@Nullable
	ICygnusFunnelable getFor(World world, BlockPos pos, IBlockState state, EnumFacing face);
}
