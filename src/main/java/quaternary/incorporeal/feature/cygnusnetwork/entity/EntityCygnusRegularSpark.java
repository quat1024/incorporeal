package quaternary.incorporeal.feature.cygnusnetwork.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import quaternary.incorporeal.core.etc.helper.CygnusHelpers;
import quaternary.incorporeal.feature.cygnusnetwork.item.CygnusNetworkItems;
import vazkii.botania.common.entity.EntitySpark;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityCygnusRegularSpark extends AbstractEntityCygnusSparkBase {
	public EntityCygnusRegularSpark(World world) {
		super(world);
	}
	
	@Nullable
	EntityCygnusMasterSpark knownMaster;
	@Nullable
	AbstractEntityCygnusSparkBase uplinkToMaster;
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(world.isRemote) return;
		
		//This stuff isn't *super* important and it's pretty expensive so I'll add this little clock
		if(world.getTotalWorldTime() % 20 != 0) return;
		
		if(knownMaster == null) {
			//Look for a master spark
			List<AbstractEntityCygnusSparkBase> nearbySparks = findNearbyMatchingTintSparks();
			
			//First, check and see if there are any master sparks right nearby
			for(AbstractEntityCygnusSparkBase spark : nearbySparks) {
				if(spark instanceof EntityCygnusMasterSpark) {
					knownMaster = (EntityCygnusMasterSpark) spark;
					uplinkToMaster = knownMaster;
					break;
				}
			}
			
			//None?
			if(knownMaster == null) {
				//Next, check if any nearby sparks have an uplink to a master spark
				for(AbstractEntityCygnusSparkBase spark : nearbySparks) {
					if(spark instanceof EntityCygnusRegularSpark) {
						EntityCygnusRegularSpark regularSpark = (EntityCygnusRegularSpark) spark;
						if(regularSpark.knownMaster != null) {
							knownMaster = regularSpark.knownMaster;
							uplinkToMaster = regularSpark;
							break;
						}
					}
				}
				
				//Still none? Oh well try again next time I guess
				//TODO add a sleep/wake system so sparks don't sit there idle doing tons of expensive checks
			}
		} else {
			//If I think I know of a master spark, sanity-check to make sure it isn't stale
			if(knownMaster.isDead || !world.loadedEntityList.contains(knownMaster)) {
				clearMaster();
			} else if(uplinkToMaster != null && knownMaster != uplinkToMaster) {
				//Traverse the uplink tree back upwards and make sure that the master spark is still reachable from here
				AbstractEntityCygnusSparkBase uplink = uplinkToMaster;
				Set<AbstractEntityCygnusSparkBase> visitedSparks = new HashSet<>();
				visitedSparks.add(this);
				
				boolean ok = false;
				
				while(true) {
					if(uplink == null || visitedSparks.contains(uplink)) {
						//Wuh oh can't find the master spark
						break;
					}
					
					visitedSparks.add(uplink);
					if(uplink instanceof EntityCygnusMasterSpark) {
						//Here it is!!!
						ok = true;
						break;
					} else if(uplink instanceof EntityCygnusRegularSpark) {
						uplink = ((EntityCygnusRegularSpark) uplink).uplinkToMaster;
					} else {
						throw new RuntimeException("Found a spark that's not a master or nonmaster spark? wot?");
					}
				}
				
				if(!ok) {
					//Was not able to traverse the tree to find a master spark. It's not reachable after all
					clearMaster();
				}
			}
		}
	}
	
	private void clearMaster() {
		//The price is right losing horn.mp3
		knownMaster = null;
		uplinkToMaster = null;
	}
	
	@Override
	public void setTint(EnumDyeColor tint) {
		super.setTint(tint);
		clearMaster();
	}
	
	@Override
	protected boolean canStay() {
		return CygnusHelpers.isSparkable(world, getAttachedPosition(), false);
	}
	
	@Override
	protected ItemStack getAssociatedItemStack() {
		return new ItemStack(CygnusNetworkItems.CYGNUS_SPARK);
	}
	
	@Override
	protected void traceNetwork(EntityPlayer player) {
		if(uplinkToMaster == null) return;
		
		//Trace out the path to the master spark
		//This doesn't trace the whole network. Work on that.
		AbstractEntityCygnusSparkBase a = this;
		AbstractEntityCygnusSparkBase b = this.uplinkToMaster;
		while(true) {
			EntitySpark.particleBeam(player, a, b);
			a = b;
			if(b instanceof EntityCygnusRegularSpark) {
				b = ((EntityCygnusRegularSpark) b).uplinkToMaster;
			} else break;
		}
	}
	
	@Override
	public EntityCygnusMasterSpark getMasterSpark() {
		return knownMaster;
	}
}
