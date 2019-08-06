package quaternary.incorporeal.feature.naturaldevices;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.INaturalDeviceRegistry;
import quaternary.incorporeal.api.feature.IClientFeatureTwin;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.core.client.ClientHelpers;
import quaternary.incorporeal.feature.naturaldevices.block.NaturalDevicesBlocks;
import quaternary.incorporeal.feature.naturaldevices.etc.DispenserBehaviorRedstoneRoot;
import quaternary.incorporeal.feature.naturaldevices.event.PlantRedstoneRootEventHandler;
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
	public void preinit(FMLPreInitializationEvent e) {
		PlantRedstoneRootEventHandler.register();
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.manaResource, new DispenserBehaviorRedstoneRoot());
		
		INaturalDeviceRegistry reg = Incorporeal.API.getNaturalDeviceRegistry();
		
		reg.register((rand) -> {
			return NaturalDevicesBlocks.NATURAL_REPEATER.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.byHorizontalIndex(rand.nextInt(4)));
		}, 80);
		
		reg.register((rand) -> {
			return NaturalDevicesBlocks.NATURAL_COMPARATOR.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.byHorizontalIndex(rand.nextInt(4)));
		}, 20);
	}
	
	@Override
	public void blocks(IForgeRegistry<Block> blocks) {
		NaturalDevicesBlocks.registerBlocks(blocks);
	}
	
	@Override
	public void items(IForgeRegistry<Item> items) {
		NaturalDevicesItems.registerItems(items);
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
