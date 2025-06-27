package com.tcis.models;

public final class CardVariant {
    public static final CardVariant NORMAL = new CardVariant("Normal", 1.0);
    public static final CardVariant EXTENDED_ART = new CardVariant("Extended-art", 1.5);
    public static final CardVariant FULL_ART = new CardVariant("Full-art", 2.0);
    public static final CardVariant ALT_ART = new CardVariant("Alt-art", 3.0);

    private final String displayName;
    private final double multiplier;

    private CardVariant(String displayName, double multiplier) {
        this.displayName = displayName;
        this.multiplier = multiplier;
    }

    public String getDisplayName() { return displayName; }
    public double getMultiplier() { return multiplier; }

    public static CardVariant fromString(String text) {
        if (text == null) return NORMAL;
        switch (text.trim().toLowerCase()) {
            case "extended-art": return EXTENDED_ART;
            case "full-art": return FULL_ART;
            case "alt-art": return ALT_ART;
            case "normal":
            default: return NORMAL;
        }
    }
}
