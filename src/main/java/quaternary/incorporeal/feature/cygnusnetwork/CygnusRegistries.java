package quaternary.incorporeal.feature.cygnusnetwork;

import quaternary.incorporeal.api.cygnus.ICygnusAction;
import quaternary.incorporeal.api.cygnus.ICygnusCondition;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.api.cygnus.ILooseCygnusFunnelable;
import quaternary.incorporeal.feature.cygnusnetwork.etc.SimpleRegistry;

import java.util.HashSet;
import java.util.Set;

public final class CygnusRegistries {
	private CygnusRegistries() {
	}
	
	public static final SimpleRegistry<ICygnusAction> ACTIONS = new SimpleRegistry<>();
	public static final SimpleRegistry<ICygnusCondition> CONDITIONS = new SimpleRegistry<>();
	public static final SimpleRegistry<ICygnusDatatype<?>> DATATYPES = new SimpleRegistry<>();
	
	public static final Set<ILooseCygnusFunnelable> LOOSE_FUNNELABLES = new HashSet<>();
	
	public static void freezeRegistries() {
		ACTIONS.freeze();
		CONDITIONS.freeze();
		DATATYPES.freeze();
	}
}
