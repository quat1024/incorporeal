package quaternary.incorporeal.feature.decorative.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockLokiW extends Block {
	public BlockLokiW() {
		//very holy block
		super(Material.CLAY);
		setHardness(3f);
		setResistance(2f);
		setSoundType(SoundType.SLIME);
		
		setHarvestLevel("shovel", 1);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public boolean isToolEffective(String type, IBlockState state) {
		return type.equals("shovel");
	}
	
	@Nullable
	@Override
	public String getHarvestTool(IBlockState state) {
		return "shovel";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag mistake) {
		tooltip.add(I18n.format("incorporeal.etc.holyblock"));
	}
}
