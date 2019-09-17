package quaternary.incorporeal.api.cygnus;

import quaternary.incorporeal.api.IDocumentableComponent;

import java.util.function.Consumer;

/**
 * Represents an action that can be performed on a Cygnus stack.
 * In-game, these are exposed as Cygnus word cards.
 * 
 * This interface is completely defined by its two superinterfaces, and is therefore empty.
 * 
 * @author quaternary
 * @since 1.2.4
 */
public interface ICygnusAction extends Consumer<ICygnusStack>, IDocumentableComponent {}
