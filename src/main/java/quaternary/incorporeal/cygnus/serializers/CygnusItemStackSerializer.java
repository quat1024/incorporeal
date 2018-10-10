package quaternary.incorporeal.cygnus.serializers;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;

import java.io.IOException;

public class CygnusItemStackSerializer implements ICygnusSerializer<ItemStack> {
	@Override
	public ResourceLocation getType() {
		return new ResourceLocation(Incorporeal.MODID, "item_stack");
	}
	
	@Override
	public Class<ItemStack> getSerializedClass() {
		return ItemStack.class;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, ItemStack item) {
		nbt.setTag("Stack", item.serializeNBT());
	}
	
	@Override
	public ItemStack readFromNBT(NBTTagCompound nbt) {
		return new ItemStack(nbt.getCompoundTag("Stack"));
	}
	
	@Override
	public void writeToPacketBuffer(PacketBuffer buf, ItemStack item) {
		buf.writeItemStack(item);
	}
	
	@Override
	public ItemStack readFromPacketBuffer(PacketBuffer buf) {
		try {
			return buf.readItemStack();
		} catch (IOException e) {
			Incorporeal.LOGGER.error("Problem deserializing itemstack from packetbuffer!");
			throw new RuntimeException(e);
		}
	}
}
