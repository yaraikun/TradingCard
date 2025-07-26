package com.tcis.models.binder;

import com.tcis.models.card.Card;
import com.tcis.models.card.Rarity;

/**
 * Represents a sellable binder that can only contain Rare and Legendary cards.
 * When sold, its price is the sum of the real values of its cards plus a 10%
 * handling fee.
 */
public class RaresBinder extends SellableBinder {

    /**
     * Constructs a new RaresBinder.
     * @param name The name for the binder.
     */
    public RaresBinder(String name) {
        super(name);
    }

    /**
     * Determines if a card can be added based on rares rules.
     * @param card The card to check.
     * @return true only if the card's rarity is Rare or Legendary.
     */
    @Override
    public boolean canAddCard(Card card) {
        return card.getRarity() == Rarity.RARE ||
               card.getRarity() == Rarity.LEGENDARY;
    }

    /**
     * Calculates the sale price of the binder.
     * @return The total sum of the real values of all cards inside, plus a 10%
     * handling fee.
     */
    @Override
    public double calculatePrice() {
        double totalCardValue = 0.0;

        for (Card card : this.cards)
            totalCardValue += card.getCalculatedValue();

        // Price is the total value plus a 10% fee
        return totalCardValue * 1.10;
    }
}
