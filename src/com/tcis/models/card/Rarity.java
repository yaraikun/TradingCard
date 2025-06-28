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
        Purpose: Returns the display name of the instance's rarity when called
    */
    public String getDisplayName() {
        return displayName;
    }

}
