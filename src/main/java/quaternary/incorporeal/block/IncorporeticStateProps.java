package quaternary.incorporeal.block;

import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.block.properties.UnlistedSimpleRegistryCondition;

import java.util.function.Consumer;
import java.util.function.Predicate;

public final class IncorporeticStateProps {
	private IncorporeticStateProps() {}
	
	public static final UnlistedSimpleRegistryCondition<Consumer<ICygnusStack>> UNLISTED_ACTION = UnlistedSimpleRegistryCondition.create(
					"action",
					(Class<Consumer<ICygnusStack>>) (Class<?>) Consumer.class, //oof
					Incorporeal.API.getCygnusStackActionRegistry()
	);
	
	public static final UnlistedSimpleRegistryCondition<Predicate<ICygnusStack>> UNLISTED_CONDITION = UnlistedSimpleRegistryCondition.create(
					"condition",
					(Class<Predicate<ICygnusStack>>) (Class<?>) Predicate.class, //OOF
					Incorporeal.API.getCygnusStackConditionRegistry()
	);
}
