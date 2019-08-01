package quaternary.incorporeal.feature.soulcores.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import quaternary.incorporeal.core.etc.BiOptional;
import quaternary.incorporeal.feature.soulcores.tile.TilePotionSoulCore;

import java.util.Collections;

public class EntityPotionSoulCoreCollector extends EntityLivingBase {
	public EntityPotionSoulCoreCollector(World world) {
		super(world);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		setSize(0.3f, 0.3f);
		setNoGravity(true);
		isImmuneToFire = true;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200f);
	}
	
	private BiOptional<EntityPlayer, TilePotionSoulCore> find() {
		TileEntity tile = world.getTileEntity(new BlockPos(posX, posY, posZ));
		if(tile instanceof TilePotionSoulCore) {
			TilePotionSoulCore psc = (TilePotionSoulCore) tile;
			return BiOptional.of(psc.findPlayer().orElse(null), psc);
		}
		
		return BiOptional.empty();
	}
	
	@Override
	public void onEntityUpdate() {
		//super.onEntityUpdate(); //No call to super.
		setHealth(getMaxHealth());
		
		motionX = 0;
		motionY = 0;
		motionZ = 0;
		
		find().ifBothPresent((player, tile) -> {
			//transfer pot effects to bound player
			for(PotionEffect effect : getActivePotionEffects()) {
				player.addPotionEffect(effect);
				tile.drainMana(200);
			}
			
			//clear pot effects on myself
			clearActivePotions();
			getActivePotionMap().clear(); //super ultra clear effects (lol)
		}).ifSecondNotPresent(this::setDead);
	}
	
	//used for instant effects
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		BiOptional<EntityPlayer, TilePotionSoulCore> biopic = find(); //PUN
		if(biopic.hasBoth()) {
			boolean happened = biopic.getFirst().attackEntityFrom(source, amount);
			if(happened) biopic.getSecond().drainMana(200);
			return happened;
		}
		
		return false;
	}
	
	//also used for instant effects
	@Override
	public void heal(float healAmount) {
		find().ifBothPresent((player, tile) -> {
			player.heal(healAmount);
			tile.drainMana(200);
		});
	}
	
	@Override
	public boolean isEntityUndead() {
		return true; //Well, I mean... it _is_ like a stabilized soul or something...?
	}
	
	//Below this line: stuff relating to this entity being an entitylivingbase
	//ONLY for purposes of potion effect application
	
	@Override
	public float getEyeHeight() {
		return 0.3f / 2;
	}
	
	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return Collections.emptyList();
	}
	
	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
		//No-op.
	}
	
	@Override
	public EnumHandSide getPrimaryHand() {
		return EnumHandSide.RIGHT; //Sorry lefties xd
	}
	
	@Override
	public boolean startRiding(Entity entityIn) {
		return false;
	}
	
	@Override
	public boolean startRiding(Entity entityIn, boolean force) {
		return false;
	}
	
	@Override
	public void swingArm(EnumHand hand) {
		//No-op.
	}
	
	@Override
	protected boolean isMovementBlocked() {
		return true;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true; //so you can click it for applyplayerinteraction
	}
	
	@Override
	public boolean canBePushed() {
		return false;
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		//You shouldn't be able to even reach the entity, but if one ever lingers outside of the block
		//This should let you clean it up.
		setDead();
		return EnumActionResult.SUCCESS;
	}
}
