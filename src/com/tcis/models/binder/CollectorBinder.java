package com.tcis.models.binder;

import com.tcis.models.card.Card;
import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;

/**
 * Represents a high-value collector's binder with strict content rules.
 * This binder can only contain Rare or Legendary cards that have a special
 * variant (not Normal). This binder can be used for trading but cannot be
 * sold.
 */
public class CollectorBinder extends Binder {

    /**
     * Constructs a new CollectorBinder.
     * @param name The name for the binder.
     */
    public CollectorBinder(String name) {
        super(name);
    }

    /**
     * Determines if a card can be added based on collector rules.
     * @param card The card to check.
     * @return true only if the card is Rare or Legendary AND its variant is
     * not Normal.
     */
    @Override
    public boolean canAddCard(Card card) {
        boolean isHighRarity = (card.getRarity() == Rarity.RARE ||
                                card.getRarity() == Rarity.LEGENDARY);
        boolean isSpecialVariant = (card.getVariant() != Variant.NORMAL);

        return isHighRarity && isSpecialVariant;
    }

    /**
     * Specifies if this binder type can be sold.
     * @return false, as Collector Binders are not sellable.
     */
    @Override
    public boolean isSellable() {
        return false;
    }

    /**
     * Specifies if this binder type can be used for trading.
     * @return true, as trading from Collector Binders is allowed.
     */
    @Override
    public boolean canTrade() {
        return true;
    }

    /**
     * Calculates the sale price.
     * @return 0.0, as this binder cannot be sold.
     */
    @Override
    public double calculatePrice() {
        return 0.0;
    }
}
