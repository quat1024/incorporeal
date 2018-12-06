package quaternary.incorporeal.api.cygnus;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * A stack that the Cygnus system can push and pull items from.
 * You'll be dealing with this interface when you create custom Cygnus actions or conditions.
 * 
 * @author quaternary
 * @since 1.1
 */
public interface ICygnusStack {
	/////// Pushing
	
	/**
	 * Push an item onto the end of the stack.
	 * No-ops if the stack is completely full.
	 * TODO make it push items off the top instead, lol.
	 * 
	 * @param item the item to push onto the stack
	 * @throws NullPointerException if <pre>item</pre> is <pre>null</pre>
	 * @throws IllegalArgumentException if <pre>item</pre> is not a known Cygnus datatype
	 */
	<T> void push(@Nonnull T item);
	
	/////// Popping
	
	/**
	 * Remove and return an item from the top of the stack.
	 * @return <pre>Optional.of</pre> the item, or <pre>Optional.empty()</pre> if the stack is empty
	 */
	Optional<Object> pop();
	
	/**
	 * Delete items from the top of the stack.
	 * @param count how many items to erase
	 */
	void popDestroy(int count);
	
	/////// Peeking
	
	/**
	 * Returns the item on the top of the stack.
	 * It's like "pop", but non-destructive.
	 * @return <pre>Optional.of</pre> the item on top of the stack, or <pre>Optional.empty()</pre> if the stack is empty
	 */
	Optional<Object> peek();
	
	/**
	 * Return an item from somewhere in the middle of the stack.
	 * @param peekDepth how many items to look down from the top (0 = the top, 1 = the item underneath the top, etc)
	 * @return <pre>Optional.of</pre> the item located <pre>peekDepth</pre> down from the top of the stack, or <pre>Optional.empty()</pre> if the stack is shallower than that
	 */
	Optional<Object> peek(int peekDepth);
	
	/**
	 * Return the item on the top of the stack, but only if it is of the specified type.
	 * @param classs The class that the item must exactly match.
	 * @return <pre>Optional.of</pre> the item located on the top of the stack, or <pre>Optional.empty()</pre> if the stack is empty or if the item on top of the stack is not of the specified type
	 */
	<T> Optional<T> peekMatching(Class<T> classs);
	
	/**
	 * Return the item located <pre>peekDepth</pre> items down from the top of the stack, but only if the type matches the specified type. You know the drill by now.
	 */
	<T> Optional<T> peekMatching(Class<T> classs, int peekDepth);
	
	/////// Querying
	
	/**
	 * @return how many items are in the stack
	 */
	int depth();
	
	/**
	 * @return how many items this stack can hold before overflowing
	 */
	int maxDepth();
	
	/**
	 * @return <pre>true</pre> iff the stack contains no items
	 */
	boolean isEmpty();
	
	/**
	 * @return <pre>true</pre> iff the stack is completely filled with items
	 */
	boolean isFull();
	
	/////// Maintaining
	
	/**
	 * Erases the entire contents of the stack.
	 */
	void clear();
	
	/**
	 * @return <pre>true</pre> iff a stack modifying operation was performed after the last call to {@link ICygnusStack#clean()}
	 */
	boolean isDirty();
	
	/**
	 * @see ICygnusStack#isDirty() 
	 */
	void clean();
	
	/**
	 * @return a "shallow copy" of this stack
	 */
	ICygnusStack copy();
	
	/////// Saving
	
	/**
	 * @return an NBTTagCompound ready for on-disk serialization
	 */
	NBTTagCompound toNBT();
	
	/**
	 * Erase this stack and replace it with data stored in this NBTTagCompound
	 */
	void fromNBT(NBTTagCompound nbt);
	
	/**
	 * Write this stack to a PacketBuffer ready to be sent to clients over the network
	 */
	void toPacketBuffer(PacketBuffer buf);
	
	/**
	 * Erase this stack and replace it with the data read from this PacketBuffer
	 */
	void fromPacketBuffer(PacketBuffer buf);
}
