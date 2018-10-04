package quaternary.incorporeal.entity.cygnus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.etc.EnumDyeColorSerializer;

import java.util.List;

//Cygnus sparks work a little differently from corporea sparks.
//Notably, there's never any need to get all of the sparks on the network.
//Sparks merely provide blocks access to a Cygnus stack; all of the blocks
//on the network access the same exact stack.
//Therefore, their structure is a little different.
public abstract class AbstractEntityCygnusSparkBase extends Entity {
	protected abstract boolean canStay();
	protected abstract ItemStack getAssociatedItemStack();
	
	public static final int SEARCH_RADIUS = 8;
	
	private static final DataParameter<EnumDyeColor> TINT = EntityDataManager.createKey(EntityCygnusRegularSpark.class, EnumDyeColorSerializer.INST);
	
	public AbstractEntityCygnusSparkBase(World world) {
		super(world);
	}
	
	@Override
	protected void entityInit() {
		isImmuneToFire = true;
		setSize(.15f, .15f);
		dataManager.register(TINT, EnumDyeColor.WHITE);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!canStay()) {
			entityDropItem(getAssociatedItemStack(), 0);
			setDead();
		}
	}
	
	public EnumDyeColor getTint() {
		return dataManager.get(TINT);
	}
	
	public void setTint(EnumDyeColor tint) {
		dataManager.set(TINT, tint);
	}
	
	protected List<AbstractEntityCygnusSparkBase> findNearbyMatchingTintSparks() {
		AxisAlignedBB searchBox = new AxisAlignedBB(posX, posY, posZ, posX, posY, posZ).grow(SEARCH_RADIUS);
		List<AbstractEntityCygnusSparkBase> neighbors = world.getEntitiesWithinAABB(AbstractEntityCygnusSparkBase.class, searchBox);
		neighbors.removeIf(other -> other.getTint() != getTint());
		return neighbors;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Tint", getTint().getMetadata());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		setTint(EnumDyeColor.byMetadata(nbt.getInteger("Tint")));
	}
	
	public BlockPos getAttachedPosition() {
		return getPosition().down();
	}
	
	public IBlockState getAttachedState() {
		return world.getBlockState(getAttachedPosition());
	}
}
