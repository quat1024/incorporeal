package quaternary.incorporeal.feature.cygnusnetwork.cap;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;

import javax.annotation.Nullable;

public final class IncorporeticCygnusCapabilities {
	private IncorporeticCygnusCapabilities() {
	}
	
	@CapabilityInject(ICygnusFunnelable.class)
	public static Capability<ICygnusFunnelable> FUNNEL_CAP = null;
	
	public static void register(FMLPreInitializationEvent e) {
		CapabilityManager.INSTANCE.register(ICygnusFunnelable.class, new DummyNBTSerializer<>(), NooneCaresDefaultImplementationOfICygnusFunnelable::new);
	}
	
	public static final class DummyNBTSerializer<T> implements Capability.IStorage<T> {
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			return new NBTTagCompound();
		}
		
		@Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
			//No-op
		}
	}
	
	public static final class NooneCaresDefaultImplementationOfICygnusFunnelable implements ICygnusFunnelable {
		//The whole thing is defined in default methods so lol nothing to override here.
		
		//...
		
		//Does anyone actually use default cap implementations? I doubt it, really.
	}
}
