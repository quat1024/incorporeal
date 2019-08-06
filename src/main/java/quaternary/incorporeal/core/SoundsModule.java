package quaternary.incorporeal.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import quaternary.incorporeal.Incorporeal;

public abstract class SoundsModule {
	protected static SoundEvent make(String name) {
		ResourceLocation eggs = new ResourceLocation(Incorporeal.MODID, name);
		return new SoundEvent(eggs).setRegistryName(eggs);
	}
}
