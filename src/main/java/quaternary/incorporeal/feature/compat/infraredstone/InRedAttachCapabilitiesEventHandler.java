//This links against InfraRedstone, which is covered under the
//GNU Lesser General Public License.
//You can find its source here: https://github.com/elytra/InfraRedstone
//So... keep that in mind!

package quaternary.incorporeal.feature.compat.infraredstone;

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
import quaternary.incorporeal.feature.cygnusnetwork.cap.AttachCapabilitiesEventHandler;
import quaternary.incorporeal.feature.cygnusnetwork.cap.IncorporeticCygnusCapabilities;
import quaternary.incorporeal.core.etc.LazyGenericCapabilityProvider;
import quaternary.incorporeal.feature.cygnusnetwork.tile.TileCygnusFunnel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigInteger;

public class InRedAttachCapabilitiesEventHandler {
	public static final Capability<IInfraRedstone> INFRA_READABLE_CAP = InfraRedstone.CAPABILITY_IR;
	
	private static final ResourceLocation FUNNEL_HANDLER = AttachCapabilitiesEventHandler.FUNNEL_HANDLER;
	private static final ResourceLocation INRED_CABLE_HANDLER = new ResourceLocation("incorporeal", "inred_compat_cable");
	private static final ResourceLocation CATCHALL_FUNNEL_HANDLER = new ResourceLocation("incorporeal", "catchall_funnel_handler");
	
	@SubscribeEvent
	public static void tileCaps(AttachCapabilitiesEvent<TileEntity> e) {
		TileEntity tile = e.getObject();
		
		//This just makes inred cables point towards cygnus funnels
		//Which makes it possible for them to read their value
		if(tile instanceof TileCygnusFunnel) {
			e.addCapability(INRED_CABLE_HANDLER, new LazyGenericCapabilityProvider<>(
				INFRA_READABLE_CAP,
				() -> InfraRedstoneHandler.ALWAYS_OFF
			));
			
			return;
		}
		
		//Infraredstone diodes
		if(tile instanceof TileEntityDiode) {
			e.addCapability(FUNNEL_HANDLER, new LazyGenericCapabilityProvider<>(
				IncorporeticCygnusCapabilities.FUNNEL_CAP,
				() -> new InfraDiodeFunnelable((TileEntityDiode) tile)
			));
			
			return;
		}
		
		//Since you can't call hasCapability in the AttachCapabilitiesEvent, I cast a wide net here
		e.addCapability(CATCHALL_FUNNEL_HANDLER, new ICapabilityProvider() {
			@Override
			public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
				if(capability == IncorporeticCygnusCapabilities.FUNNEL_CAP) {
					return tile.hasCapability(InfraRedstone.CAPABILITY_IR, facing);
				} else return false;
			}
			
			@Nullable
			@Override
			public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
				if(capability == IncorporeticCygnusCapabilities.FUNNEL_CAP) {
					IInfraRedstone ir = tile.getCapability(InfraRedstone.CAPABILITY_IR, facing);
					if(ir == null) return null;
					//noinspection unchecked
					else return (T) new InfraReadableFunnelable(ir); //TODO cache...?
				} else return null;
			}
		});
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
