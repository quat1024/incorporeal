package quaternary.incorporeal.feature.cygnusnetwork.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.core.etc.helper.CygnusHelpers;

public class ItemCygnusSpark extends Item {
	public ItemCygnusSpark(boolean isMaster) {
		this.isMaster = isMaster;
	}
	
	public final boolean isMaster;
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean canPlaceHere = CygnusHelpers.isSparkable(world, pos, isMaster);
		boolean isSparkAlreadyHere = CygnusHelpers.isSparked(world, pos);
		
		if(canPlaceHere && !isSparkAlreadyHere) {
			if(!world.isRemote) CygnusHelpers.spawnSparkAt(world, pos, isMaster);
			
			if(!player.isCreative()) player.getHeldItem(hand).shrink(1);
			
			player.swingArm(hand);
			
			return EnumActionResult.PASS;
		} else return EnumActionResult.FAIL;
	}
}
