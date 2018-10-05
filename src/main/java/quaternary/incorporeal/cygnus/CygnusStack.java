package quaternary.incorporeal.cygnus;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import quaternary.incorporeal.Incorporeal;

import java.util.Optional;

public class CygnusStack {
	public CygnusStack(int maxDepth) {
		this.maxDepth = maxDepth;
		stack = new Object[maxDepth];
	}
	
	private int maxDepth;
	private Object[] stack;
	
	//One ahead of the index of the most recently pushed item.
	//Or, the total number of items in the stack.
	//One and the same.
	int cursor = 0;
	
	public boolean push(Object item) {
		if(item.getClass() == Optional.class) throw new IllegalArgumentException("Can't push Optionals onto the stack - programmer error");
		if(cursor == maxDepth) return false;
		stack[cursor] = item;
		cursor++;
		return true;
	}
	
	public Optional<Object> pop() {
		if(cursor == 0) return Optional.empty();
		cursor--;
		return Optional.of(stack[cursor]);
	}
	
	public void popDestroy(int popDepth) {
		cursor -= popDepth;
		if(cursor < 0) throw new IllegalStateException("Stack underflow using popDestroy - programmer error");
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
		stack = new Object[maxDepth];
		cursor = 0;
	}
	
	//serializing!
	
	public NBTTagCompound toNBT() {
		NBTTagCompound ret = new NBTTagCompound();
		ret.setInteger("Version", 1); // ...?
		
		ret.setInteger("Cursor", cursor);
		ret.setInteger("MaxDepth", maxDepth);
		
		NBTTagList nbtList = new NBTTagList();
		for(int i = 0; i < cursor; i++) {
			if(stack[i] == null) continue;
			NBTTagCompound entry = new NBTTagCompound();
			entry.setInteger("Depth", i);
			Incorporeal.API.getCygnusSerializerRegistry().writeToNBT(entry, stack[i]);
			nbtList.appendTag(entry);
		}
		
		ret.setTag("Stack", nbtList);
		
		return ret;
	}
	
	public void fromNBT(NBTTagCompound nbt) {
		if(nbt.getInteger("Version") == 1) {
			stack = new Object[nbt.getInteger("MaxDepth")];
			cursor = nbt.getInteger("Cursor");
			
			NBTTagList nbtList = nbt.getTagList("Stack", Constants.NBT.TAG_COMPOUND);
			for(int i = 0; i < nbtList.tagCount(); i++) {
				NBTTagCompound entry = nbtList.getCompoundTagAt(i);
				stack[entry.getInteger("Depth")] = Incorporeal.API.getCygnusSerializerRegistry().readFromNBT(entry);
			}
		} else {
			Incorporeal.LOGGER.info("Problem deserializing NBT compound: Mismatched version :( " + nbt);
			clear();
		}
	}
	
	public void writeToByteBuf(ByteBuf buf) {
		throw new RuntimeException("NYI");
	}
	
	public void readFromByteBuf() {
		throw new RuntimeException("NYI");
	}
}
