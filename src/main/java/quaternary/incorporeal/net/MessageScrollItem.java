package quaternary.incorporeal.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import quaternary.incorporeal.api.IScrollableItem;

public class MessageScrollItem implements IncorporeticPacketHandler.IIncorporeticMessage {
	public MessageScrollItem() {}
	
	public MessageScrollItem(int dwheel) {
		this.dwheel = dwheel;
	}
	
	public int dwheel;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dwheel);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		dwheel = buf.readInt();
	}
	
	public static class Handler implements IMessageHandler<MessageScrollItem, IMessage> {
		@Override
		public IMessage onMessage(MessageScrollItem m, MessageContext ctx) {
			EntityPlayerMP playerMP = ctx.getServerHandler().player;
			//noinspection Convert2Lambda
			playerMP.getServerWorld().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					InventoryPlayer inv = playerMP.inventory;
					
					int currentIndex = inv.currentItem;
					ItemStack currentStack = inv.getCurrentItem();
					
					if(currentStack.getItem() instanceof IScrollableItem) {
						ItemStack newStack = ((IScrollableItem) currentStack.getItem()).scrollChange(currentStack, m.dwheel);
						inv.setInventorySlotContents(currentIndex, newStack);
						inv.markDirty();
					}
				}
			});
			
			return null;
		}
	}
}
