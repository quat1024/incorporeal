package quaternary.incorporeal.core;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.core.etc.helper.EtcHelpers;

import java.util.Objects;

public class BlocksModule {
	public static <T extends Block> T name(T block, String name) {
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		block.setTranslationKey(Incorporeal.MODID + "." + name);
		block.setCreativeTab(Incorporeal.TAB);
		return block;
	}
	
	//For the few occasions where i need to extend a botania block and they setregname in the ctor
	public static <T extends Block> T specialSnowflakeRemap(T block) {
		EtcHelpers.fixRegistryNameDespacito(block);
		ResourceLocation xd = Objects.requireNonNull(block.getRegistryName());
		block.setTranslationKey(xd.getNamespace() + "." + xd.getPath());
		block.setCreativeTab(Incorporeal.TAB);
		return block;
	}
}
