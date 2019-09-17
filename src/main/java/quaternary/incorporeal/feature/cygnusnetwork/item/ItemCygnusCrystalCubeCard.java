package quaternary.incorporeal.feature.cygnusnetwork.item;

import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusCondition;
import quaternary.incorporeal.feature.cygnusnetwork.IncorporeticCygnusConditions;

public class ItemCygnusCrystalCubeCard extends ItemCygnusCard<ICygnusCondition> {
	public ItemCygnusCrystalCubeCard() {
		super(
			Incorporeal.API.getCygnusStackConditionRegistry(),
			"Condition",
			IncorporeticCygnusConditions.NOTHING);
	}
}
