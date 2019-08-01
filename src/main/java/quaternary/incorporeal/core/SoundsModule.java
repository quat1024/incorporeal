package quaternary.incorporeal.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.feature.soulcores.sound.SoulCoresSounds;

public abstract class SoundsModule {
	protected static SoundEvent make(String name) {
		ResourceLocation eggs = new ResourceLocation(Incorporeal.MODID, name);
		return new SoundEvent(eggs).setRegistryName(eggs);
	}
}
