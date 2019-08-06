package quaternary.incorporeal;

import net.minecraftforge.fml.common.Loader;
import quaternary.incorporeal.api.feature.IFeature;
import quaternary.incorporeal.feature.compat.crafttweaker.CrafttweakerCompatFeature;
import quaternary.incorporeal.feature.compat.infraredstone.InfraRedstoneCompatFeature;
import quaternary.incorporeal.feature.compat.jei.JeiCompatFeature;
import quaternary.incorporeal.feature.corporetics.CorporeticsFeature;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusNetworkFeature;
import quaternary.incorporeal.feature.decorative.DecorativeFeature;
import quaternary.incorporeal.feature.naturaldevices.NaturalDevicesFeature;
import quaternary.incorporeal.feature.skytouching.SkytouchingFeature;
import quaternary.incorporeal.feature.soulcores.SoulCoresFeature;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public final class IncorporeticFeatures {
	private IncorporeticFeatures() {
	}
	
	public static final IFeature CORPORETICS = new CorporeticsFeature();
	
	public static final IFeature NATURAL_DEVICES = new NaturalDevicesFeature();
	public static final IFeature CYGNUS_NETWORK = new CygnusNetworkFeature();
	public static final IFeature SKYTOUCHING = new SkytouchingFeature();
	public static final IFeature DECORATIVE = new DecorativeFeature();
	public static final IFeature SOUL_CORES = new SoulCoresFeature();
	
	public static final IFeature INFRAREDSTONE_COMPAT = new InfraRedstoneCompatFeature();
	public static final IFeature JEI_COMPAT = new JeiCompatFeature();
	public static final IFeature CRAFTTWEAKER_COMPAT = new CrafttweakerCompatFeature();
	
	private static final Set<IFeature> eligibleFeatures = new HashSet<>();
	private static final Set<IFeature> enabledFeatures = new HashSet<>(); //written to by config system
	
	public static boolean isEnabled(IFeature feature) {
		return enabledFeatures.contains(feature);
	}
	
	public static void forEach(Consumer<IFeature> action) {
		enabledFeatures.forEach(action);
	}
	
	//pkg private
	static void enableFeature(IFeature feature) {
		enabledFeatures.add(feature);
	}
	
	//pkg private
	static void forEachIncludingDisabled(Consumer<IFeature> action) {
		eligibleFeatures.forEach(action);
	}
	
	static {
		//noinspection OverlyBroadCatchBlock
		try {
				nextFeature:
			for(Field field : IncorporeticFeatures.class.getDeclaredFields()) {
				if(field.getType() != IFeature.class) continue;
				IFeature feature = (IFeature) field.get(null);
				
				for(String modId : feature.requiredModIDs()) {
					if(!Loader.isModLoaded(modId)) {
						continue nextFeature;
					}
				}
				
				eligibleFeatures.add(feature);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
