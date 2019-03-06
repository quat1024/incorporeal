package quaternary.incorporeal.net;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;

public final class IncorporeticPacketHandler {
	private IncorporeticPacketHandler () {}
	
	private static SimpleNetworkWrapper NET;
	
	public static void init() {
		NET = new SimpleNetworkWrapper(Incorporeal.MODID);
		
		NET.registerMessage(MessageSparkleLine.Handler.class, MessageSparkleLine.class, 0, Side.CLIENT);
		NET.registerMessage(MessageSkytouchingEffect.Handler.class, MessageSkytouchingEffect.class, 1, Side.CLIENT);
	}
	
	public static void sendToAllTracking(IIncorporeticMessage message, World world, BlockPos point) {
		NET.sendToAllTracking(message, new NetworkRegistry.TargetPoint(world.provider.getDimension(), point.getX(), point.getY(), point.getZ(), 0));
	}
	
	//marker interface to make sending other modded packets through this net wrapper a syntax error
	interface IIncorporeticMessage extends IMessage {}
}
