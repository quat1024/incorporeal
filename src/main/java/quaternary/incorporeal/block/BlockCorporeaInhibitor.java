package quaternary.incorporeal.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.ICorporeaInhibitor;
import quaternary.incorporeal.etc.DummyWorldEventListener;
import quaternary.incorporeal.lexicon.IncorporeticLexicon;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.entity.EntityCorporeaSpark;

import java.util.List;

public class BlockCorporeaInhibitor extends Block implements ICorporeaInhibitor, ILexiconable {
	public BlockCorporeaInhibitor() {
		super(Material.ROCK);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public boolean shouldBlockCorporea(World world, IBlockState state) {
		return true;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		Incorporeal.LOGGER.info("onBlockAdded " + pos);
		notifyNearbyCorporeaSparks(world, pos); //TODO: defer this?
	}
	
	@SubscribeEvent
	public void loadEvent(WorldEvent.Load e) {
		World world = e.getWorld();
		if(world.isRemote) return;
		
		world.addEventListener(new DummyWorldEventListener(){
			@Override
			public void notifyBlockUpdate(World world, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
				if(oldState.getBlock() instanceof BlockCorporeaInhibitor && !(newState.getBlock() instanceof BlockCorporeaInhibitor)) {
					notifyNearbyCorporeaSparks(world, pos);
				}
			}
		});
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		Incorporeal.LOGGER.info(state);
		Incorporeal.LOGGER.info(pos);
		Incorporeal.LOGGER.info(blockIn);
		Incorporeal.LOGGER.info(fromPos);
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
	}
	
	private static void notifyNearbyCorporeaSparks(World world, BlockPos pos) {
		AxisAlignedBB aabb = new AxisAlignedBB(pos).grow(8);
		
		List<EntityCorporeaSpark> nearbySporks = world.getEntitiesWithinAABB(EntityCorporeaSpark.class, aabb);
		
		for(EntityCorporeaSpark spork : nearbySporks) {
			try {
				ReflectionHelper.findMethod(EntityCorporeaSpark.class, "restartNetwork", null).invoke(spork);
			} catch (Exception oof) {
				//oof
			}
			ReflectionHelper.setPrivateValue(EntityCorporeaSpark.class, spork, true, "firstTick");
		}
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return IncorporeticLexicon.corporeaInhibitor;
	}
}
