package quaternary.incorporeal.block.soulcore;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import quaternary.incorporeal.tile.soulcore.AbstractTileSoulCore;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

import javax.annotation.Nullable;

public abstract class AbstractBlockSoulCore extends Block implements ILexiconable {
	protected AbstractBlockSoulCore() {
		super(Material.CIRCUITS);
		setHardness(1f);
		setSoundType(SoundType.METAL);
	}
	
	public static final DamageSource SOUL = new DamageSource("incorporeal.soul").setMagicDamage();
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(!(placer instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer) placer;
		
		TileEntity tile = world.getTileEntity(pos);
		
		if(tile instanceof AbstractTileSoulCore) {
			AbstractTileSoulCore abs = (AbstractTileSoulCore) tile; 
			setOwner(abs, player, player.getGameProfile());
			abs.receiveInitialMana();
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof AbstractTileSoulCore) {
			AbstractTileSoulCore abs = (AbstractTileSoulCore) tile;
			setOwner(abs, player, player.getGameProfile());
			return true;
		}
		
		return false;
	}
	
	private boolean setOwner(AbstractTileSoulCore tile, EntityPlayer player, GameProfile prof) {
		boolean shouldSet = !tile.hasOwnerProfile() || !prof.getId().equals(tile.getOwnerProfile().getId());
		if(shouldSet) {
			tile.setOwnerProfile(prof);
			player.attackEntityFrom(SOUL, 5.0f);
			//Particles or something, idk.
			return true;
		} else return false;
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
		return IncorporeticLexicon.soulCores;
	}
}
