package quaternary.incorporeal.feature.cygnusnetwork.etc;

import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
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

public class LooseRedstoneRepeaterCygnusFunnelable implements ILooseCygnusFunnelable {
	@Nullable
	@Override
	public ICygnusFunnelable getFor(World world, BlockPos pos, IBlockState state, EnumFacing face) {
		if(state.getBlock() instanceof BlockRedstoneRepeater) {
			return new ICygnusFunnelable() {
				@Override
				public boolean canGiveCygnusItem() {
					return true;
				}
				
				@Override
				public boolean canAcceptCygnusItem() {
					return true;
				}
				
				@Nullable
				@Override
				public Object giveItemToCygnus() {
					return BigInteger.valueOf(state.getValue(BlockRedstoneRepeater.DELAY));
				}
				
				@Override
				public void acceptItemFromCygnus(Object item) {
					if(item instanceof BigInteger) {
						int value = ((BigInteger) item).intValue();
						if(value >= 1 && value <= 4) {
							world.setBlockState(pos, state.withProperty(BlockRedstoneRepeater.DELAY, value));
						}
					}
				}
			};
		} else return null;
	}
	
	@Override
	public void document(List<LexiconPage> pages) {
		pages.add(new PageHeadingIcon(
			new ItemStack(Blocks.UNPOWERED_REPEATER),
			"item.diode.name",
			"botania.page.incorporeal.cygnus_funnel.repeater"
		));
	}
}
