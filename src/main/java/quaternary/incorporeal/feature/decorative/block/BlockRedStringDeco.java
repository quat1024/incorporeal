package quaternary.incorporeal.feature.decorative.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockRedStringDeco extends Block {
	public BlockRedStringDeco() {
		super(Material.ROCK);
		setHardness(2f);
		setResistance(10f);
		setSoundType(SoundType.STONE);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag mistake) {
		tooltip.add(I18n.format("incorporeal.etc.safeForDecoration"));
	}
}
