package quaternary.incorporeal.feature.cygnusnetwork.item;

import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.IncorporeticCygnusConditions;

import java.util.function.Predicate;

public class ItemCygnusCrystalCubeCard extends ItemCygnusCard<Predicate<ICygnusStack>> {
	public ItemCygnusCrystalCubeCard() {
		super(
			Incorporeal.API.getCygnusStackConditionRegistry(),
			"Condition",
			IncorporeticCygnusConditions.NOTHING);
	}
}
