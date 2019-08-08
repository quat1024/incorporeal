package quaternary.incorporeal.core.feature;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import quaternary.incorporeal.IncorporeticFeatures;

import java.util.function.BooleanSupplier;

@SuppressWarnings("unused") //Referenced from _factories.json
public class FeatureConditionFactory implements IConditionFactory {
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		JsonElement elem = json.get("feature");
		
		if(elem.isJsonArray()) {
			return () -> {
				for(JsonElement yeah : elem.getAsJsonArray()) {
					if(!IncorporeticFeatures.isEnabled(yeah.getAsString())) return false;
				}
				return true;
			};
		}
		
		return () -> IncorporeticFeatures.isEnabled(elem.getAsString());
	}
}
