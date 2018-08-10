package quaternary.incorporeal.etc.helper;

import net.minecraft.util.math.MathHelper;

public final class EtcHelpers {
	private EtcHelpers() {}
	
	public static float sinDegrees(float degreesIn) {
		return MathHelper.sin((degreesIn % 360) * (float) (Math.PI / 180f));
	}
	
	public static float cosDegrees(float degreesIn) {
		return MathHelper.cos((degreesIn % 360) * (float) (Math.PI / 180f));
	}
}
