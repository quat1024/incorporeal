package quaternary.incorporeal.entity;

import com.google.common.collect.Iterators;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.tile.soulcore.TilePotionSoulCore;

import java.util.Collections;

public class EntityPotionSoulCoreCollector extends EntityLivingBase {
	public EntityPotionSoulCoreCollector(World world) {
		super(world);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		setSize(0.3f, 0.3f);
	}
	
	@Override
	public float getEyeHeight() {
		return 0.3f / 2;
	}
	
	@Override
	public void onEntityUpdate() {
		//super.onEntityUpdate(); //No call to super.
		if(getActivePotionEffects().isEmpty()) return;
		
		TileEntity tile = world.getTileEntity(getPosition());
		if(tile instanceof TilePotionSoulCore) {
			TilePotionSoulCore psc = (TilePotionSoulCore) tile;
			psc.findPlayer().ifPresent(player -> {
				for(PotionEffect effect : getActivePotionEffects()) {
					player.addPotionEffect(effect);
					psc.drainMana(200); //TODO balance this shit
				}
				
				//clearActivePotions opens a slight window for special potions to reapply themselves
				//Since it calls unapply methods in the potion effect that a naughty potion may override
				//Or maybe idk, I'm just being paranoid
				clearActivePotions();
				getActivePotionMap().clear();
			});
		} else {
			setDead();
		}
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
}
