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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public final class IncorporeticFeatures {
	private IncorporeticFeatures() {}
	
	public static final IFeature CORPORETICS = new CorporeticsFeature();
	
	public static final IFeature NATURAL_DEVICES = new NaturalDevicesFeature();
	public static final IFeature CYGNUS_NETWORK = new CygnusNetworkFeature();
	public static final IFeature SKYTOUCHING = new SkytouchingFeature();
	public static final IFeature DECORATIVE = new DecorativeFeature();
	public static final IFeature SOUL_CORES = new SoulCoresFeature();
	
	public static final IFeature INFRAREDSTONE_COMPAT = new InfraRedstoneCompatFeature();
	public static final IFeature JEI_COMPAT = new JeiCompatFeature();
	public static final IFeature CRAFTTWEAKER_COMPAT = new CrafttweakerCompatFeature();
	
	private static final ArrayList<IFeature> eligibleFeatures = new ArrayList<>();
	private static final ArrayList<IFeature> enabledFeatures = new ArrayList<>(); //written to by config system
	private static final Map<String, IFeature> byName = new HashMap<>();
	
	public static boolean isEnabled(IFeature feature) {
		return enabledFeatures.contains(feature);
	}
	
	public static boolean isEnabled(String name) {
		IFeature f = byName(name);
		if(f == null) return false;
		else return isEnabled(f);
	}
	
	public static void forEach(Consumer<IFeature> action) {
		enabledFeatures.forEach(action);
	}
	
	public static IFeature byName(String name) {
		return byName.get(name);
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
			Field[] fields = IncorporeticFeatures.class.getDeclaredFields();
			List<Field> fieldList = Arrays.asList(fields);
			fieldList.sort(Comparator.comparing(Field::getName));
			
				nextFeature:
			for(Field field : fieldList) {
				if(field.getType() != IFeature.class) continue;
				IFeature feature = (IFeature) field.get(null);
				
				for(String modId : feature.requiredModIDs()) {
					if(!Loader.isModLoaded(modId)) {
						continue nextFeature;
					}
				}
				
				eligibleFeatures.add(feature);
				byName.put(feature.name(), feature);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
