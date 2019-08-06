package quaternary.incorporeal.feature.cygnusnetwork.item;

import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.feature.cygnusnetwork.IncorporeticCygnusActions;

import java.util.function.Consumer;

public class ItemCygnusWordCard extends ItemCygnusCard<Consumer<ICygnusStack>> {
	public ItemCygnusWordCard() {
		super(
			Incorporeal.API.getCygnusStackActionRegistry(),
			"Action",
			IncorporeticCygnusActions.NOTHING);
	}
}