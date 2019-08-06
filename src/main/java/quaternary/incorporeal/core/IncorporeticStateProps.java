package quaternary.incorporeal.core;

import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.block.UnlistedSimpleRegistryProperty;

import java.util.function.Consumer;
import java.util.function.Predicate;

public final class IncorporeticStateProps {
	private IncorporeticStateProps() {
	}
	
	public static final UnlistedSimpleRegistryProperty<Consumer<ICygnusStack>> UNLISTED_ACTION = UnlistedSimpleRegistryProperty.create(
		"action",
		(Class<Consumer<ICygnusStack>>) (Class<?>) Consumer.class, //oof
		Incorporeal.API.getCygnusStackActionRegistry()
	);
	
	public static final UnlistedSimpleRegistryProperty<Predicate<ICygnusStack>> UNLISTED_CONDITION = UnlistedSimpleRegistryProperty.create(
		"condition",
		(Class<Predicate<ICygnusStack>>) (Class<?>) Predicate.class, //OOF
		Incorporeal.API.getCygnusStackConditionRegistry()
	);
}
