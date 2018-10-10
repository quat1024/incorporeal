package quaternary.incorporeal.cygnus.serializers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;
import quaternary.incorporeal.cygnus.CygnusError;

public class CygnusErrorSerializer implements ICygnusSerializer<CygnusError> {
	@Override
	public ResourceLocation getType() {
		return new ResourceLocation(Incorporeal.MODID, "cygnus_error");
	}
	
	@Override
	public Class<CygnusError> getSerializedClass() {
		return CygnusError.class;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, CygnusError item) {
		nbt.setString("ErrorMessage", item.errorTranslationKey);
		if(item.errorFormat.length > 0) {
			NBTTagList formatList = new NBTTagList();
			for(String s : item.errorFormat) {
				formatList.appendTag(new NBTTagString(s));
			}
			
			nbt.setTag("ErrorParams", formatList);
		}
	}
	
	@Override
	public CygnusError readFromNBT(NBTTagCompound nbt) {
		String message = nbt.getString("ErrorMessage");
		if(nbt.hasKey("ErrorParams")) {
			NBTTagList formatList = nbt.getTagList("ErrorParams", Constants.NBT.TAG_STRING);
			String[] formats = new String[formatList.tagCount()];
			for(int i = 0; i < formatList.tagCount(); i++) {
				formats[i] = formatList.getStringTagAt(i);
			}
			return new CygnusError(message, formats);
		} else return new CygnusError(message);
	}
	
	@Override
	public void writeToPacketBuffer(PacketBuffer buf, CygnusError item) {
		buf.writeString(item.errorTranslationKey);
		buf.writeInt(item.errorFormat.length);
		for(String s : item.errorFormat) {
			buf.writeString(s);
		}
	}
	
	@Override
	public CygnusError readFromPacketBuffer(PacketBuffer buf) {
		String key = buf.readString(CygnusError.MAX_KEY_LENGTH);
		int formatArguments = buf.readInt();
		
		if(formatArguments == 0) return new CygnusError(key);
		
		String[] formats = new String[formatArguments];
		for(int i = 0; i < formatArguments; i++) {
			formats[i] = buf.readString(CygnusError.MAX_KEY_LENGTH);
		}
		
		return new CygnusError(key, formats);
	}
}
