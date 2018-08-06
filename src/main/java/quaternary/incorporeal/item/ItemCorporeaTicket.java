package quaternary.incorporeal.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.text.WordUtils;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import vazkii.botania.api.corporea.CorporeaRequest;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

public class ItemCorporeaTicket extends Item implements ILexiconable {
	public ItemCorporeaTicket() {
		addPropertyOverride(new ResourceLocation(Incorporeal.MODID, "written_ticket"), (stack, world, entity) -> {
			return isRequestable(stack) ? 1 : 0;
		});
	}
	
	public static ItemStack createFromRequest(Object request, int requestedCount) {
		ItemStack stack = new ItemStack(IncorporeticItems.CORPOREA_TICKET);
		stack.setTagInfo("RequestAmount", new NBTTagInt(requestedCount));
		if(request instanceof ItemStack) {
			stack.setTagInfo("RequestItem", ((ItemStack) request).writeToNBT(new NBTTagCompound()));
		} else if (request instanceof String) {
			stack.setTagInfo("RequestString", new NBTTagString((String) request));
		} else throw new IllegalArgumentException("Corporea request not a String or ItemStack?????????");
		
		return stack;
	}
	
	public static CorporeaRequest getRequestFromTicket(ItemStack ticket) {
		if(ticket.isEmpty()) return null;
		
		NBTTagCompound cmp = ticket.getTagCompound();
		if(cmp != null && cmp.hasKey("RequestAmount")) {
			int count = cmp.getInteger("RequestAmount");
			Object request;
			
			if(cmp.hasKey("RequestItem")) request = new ItemStack(cmp.getCompoundTag("RequestItem"));
			else request = cmp.getString("RequestString");
			
			return new CorporeaRequest(request, true, count);
		} else return null;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		NBTTagCompound cmp = stack.getTagCompound();
		if(cmp != null && cmp.hasKey("RequestAmount")) {
			int count = cmp.getInteger("RequestAmount");
			String requestName;
			if(cmp.hasKey("RequestItem")) {
				ItemStack storedStack = new ItemStack(cmp.getCompoundTag("RequestItem"));
				requestName = storedStack.getDisplayName();
			} else {
				requestName = cmp.getString("RequestString");
			}
			
			requestName = WordUtils.capitalizeFully(requestName).trim();
			
			return I18n.translateToLocalFormatted("item." + Incorporeal.MODID + ".filled_corporea_ticket.name", count, requestName).trim();
		} else {
			return super.getItemStackDisplayName(stack);
		}
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.corporeaSolidifier;
	}
	
	public static boolean isRequestable(ItemStack stack) {
		return getRequestFromTicket(stack) != null;
	}
}
