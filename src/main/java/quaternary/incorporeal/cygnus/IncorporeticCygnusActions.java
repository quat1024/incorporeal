package quaternary.incorporeal.cygnus;

import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.ISimpleRegistry;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.etc.helper.CorporeaHelper2;
import vazkii.botania.api.corporea.CorporeaRequest;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class IncorporeticCygnusActions {
	private IncorporeticCygnusActions() {}
	
	public static Consumer<ICygnusStack> NOTHING;
	
	public static void registerCygnusActions() {
		ISimpleRegistry<Consumer<ICygnusStack>> reg = Incorporeal.API.getCygnusStackActionRegistry();
		
		NOTHING = stack -> {};
		reg.register(new ResourceLocation(Incorporeal.MODID, "nothing"), NOTHING);
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "duplicate"), stack -> {
			Optional<Object> peek = stack.peek();
			if(peek.isPresent()) {
				stack.push(peek.get());
			} else stack.push(new CygnusError());
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "number_add"), stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, BigInteger::add);
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "number_subtract"), stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, BigInteger::subtract);
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "number_multiply"), stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, BigInteger::multiply);
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "number_divide"), stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, (under, top) -> {
				return top.equals(BigInteger.ZERO) ? new CygnusError(CygnusError.INVALID_MATH, CygnusError.INVALID_MATH + ".divide_by_0") : under.divide(top);
			});
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "number_remainder"), stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, (under, top) -> {
				return top.equals(BigInteger.ZERO) ? new CygnusError(CygnusError.INVALID_MATH, CygnusError.INVALID_MATH + ".divide_by_0") : under.remainder(top);
			});
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "request_get_count"), stack -> {
			pushIfMatching(CorporeaRequest.class, stack, req -> BigInteger.valueOf(req.count));
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "request_set_count"), stack -> {
			pushIfMatching(CorporeaRequest.class, BigInteger.class, stack, (req, cnt) -> {
				CorporeaRequest copy = CorporeaHelper2.copyCorporeaRequest(req);
				copy.count = cnt.intValue();
				return copy;
			});
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "request_set_item"), stack -> {
			pushIfMatching(CorporeaRequest.class, CorporeaRequest.class, stack, (donor, acceptor) -> {
				return new CorporeaRequest(donor.matcher, donor.checkNBT, acceptor.count);
			});
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "stack_get_depth"), stack -> {
			pushIfMatching(CygnusStack.class, stack, stackstack -> {
				return BigInteger.valueOf(stackstack.depth());
			});
		});
	}
	
	//Quick helper funcs
	private static <TOP> void pushIfMatching(Class<TOP> topClass, ICygnusStack stack, Function<TOP, Object> resultFactory) {
		Optional<TOP> x = stack.peekMatching(topClass, 0);
		if(x.isPresent()) {
			stack.popDestroy(1);
			stack.push(resultFactory.apply(x.get()));
		} else {
			if(stack.depth() < 1) stack.push(new CygnusError(CygnusError.UNDERFLOW));
			else stack.push(new CygnusError(CygnusError.MISMATCH));
		}
	}
	
	private static <TOP, UNDER> void pushIfMatching(Class<UNDER> underClass, Class<TOP> topClass, ICygnusStack stack, BiFunction<UNDER, TOP, Object> resultFactory) {
		Optional<TOP> x = stack.peekMatching(topClass, 0);
		Optional<UNDER> y = stack.peekMatching(underClass, 1);
		if(x.isPresent() && y.isPresent()) {
			stack.popDestroy(2);
			stack.push(resultFactory.apply(y.get(), x.get()));
		} else {
			if(stack.depth() < 2) stack.push(new CygnusError(CygnusError.UNDERFLOW));
			else stack.push(new CygnusError(CygnusError.MISMATCH));
		}
	}
}
