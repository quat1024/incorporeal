package quaternary.incorporeal.cygnus;

import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.ISimpleRegistry;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.api.cygnus.ICygnusStack;

import java.util.Optional;
import java.util.function.Predicate;

public final class IncorporeticCygnusConditions {
	private IncorporeticCygnusConditions() {}
	
	public static Predicate<ICygnusStack> NOTHING;
	
	public static void registerCygnusConditions() {
		ISimpleRegistry<Predicate<ICygnusStack>> reg = Incorporeal.API.getCygnusStackConditionRegistry();
		
		NOTHING = stack -> false;
		reg.register(new ResourceLocation(Incorporeal.MODID, "nothing"), NOTHING);
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "empty_stack"), ICygnusStack::isEmpty);
		reg.register(new ResourceLocation(Incorporeal.MODID, "full_stack"), ICygnusStack::isFull);
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "equal_value"), stack -> {
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
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "equal_type"), stack -> {
			Optional<?> top = stack.peek(0);
			Optional<?> under = stack.peek(1);
			if(top.isPresent() && under.isPresent()) {
				return top.get().getClass() == under.get().getClass();
			} else return false;
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "less_than"), stack -> {
			return compareTopTwo(stack) < 0; 
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "greater_than"), stack -> {
			return compareTopTwo(stack) > 0;
		});
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "errored"), stack -> {
			return stack.peekMatching(CygnusError.class).isPresent();
		});
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
