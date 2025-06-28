package com.tcis.models.card;

/*
This rarity enum represents the fixed set of rarities a card can have.
*/
public enum Rarity {
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    LEGENDARY("Legendary");

    private final String displayName;

    /*
    Method: Rarity Constructor
    Purpose: Creates an instance of rarity based on specific constant given
    @param displayName: Name of rarity
    */
    Rarity(String displayName) {
        this.displayName = displayName;
    }

    /*
        Method: getDisplayName
        Purpose: A getter for the display name of the instance's rarity when called
        Returns: Display name of instance's rarity
    */
    public String getDisplayName() {
        return this.displayName;
    }

    /*
        Method: fromInt

        Purpose: A method to get a CardRarity from a 1-based integer index.

        Returns: Corresponding CardRarity.
    */
    public static Rarity fromInt(int choice) {
        if (choice > 0 && choice <= Rarity.values().length) {
            return Rarity.values()[choice - 1];
        }

        return null;
    }
}
