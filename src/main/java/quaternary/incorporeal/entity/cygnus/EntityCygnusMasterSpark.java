package quaternary.incorporeal.entity.cygnus;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.etc.CygnusStackDataSerializer;
import quaternary.incorporeal.etc.helper.CygnusHelpers;
import quaternary.incorporeal.item.cygnus.IncorporeticCygnusItems;

public class EntityCygnusMasterSpark extends AbstractEntityCygnusSparkBase {
	public EntityCygnusMasterSpark(World world) {
		super(world);
	}
	
	private static final DataParameter<CygnusStack> CYGNUS_STACK = EntityDataManager.createKey(EntityCygnusMasterSpark.class, CygnusStackDataSerializer.INST);
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(CYGNUS_STACK, new CygnusStack(16));
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!world.isRemote) {
			if(getCygnusStack().isDirty()) {
				dataManager.setDirty(CYGNUS_STACK);
				getCygnusStack().clean();
			}
		}
	}
	
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
	
	public CygnusStack getCygnusStack() {
		return dataManager.get(CYGNUS_STACK);
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setTag("Stack", getCygnusStack().toNBT());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		getCygnusStack().fromNBT(nbt.getCompoundTag("Stack"));
	}
}
