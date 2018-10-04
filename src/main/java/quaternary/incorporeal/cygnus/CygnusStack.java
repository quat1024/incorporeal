package quaternary.incorporeal.cygnus;

import java.util.Optional;

public class CygnusStack {
	public CygnusStack(int maxDepth) {
		this.maxDepth = maxDepth;
		stack = new Object[maxDepth];
	}
	
	private final int maxDepth;
	private final Object[] stack;
	
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
		/*
		//TODO: It should be fine to not clear the array, since the cursor passes over it?
		ICygnusItem itemAt = stack[cursor];
		stack[cursor] = null;
		*/
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
}
