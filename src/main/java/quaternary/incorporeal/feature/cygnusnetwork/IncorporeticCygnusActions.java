package quaternary.incorporeal.feature.cygnusnetwork;

import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusAction;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.core.etc.helper.CorporeaHelper2;
import vazkii.botania.api.corporea.CorporeaRequest;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.lexicon.page.PageText;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class IncorporeticCygnusActions {
	private IncorporeticCygnusActions() {
	}
	
	public static ICygnusAction NOTHING;
	
	public static void registerCygnusActions() {
		NOTHING = stack -> {};
		register("nothing", NOTHING);
		
		registerWithLexicon("duplicate", stack -> {
			stack.push(stack.peek().orElseGet(() -> new CygnusError(CygnusError.UNDERFLOW)));
		});
		
		registerWithLexicon("number_add", stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, BigInteger::add);
		});
		
		registerWithLexicon("number_subtract", stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, BigInteger::subtract);
		});
		
		registerWithLexicon("number_multiply", stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, BigInteger::multiply);
		});
		
		registerWithLexicon("number_divide", stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, (under, top) -> {
				return top.equals(BigInteger.ZERO) ? new CygnusError(CygnusError.INVALID_MATH, CygnusError.INVALID_MATH + ".divide_by_0") : under.divide(top);
			});
		});
		
		registerWithLexicon("number_remainder", stack -> {
			pushIfMatching(BigInteger.class, BigInteger.class, stack, (under, top) -> {
				return top.equals(BigInteger.ZERO) ? new CygnusError(CygnusError.INVALID_MATH, CygnusError.INVALID_MATH + ".divide_by_0") : under.remainder(top);
			});
		});
		
		registerWithLexicon("request_get_count", stack -> {
			pushIfMatching(CorporeaRequest.class, stack, req -> BigInteger.valueOf(req.count));
		});
		
		registerWithLexicon("request_set_count", stack -> {
			pushIfMatching(CorporeaRequest.class, BigInteger.class, stack, (req, cnt) -> {
				CorporeaRequest copy = CorporeaHelper2.copyCorporeaRequest(req);
				copy.count = cnt.intValue();
				return copy;
			});
		});
		
		registerWithLexicon("request_set_item", stack -> {
			pushIfMatching(CorporeaRequest.class, CorporeaRequest.class, stack, (donor, acceptor) -> {
				return new CorporeaRequest(donor.matcher, donor.checkNBT, acceptor.count);
			});
		});
		
		registerWithLexicon("stack_get_depth", stack -> {
			pushIfMatching(CygnusStack.class, stack, stackstack -> {
				return BigInteger.valueOf(stackstack.depth());
			});
		});
	}
	
	private static void registerWithLexicon(String name, Consumer<ICygnusStack> cons) {
		register(name, new ICygnusAction() {
			@Override
			public void accept(ICygnusStack stack) {
				cons.accept(stack);
			}
			
			@Override
			public void document(List<LexiconPage> pages) {
				pages.add(new PageText("botania.page.incorporeal.cygnus_word.action." + name));
			}
		});
	}
	
	private static void register(String name, ICygnusAction action) {
		Incorporeal.API.getCygnusStackActionRegistry().register(new ResourceLocation(Incorporeal.MODID, name), action);
	}
	
	//Quick helper funcs
	private static <TOP> void pushIfMatching(Class<TOP> topClass, ICygnusStack stack, Function<TOP, Object> resultFactory) {
		stack.push(stack.peekMatching(topClass).flatMap(top -> {
				stack.popDestroy(1);
				return Optional.of(resultFactory.apply(top));
			}).orElseGet(() ->
				new CygnusError(stack.depth() < 1 ? CygnusError.UNDERFLOW : CygnusError.MISMATCH))
		);
	}
	
	private static <TOP, UNDER> void pushIfMatching(Class<UNDER> underClass, Class<TOP> topClass, ICygnusStack stack, BiFunction<UNDER, TOP, Object> resultFactory) {
		stack.push(stack.peekMatching(topClass, 0).flatMap(top ->
			stack.peekMatching(underClass, 1).flatMap(under -> {
				stack.popDestroy(2);
				return Optional.of(resultFactory.apply(under, top));
			})
		).orElseGet(() ->
			new CygnusError(stack.depth() < 2 ? CygnusError.UNDERFLOW : CygnusError.MISMATCH)
		));
	}
}
