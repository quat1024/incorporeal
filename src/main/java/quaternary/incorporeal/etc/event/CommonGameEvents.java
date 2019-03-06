package quaternary.incorporeal.etc.event;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.block.IncorporeticBlocks;
import quaternary.incorporeal.item.IncorporeticItems;
import quaternary.incorporeal.net.IncorporeticPacketHandler;
import quaternary.incorporeal.net.MessageSkytouchingEffect;
import quaternary.incorporeal.recipe.skytouch.IncorporeticSkytouchingRecipes;
import quaternary.incorporeal.recipe.skytouch.RecipeSkytouching;
import vazkii.botania.common.item.ModItems;

import java.util.List;

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
	
	@SubscribeEvent
	public static void worldTick(TickEvent.WorldTickEvent e) {
		if(e.phase != TickEvent.Phase.START) return;
		World world = e.world;
		if(world.isRemote) return;
		
		List<EntityItem> items = world.getEntities(EntityItem.class, i -> {
			return
				i != null &&
				!i.isDead &&
				i.prevPosY >= RecipeSkytouching.LOWEST_SKYTOUCHING_RECIPE_Y &&
				i.posY < i.prevPosY; //needs to be falling
		});
		
		for(EntityItem ent : items) {
			for(RecipeSkytouching recipe : IncorporeticSkytouchingRecipes.ALL) {
				if(recipe.matches(ent.getItem(), ent.prevPosY)) {
					for(ItemStack out : recipe.getOutputs(ent.getItem(), ent.prevPosY)) {
						world.spawnEntity(new EntityItem(world, ent.posX, ent.posY, ent.posZ, out));
					}
					
					IncorporeticPacketHandler.sendToAllTracking(new MessageSkytouchingEffect(ent.posX, ent.posY, ent.posZ), world, ent.getPosition());
					ent.setDead();
					
					break;
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void missingBlocks(RegistryEvent.MissingMappings<Block> e) {
		for(RegistryEvent.MissingMappings.Mapping<Block> map : e.getMappings()) {
			if(map.key.getPath().equals("corporea_liar")) {
				map.remap(IncorporeticBlocks.RED_STRING_LIAR);
			}
		}
	}
	
	@SubscribeEvent
	public static void missingItems(RegistryEvent.MissingMappings<Item> e) {
		for(RegistryEvent.MissingMappings.Mapping<Item> map : e.getMappings()) {
			if(map.key.getPath().equals("corporea_liar")) {
				map.remap(IncorporeticItems.RED_STRING_LIAR);
			}
		}
	}
}
