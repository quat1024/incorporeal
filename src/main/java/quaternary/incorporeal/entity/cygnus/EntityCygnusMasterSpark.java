package quaternary.incorporeal.entity.cygnus;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.etc.helper.CygnusHelpers;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;

public class EntityCygnusMasterSpark extends AbstractEntityCygnusSparkBase {
	public EntityCygnusMasterSpark(World world) {
		super(world);
	}
	
	public final CygnusStack cygnusStack = new CygnusStack(16);
	
	@Override
	protected boolean canStay() {
		return CygnusHelpers.isSparkable(world, getAttachedPosition(), true);
	}
	
	@Override
	protected ItemStack getAssociatedItemStack() {
		return new ItemStack(IncorporeticCygnusItems.MASTER_CYGNUS_SPARK);
	}
	
	@Override
	public EntityCygnusMasterSpark getMasterSpark() {
		return this;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setTag("Stack", cygnusStack.toNBT());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		cygnusStack.fromNBT(nbt.getCompoundTag("Stack"));
	}
}
