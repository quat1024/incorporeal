package quaternary.incorporeal.entity.cygnus;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import quaternary.incorporeal.etc.helper.CygnusHelpers;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;

public class EntityCygnusMasterSpark extends AbstractEntityCygnusSparkBase {
	public EntityCygnusMasterSpark(World world) {
		super(world);
	}
	
	@Override
	protected boolean canStay() {
		return CygnusHelpers.isSparkable(world, getAttachedPosition(), true);
	}
	
	@Override
	protected ItemStack getAssociatedItemStack() {
		return new ItemStack(IncorporeticCygnusItems.MASTER_CYGNUS_SPARK);
	}
}
