package quaternary.incorporeal.cygnus.serializers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusSerializer;

import java.math.BigInteger;

public class CygnusBigIntegerSerializer implements ICygnusSerializer<BigInteger> {
	@Override
	public ResourceLocation getType() {
		return new ResourceLocation(Incorporeal.MODID, "big_integer");
	}
	
	@Override
	public Class<BigInteger> getSerializedClass() {
		return BigInteger.class;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, BigInteger item) {
		nbt.setByteArray("BigInt", item.toByteArray());
	}
	
	@Override
	public BigInteger readFromNBT(NBTTagCompound nbt) {
		//Quick hack because there's no way to get a byte array tag from a string (that i know of)
		//So you can't set it with entitydata etc
		if(nbt.hasKey("BigInt", Constants.NBT.TAG_LIST)) {
			NBTTagList list = nbt.getTagList("BigInt", Constants.NBT.TAG_INT);
			if(list.tagCount() == 0) return BigInteger.ZERO;
			
			byte[] bytes = new byte[list.tagCount()];
			for(int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte) list.getIntAt(i);
			}
			return new BigInteger(bytes);
		}
		
		return new BigInteger(nbt.getByteArray("BigInt"));
	}
	
	@Override
	public void writeToPacketBuffer(PacketBuffer buf, BigInteger item) {
		buf.writeBytes(item.toByteArray());
	}
	
	@Override
	public BigInteger readFromPacketBuffer(PacketBuffer buf) {
		return new BigInteger(buf.readByteArray());
	}
}
