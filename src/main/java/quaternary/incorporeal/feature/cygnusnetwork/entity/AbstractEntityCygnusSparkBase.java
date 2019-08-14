package quaternary.incorporeal.feature.cygnusnetwork.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.common.item.ItemTwigWand;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.material.ItemDye;

import java.util.List;

//Cygnus sparks work a little differently from corporea sparks.
//Notably, there's never any need to get all of the sparks on the network.
//Sparks merely provide blocks access to a Cygnus stack; all of the blocks
//on the network access the same exact stack.
//Therefore, their structure is a little different.
public abstract class AbstractEntityCygnusSparkBase extends Entity {
	//Can this spark stay put on this block?
	protected abstract boolean canStay();
	
	//What is the item used to place this spark?
	protected abstract ItemStack getAssociatedItemStack();
	
	//Draw out the network with particles, since the player is asking for that
	protected abstract void traceNetwork(EntityPlayer player);
	
	//What is the master spark on your network?
	public abstract EntityCygnusMasterSpark getMasterSpark();
	
	public static final int SEARCH_RADIUS = 8;
	
	public static void createDataParameters() {
		TINT = EntityDataManager.createKey(AbstractEntityCygnusSparkBase.class, DataSerializers.BYTE);
		PHANTOM = EntityDataManager.createKey(AbstractEntityCygnusSparkBase.class, DataSerializers.BOOLEAN);
	}
	
	protected static DataParameter<Byte> TINT;
	protected static DataParameter<Boolean> PHANTOM;
	
	public AbstractEntityCygnusSparkBase(World world) {
		super(world);
	}
	
	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return getAssociatedItemStack().copy();
	}
	
	@Override
	protected void entityInit() {
		isImmuneToFire = true;
		setSize(.2f, .5f); //Bigger than corporea sparks because those fuckers are hard to click LMAO
		dataManager.register(TINT, (byte) EnumDyeColor.WHITE.getMetadata());
		dataManager.register(PHANTOM, false);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!world.isRemote) {
			posX = MathHelper.floor(posX) + .5;
			posY = MathHelper.floor(posY) + .5;
			posZ = MathHelper.floor(posZ) + .5;
			
			if(!canStay()) {
				drop();
			}
		}
	}
	
	public void drop() {
		if(!world.isRemote) {
			entityDropItem(getAssociatedItemStack(), 0);
			setDead();
		}
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		boolean ret = processInitialInteractInner(player, hand);
		
		if(ret) {
			//Was noticing a pattern.
			player.swingArm(hand);
		}
		
		return ret;
	}
	
	protected boolean processInitialInteractInner(EntityPlayer player, EnumHand hand) {
		ItemStack held = player.getHeldItem(hand);
		
		if(held.getItem() instanceof ItemTwigWand) {
			if(player.isSneaking()) {
				drop();
			} else {
				traceNetwork(player);
			}
			
			return true;
		}
		
		if(held.getItem() instanceof ItemDye) { //Botania dye, not the vanilla one
			EnumDyeColor dyeTint = EnumDyeColor.byMetadata(held.getMetadata());
			if(getTint() != dyeTint) {
				if(!player.isCreative()) held.shrink(1);
				setTint(dyeTint);
				return true;
			}
		}
		
		if(held.getItem() == ModItems.phantomInk) {
			setPhantomInked(true);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
	
	@Override
	public boolean canBePushed() {
		return false;
	}
	
	public EnumDyeColor getTint() {
		return EnumDyeColor.byMetadata(dataManager.get(TINT));
	}
	
	public void setTint(EnumDyeColor tint) {
		dataManager.set(TINT, (byte) tint.getMetadata());
		dataManager.setDirty(TINT);
	}
	
	//I'd love to use isInvisible but Entity has that as a method lol
	public boolean isPhantomInked() {
		return dataManager.get(PHANTOM);
	}
	
	public void setPhantomInked(boolean phantom) {
		dataManager.set(PHANTOM, phantom);
		dataManager.setDirty(PHANTOM);
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
		nbt.setBoolean("Phantom", isPhantomInked());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		setTint(EnumDyeColor.byMetadata(nbt.getInteger("Tint")));
		setPhantomInked(nbt.getBoolean("Phantom"));
	}
	
	public BlockPos getAttachedPosition() {
		return getPosition().down(2);
	}
	
	public IBlockState getAttachedState() {
		return world.getBlockState(getAttachedPosition());
	}
}
