package quaternary.incorporeal.feature.naturaldevices;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.incorporeal.api.INaturalDeviceRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public final class NaturalDeviceRegistry implements INaturalDeviceRegistry {
	private final List<Pair<Function<Random, IBlockState>, Double>> devices = new ArrayList<>();
	private double totalWeight = 0;
	
	public void register(Function<Random, IBlockState> deviceStateFactory, double weight) {
		devices.add(Pair.of(deviceStateFactory, weight));
		totalWeight += weight;
	}
	
	public IBlockState pullRandomDevice(Random rand) {
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
