package quaternary.incorporeal.item;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.block.soulcore.AbstractBlockSoulCore;
import quaternary.incorporeal.tile.soulcore.AbstractTileSoulCore;

import java.util.UUID;

public class ItemSoulCore extends ItemBlock {
	public ItemSoulCore(AbstractBlockSoulCore block) {
		super(block);
	}
	
	private AbstractBlockSoulCore getSoulBlock() {
		return (AbstractBlockSoulCore) block;
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
			UUID uuid = null;
			
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileEntitySkull) {
				TileEntitySkull skull = (TileEntitySkull) tile;
				GameProfile profile = skull.getPlayerProfile();
				if(profile != null) uuid = profile.getId();
			}
			
			if(uuid == null) return EnumActionResult.PASS;
			
			IBlockState placedState = block.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, heldStack.getMetadata(), player, hand);
			boolean success = placeBlockAt(heldStack, player, world, pos, facing, hitX, hitY, hitZ, placedState) ;
			if(success) {
				//play placing noise TODO this can be souped up right?
				placedState = world.getBlockState(pos);
				SoundType soundtype = placedState.getBlock().getSoundType(placedState, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				
				//shrincc
				heldStack.shrink(1);
				
				//set the uuid on the tile entity to the one on the skull
				if(!world.isRemote) {
					TileEntity soulcoreTile = world.getTileEntity(pos);
					if(soulcoreTile instanceof AbstractTileSoulCore) {
						((AbstractTileSoulCore) soulcoreTile).setOwnerUUID(uuid);
					}
				}
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
}
