package quaternary.incorporeal.feature.cygnusnetwork.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.core.etc.helper.CygnusHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.cap.IncorporeticCygnusCapabilities;
import quaternary.incorporeal.feature.cygnusnetwork.etc.CygnusStackDataSerializer;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;

import javax.annotation.Nullable;

public class EntityCygnusMasterSpark extends AbstractEntityCygnusSparkBase implements ICygnusFunnelable {
	public EntityCygnusMasterSpark(World world) {
		super(world);
	}
	
	public static void createDataParameters() {
		CYGNUS_STACK = EntityDataManager.createKey(EntityCygnusMasterSpark.class, CygnusStackDataSerializer.INST);
	}
	
	protected static DataParameter<CygnusStack> CYGNUS_STACK;
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 2048; //big render boi
	}
	
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
		return new ItemStack(CygnusNetworkItems.MASTER_CYGNUS_SPARK);
	}
	
	@Override
	protected void traceNetwork(EntityPlayer player) {
		//TODO.
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
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if(capability == IncorporeticCygnusCapabilities.FUNNEL_CAP) return true;
		else return super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == IncorporeticCygnusCapabilities.FUNNEL_CAP) return (T) this;
		else return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean canGiveCygnusItem() {
		return true;
	}
	
	@Override
	public boolean canAcceptCygnusItem() {
		return true;
	}
	
	@Nullable
	@Override
	public Object giveItemToCygnus() {
		CygnusStack realStack = getCygnusStack();
		CygnusStack ret = realStack.copy();
		realStack.clear();
		return ret;
	}
	
	@Override
	public void acceptItemFromCygnus(Object item) {
		if(item instanceof ICygnusStack) {
			ICygnusStack newStack = (ICygnusStack) item;
			CygnusStack myStack = getCygnusStack();
			
			myStack.clear();
			for(int i = newStack.depth() - 1; i >= 0; i--) {
				newStack.peek(i).ifPresent(myStack::push);
			}
		}
	}
}
