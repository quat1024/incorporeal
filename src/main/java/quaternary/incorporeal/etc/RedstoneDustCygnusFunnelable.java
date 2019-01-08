package quaternary.incorporeal.etc;

import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;

import javax.annotation.Nullable;
import java.math.BigInteger;

//BlockCygnusFunnel hardcodes this response when asked about redstone dust.
//It's not my block to add an interface on to, and ASM is way overkill.
public class RedstoneDustCygnusFunnelable implements ICygnusFunnelable {
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
