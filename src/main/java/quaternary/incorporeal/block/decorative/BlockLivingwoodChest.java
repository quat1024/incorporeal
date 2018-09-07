package quaternary.incorporeal.block.decorative;

import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import quaternary.incorporeal.tile.decorative.TileLivingwoodChest;

public class BlockLivingwoodChest extends BlockChest {
	public BlockLivingwoodChest() {
		super(Type.BASIC);
		setHardness(2.5f);
		setSoundType(SoundType.WOOD);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileLivingwoodChest();
	}
}
