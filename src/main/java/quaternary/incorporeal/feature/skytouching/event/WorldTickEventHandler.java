package quaternary.incorporeal.feature.skytouching.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.core.IncorporeticPacketHandler;
import quaternary.incorporeal.feature.skytouching.net.MessageSkytouchingEffect;
import quaternary.incorporeal.feature.skytouching.recipe.SkytouchingRecipes;

import java.util.List;

public final class WorldTickEventHandler {
	private WorldTickEventHandler() {
	}
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(WorldTickEventHandler.class);
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
					i.prevPosY >= SkytouchingRecipes.LOWEST_SKYTOUCH_Y &&
					i.posY < i.prevPosY; //needs to be falling
		});
		
		for(EntityItem ent : items) {
			for(IRecipeSkytouching recipe : SkytouchingRecipes.ALL) {
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
}
