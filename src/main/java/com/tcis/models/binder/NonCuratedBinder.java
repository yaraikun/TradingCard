package com.tcis.models.binder;

import com.tcis.models.card.Card;

/**
 * Represents a basic, non-specialized binder with no restrictions on its
 * contents.
 *
 * <p>
 * This class serves as the MCO1 equivalent of a binder. It allows any card
 * to be added, permits trading, but cannot be sold as a whole unit.
 * </p>
 */
public class NonCuratedBinder extends Binder {

    /**
     * Constructs a new NonCuratedBinder.
     *
     * @param name The name for the binder.
     */
    public NonCuratedBinder(String name) {
        super(name);
    }

    /**
     * Determines if a card can be added. For a Non-Curated Binder, any card is
     * allowed.
     *
     * @param card The card to check (is not used, but required by the
     *             superclass contract).
     * @return always true, as there are no restrictions.
     */
    public boolean canAddCard(Card card) {
        return true;
    }

    /**
     * Specifies if this binder type can be sold.
     *
     * @return false, as Non-Curated Binders are not sellable.
     */
    public boolean isSellable() {
        return false;
    }

    /**
     * Specifies if this binder type can be used for trading.
     *
     * @return true, as trading from Non-Curated Binders is allowed.
     */
    public boolean canTrade() {
        return true;
    }

    /**
     * Calculates the sale price.
     *
     * @return 0.0, as this binder cannot be sold.
     */
    public double calculatePrice() {
        return 0.0;
    }
}
