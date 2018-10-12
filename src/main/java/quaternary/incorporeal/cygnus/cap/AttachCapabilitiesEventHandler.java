package quaternary.incorporeal.cygnus.cap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.etc.helper.CorporeaHelper2;
import vazkii.botania.api.corporea.CorporeaRequest;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class AttachCapabilitiesEventHandler {
	public static final ResourceLocation FUNNEL_HANDLER = new ResourceLocation(Incorporeal.MODID, "cygnus_funnel_handler");
	
	@SubscribeEvent
	public static void attachTileCapabilities(AttachCapabilitiesEvent<TileEntity> e) {
		if(e.getObject() instanceof TileCorporeaRetainer) {
			TileCorporeaRetainer retainer = (TileCorporeaRetainer) e.getObject();
			
			e.addCapability(FUNNEL_HANDLER, new GenericCapabilityProvider<>(
							IncorporeticCygnusCapabilities.FUNNEL_CAP,
							new ICygnusFunnelable() {
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
									CorporeaRequest request = CorporeaHelper2.getCorporeaRequestInRetainer(retainer);
									if(request != null) {
										CorporeaHelper2.clearRetainer(retainer);
										return request;
									} else return null;
								}
								
								@Override
								public void acceptItemFromCygnus(Object item) {
									if(item instanceof CorporeaRequest) {
										CorporeaHelper2.setCorporeaRequestInRetainer(retainer, (CorporeaRequest) item);
									}
								}
							}
			));
		}
	}
	
	//Just for saving typing ;)
	public static final class GenericCapabilityProvider<C> implements ICapabilityProvider {
		public GenericCapabilityProvider(Capability<C> cap, C impl) {
			this.cap = cap;
			this.impl = impl;
		}
		
		private final Capability<C> cap;
		private final C impl;
		
		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return capability == cap;
		}
		
		@Nullable
		@Override
		@SuppressWarnings("unchecked")
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			return capability == cap ? (T) impl : null;
		}
	}
}
