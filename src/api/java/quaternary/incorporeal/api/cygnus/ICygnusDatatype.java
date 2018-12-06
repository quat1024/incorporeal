package quaternary.incorporeal.api.cygnus;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.api.IIncorporealAPI;

import javax.annotation.Nullable;

/**
 * This class tells Incorporeal everything it needs to know about a Cygnus datatype.
 * It contains serialization functions for saving to NBT and sending over the network,
 * as well as a few miscellaneous functions relating to gameplay.
 * 
 * @see IIncorporealAPI#getCygnusDatatypeRegistry() 
 * @see ICygnusDatatypeRegistry#registerDatatype(ICygnusDatatype) 
 *
 * @author quaternary
 * @since 1.1
 */
public interface ICygnusDatatype<T> {
	/**
	 * @return A globally-unique ResourceLocation that describes this Cygnus serializer.
	 */
	ResourceLocation getResourceLocation();
	
	/**
	 * @return The class that this Cygnus serializer is in charge of.
	 */
	Class<T> getTypeClass();
	
	/**
	 * Write this item to an NBT tag compound.
	 * You may use any keys other than "Type", which is used internally.
	 * 
	 * For example: <pre>nbt.setInteger("Int", item);</pre>
	 * 
	 * @param nbt The NBT tag compound to write to.
	 * @param item The item to write to the compound.
	 */
	void writeToNBT(NBTTagCompound nbt, T item);
	
	/**
	 * Read an item from the NBT tag compound.
	 * @param nbt The NBT tag compound to read items from.
	 * @return The item.
	 */
	T readFromNBT(NBTTagCompound nbt);
	
	/**
	 * Serialize this item to a PacketBuffer.
	 * @param buf The PacketBuffer to write to.
	 * @param item The item to write to the buffer.
	 */
	void writeToPacketBuffer(PacketBuffer buf, T item);
	
	/**
	 * Deserialize an item from a PacketBuffer.
	 * @param buf The PacketBuffer to read from.
	 * @return The item read from the buffer.
	 */
	T readFromPacketBuffer(PacketBuffer buf);
	
	/**
	 * Validate the item before it gets pushed on to the stack.
	 * If <pre>null/pre> is returned, the item will be pushed onto the stack normally.
	 * However, if an <pre>Object</pre> is returned, it will be pushed onto the stack instead.
	 * This can be used to push errors onto the stack, if the item is somehow invalid
	 * (too large BigIntegers, too deeply nested stacks, etc).
	 * 
	 * @return an error to push on to the stack instead of this item, or <pre>null</pre> if there is no error
	 */
	@Nullable
	default Object getError(T item, ICygnusStack stack) {
		return null;
	}
	
	/**
	 * Are these two items identical?
	 * Falls back to {@link Object#equals(Object)}.
	 * @return whether the items are functionally identical
	 */
	default boolean areEqual(T item1, T item2) {
		return item1.equals(item2);
	}
	
	/**
	 * Can items of this type be compared to other items, of the same type?
	 * This will enable Cygnus Crystal Cube actions such as "greater than" and "less than" to function on items of this type.
	 * @return whether it makes sense to compare two items of this datatype
	 */
	default boolean canCompare() {
		return false;
	}
	
	/**
	 * Compare the two items.
	 * Will never be called if {@link ICygnusDatatype#canCompare()} is <pre>false</pre>.
	 * 
	 * @return a number >= 1 if <pre>item1</pre> is <i>greater than</i> <pre>item2</pre>, a number <= 1 if <pre>item1</pre> is <i>smaller than</i> <pre>item2</pre>, or 0 if they are equal
	 */
	default int compare(T item1, T item2) {
		return 0;
	}
	
	/**
	 * Convert this item to a string.
	 * Falls back to {@link Object#toString()} if not overridden.
	 * You should override this, since that usually looks stupid.
	 * 
	 * @param item The item to convert into a friendly suitable-for-display string.
	 */
	default String toString(T item) {
		return item.toString();
	}
	
	/**
	 * Java generics are stupid. Don't override
	 */
	@SuppressWarnings("unchecked")
	default boolean areEqualUnchecked(Object item1, Object item2) {
		return areEqual((T) item1, (T) item2);
	}
	
	/**
	 * Java generics are stupid. Don't override
	 */
	@SuppressWarnings("unchecked")
	default int compareUnchecked(Object item1, Object item2) {
		return compare((T) item1, (T) item2);
	}
	
	/**
	 * Java genetics are stupid. Don't override
	 */
	default String toStringUnchecked(Object item) {
		return toString((T) item);
	}
}
