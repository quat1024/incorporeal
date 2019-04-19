package quaternary.incorporeal.etc;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

//An optional with two items.
//Either one can be available, or both.
//Is this overkill? Yeah, probably.
public class BiOptional<A, B> {
	private BiOptional(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	private BiOptional() {
		this(null, null);
	}
	
	public static <A, B> BiOptional<A, B> of(A a, B b) {
		return new BiOptional<>(a, b);
	}
	
	public static <A, B> BiOptional<A, B> empty() {
		return new BiOptional<>();
	}
	
	public static <A, B> BiOptional<A, B> ofFirst(A a) {
		return new BiOptional<>(a, null);
	}
	
	public static <A, B> BiOptional<A, B> ofSecond(B b) {
		return new BiOptional<>(null, b);
	}
	
	private final A a;
	private final B b;
	
	//Hey, look, it's chainable!
	public BiOptional<A, B> ifBothPresent(BiConsumer<A, B> func) {
		if(a != null && b != null) func.accept(a, b);
		return this;
	}
	
	public BiOptional<A, B> ifFirstPresent(Consumer<A> func) {
		if(a != null) func.accept(a);
		return this;
	}
	
	public BiOptional<A, B> ifSecondPresent(Consumer<B> func) {
		if(b != null) func.accept(b);
		return this;
	}
	
	public BiOptional<A, B> ifFirstNotPresent(Runnable func) {
		if(a == null) func.run();
		return this;
	}
	
	public BiOptional<A, B> ifSecondNotPresent(Runnable func) {
		if(b == null) func.run();
		return this;
	}
	
	public BiOptional<A, B> ifEitherPresent(Runnable func) {
		if(a != null || b != null) func.run();
		return this;
	}
	
	public BiOptional<A, B> ifNeitherPresent(Runnable func) {
		if(a == null && b == null) func.run();
		return this;
	}
	
	public BiOptional<A, B> ifEitherNotPresent(Runnable func) {
		if(a == null || b == null) func.run();
		return this;
	}
	
	//Narrowers
	public Optional<A> first() {
		return Optional.ofNullable(a);
	}
	
	public Optional<B> second() {
		return Optional.ofNullable(b);
	}
	
	//Getters
	public A getFirst() {
		if(a == null) throw new NullPointerException();
		return a;
	}
	
	public B getSecond() {
		if(b == null) throw new NullPointerException();
		return b;
	}
	
	@Nullable
	public A getFirstNullable() {
		return a;
	}
	
	@Nullable
	public B getSecondNullable() {
		return b;
	}
	
	//Idk
	public boolean hasFirst() {
		return a != null;
	}
	
	public boolean hasSecond() {
		return b != null;
	}
	
	public boolean hasBoth() {
		return a != null && b != null;
	}
}
