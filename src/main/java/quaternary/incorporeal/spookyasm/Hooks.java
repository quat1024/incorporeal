package quaternary.incorporeal.spookyasm;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.etc.ICustomWrappedInventory;
import quaternary.incorporeal.etc.LyingWrappedInventory;
import quaternary.incorporeal.tile.TileCorporeaLiar;
import vazkii.botania.api.corporea.*;

public class Hooks {
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
}
