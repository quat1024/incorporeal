package quaternary.incorporeal.cygnus;

import com.google.common.base.Preconditions;
import net.minecraft.util.text.translation.I18n;

//Cygnus datatype that represents an error.
//Shows up in cases of stack underflow, division-by-zero, or really any case when an operation can't work.
//Stack overflows are an exception; they just fail silently.
public class CygnusError {
	public static final int MAX_KEY_LENGTH = 256;
	
	public static final String UNSPECIFIED = "incorporeal.cygnus.error.unspecified";
	public static final String UNDERFLOW = "incorporeal.cygnus.error.stack_underflow";
	public static final String MISMATCH = "incorporeal.cygnus.error.mismatched_type";
	public static final String OUT_OF_RANGE = "incorporeal.cygnus.error.out_of_range";
	public static final String INVALID_MATH = "incorporeal.cygnus.error.invalid_math";
	
	public CygnusError() {
		this(UNSPECIFIED);
	}
	
	public CygnusError(String errorTranslationKey) {
		this(errorTranslationKey.isEmpty() ? UNSPECIFIED : errorTranslationKey, new String[0]);
	}
	
	public CygnusError(String errorTranslationKey, String... errorFormat) {
		Preconditions.checkArgument(errorTranslationKey.length() <= MAX_KEY_LENGTH, "Too long translation key!");
		for(String s : errorFormat) {
			Preconditions.checkArgument(s.length() <= MAX_KEY_LENGTH, "Too long format argument!");
		}
		
		this.errorTranslationKey = errorTranslationKey;
		this.errorFormat = errorFormat;
	}
	
	public final String errorTranslationKey;
	public final String[] errorFormat;
	
	public String getTranslatedText() {
		return I18n.translateToLocalFormatted(errorTranslationKey, errorFormat);
	}
	
	//TODO remove toString stuff
	@Override
	public String toString() {
		return getTranslatedText();
	}
}
