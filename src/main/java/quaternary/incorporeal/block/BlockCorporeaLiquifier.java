package quaternary.incorporeal.block;


import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.lexicon.IncorporealLexiData;
import quaternary.incorporeal.tile.TileCorporeaLiquifier;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

import javax.annotation.Nullable;

public class BlockCorporeaLiquifier extends Block implements ILexiconable {
	public BlockCorporeaLiquifier() {
		super(Material.IRON, MapColor.PURPLE);
		
		setRegistryName(new ResourceLocation(Incorporeal.MODID, "corporea_liquifier"));
		setUnlocalizedName(Incorporeal.MODID + ".corporea_liquifier");
	}
	
	//Tile entity creation stuff
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCorporeaLiquifier();
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporealLexiData.corporeaTickets;
	}
}
