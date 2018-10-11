package quaternary.incorporeal.cygnus.types;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatypeInfo;

import java.io.IOException;

public class CygnusItemStackType implements ICygnusDatatypeInfo<ItemStack> {
	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Incorporeal.MODID, "item_stack");
	}
	
	@Override
	public Class<ItemStack> getTypeClass() {
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
	
	@Override
	public boolean areEqual(ItemStack item1, ItemStack item2) {
		return ItemStack.areItemStacksEqual(item1, item2);
	}
	
	@Override
	public boolean canCompare() {
		return true;
	}
	
	@Override
	public int compare(ItemStack item1, ItemStack item2) {
		return Integer.compare(item1.getCount(), item2.getCount());
	}
}
