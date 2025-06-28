package com.tcis.models.card;

/*
    Enum: Rarity
    
    Purpose: Represents the fixed set of rarities a card can have. Using an
    enum provides type safety, a clear list of valid options, and a single
    source for all rarity-related data in the system.
*/
public enum Rarity {
    /*
        Enum Constants: COMMON, UNCOMMON, RARE, LEGENDARY
        
        Purpose: These define the specific, immutable instances of Rarity.
        Each constant is associated with a user-friendly display name that is 
        used throughout the UI.
    */
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    LEGENDARY("Legendary");

    /*
        Attribute: displayName
        
        Purpose: Stores the user-friendly string representation of the
        rarity (e.g., "Common"). This field is final to ensure it is immutable
        after the enum constant is initialized.
    */
    private final String displayName;

    /*
        Constructor: Rarity
        
        Purpose: A private constructor to initialize each enum constant with
        its corresponding display name. This is called implicitly by the Java 
        runtime for each constant defined above.
        
        @param displayName: The user-friendly name to be associated with the=
                            enum constant.
    */
    Rarity(String displayName) {
        this.displayName = displayName;
    }

    /*
        Method: getDisplayName
        
        Purpose: A public getter method to retrieve the user-friendly display 
        name of an enum instance.
        
        Returns: A String containing the display name of the rarity
        (e.g., "Common").
    */
    public String getDisplayName() {
        return this.displayName;
    }

    /*
        Method: fromInt
        
        Purpose: A static factory method that converts a 1-based integer
        (typically from user input) into the corresponding Rarity enum
        constant. This decouples the UI's numbered options from the enum's
        internal, 0-based ordinal values.
        
        Returns: The matching Rarity constant if the choice is valid (within
        the range of defined rarities), or null if the choice is out of bounds.
        
        @param choice: The 1-based integer representing the user's selection.
    */
    public static Rarity fromInt(int choice) {
        if (choice > 0 && choice <= Rarity.values().length) {
            // Convert 1-based choice to 0-based array index
            return Rarity.values()[choice - 1];
        }
        return null;
    }
}
