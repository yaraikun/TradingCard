package com.tcis.models.binder;

import com.tcis.models.card.Card;
import com.tcis.models.card.Variant;

/**
 * Represents a sellable binder that can only contain cards with special
 * (non-Normal) variants.
 *
 * <p>Its price can be set manually by the user, but cannot be lower than the
 * total real value of its contents. When sold, it incurs a 10% handling fee
 * on its set price.</p>
 */
public class LuxuryBinder extends SellableBinder {
    /**
     * Stores the custom price set by the user. A value of 0.0 indicates that
     * no custom price has been set.
     */
    private double customPrice;

    /**
     * Constructs a new LuxuryBinder. The custom price is initially 0.
     *
     * @param name The name for the binder.
     */
    public LuxuryBinder(String name) {
        super(name);
        this.customPrice = 0.0;
    }

    /**
     * Determines if a card can be added based on luxury rules.
     *
     * @param card The card to check.
     * @return true only if the card's variant is not Normal.
     */
    public boolean canAddCard(Card card) {
        return card.getVariant() != Variant.NORMAL;
    }

    /**
     * Calculates the total real value of all cards currently in the binder.
     * This is used as a minimum baseline for setting the custom price.
     *
     * @return The sum of the calculated values of all cards.
     */
    private double getTotalCardValue() {
        double totalValue = 0.0;

        for (Card card : this.cards)
            totalValue += card.getCalculatedValue();

        return totalValue;
    }

    /**
     * Sets a custom price for the binder. The price is only set if it is not
     * lower than the total real value of the cards contained within.
     *
     * @param price The desired custom price.
     * @return true if the price was successfully set, false otherwise.
     */
    public boolean setPrice(double price) {
        if (price >= getTotalCardValue()) {
            this.customPrice = price;
            return true;
        }

        return false;
    }

    /**
     * Calculates the final sale price of the binder.
     *
     * <p>If a custom price has been set (i.e., is greater than 0), it returns
     * that price plus a 10% handling fee. Otherwise, it defaults to the total
     * real value of the cards plus a 10% handling fee.</p>
     *
     * @return The final sale price of the binder.
     */
    public double calculatePrice() {
        double basePrice = (this.customPrice > 0)
                           ? this.customPrice
                           : getTotalCardValue();
        
        return basePrice * 1.10;
    }
}
