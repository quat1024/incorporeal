package quaternary.incorporeal.etc.helper;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import vazkii.botania.api.corporea.*;
import vazkii.botania.common.entity.EntityCorporeaSpark;

import java.util.List;

import static vazkii.botania.api.corporea.CorporeaHelper.requestItem;

public class CorporeaHelper2 {
	private CorporeaHelper2() {}
	
	public static EntityCorporeaSpark getSparkEntityForBlock(World world, BlockPos pos) {
		List<EntityCorporeaSpark> sparks = world.getEntitiesWithinAABB(EntityCorporeaSpark.class, new AxisAlignedBB(pos.up(), pos.add(1, 2, 1)));
		return sparks.isEmpty() ? null : sparks.get(0);
	}
	
	//Perform and spawn items from a request, much like a Corporea Index.
	public static void spawnRequest(World w, CorporeaRequest request, ICorporeaSpark spork, Vec3d spawningPos) {
		List<ItemStack> stacks = CorporeaHelper.requestItem(request.matcher, request.count, spork, request.checkNBT, true);
		spork.onItemsRequested(stacks);
		
		for(ItemStack stack : stacks) {
			if(stack.isEmpty()) continue;
			EntityItem ent = new EntityItem(w);
			ent.setItem(stack);
			ent.setPosition(spawningPos.x, spawningPos.y, spawningPos.z);
			w.spawnEntity(ent);			
		}
	}
	
	public static void spawnRequest(World w, CorporeaRequest request, ICorporeaSpark spork, BlockPos spawningPos) {
		spawnRequest(w, request, spork, new Vec3d(spawningPos.getX() + .5, spawningPos.getY() + 1.5, spawningPos.getZ() + .5));
	}
	
	public static String requestToString(CorporeaRequest request) {
		String count;
		String item;
		boolean pluralize = true;
		
		if(request.matcher instanceof ItemStack) {
			item = ((ItemStack)request.matcher).getDisplayName().toLowerCase();
		} else item = request.matcher.toString();
		
		if(request.count == 1) {
			count = "";
			pluralize = false;
		} else if(request.count == 64) {
			count = "a stack of ";
		} else if(request.count == Integer.MAX_VALUE) {
			count = "all ";
		} else {
			count = String.valueOf(request.count) + " ";
		}
		
		if(item.charAt(item.length() - 1) == 's') {
			pluralize = false;
		}
		
		return count + item + (pluralize ? "s" : "");
	}
	
	//Causes a spark to recheck its connections next time it ticks.
	//Call when e.g. changing the color of a spark or blocking a connection somehow.
	public static void causeSparkRelink(EntityCorporeaSpark spork) {
		try {
			ReflectionHelper.setPrivateValue(EntityCorporeaSpark.class, spork, null, "master");
			ReflectionHelper.setPrivateValue(EntityCorporeaSpark.class, spork, true, "firstTick");
			ReflectionHelper.findMethod(EntityCorporeaSpark.class, "restartNetwork", null).invoke(spork);
		} catch (Exception oof) {
			//oof
		}
	}
}
