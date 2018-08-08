package quaternary.incorporeal.etc.event;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticBlocks;
import vazkii.botania.common.item.ModItems;

@Mod.EventBusSubscriber(modid = Incorporeal.MODID)
public final class CommonGameEvents {
	private CommonGameEvents() {}
	
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
			
			if(player.canPlayerEdit(pos.offset(facing), facing, stack) && farmlandState.getBlock().canSustainPlant(farmlandState, world, pos, EnumFacing.UP, IncorporeticBlocks.REDSTONE_ROOT_CROP)) {
				world.setBlockState(pos.up(), IncorporeticBlocks.REDSTONE_ROOT_CROP.getDefaultState());
				
				if (player instanceof EntityPlayerMP) {
					CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos.up(), stack);
				}
				
				if(!player.isCreative()) stack.shrink(1);
				
				player.swingArm(e.getHand());
			}
		}
	}
}
