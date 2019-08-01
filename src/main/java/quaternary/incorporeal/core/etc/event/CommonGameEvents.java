package quaternary.incorporeal.core.etc.event;

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
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.core.sortme.IncorporeticPacketHandler;
import quaternary.incorporeal.feature.corporetics.block.CorporeticsBlocks;
import quaternary.incorporeal.feature.corporetics.item.CorporeticsItems;
import quaternary.incorporeal.feature.naturaldevices.block.NaturalDevicesBlocks;
import quaternary.incorporeal.feature.skytouching.net.MessageSkytouchingEffect;
import quaternary.incorporeal.feature.skytouching.recipe.IncorporeticSkytouchingRecipes;
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
			
			if(player.canPlayerEdit(pos.offset(facing), facing, stack) && farmlandState.getBlock().canSustainPlant(farmlandState, world, pos, EnumFacing.UP, NaturalDevicesBlocks.REDSTONE_ROOT_CROP)) {
				world.setBlockState(pos.up(), NaturalDevicesBlocks.REDSTONE_ROOT_CROP.getDefaultState());
				
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
				i.prevPosY >= IncorporeticSkytouchingRecipes.LOWEST_SKYTOUCH_Y &&
				i.posY < i.prevPosY; //needs to be falling
		});
		
		for(EntityItem ent : items) {
			for(IRecipeSkytouching recipe : IncorporeticSkytouchingRecipes.ALL) {
				if(recipe.matches(ent)) {
					for(ItemStack out : recipe.getOutputs(ent)) {
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
				map.remap(CorporeticsBlocks.RED_STRING_LIAR);
			}
		}
	}
	
	@SubscribeEvent
	public static void missingItems(RegistryEvent.MissingMappings<Item> e) {
		for(RegistryEvent.MissingMappings.Mapping<Item> map : e.getMappings()) {
			if(map.key.getPath().equals("corporea_liar")) {
				map.remap(CorporeticsItems.RED_STRING_LIAR);
			}
		}
	}
}
