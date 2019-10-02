package quaternary.incorporeal.feature.cygnusnetwork.etc;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.api.cygnus.ILooseCygnusFunnelable;
import quaternary.incorporeal.feature.cygnusnetwork.lexicon.PageHeadingIcon;
import vazkii.botania.api.lexicon.LexiconPage;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.List;

//BlockCygnusFunnel hardcodes this response when asked about redstone dust.
//It's not my block to add an interface on to, and ASM is way overkill.
public class LooseRedstoneDustCygnusFunnelable implements ILooseCygnusFunnelable {
	@Nullable
	@Override
	public ICygnusFunnelable getFor(World world, BlockPos pos, IBlockState state, EnumFacing face) {
		if(state.getBlock() == Blocks.REDSTONE_WIRE) {
			return forLevel(state.getValue(BlockRedstoneWire.POWER));
		} else return null;
	}
	
	@Override
	public void document(List<LexiconPage> pages) {
		pages.add(new PageHeadingIcon(
			new ItemStack(Items.REDSTONE),
			"botania.page.incorporeal.cygnus_funnel.redstone_dust"
		));
	}
	
	//a Cache
	private static final RedstoneDustCygnusFunnelable[] FUNNELABLES = new RedstoneDustCygnusFunnelable[16];
	
	static {
		for(int i = 0; i < 16; i++) {
			FUNNELABLES[i] = new RedstoneDustCygnusFunnelable(BigInteger.valueOf(i));
		}
	}
	
	public static RedstoneDustCygnusFunnelable forLevel(int level) {
		return FUNNELABLES[level];
	}
	
	///////////////
	
	public static final class RedstoneDustCygnusFunnelable implements ICygnusFunnelable {
		private RedstoneDustCygnusFunnelable(BigInteger level) {
			this.level = level;
		}
		
		private final BigInteger level;
		
		@Override
		public boolean canGiveCygnusItem() {
			return true;
		}
		
		@Nullable
		@Override
		public Object giveItemToCygnus() {
			return level;
		}
	}
}
