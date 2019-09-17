package quaternary.incorporeal.api;

import vazkii.botania.api.lexicon.LexiconPage;

import java.util.List;

public interface IDocumentableComponent {
	/**
	 * Add a documentation page to the appropriate Lexicon entry.
	 * @param pages append your pages to this list
	 * @since 1.2.4
	 */
	default void document(List<LexiconPage> pages) {
		
	}
}
