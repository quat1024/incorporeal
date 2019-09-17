package quaternary.incorporeal.api.cygnus;

import quaternary.incorporeal.api.IDocumentableComponent;

import java.util.function.Predicate;

/**
 * Represents a condition that a Cygnus stack can be tested against.
 * In-game, these are exposed as Cygnus crystal cube cards.
 *
 * This interface is completely defined by its two superinterfaces, and is therefore empty.
 *
 * @author quaternary
 * @since 1.2.4
 */
public interface ICygnusCondition extends Predicate<ICygnusStack>, IDocumentableComponent {}
