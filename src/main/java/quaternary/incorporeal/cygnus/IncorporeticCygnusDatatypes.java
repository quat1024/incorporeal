package quaternary.incorporeal.cygnus;

import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.ISimpleRegistry;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.cygnus.types.CygnusBigIntegerType;
import quaternary.incorporeal.cygnus.types.CygnusCorporeaRequestType;
import quaternary.incorporeal.cygnus.types.CygnusErrorType;
import quaternary.incorporeal.cygnus.types.CygnusStackType;

public class IncorporeticCygnusDatatypes {
	private IncorporeticCygnusDatatypes() {}
	
	public static void registerCygnusDatatypes() {
		ISimpleRegistry<ICygnusDatatype<?>> reg = Incorporeal.API.getCygnusDatatypeRegistry();
		
		reg.register(new ResourceLocation(Incorporeal.MODID, "big_integer"), new CygnusBigIntegerType());
		reg.register(new ResourceLocation(Incorporeal.MODID, "corporea_request"), new CygnusCorporeaRequestType());
		reg.register(new ResourceLocation(Incorporeal.MODID, "error"), new CygnusErrorType());
		reg.register(new ResourceLocation(Incorporeal.MODID, "cygnus_stack"), new CygnusStackType());
	}
}
