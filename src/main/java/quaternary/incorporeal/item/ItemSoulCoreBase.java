package quaternary.incorporeal.item;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.block.soulcore.BlockSoulCoreBase;

public abstract class ItemSoulCoreBase extends ItemBlock {
	public ItemSoulCoreBase(BlockSoulCoreBase block) {
		super(block);
	}
	
	private BlockSoulCoreBase getSoulBlock() {
		return (BlockSoulCoreBase) block;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldStack = player.getHeldItem(hand);
		
		if(heldStack.isEmpty()) return EnumActionResult.FAIL;
		if(!player.canPlayerEdit(pos, facing, heldStack)) return EnumActionResult.FAIL;
		//Don't check mayPlace, because I want to *replace* the skull. mayPlace will fail.
		
		IBlockState clickedState = world.getBlockState(pos);
		Block clickedBlock = clickedState.getBlock();
		
		if(clickedBlock instanceof BlockSkull) {
			IBlockState placedState = block.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, heldStack.getMetadata(), player, hand);
			if(placeBlockAt(heldStack, player, world, pos, facing, hitX, hitY, hitZ, placedState)) {
				//copy this weird shit from itemblock
				placedState = world.getBlockState(pos);
				SoundType soundtype = placedState.getBlock().getSoundType(placedState, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				heldStack.shrink(1);
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
}
