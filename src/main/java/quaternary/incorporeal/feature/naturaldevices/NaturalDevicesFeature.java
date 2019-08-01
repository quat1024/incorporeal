package quaternary.incorporeal.feature.naturaldevices;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.api.feature.IClientFeatureTwin;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.core.client.ClientHelpers;
import quaternary.incorporeal.core.etc.DispenserBehaviorRedstoneRoot;
import quaternary.incorporeal.core.etc.IncorporeticNaturalDevices;
import quaternary.incorporeal.feature.naturaldevices.block.NaturalDevicesBlocks;
import quaternary.incorporeal.feature.naturaldevices.item.NaturalDevicesItems;
import vazkii.botania.common.item.ModItems;

public class NaturalDevicesFeature implements IFeature {
	@Override
	public String name() {
		return "naturalDevices";
	}
	
	@Override
	public String description() {
		return "Mysterious natural devices grown from redstone.";
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		IncorporeticNaturalDevices.init();
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.manaResource, new DispenserBehaviorRedstoneRoot());
	}
	
	@Override
	public void blocks(IForgeRegistry<Block> blocks) {
		NaturalDevicesBlocks.registerBlocks(blocks);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IClientFeatureTwin client() {
		return new IClientFeatureTwin() {
			@Override
			public void models() {
				ClientHelpers.setSimpleModel(NaturalDevicesItems.NATURAL_REPEATER);
				ClientHelpers.setSimpleModel(NaturalDevicesItems.NATURAL_COMPARATOR);
			}
		};
	}
}
