package quaternary.incorporeal.tile.cygnus;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import quaternary.incorporeal.block.cygnus.BlockCygnusCrystalCube;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.etc.helper.CygnusHelpers;

public class TileCygnusCrystalCube extends TileEntity implements ITickable {
	boolean enabled;
	long lastUpdate = 0;
	
	@Override
	public void update() {
		lastUpdate = world.getTotalWorldTime();
		
		Block block = getBlockType();
		if(block instanceof BlockCygnusCrystalCube) {
			BlockCygnusCrystalCube cube = (BlockCygnusCrystalCube) block;
			
			EntityCygnusMasterSpark master = CygnusHelpers.getMasterSparkForSparkAt(world, pos);
			if(master != null) {
				boolean wasEnabled = enabled;
				enabled = cube.condition.test(master.getCygnusStack());
				if(enabled != wasEnabled) {
					world.updateComparatorOutputLevel(pos, cube);
				}
			}
		}
	}
	
	public boolean isEnabled() {
		if(lastUpdate != world.getTotalWorldTime()) update();
		return enabled;
	}
}
