package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;

public class BlockCorporeaRetainerDecrementer extends Block {
	public BlockCorporeaRetainerDecrementer() {
		super(Material.IRON);
		setHardness(5.5f);
		setSoundType(SoundType.METAL);
		
		setDefaultState(getDefaultState().withProperty(POWERED, false));
	}
	
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		updatePoweredState(world, pos, state);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		updatePoweredState(world, pos, state);
	}
	
	private void updatePoweredState(World world, BlockPos pos, IBlockState state) {
		boolean shouldPower = world.isBlockPowered(pos);
		boolean isPowered = state.getValue(POWERED);
		
		if(shouldPower != isPowered) {
			world.setBlockState(pos, state.withProperty(POWERED, shouldPower));
			
			if(shouldPower) {
				for(EnumFacing horiz : EnumFacing.HORIZONTALS) {
					TileEntity tile = world.getTileEntity(pos.offset(horiz));
					if(tile instanceof TileCorporeaRetainer) {
						TileCorporeaRetainer retainer = (TileCorporeaRetainer) tile;
						if(!retainer.hasPendingRequest()) continue;
						
						int retainerCount = ReflectionHelper.getPrivateValue(TileCorporeaRetainer.class, retainer, "requestCount");
						retainerCount--;
						if(retainerCount > 0) {
							ReflectionHelper.setPrivateValue(TileCorporeaRetainer.class, retainer, retainerCount, "requestCount");
						} else {
							ReflectionHelper.setPrivateValue(TileCorporeaRetainer.class, retainer, false, "pendingRequest");
							world.updateComparatorOutputLevel(pos.offset(horiz), ModBlocks.corporeaRetainer);
						}
					}
				}
			}
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, POWERED);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(POWERED, meta == 1);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}
}
