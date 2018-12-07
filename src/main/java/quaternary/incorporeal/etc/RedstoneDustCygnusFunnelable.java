package quaternary.incorporeal.etc;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;

import javax.annotation.Nullable;
import java.math.BigInteger;

//BlockCygnusFunnel hardcodes this response when asked about redstone dust.
//It's not my block to add an interface on to, and ASM is way overkill.
public class RedstoneDustCygnusFunnelable implements ICygnusFunnelable {
	public RedstoneDustCygnusFunnelable(IBlockState state) {
		this.level = BigInteger.valueOf(state.getValue(BlockRedstoneWire.POWER));
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
