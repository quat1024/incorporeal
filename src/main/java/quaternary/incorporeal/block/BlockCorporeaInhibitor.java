package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.api.ICorporeaInhibitor;
import quaternary.incorporeal.etc.helper.CorporeaHelper2;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.entity.EntityCorporeaSpark;

import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber
public class BlockCorporeaInhibitor extends Block implements ICorporeaInhibitor, ILexiconable {
	public BlockCorporeaInhibitor() {
		super(Material.ROCK);
		this.setHardness(5.5F);
		this.setSoundType(SoundType.METAL);
	}
	
	private static final WeakHashMap<World, Set<BlockPos>> deferredCheckPositions = new WeakHashMap<>();
	
	@Override
	public boolean shouldBlockCorporea(World world, IBlockState state, BlockPos pos) {
		return true;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		notifyNearbyCorporeaSparksDeferred(world, pos);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		notifyNearbyCorporeaSparksDeferred(world, pos);
	}
	
	protected static void notifyNearbyCorporeaSparksDeferred(World world, BlockPos pos) {
		Set<BlockPos> positions = deferredCheckPositions.computeIfAbsent(world, (w) -> new HashSet<>());
		positions.add(pos);
	}
	
	@SubscribeEvent
	public static void worldTick(TickEvent.WorldTickEvent e) {
		if(e.phase == TickEvent.Phase.END) {
			Set<BlockPos> positions = deferredCheckPositions.get(e.world);
			if(positions != null) {
				for(BlockPos pos : positions) {
					notifyNearbyCorporeaSparks(e.world, pos);
					positions.remove(pos);
				}
			}
		}
	}
	
	private static void notifyNearbyCorporeaSparks(World world, BlockPos pos) {
		AxisAlignedBB aabb = new AxisAlignedBB(pos).grow(8);
		
		world.getEntitiesWithinAABB(EntityCorporeaSpark.class, aabb).forEach(CorporeaHelper2::causeSparkRelink);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return !(world.getBlockState(pos.offset(side)).getBlock() instanceof BlockCorporeaInhibitor);
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.corporeaInhibitor;
	}
}
