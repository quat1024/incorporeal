package quaternary.incorporeal.etc;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

//This is a little capability provider that basically just abstracts over the by-far most
//common shape of a capability provider. Boilerplate removal, in other words.
//
//Why use a supplier?
//Because the attachcapsevent is fired very, very early in *construction* of a tileentity,
//sometimes just calling getCapability on a tile entity can NPE, since some tiles don't expect
//to have methods called on them before, well, the class initializer/constructor finishes.
//
//The supplier helps a bit with that. And like, frankly I forget exactly how, but it makes
//the errors go away so \_(:D)_/
public class LazyGenericCapabilityProvider<C> implements ICapabilityProvider {
	public LazyGenericCapabilityProvider(Capability<C> cap, Supplier<C> implFactory) {
		this.cap = cap;
		this.implFactory = implFactory;
	}
	
	private final Capability<C> cap;
	private final Supplier<C> implFactory;
	private C impl = null;
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == cap;
	}
	
	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == cap) {
			if(impl == null) impl = implFactory.get();
			return (T) impl;
		} else return null;
	}
}
