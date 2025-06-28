package com.tcis.models.card;

/*
    Enum: Variant
    
    Purpose: Represents the fixed set of variants a card can have. Using an
    enum provides type safety, a clear list of valid options, and a single
    source for all rarity-related data in the system.
*/
public enum Variant {
    /*
        Enum Constants: NORMAL, EXTENDED_ART, FULL_ART, ALT_ART
        
        Purpose: These define the specific, immutable instances of Variant.
        Each constant is associated with a user-friendly display name that is 
        used throughout the UI.
    */
    NORMAL("Normal", 1.0),
    EXTENDED_ART("Extended-art", 1.5),
    FULL_ART("Full-art", 2.0),
    ALT_ART("Alt-art", 3.0);

    /*
        Attribute: displayName
        
        Purpose: Stores the user-friendly string representation of the variant
        (e.g., "Full-art"). This field is final to ensure it is immutable
        after the enum constant is initialized.
    */
    private final String displayName;

    /*
        Attribute: multiplier
        
        Purpose: Stores the numerical multiplier associated with the variant.
        This value is used to calculate the final value of a card (e.g., 2.0
        for a 100% increase).
    */
    private final double multiplier;

    /*
        Method: Variant Constructor

        Purpose: Creates an instance of variant based on specific constant given

        @param displayName: Name of variant
        @param multiplier: The specific multiplier associated with specific variant
    */
    Variant(String displayName, double multiplier) {
        this.displayName = displayName;
        this.multiplier = multiplier;
    }

    /*
        Method: getDisplayName

        Purpose: A getter for the display name of the instance's variant when called

        Returns: Display name of instance's rarity
    */
    public String getDisplayName() {
        return this.displayName;
    }

    /*
        Method: getMultiplier

        Purpose: A getter for the multiplier of the instance's variant when called

        Returns: Multiplier of the instance's variant
    */
    public double getMultiplier() {
        return this.multiplier;
    }

    /*
        Method: fromInt

        Purpose: A method to get a CardVariant from a 1-based integer index.

        Returns: Corresponding CardVariant.
    */
    public static Variant fromInt(int choice) {
        if (choice > 0 && choice <= Variant.values().length) {
            return Variant.values()[choice - 1];
        }

        return null;
    }
}
