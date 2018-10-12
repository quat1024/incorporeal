package quaternary.incorporeal.cygnus.cap;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class AttachCapabilitiesEventHandler {
	public static final ResourceLocation FUNNEL_HANDLER = new ResourceLocation(Incorporeal.MODID, "cygnus_funnel_handler");
	
	static final Field retainerRequestField = ReflectionHelper.findField(TileCorporeaRetainer.class, "request");
	static final Field retainerCountField = ReflectionHelper.findField(TileCorporeaRetainer.class, "requestCount");
	
	@SubscribeEvent
	public static void attachTileCapabilities(AttachCapabilitiesEvent<TileEntity> e) {
		if(e.getObject() instanceof TileCorporeaRetainer) {
			TileCorporeaRetainer retainer = (TileCorporeaRetainer) e.getObject();
			e.addCapability(FUNNEL_HANDLER, new ICapabilityProvider() {
				RetainerFunnelable funnelable = new RetainerFunnelable();
				
				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
					return capability == IncorporeticCygnusCapabilities.FUNNEL_CAP;
				}
				
				@Nullable
				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
					return capability == IncorporeticCygnusCapabilities.FUNNEL_CAP ? (T) funnelable : null;
				}
				
				class RetainerFunnelable implements ICygnusFunnelable {
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
						try {
							Object request = retainerRequestField.get(retainer);
							int count = retainerCountField.getInt(retainer);
							
							if(request instanceof ItemStack) {
								ItemStack copy = ((ItemStack)request).copy();
								copy.setCount(count);
								//TODO clear the request too
								return copy;
							} else if(request instanceof String) {
								//TODO handle this case
								Incorporeal.LOGGER.info("Cant handle string retained requests sorry!");
							}
						} catch (Exception oof) {
							//oof
						}
						
						return ItemStack.EMPTY;
					}
					
					@Override
					public void acceptItemFromCygnus(Object item) {
						if(item instanceof ItemStack) {
							ItemStack stack = ((ItemStack) item).copy();
							int count = stack.getCount();
							stack.setCount(1);
							try {
								retainerRequestField.set(retainer, stack);
								retainerCountField.set(retainer, count);
							} catch (Exception oof) {
								//oof
							}
						}
					}
				}
			});
		}
	}
}
