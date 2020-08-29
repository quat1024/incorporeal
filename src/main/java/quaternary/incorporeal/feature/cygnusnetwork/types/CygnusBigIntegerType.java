package quaternary.incorporeal.feature.cygnusnetwork.types;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.StringUtils;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusError;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.lexicon.page.PageText;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.List;

public class CygnusBigIntegerType implements ICygnusDatatype<BigInteger> {
	@Override
	public Class<BigInteger> getTypeClass() {
		return BigInteger.class;
	}
	
	@Override
	public String getTranslationKey() {
		return "incorporeal.cygnus.type.number";
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, BigInteger item) {
		nbt.setByteArray("BigInt", item.toByteArray());
	}
	
	@Override
	public BigInteger readFromNBT(NBTTagCompound nbt) {
		//Quick hack because there's no way to get a byte array tag from a string (that i know of)
		//So you can't set it with /entitydata etc
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
		byte[] bytes = item.toByteArray();
		//TODO length check?
		buf.writeByteArray(bytes);
	}
	
	@Override
	public BigInteger readFromPacketBuffer(PacketBuffer buf) {
		return new BigInteger(buf.readByteArray(1_000_000)); //Xd
	}
	
	private static final BigInteger topCap = new BigInteger("+" + StringUtils.repeat('9', 50), 10);
	private static final BigInteger bottomCap = new BigInteger("-" + StringUtils.repeat('9', 50), 10);
	
	private static final String TOO_SMALL_NUMBER = CygnusError.INVALID_MATH + ".too_small";
	private static final String TOO_BIG_NUMBER = CygnusError.INVALID_MATH + ".too_big";
	
	@Nullable
	@Override
	public Object getError(BigInteger item, ICygnusStack stack) {
		if(item.compareTo(bottomCap) < 0) {
			return new CygnusError(CygnusError.INVALID_MATH, TOO_SMALL_NUMBER);
		} else if(item.compareTo(topCap) > 0) {
			return new CygnusError(CygnusError.INVALID_MATH, TOO_BIG_NUMBER);
		} else return null;
	}
	
	@Override
	public boolean areEqual(BigInteger item1, BigInteger item2) {
		return item1.equals(item2);
	}
	
	@Override
	public int compare(BigInteger item1, BigInteger item2) {
		return item1.compareTo(item2);
	}

	@Override
	public boolean canCompare() {
		return true;
	}

	private static final BigInteger FIFTEEN = BigInteger.valueOf(15);
	
	@Override
	public int toComparator(BigInteger item) {
		if(item.compareTo(BigInteger.ONE) < 0) return 1;
		if(item.compareTo(FIFTEEN) > 0) return 15;
		else return item.intValue();
	}
	
	@Override
	public void document(List<LexiconPage> pages) {
		pages.add(new PageText("botania.page.incorporeal.cygnus_types.biginteger"));
	}
}
