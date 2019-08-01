package quaternary.incorporeal.core.sortme;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.feature.corporetics.net.MessageSparkleLine;
import quaternary.incorporeal.feature.cygnusnetwork.net.MessageScrollItem;
import quaternary.incorporeal.feature.skytouching.net.MessageSkytouchingEffect;

public final class IncorporeticPacketHandler {
	private IncorporeticPacketHandler () {}
	
	private static SimpleNetworkWrapper NET;
	
	public static void init() {
		NET = new SimpleNetworkWrapper(Incorporeal.MODID);
		
		NET.registerMessage(MessageSparkleLine.Handler.class, MessageSparkleLine.class, 0, Side.CLIENT);
		NET.registerMessage(MessageSkytouchingEffect.Handler.class, MessageSkytouchingEffect.class, 1, Side.CLIENT);
		
		NET.registerMessage(MessageScrollItem.Handler.class, MessageScrollItem.class, 2, Side.SERVER);
	}
	
	public static void sendToAllTracking(IIncorporeticMessage message, World world, BlockPos point) {
		NET.sendToAllTracking(message, new NetworkRegistry.TargetPoint(world.provider.getDimension(), point.getX(), point.getY(), point.getZ(), 0));
	}
	
	public static void sendToServer(IIncorporeticMessage message) {
		NET.sendToServer(message);
	}
	
	//marker interface to make sending other modded packets through this net wrapper a syntax error
	public interface IIncorporeticMessage extends IMessage {}
}
