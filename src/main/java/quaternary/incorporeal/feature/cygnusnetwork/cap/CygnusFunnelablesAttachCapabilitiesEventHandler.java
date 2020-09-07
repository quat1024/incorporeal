package quaternary.incorporeal.feature.cygnusnetwork.cap;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.core.etc.LazyGenericCapabilityProvider;
import quaternary.incorporeal.core.etc.helper.CorporeaHelper2;
import quaternary.incorporeal.feature.corporetics.item.CorporeticsItems;
import quaternary.incorporeal.feature.corporetics.item.ItemCorporeaTicket;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;
import quaternary.incorporeal.feature.cygnusnetwork.item.ItemCygnusTicket;
import quaternary.incorporeal.feature.cygnusnetwork.lexicon.PageHeadingIcon;
import vazkii.botania.api.corporea.CorporeaRequest;
import vazkii.botania.api.corporea.ICorporeaRequestor;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;
import vazkii.botania.common.block.tile.corporea.TileCorporeaCrystalCube;
import vazkii.botania.common.block.tile.corporea.TileCorporeaFunnel;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;

import javax.annotation.Nullable;

//Is this class name long enough?
public final class CygnusFunnelablesAttachCapabilitiesEventHandler {
	private CygnusFunnelablesAttachCapabilitiesEventHandler() {}
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(CygnusFunnelablesAttachCapabilitiesEventHandler.class);
	}
	
	public static void document() {
		Incorporeal.API.documentCygnusFunnelable(pages -> {
			pages.addAll(ImmutableList.of(
				new PageHeadingIcon(
					new ItemStack(ModBlocks.corporeaRetainer),
					"botania.page.incorporeal.cygnus_funnel.retainer"
				),
				new PageHeadingIcon(
					new ItemStack(ModBlocks.corporeaCrystalCube),
					"botania.page.incorporeal.cygnus_funnel.crystal_cube"
				),
				new PageHeadingIcon(
					new ItemStack(ModBlocks.corporeaFunnel),
					"botania.page.incorporeal.cygnus_funnel.funnel"
				),
				new PageHeadingIcon(
					new ItemStack(ModBlocks.corporeaIndex),
					"botania.page.incorporeal.cygnus_funnel.index"
				),
				new PageHeadingIcon(
					new ItemStack(CygnusNetworkItems.MASTER_CYGNUS_SPARK),
					"botania.page.incorporeal.cygnus_funnel.master_cygnus_spark"
				),
				new PageHeadingIcon(
					new ItemStack(Blocks.FLOWER_POT),
					"botania.page.incorporeal.cygnus_funnel.item_entity.heading",
					"botania.page.incorporeal.cygnus_funnel.item_entity"
				),
				new PageHeadingIcon(
					new ItemStack(Items.PAPER),
					"botania.page.incorporeal.cygnus_funnel.paper"
				),
				new PageHeadingIcon(
					new ItemStack(CygnusNetworkItems.CYGNUS_TICKET),
					"botania.page.incorporeal.cygnus_funnel.ticket"
				)
			));
		});
	}
	
	public static final ResourceLocation FUNNEL_HANDLER = new ResourceLocation(Incorporeal.MODID, "cygnus_funnel_handler");
	
	@SubscribeEvent
	public static void attachTileCapabilities(AttachCapabilitiesEvent<TileEntity> e) {
		TileEntity tile = e.getObject();
		
		if(tile instanceof TileCorporeaRetainer) {
			e.addCapability(FUNNEL_HANDLER, new LazyGenericCapabilityProvider<>(
				IncorporeticCygnusCapabilities.FUNNEL_CAP,
				() -> new CorporeaRetainerFunnelable((TileCorporeaRetainer) tile)
			));
		} else if(tile instanceof TileCorporeaCrystalCube) {
			e.addCapability(FUNNEL_HANDLER, new LazyGenericCapabilityProvider<>(
				IncorporeticCygnusCapabilities.FUNNEL_CAP,
				() -> new CorporeaCrystalCubeFunnelable((TileCorporeaCrystalCube) tile)
			));
		} else if(tile instanceof TileCorporeaFunnel) {
			e.addCapability(FUNNEL_HANDLER, new LazyGenericCapabilityProvider<>(
				IncorporeticCygnusCapabilities.FUNNEL_CAP,
				() -> new CorporeaRequesterFunnelable<>((TileCorporeaFunnel) tile)
			));
		} else if(tile instanceof TileCorporeaIndex) {
			e.addCapability(FUNNEL_HANDLER, new LazyGenericCapabilityProvider<>(
				IncorporeticCygnusCapabilities.FUNNEL_CAP,
				() -> new CorporeaRequesterFunnelable<>((TileCorporeaIndex) tile)
			));
		}
	}
	
	@SubscribeEvent
	public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> e) {
		Entity ent = e.getObject();
		
		if(ent instanceof EntityItem) {
			e.addCapability(FUNNEL_HANDLER, new LazyGenericCapabilityProvider<>(
				IncorporeticCygnusCapabilities.FUNNEL_CAP,
				() -> new ItemEntityFunnelable((EntityItem) ent)
			));
		} else if(ent instanceof EntityItemFrame) {
			e.addCapability(FUNNEL_HANDLER, new LazyGenericCapabilityProvider<>(
				IncorporeticCygnusCapabilities.FUNNEL_CAP,
				() -> new ItemFrameFunnelable((EntityItemFrame) ent)
			));
		}
	}
	
	//and now, the actual funnelables:
	private static final class CorporeaRetainerFunnelable implements ICygnusFunnelable {
		public CorporeaRetainerFunnelable(TileCorporeaRetainer retainer) {
			this.retainer = retainer;
		}
		
		private final TileCorporeaRetainer retainer;
		
		@Override
		public boolean canGiveCygnusItem() {
			return retainer.hasPendingRequest();
		}
		
		@Override
		public boolean canAcceptCygnusItem() {
			return true;
		}
		
		@Nullable
		@Override
		public Object giveItemToCygnus() {
			return CorporeaHelper2.getCorporeaRequestInRetainer(retainer);
		}
		
		@Override
		public void acceptItemFromCygnus(Object item) {
			if(item instanceof CorporeaRequest) {
				CorporeaHelper2.setCorporeaRequestInRetainer(retainer, (CorporeaRequest) item);
			}
		}
	}
	
	private static final class CorporeaCrystalCubeFunnelable implements ICygnusFunnelable {
		public CorporeaCrystalCubeFunnelable(TileCorporeaCrystalCube crystalCube) {
			this.crystalCube = crystalCube;
		}
		
		private final TileCorporeaCrystalCube crystalCube;
		
		@Override
		public boolean canGiveCygnusItem() {
			return !crystalCube.getRequestTarget().isEmpty();
		}
		
		@Override
		public boolean canAcceptCygnusItem() {
			return true;
		}
		
		@Override
		public Object giveItemToCygnus() {
			ItemStack item = crystalCube.getRequestTarget();
			//Is this needed
			ItemStack item2 = item.copy();
			item2.setCount(1);
			int count = crystalCube.getItemCount();
			
			return new CorporeaRequest(item2, true, count);
		}
		
		@Override
		public void acceptItemFromCygnus(Object item) {
			if(item instanceof CorporeaRequest) {
				CorporeaRequest request = (CorporeaRequest) item;
				if(request.matcher instanceof ItemStack) {
					crystalCube.setRequestTarget((ItemStack) request.matcher);
				}
			}
		}
	}
	
	private static final class CorporeaRequesterFunnelable<T extends TileCorporeaBase & ICorporeaRequestor> implements ICygnusFunnelable {
		public CorporeaRequesterFunnelable(T tile) {
			this.tile = tile;
		}
		
		private final T tile;
		
		@Override
		public boolean canAcceptCygnusItem() {
			return tile.getSpark() != null;
		}
		
		@Override
		public void acceptItemFromCygnus(Object item) {
			if(item instanceof CorporeaRequest) {
				CorporeaRequest request = (CorporeaRequest) item;
				
				tile.doCorporeaRequest(request.matcher, request.count, tile.getSpark());
			}
		}
	}
	
	//a helper used for the item entity & item frame funnelables since they are similar
	private static abstract class AbstractItemStackFunnelable implements ICygnusFunnelable {
		protected abstract ItemStack getStack();
		
		protected abstract void setStack(ItemStack stack);
		
		@Override
		public boolean canGiveCygnusItem() {
			ItemStack stack = getStack();
			
			if(stack.getItem() == CygnusNetworkItems.CYGNUS_TICKET) {
				//it's a ticket with a request
				return ItemCygnusTicket.hasCygnusItem(stack);
			}
			else if (stack.getItem() == CorporeticsItems.CORPOREA_TICKET) {
				return ItemCorporeaTicket.hasCygnusItem(stack);
			}
			else{
				//it's some other item
				return !stack.isEmpty();
			}
		}
		
		@Override
		public boolean canAcceptCygnusItem() {
			//can only accept items if it's a ticket to be overwritten or a paper
			Item item = getStack().getItem();
			return item == Items.PAPER || item == CygnusNetworkItems.CYGNUS_TICKET;
		}
		
		@Nullable
		@Override
		public Object giveItemToCygnus() {
			ItemStack stack = getStack();
			
			if(stack.getItem() instanceof ItemCygnusTicket && ItemCygnusTicket.hasCygnusItem(stack)) {
				return ItemCygnusTicket.getCygnusItem(stack);
			} else if (stack.getItem() instanceof ItemCorporeaTicket && ItemCorporeaTicket.hasCygnusItem(stack)) {
				return ItemCorporeaTicket.getCygnusItem(stack);
			}
			else {
				//it's not a ticket, so submit whatever it is as a corporea request
				ItemStack stackCopy = stack.copy();
				int count = stackCopy.getCount();
				stackCopy.setCount(1);
				return new CorporeaRequest(stackCopy, true, count);
			}
		}
		
		@Override
		public void acceptItemFromCygnus(Object item) {
			ItemStack stack = getStack();
			Item stackItem = stack.getItem();
			
			if(stackItem == Items.PAPER || stackItem instanceof ItemCygnusTicket) {
				ItemStack newStack = new ItemStack(CygnusNetworkItems.CYGNUS_TICKET, stack.getCount());
				ItemCygnusTicket.setCygnusItem(newStack, item);
				setStack(newStack);
			}
		}
	}
	
	public static final class ItemEntityFunnelable extends AbstractItemStackFunnelable {
		public ItemEntityFunnelable(EntityItem ent) {
			this.ent = ent;
			is = ent.getItem().copy();
		}

		private ItemStack is;
		private EntityItem ent;
		
		@Override
		protected ItemStack getStack() {
			return is;
		}
		
		@Override
		protected void setStack(ItemStack stack) {
			ent.setItem(stack);
		}
	}
	
	private static final class ItemFrameFunnelable extends AbstractItemStackFunnelable {
		public ItemFrameFunnelable(EntityItemFrame frame) {
			this.frame = frame;
		}
		
		private final EntityItemFrame frame;
		
		@Override
		protected ItemStack getStack() {
			return frame.getDisplayedItem();
		}
		
		@Override
		protected void setStack(ItemStack stack) {
			frame.setDisplayedItem(stack);
		}
	}
}
