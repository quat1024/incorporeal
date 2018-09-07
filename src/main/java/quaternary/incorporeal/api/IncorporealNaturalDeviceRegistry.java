package quaternary.incorporeal.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/** Used to determine what blocks the "planted redstone root" will be able to grow into. */
//TODO Implementation in API
public final class IncorporealNaturalDeviceRegistry {
	private IncorporealNaturalDeviceRegistry() {}
	
	private static final List<Pair<Function<Random, IBlockState>, Double>> devices = new ArrayList<>();
	private static double totalWeight = 0;
	
	public static void addNaturalDevice(IBlockState deviceState, double weight) {
		addNaturalDevice((rand) -> deviceState, weight);
	}
	
	/** Recommended to supply a function that makes the crop point in a random direction. */
	public static void addNaturalDevice(Function<Random, IBlockState> deviceStateFactory, double weight) {
		devices.add(Pair.of(deviceStateFactory, weight));
		totalWeight += weight;
	}
	
	public static IBlockState pullRandomDevice(Random rand) {
		if(devices.isEmpty()) {
			//TODO: throw if this happens too late (i.e. ingame?)
			return Blocks.AIR.getDefaultState();
		}
		
		double choice = rand.nextDouble() * totalWeight;
		double runningSum = 0;
		for(Pair<Function<Random, IBlockState>, Double> entry : devices) {
			runningSum += entry.getRight();
			if(runningSum >= choice) return entry.getLeft().apply(rand);
		}
		
		throw new RuntimeException("Error picking a natural device crop. This should be impossible? runningSum = " + runningSum + " choice = " + choice + " totalWeight = " + totalWeight);
	}
}
