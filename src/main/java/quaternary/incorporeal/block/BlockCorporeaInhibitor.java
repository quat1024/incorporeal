package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.ICorporeaInhibitor;

public class BlockCorporeaInhibitor extends Block implements ICorporeaInhibitor {
	public BlockCorporeaInhibitor() {
		super(Material.ROCK);
		
		setRegistryName(new ResourceLocation(Incorporeal.MODID, "corporea_inhibitor"));
		setUnlocalizedName(Incorporeal.MODID + ".corporea_inhibitor");
	}
	
	@Override
	public boolean shouldBlockCorporea(World world, IBlockState state) {
		return true;
	}
}
