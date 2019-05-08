package quaternary.incorporeal.etc;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;

public final class IncorporeticSounds {
	private IncorporeticSounds() {}
	
	public static final SoundEvent SHRINE = make("shrine");
	public static final SoundEvent UNSTABLE = make("unstable");
	
	public static void registerSounds(IForgeRegistry<SoundEvent> reg) {
		reg.registerAll(SHRINE, UNSTABLE);
	}
	
	private static SoundEvent make(String name) {
		ResourceLocation eggs = new ResourceLocation(Incorporeal.MODID, name);
		return new SoundEvent(eggs).setRegistryName(eggs);
	}
}
