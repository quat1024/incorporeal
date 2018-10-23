package quaternary.incorporeal.spookyasm;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporealapi.ICorporeaInhibitor;
import quaternary.incorporealapi.ICustomWrappedInventory;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.corporea.IWrappedInventory;
import vazkii.botania.api.corporea.InvWithLocation;
import vazkii.botania.common.core.helper.MathHelper;
import vazkii.botania.common.entity.EntityCorporeaSpark;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.List;

public final class Hooks {
	private Hooks() { }
	
	@SuppressWarnings("unused") //called through asm bullshit
	public static IWrappedInventory invWrapHook(InvWithLocation inv, ICorporeaSpark spork, IWrappedInventory wrap) {
		if(wrap == null) {
			World world = inv.world;
			BlockPos pos = inv.pos;
			TileEntity te = world.getTileEntity(pos);
			
			
			if(te instanceof ICustomWrappedInventory) {				
				wrap = ((ICustomWrappedInventory)te).wrap(inv, spork);
			}
		}
		
		return wrap;
	}
	
	@SuppressWarnings("unused")
	public static List<ICorporeaSpark> nearbyCorporeaSparkHook(List<ICorporeaSpark> allNearby, EntityCorporeaSpark me) {
		BlockPos myPos = me.getPosition();
		World world = me.world;
		if(world.isRemote) return allNearby;
		
		allNearby.removeIf(otherSpork -> {
			BlockPos otherPos;
			if(otherSpork instanceof EntityCorporeaSpark) {
				otherPos = ((EntityCorporeaSpark)otherSpork).getPosition();
			} else {
				InvWithLocation otherInventory = otherSpork.getSparkInventory();
				if(otherInventory == null) return true;
				otherPos = otherInventory.pos.up();
			}
			
			float searchStartX = myPos.getX() + .5f;
			float searchStartY = myPos.getY() - 0.2f;
			float searchStartZ = myPos.getZ() + .5f;
			
			float searchEndX = otherPos.getX() + .5f;
			float searchEndY = otherPos.getY() - 0.2f;
			float searchEndZ = otherPos.getZ() + .5f;
			
			//Shit raycast THE MOVIE
			final float distanceDivider = 1.5f;
			
			float dist = MathHelper.pointDistanceSpace(searchStartX, searchStartY, searchStartZ, searchEndX, searchEndY, searchEndZ);
			
			float searchStepX = (searchEndX - searchStartX) / dist / distanceDivider;
			float searchStepY = (searchEndY - searchStartY) / dist / distanceDivider;
			float searchStepZ = (searchEndZ - searchStartZ) / dist / distanceDivider;
			
			float searchX = searchStartX;
			float searchY = searchStartY;
			float searchZ = searchStartZ;
			
			BlockPos.MutableBlockPos prevPos = new BlockPos.MutableBlockPos(-1, -1, -1);
			BlockPos.MutableBlockPos oldPos = new BlockPos.MutableBlockPos(-1, -1, -1);
			
			for(int i=0; i < dist * distanceDivider; i++) {
				prevPos.setPos(searchX, searchY, searchZ);
				if(!oldPos.equals(prevPos)) {
					oldPos.setPos(searchX, searchY, searchZ);
					BlockPos searchPos = new BlockPos(searchX, searchY, searchZ);
					
					IBlockState state = world.getBlockState(searchPos);
					Block block = state.getBlock();
					if(block instanceof ICorporeaInhibitor) {
						boolean blocksCorporea = ((ICorporeaInhibitor)block).shouldBlockCorporea(world, state, searchPos);
						if(blocksCorporea) return true;
					}
				}
				
				searchX += searchStepX;
				searchY += searchStepY;
				searchZ += searchStepZ;
			}
			
			return false;
		});
		
		return allNearby;
	}
	
	@SuppressWarnings("unused")
	public static void displayEvil(int x, int y, int mana, int maxMana) {
		if (mana < 0) {
			return; // Unknown
		}
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(mc);
		String text = mana + "/" + maxMana;
		
		x = res.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(text) / 2;
		y -= 1;
		mc.fontRenderer.drawStringWithShadow(text, x, y, 16777215);
	}
}
