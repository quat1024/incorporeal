package quaternary.incorporeal.cygnus.serializers;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;

public class CygnusItemStackSerializer implements ICygnusSerializer<ItemStack> {
	@Override
	public ResourceLocation getName() {
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
	public void writeToByteBuf(ByteBuf buf, ItemStack item) {
		ByteBufUtils.writeItemStack(buf, item);
	}
	
	@Override
	public ItemStack readFromByteBuf(ByteBuf buf) {
		return ByteBufUtils.readItemStack(buf);
	}
}
