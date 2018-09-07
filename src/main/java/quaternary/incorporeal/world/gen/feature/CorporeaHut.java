package quaternary.incorporeal.world.gen.feature;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class CorporeaHut implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(!world.getWorldInfo().isMapFeaturesEnabled()) return;
		
		//TODO good config
		//- dimension white/blacklist
		//- biome white/blacklist?
		//- chance
		
		int placeX = chunkX * 16 + 8;
		int placeZ = chunkZ * 16 + 8;
		int placeY = world.getHeight(placeX, placeZ) + 10;
		//why not, right
		BlockPos.PooledMutableBlockPos bp = BlockPos.PooledMutableBlockPos.retain(placeX, placeY, placeZ);
		world.setBlockState(bp, Blocks.OBSIDIAN.getDefaultState(), 0); //Test
		bp.release();
	}
}
