package quaternary.incorporeal.etc;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.util.EnumFacing;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.INaturalDeviceRegistry;
import quaternary.incorporeal.block.IncorporeticBlocks;

public final class IncorporeticNaturalDevices {
	private IncorporeticNaturalDevices() {}
	
	public static void init() {
		INaturalDeviceRegistry reg = Incorporeal.API.getNaturalDeviceRegistry();
		
		reg.register((rand) -> {
			return IncorporeticBlocks.NATURAL_REPEATER.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.byHorizontalIndex(rand.nextInt(4)));
		}, 80);
		
		reg.register((rand) -> {
			return IncorporeticBlocks.NATURAL_COMPARATOR.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.byHorizontalIndex(rand.nextInt(4)));
		}, 20);
	}
}
