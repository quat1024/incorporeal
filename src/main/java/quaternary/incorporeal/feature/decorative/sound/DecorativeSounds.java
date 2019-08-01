package quaternary.incorporeal.feature.decorative.sound;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.core.SoundsModule;

public class DecorativeSounds extends SoundsModule {
	public static final SoundEvent SHRINE = make("shrine");
	
	public static void registerSounds(IForgeRegistry<SoundEvent> sounds) {
		sounds.register(SHRINE);
	}
}
