package quaternary.incorporeal.feature.cygnusnetwork;

import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusCondition;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;
import quaternary.incorporeal.feature.cygnusnetwork.lexicon.PageHeadingIcon;
import vazkii.botania.api.lexicon.LexiconPage;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class IncorporeticCygnusConditions {
	private IncorporeticCygnusConditions() {
	}
	
	public static ICygnusCondition NOTHING;
	
	public static void registerCygnusConditions() {
		NOTHING = registerWithLexicon("nothing", stack -> false);
		
		registerWithLexicon("empty_stack", ICygnusStack::isEmpty);
		registerWithLexicon("full_stack", ICygnusStack::isFull);
		
		registerWithLexicon("equal_value", stack -> {
			Optional<?> top = stack.peek(0);
			Optional<?> under = stack.peek(1);
			if(top.isPresent() && under.isPresent()) {
				Object thingTop = top.get();
				Object thingUnder = under.get();
				if(thingTop.getClass() != thingUnder.getClass()) return false;
				
				return CygnusDatatypeHelpers.forClass(thingTop.getClass()).areEqualUnchecked(thingTop, thingUnder);
			}
			
			return false;
		});
		
		registerWithLexicon("equal_type", stack -> {
			Optional<?> top = stack.peek(0);
			Optional<?> under = stack.peek(1);
			if(top.isPresent() && under.isPresent()) {
				return top.get().getClass() == under.get().getClass();
			} else return false;
		});
		
		registerWithLexicon("less_than", stack -> {
			return compareTopTwo(stack) < 0;
		});
		
		registerWithLexicon("greater_than", stack -> {
			return compareTopTwo(stack) > 0;
		});
		
		registerWithLexicon("errored", stack -> {
			return stack.peekMatching(CygnusError.class).isPresent();
		});
	}
	
	private static ICygnusCondition registerWithLexicon(String name, Predicate<ICygnusStack> cond) {
		return register(name, new ICygnusCondition() {
			@Override
			public boolean test(ICygnusStack stack) {
				return cond.test(stack);
			}
			
			@Override
			public void document(List<LexiconPage> pages) {
				pages.add(new PageHeadingIcon(
					CygnusNetworkItems.CRYSTAL_CUBE_CARD.setTo(this),
					CygnusNetworkItems.CRYSTAL_CUBE_CARD.langKeyForValue(this),
					"botania.page.incorporeal.cygnus_crystal_cube.condition." + name
				));
			}
		});
	}
	
	private static ICygnusCondition register(String name, ICygnusCondition action) {
		return Incorporeal.API.getCygnusStackConditionRegistry().register(new ResourceLocation(Incorporeal.MODID, name), action);
	}
	
	private static int compareTopTwo(ICygnusStack stack) {
		Optional<?> top = stack.peek(0);
		Optional<?> under = stack.peek(1);
		if(top.isPresent() && under.isPresent()) {
			Object thingTop = top.get();
			Object thingUnder = under.get();
			if(thingTop.getClass() != thingUnder.getClass()) return 0;
			
			ICygnusDatatype<?> type = CygnusDatatypeHelpers.forClass(thingTop.getClass());
			if(!type.canCompare()) return 0;
			else return type.compareUnchecked(thingTop, thingUnder);
		}
		
		return 0;
	}
}
