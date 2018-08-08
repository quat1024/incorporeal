package quaternary.incorporeal.item;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.IncorporeticConfig;
import quaternary.incorporeal.block.soulcore.AbstractBlockSoulCore;
import quaternary.incorporeal.tile.soulcore.AbstractTileSoulCore;

public class ItemSoulCore extends ItemBlock {
	public ItemSoulCore(AbstractBlockSoulCore block) {
		super(block);
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
			GameProfile profile = null;
			
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileEntitySkull) {
				TileEntitySkull skull = (TileEntitySkull) tile;
				profile = skull.getPlayerProfile();
			}
			
			if(profile == null) return EnumActionResult.PASS;
			
			if(!IncorporeticConfig.SoulCore.EVERYONE_ANYONE && !profile.equals(player.getGameProfile())) return EnumActionResult.PASS;
			
			IBlockState placedState = block.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, heldStack.getMetadata(), player, hand);
			boolean success = placeBlockAt(heldStack, player, world, pos, facing, hitX, hitY, hitZ, placedState) ;
			if(success) {
				//play placing noise TODO this can be souped up right?
				placedState = world.getBlockState(pos);
				SoundType soundtype = placedState.getBlock().getSoundType(placedState, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				
				//shrincc
				if(!player.isCreative()) heldStack.shrink(1);
				
				//set the game profile on the tile entity to the one on the skull
				if(!world.isRemote) {
					TileEntity soulcoreTile = world.getTileEntity(pos);
					if(soulcoreTile instanceof AbstractTileSoulCore) {
						((AbstractTileSoulCore) soulcoreTile).setOwnerProfile(profile);
					}
				}
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
}
