package quaternary.incorporeal.feature.cygnusnetwork.item;

import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusAction;
import quaternary.incorporeal.feature.cygnusnetwork.IncorporeticCygnusActions;

public class ItemCygnusWordCard extends ItemCygnusCard<ICygnusAction> {
	public ItemCygnusWordCard() {
		super(
			Incorporeal.API.getCygnusStackActionRegistry(),
			"Action",
			IncorporeticCygnusActions.NOTHING);
	}
}