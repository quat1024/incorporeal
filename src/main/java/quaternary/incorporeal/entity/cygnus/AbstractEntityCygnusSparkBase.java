package quaternary.incorporeal.entity.cygnus;

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
import net.minecraft.world.World;
import quaternary.incorporeal.etc.EnumDyeColorSerializer;
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
	protected abstract boolean canStay();
	protected abstract ItemStack getAssociatedItemStack();
	
	public abstract EntityCygnusMasterSpark getMasterSpark();
	
	public static final int SEARCH_RADIUS = 8;
	
	private static final DataParameter<EnumDyeColor> TINT = EntityDataManager.createKey(AbstractEntityCygnusSparkBase.class, EnumDyeColorSerializer.INST);
	private static final DataParameter<Boolean> PHANTOM = EntityDataManager.createKey(AbstractEntityCygnusSparkBase.class, DataSerializers.BOOLEAN);
	
	public AbstractEntityCygnusSparkBase(World world) {
		super(world);
	}
	
	@Override
	protected void entityInit() {
		isImmuneToFire = true;
		setSize(.2f, .5f); //Bigger than corporea sparks because those fuckers are hard to click LMAO
		dataManager.register(TINT, EnumDyeColor.WHITE);
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
		ItemStack held = player.getHeldItem(hand);
		
		if(held.getItem() instanceof ItemTwigWand && player.isSneaking()) {
			player.swingArm(hand);
			drop();
			return true;
		}
		
		if(held.getItem() instanceof ItemDye) { //Botania dye, not the vanilla one
			EnumDyeColor dyeTint = EnumDyeColor.byMetadata(held.getMetadata());
			if(getTint() != dyeTint) {
				if(!player.isCreative()) held.shrink(1);
				setTint(dyeTint);
				player.swingArm(hand);
				return true;
			}
		}
		
		if(held.getItem() == ModItems.phantomInk) {
			setPhantomInked(true);
			player.swingArm(hand);
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
		return dataManager.get(TINT);
	}
	
	public void setTint(EnumDyeColor tint) {
		dataManager.set(TINT, tint);
	}
	
	//I'd love to use isInvisible but Entity has that as a method lol
	public boolean isPhantomInked() {
		return dataManager.get(PHANTOM);
	}
	
	public void setPhantomInked(boolean phantom) {
		dataManager.set(PHANTOM, phantom);
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
