package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.feature.decorative.lexicon.DecorativeLexicon;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

import javax.annotation.Nullable;
import java.util.List;

public class BlockFencePiece extends BlockFence implements ILexiconable {
	public BlockFencePiece(Block block, Material mat, MapColor color, boolean cutout) {
		super(mat, color);
		this.cutout = cutout;
		this.mainBlock = block;
		
		setSoundType(block.getSoundType());
		setHarvestLevel(block.getHarvestTool(block.getDefaultState()), block.getHarvestLevel(block.getDefaultState()));
	}
	
	private final Block mainBlock;
	private final boolean cutout;
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return cutout ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag mistake) {
		mainBlock.addInformation(stack, worldIn, tooltip, mistake);
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return DecorativeLexicon.elvenDecoration;
	}
}
