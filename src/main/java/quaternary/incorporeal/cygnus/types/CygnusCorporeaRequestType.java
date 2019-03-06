package quaternary.incorporeal.cygnus.types;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.etc.helper.CorporeaHelper2;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.CorporeaRequest;

import java.io.IOException;
import java.util.List;

public class CygnusCorporeaRequestType implements ICygnusDatatype<CorporeaRequest> {
	@Override
	public Class<CorporeaRequest> getTypeClass() {
		return CorporeaRequest.class;
	}
	
	@Override
	public String getTranslationKey() {
		return "incorporeal.cygnus.type.corporea_request";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public List<String> describe(CorporeaRequest thing) {
		StringBuilder b = new StringBuilder();
		if(thing.matcher instanceof ItemStack) {
			b.append(TextFormatting.AQUA);
		} else {
			b.append(TextFormatting.ITALIC);
		}
		
		b.append("\"");
		b.append(toString(thing));
		b.append("\"");
		b.append(TextFormatting.RESET);
		
		return ImmutableList.of(b.toString());
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, CorporeaRequest item) {
		nbt.setBoolean("Strict", item.checkNBT);
		nbt.setInteger("Count", item.count);
		if(item.matcher instanceof ItemStack) {
			nbt.setTag("ItemStack", ((ItemStack)item.matcher).serializeNBT());
		} else if(item.matcher instanceof String) {
			nbt.setString("String", (String)item.matcher);
		} else {
			throw new IllegalArgumentException("CorporeaRequest containing something not an itemstack or a string?");
		}
	}
	
	@Override
	public CorporeaRequest readFromNBT(NBTTagCompound nbt) {
		boolean strict = nbt.getBoolean("Strict");
		int count = nbt.getInteger("Count");
		if(nbt.hasKey("ItemStack")) {
			return new CorporeaRequest(new ItemStack(nbt.getCompoundTag("ItemStack")), strict, count);
		} else if(nbt.hasKey("String")) {
			return new CorporeaRequest(nbt.getString("String"), strict, count);
		} else {
			Incorporeal.LOGGER.error("Tried to deserialize CorporeaRequest without an ItemStack or String. Defaulting to empty request");
			return new CorporeaRequest(ItemStack.EMPTY, false, 0);
		}
	}
	
	@Override
	public void writeToPacketBuffer(PacketBuffer buf, CorporeaRequest item) {
		buf.writeBoolean(item.checkNBT);
		buf.writeInt(item.count);
		if(item.matcher instanceof ItemStack) {
			buf.writeBoolean(true);
			buf.writeItemStack((ItemStack) item.matcher);
		} else if(item.matcher instanceof String) {
			buf.writeBoolean(false);
			buf.writeString((String)item.matcher);
		} else {
			throw new IllegalArgumentException("CorporeaRequest containing something not an itemstack or a string?");
		}
	}
	
	@Override
	public CorporeaRequest readFromPacketBuffer(PacketBuffer buf) {
		boolean strict = buf.readBoolean();
		int count = buf.readInt();
		boolean isStack = buf.readBoolean();
		if(isStack) {
			try {
				return new CorporeaRequest(buf.readItemStack(), strict, count);
			} catch (IOException e) {
				Incorporeal.LOGGER.error("Problem deserializing CorporeaRequest from packet, defaulting to empty one ", e);
				return new CorporeaRequest(ItemStack.EMPTY, false, 0);
			}
		} else {
			return new CorporeaRequest(buf.readString(32767), strict, count);
		}
	}
	
	@Override
	public boolean areEqual(CorporeaRequest item1, CorporeaRequest item2) {
		if(item1.count != item2.count) return false;
		//TODO nbt check? that is opaque to users
		if(item1.matcher instanceof ItemStack && item2.matcher instanceof ItemStack) {
			return ItemStack.areItemStacksEqual((ItemStack) item1.matcher, (ItemStack) item2.matcher);
		} else if(item1.matcher instanceof String && item2.matcher instanceof String) {
			return item1.matcher.equals(item2.matcher);
		} else if(item1.matcher instanceof ItemStack && item2.matcher instanceof String) {
			//Users shouldn't have to differentiate between stack requests and string requests.
			//This is close enough.
			return ((ItemStack) item1.matcher).getDisplayName().equalsIgnoreCase((String) item2.matcher);
		} else if(item1.matcher instanceof String && item2.matcher instanceof ItemStack) {
			return ((ItemStack) item2.matcher).getDisplayName().equalsIgnoreCase((String) item1.matcher);
		} else return false;
	}
	
	@Override
	public boolean canCompare() {
		return true;
	}
	
	@Override
	public int compare(CorporeaRequest item1, CorporeaRequest item2) {
		return Integer.compare(item1.count, item2.count);
	}
	
	@Override
	public String toString(CorporeaRequest item) {
		return CorporeaHelper2.requestToString(item);
	}
	
	@Override
	public int toComparator(CorporeaRequest item) {
		int x = CorporeaHelper.signalStrengthForRequestSize(item.count);
		return x == 0 ? 1 : x;
	}
}
