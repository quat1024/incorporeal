package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.cygnus.CygnusDatatypeHelpers;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import quaternary.incorporeal.tile.cygnus.TileCygnusRetainer;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class BlockCygnusRetainer extends BlockCygnusBase implements IWandable, IWandHUD {
	public BlockCygnusRetainer() {
		setDefaultState(getDefaultState().withProperty(LIT, false));
	}
	
	public static final PropertyBool LIT = PropertyBool.create("lit");
	
	//iwandable
	@Override
	public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing facing) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusRetainer) {
			((TileCygnusRetainer)tile).wand();
			return true;
		}
		
		return false;
	}
	
	//comparator
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusRetainer) {
			return ((TileCygnusRetainer)tile).getComparator();
		} else return 0;
	}
	
	//hud
	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileCygnusRetainer) {
			TileCygnusRetainer retainer = (TileCygnusRetainer) tile;
			List<String> message = new LinkedList<>();
			FontRenderer font = mc.fontRenderer;
			
			if(retainer.hasRetainedObject()) {
				Object o = retainer.getRetainedObject();
				
				ICygnusDatatype<?> type = CygnusDatatypeHelpers.forClass(o.getClass());
				String typeName = I18n.translateToLocal(type.getTranslationKey());
				
				message.add(TextFormatting.GREEN + I18n.translateToLocalFormatted(
					EtcHelpers.vowelizeTranslationKey("incorporeal.cygnus.retainer.some", typeName),
					typeName
				));
				
				message.addAll(type.describeUnchecked(o));
			} else {
				message.add(TextFormatting.RED + I18n.translateToLocal("incorporeal.cygnus.retainer.none"));
			}
			
			int x = res.getScaledWidth() / 2 + 10;
			int y = res.getScaledHeight() / 2 - 10;
			
			for(String s : message) {
				font.drawStringWithShadow(s, x, y, 0xFFFFFF);
				y += 12;
			}
		}
	}
	
	//TE
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCygnusRetainer();
	}
	
	//blockstate junk
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LIT);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = EtcHelpers.getTileEntityThreadsafe(world, pos);
		if(tile instanceof TileCygnusRetainer) {
			return state.withProperty(LIT, ((TileCygnusRetainer)tile).hasRetainedObject());
		} else return state;
	}
}
