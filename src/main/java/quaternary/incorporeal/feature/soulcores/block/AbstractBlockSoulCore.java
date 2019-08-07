package quaternary.incorporeal.feature.soulcores.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.feature.soulcores.lexicon.SoulCoresLexicon;
import quaternary.incorporeal.feature.soulcores.tile.AbstractTileSoulCore;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.entity.EntityDoppleganger;

import javax.annotation.Nullable;

public abstract class AbstractBlockSoulCore extends Block implements ILexiconable, IWandHUD {
	protected AbstractBlockSoulCore() {
		super(Material.CIRCUITS);
		setHardness(1f);
		setSoundType(SoundType.METAL);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!EntityDoppleganger.isTruePlayer(player)) return false;
		
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof AbstractTileSoulCore) {
			return ((AbstractTileSoulCore) tile).click(player);
		}
		
		return false;
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof AbstractTileSoulCore) {
			return ((AbstractTileSoulCore) tile).getComparatorValue();
		} else return 0;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof AbstractTileSoulCore) {
			((AbstractTileSoulCore) tile).renderHUD(mc, res, world, pos);
		} else {
			HUDHandler.drawSimpleManaHUD(0xFF0000, 1, 1, "Missing TileEntity?", res);
		}
	}
	
	@Override
	public int getLightOpacity(IBlockState state) {
		return 0;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public abstract TileEntity createTileEntity(World world, IBlockState state);
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return SoulCoresLexicon.soulCores;
	}
}
