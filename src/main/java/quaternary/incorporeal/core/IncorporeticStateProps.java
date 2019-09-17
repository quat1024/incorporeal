package quaternary.incorporeal.core;

import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusAction;
import quaternary.incorporeal.feature.cygnusnetwork.block.UnlistedSimpleRegistryProperty;

public final class IncorporeticStateProps {
	private IncorporeticStateProps() { }
	
	public static final UnlistedSimpleRegistryProperty<ICygnusAction> UNLISTED_ACTION = UnlistedSimpleRegistryProperty.create(
		"action",
		ICygnusAction.class,
		Incorporeal.API.getCygnusStackActionRegistry()
	);
}
