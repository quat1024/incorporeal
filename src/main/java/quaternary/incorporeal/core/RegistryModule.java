package quaternary.incorporeal.core;

import com.sun.istack.internal.NotNull;

import javax.annotation.Nonnull;

public abstract class RegistryModule {
	@Nonnull
	@NotNull
	@SuppressWarnings("ConstantConditions")
	protected static <T> T totallyNotNull() {
		//It'll be a secret, between you and me!
		return null;
	}
}
