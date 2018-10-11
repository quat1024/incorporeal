package quaternary.incorporeal.cygnus;

import com.google.common.base.Preconditions;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.Constants;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatypeInfo;
import quaternary.incorporeal.api.cygnus.ICygnusStack;

import java.util.Optional;

public class CygnusStack implements ICygnusStack {
	public CygnusStack(int maxDepth) {
		this.maxDepth = maxDepth;
		clear();
	}
	
	private int maxDepth;
	private Object[] stack;
	private int cursor = 0;
	
	boolean dirty = false;
	
	public <T> void push(T item) {
		if(cursor == maxDepth) return;
		
		Preconditions.checkNotNull(item);
		
		//This cast is sketchy
		ICygnusDatatypeInfo<T> datatype = Incorporeal.API.getCygnusDatatypeInfoRegistry().getDatatypeForClass((Class<T>) item.getClass());
		Object error = datatype.getError(item, this);
		
		if(error == null) {
			stack[cursor] = item;
			cursor++;
			dirty = true;
		} else {
			push(error);
		}
	}
	
	public Optional<Object> pop() {
		if(cursor == 0) return Optional.empty();
		
		Object removedObject = stack[cursor];
		stack[cursor] = null;
		cursor--;
		dirty = true;
		return Optional.of(removedObject);
	}
	
	public void popDestroy(int popDepth) {
		if(cursor < popDepth) {
			throw new IllegalStateException("Stack underflow using popDestroy - programmer error");
		}
		
		if(popDepth == 0) return;
		
		for(int i = 0; i < popDepth; i++) {
			stack[cursor] = null;
			cursor--;
		}
		
		dirty = true;
	}
	
	//Will still pop if the class doesn't match!!
	public <T> Optional<T> popMatching(Class<T> matchingClass) {
		return (Optional<T>) pop().filter(o -> o.getClass().equals(matchingClass));
	}
	
	public Optional<Object> peek() {
		return peek(0);
	}
	
	public Optional<Object> peek(int peekDepth) {
		if(cursor - peekDepth - 1 < 0) return Optional.empty();
		dirty = true;
		return Optional.of(stack[cursor - peekDepth - 1]);
	}
	
	public <T> Optional<T> peekMatching(Class<T> matchingClass) {
		return peekMatching(matchingClass, 0);
	}
	
	public <T> Optional<T> peekMatching(Class<T> matchingClass, int peekDepth) {
		return (Optional<T>) peek(peekDepth).filter(o -> o.getClass().equals(matchingClass));
	}
	
	public int depth() {
		return cursor;
	}
	
	public boolean isEmpty() {
		return cursor == 0;
	}
	
	public boolean isFull() {
		return cursor == maxDepth;
	}
	
	public int maxDepth() {
		return maxDepth;
	}
	
	public void clear() {
		cursor = 0;
		stack = new Object[maxDepth];
		dirty = true;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void clean() {
		dirty = false;
	}
	
	public CygnusStack copy() {
		CygnusStack other = new CygnusStack(maxDepth);
		//TODO make sure this doesn't push stuff backwards, lol.
		for(int i = 0; i < cursor; i++) {
			other.push(stack[i]);
		}
		return other;
	}
	
	//serializing!
	
	public NBTTagCompound toNBT() {
		NBTTagCompound ret = new NBTTagCompound();
		
		ret.setInteger("MaxDepth", maxDepth);
		
		NBTTagList nbtList = new NBTTagList();
		for(int i = 0; i < cursor; i++) {
			NBTTagCompound entry = new NBTTagCompound();
			Incorporeal.API.getCygnusDatatypeInfoRegistry().writeToNBT(entry, stack[i]);
			nbtList.appendTag(entry);
		}
		
		ret.setTag("Stack", nbtList);
		
		return ret;
	}
	
	public void fromNBT(NBTTagCompound nbt) {
		maxDepth = nbt.getInteger("MaxDepth");
		clear();
		
		NBTTagList nbtList = nbt.getTagList("Stack", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound entry = nbtList.getCompoundTagAt(i);
			stack[i] = Incorporeal.API.getCygnusDatatypeInfoRegistry().readFromNBT(entry);
		}
		
		cursor = nbtList.tagCount();
	}
	
	public void toPacketBuffer(PacketBuffer buf) {
		buf.writeInt(maxDepth);
		buf.writeInt(cursor);
		for(int i = 0; i < cursor; i++) {
			Incorporeal.API.getCygnusDatatypeInfoRegistry().writeToPacketBuffer(buf, stack[i]);
		}
	}
	
	public void fromPacketBuffer(PacketBuffer buf) {
		maxDepth = buf.readInt();
		clear();
		
		int stackAmount = buf.readInt();
		for(int i = 0; i < stackAmount; i++) {
			stack[i] = Incorporeal.API.getCygnusDatatypeInfoRegistry().readFromPacketBuffer(buf);
		}
	}
}
