//This links against InfraRedstone, which is covered under the
//GNU Lesser General Public License.
//You can find its source here: https://github.com/elytra/InfraRedstone
//So... keep that in mind!

package quaternary.incorporeal.compat.infraredstone;

import com.elytradev.infraredstone.InfraRedstone;
import com.elytradev.infraredstone.api.IInfraRedstone;
import com.elytradev.infraredstone.logic.impl.InfraRedstoneHandler;
import com.elytradev.infraredstone.tile.TileEntityDiode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.cygnus.cap.AttachCapabilitiesEventHandler;
import quaternary.incorporeal.cygnus.cap.IncorporeticCygnusCapabilities;
import quaternary.incorporeal.tile.cygnus.TileCygnusFunnel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.function.Supplier;

public class InRedAttachCapabilitiesEventHandler {
	public static final Capability<IInfraRedstone> INFRA_READABLE_CAP = InfraRedstone.CAPABILITY_IR;
	
	private static final ResourceLocation FUNNEL_HANDLER = AttachCapabilitiesEventHandler.FUNNEL_HANDLER;
	private static final ResourceLocation INRED_CABLE_HANDLER = new ResourceLocation("incorporeal", "inred_compat_cable");
	
	@SubscribeEvent
	public static void tileCaps(AttachCapabilitiesEvent<TileEntity> e) {
		TileEntity tile = e.getObject();
		
		//This just makes inred cables point towards cygnus funnels
		//Which makes it possible for them to read their value
		if(tile instanceof TileCygnusFunnel) {
			e.addCapability(INRED_CABLE_HANDLER, new LazierCapabilityProvider<>(
				INFRA_READABLE_CAP,
				() -> InfraRedstoneHandler.ALWAYS_OFF
			));
			
			return;
		}
		
		if(tile instanceof TileEntityDiode) {
			e.addCapability(FUNNEL_HANDLER, new LazierCapabilityProvider<>(
				IncorporeticCygnusCapabilities.FUNNEL_CAP,
				() -> new InfraDiodeFunnelable((TileEntityDiode) tile)
			));
			
			return;
		}
		
		if(tile.hasCapability(INFRA_READABLE_CAP, null)) {
			e.addCapability(FUNNEL_HANDLER, new LazierCapabilityProvider<>(
				IncorporeticCygnusCapabilities.FUNNEL_CAP,
				() -> new InfraReadableFunnelable(tile.getCapability(INFRA_READABLE_CAP, null))
			));
			
			return;
		}
	}
	
	//TODO: Extend this lambdish cap provider to the regular one, too, and just use it.
	//Fixes problems with getCap NPEing sometimes on other tiles since the ACE is fired way too early.
	public static class LazierCapabilityProvider<C> implements ICapabilityProvider {
		public LazierCapabilityProvider(Capability<C> cap, Supplier<C> implFactory) {
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
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			if(capability == cap) {
				if(impl == null) impl = implFactory.get();
				return (T) impl;
			} else return null;
		}
	}
	
	private static class InfraReadableFunnelable implements ICygnusFunnelable {
		public InfraReadableFunnelable(IInfraRedstone read) {
			this.read = read;
		}
		
		private final IInfraRedstone read;
		
		@Override
		public boolean canGiveCygnusItem() {
			return true;
		}
		
		@Nullable
		@Override
		public Object giveItemToCygnus() {
			return BigInteger.valueOf(read.getSignalValue());
		}
	}
	
	private static class InfraDiodeFunnelable implements ICygnusFunnelable {
		public InfraDiodeFunnelable(TileEntityDiode diode) {
			this.diode = diode;
		}
		
		private final TileEntityDiode diode;
		
		@Override
		public boolean canGiveCygnusItem() {
			return true;
		}
		
		@Override
		public boolean canAcceptCygnusItem() {
			return true;
		}
		
		@Nullable
		@Override
		public Object giveItemToCygnus() {
			return BigInteger.valueOf(diode.getMask());
		}
		
		@Override
		public void acceptItemFromCygnus(Object item) {
			if(item instanceof BigInteger) {
				//Amusingly there isn't a way to just set the mask on a diode.
				//Apart from individually triggering every switch! :D
				int newMask = Math.abs(((BigInteger)item).intValue()) & 0b11_1111;
				int mask = diode.getMask();
				
				int bitId = 0;
				for(int i = 1; i <= 0b10_0000; i <<= 1) {
					if((mask & i) != (newMask & i)) diode.setMask(bitId); //flips a bit
					bitId++;
				}
			}
		}
	}
}
