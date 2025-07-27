package com.tcis.models.binder;

import com.tcis.models.card.Card;
import com.tcis.models.card.Rarity;

/**
 * Represents a sellable binder that can only contain Common and Uncommon
 * cards.
 *
 * <p>
 * When sold, its price is the sum of the real values of its cards, with no
 * handling fee applied.
 * </p>
 */
public class PauperBinder extends SellableBinder {

    /**
     * Constructs a new PauperBinder.
     *
     * @param name The name for the binder.
     */
    public PauperBinder(String name) {
        super(name);
    }

    /**
     * Determines if a card can be added based on pauper rules.
     *
     * @param card The card to check.
     * @return true only if the card's rarity is Common or Uncommon.
     */
    public boolean canAddCard(Card card) {
        return card.getRarity() == Rarity.COMMON ||
               card.getRarity() == Rarity.UNCOMMON;
    }

    /**
     * Calculates the sale price of the binder.
     *
     * @return The total sum of the real values of all cards inside, without
     *         any handling fee.
     */
    public double calculatePrice() {
        double totalPrice = 0.0;
        for (Card card : this.cards)
            totalPrice += card.getCalculatedValue();

        return totalPrice;
    }
}
