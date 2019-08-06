package quaternary.incorporeal.feature.naturaldevices.event;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.feature.naturaldevices.block.NaturalDevicesBlocks;
import vazkii.botania.common.item.ModItems;

public final class PlantRedstoneRootEventHandler {
	private PlantRedstoneRootEventHandler() {
	}
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(PlantRedstoneRootEventHandler.class);
	}
	
	@SubscribeEvent
	public static void plantRedstoneRoot(PlayerInteractEvent.RightClickBlock e) {
		ItemStack stack = e.getItemStack();
		if(stack.getItem() == ModItems.manaResource && stack.getMetadata() == 6) {
			//It's a redstone root!
			
			//Based on pasta from ItemSeeds
			EnumFacing facing = e.getFace();
			if(facing != EnumFacing.UP) return;
			
			World world = e.getWorld();
			BlockPos pos = e.getPos();
			if(!world.isAirBlock(pos.up())) return;
			
			EntityPlayer player = e.getEntityPlayer();
			IBlockState farmlandState = world.getBlockState(pos);
			
			if(player.canPlayerEdit(pos.offset(facing), facing, stack) && farmlandState.getBlock().canSustainPlant(farmlandState, world, pos, EnumFacing.UP, NaturalDevicesBlocks.REDSTONE_ROOT_CROP)) {
				world.setBlockState(pos.up(), NaturalDevicesBlocks.REDSTONE_ROOT_CROP.getDefaultState());
				
				if(player instanceof EntityPlayerMP) {
					CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), stack);
				}
				
				if(!player.isCreative()) stack.shrink(1);
				
				player.swingArm(e.getHand());
			}
		}
	}
}
