package quaternary.incorporeal.item.cygnus;

import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.cygnus.IncorporeticCygnusConditions;

import java.util.function.Predicate;

public class ItemCygnusCrystalCubeCard extends ItemCygnusCard<Predicate<ICygnusStack>> {
	public ItemCygnusCrystalCubeCard() {
		super(
						Incorporeal.API.getCygnusStackConditionRegistry(),
						"Condition",
						IncorporeticCygnusConditions.NOTHING);
	}
}
