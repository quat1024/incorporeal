package quaternary.incorporeal.feature.cygnusnetwork;

import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.ISimpleRegistry;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.feature.cygnusnetwork.types.CygnusBigIntegerType;
import quaternary.incorporeal.feature.cygnusnetwork.types.CygnusCorporeaRequestType;
import quaternary.incorporeal.feature.cygnusnetwork.types.CygnusErrorType;
import quaternary.incorporeal.feature.cygnusnetwork.types.CygnusStackType;

public final class IncorporeticCygnusDatatypes {
	private IncorporeticCygnusDatatypes() {
	}
	
	public static void registerCygnusDatatypes() {
		ISimpleRegistry<ICygnusDatatype<?>> reg = Incorporeal.API.getCygnusDatatypeRegistry();
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "big_integer"), new CygnusBigIntegerType());
		reg.register(new ResourceLocation(Incorporeal.MODID, "corporea_request"), new CygnusCorporeaRequestType());
		reg.register(new ResourceLocation(Incorporeal.MODID, "error"), new CygnusErrorType());
		reg.register(new ResourceLocation(Incorporeal.MODID, "cygnus_stack"), new CygnusStackType());
	}
}
