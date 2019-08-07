package quaternary.incorporeal.feature.soulcores.sound;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.SoundsModule;

public class SoulCoresSounds extends SoundsModule {
	public static final SoundEvent UNSTABLE = make("unstable");
	
	public static void register(IForgeRegistry<SoundEvent> reg) {
		reg.register(UNSTABLE);
	}
}
