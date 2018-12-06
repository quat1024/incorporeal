package quaternary.incorporeal.item.cygnus;

import quaternary.incorporeal.api.cygnus.ICygnusStack;

import java.util.function.Consumer;

import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.cygnus.IncorporeticCygnusActions;

public class ItemCygnusWordCard extends ItemCygnusCard<Consumer<ICygnusStack>> {
	public ItemCygnusWordCard() {
		super(
						Incorporeal.API.getCygnusStackActionRegistry(),
						"Action",
						IncorporeticCygnusActions.NOTHING);
	}
}