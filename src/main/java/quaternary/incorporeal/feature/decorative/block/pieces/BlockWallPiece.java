package quaternary.incorporeal.feature.decorative.block.pieces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.feature.decorative.lexicon.DecorativeLexicon;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWallPiece extends BlockWall implements ILexiconable {
	public BlockWallPiece(Block block, Material mat, MapColor color, boolean cutout) {
		super(block);
		
		setHardness(block.blockHardness);
		setResistance(block.getExplosionResistance(null) * 5f / 3f); //why mojang
		
		actualMaterial = mat;
		actualMapColor = color;
		
		this.cutout = cutout;
		this.mainBlock = block;
	}
	
	public final Block mainBlock;
	public final Material actualMaterial;
	public final MapColor actualMapColor;
	public final boolean cutout;
	
	@Override
	public Material getMaterial(IBlockState state) {
		return actualMaterial;
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return actualMapColor;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return cutout ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		//superclass has some dumb shit relating to wall meta variants
		if(tab == Incorporeal.TAB) {
			items.add(new ItemStack(this));
		}
	}
	
	@Override
	public boolean isToolEffective(String type, IBlockState state) {
		return mainBlock.isToolEffective(type, state);
	}
	
	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
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
