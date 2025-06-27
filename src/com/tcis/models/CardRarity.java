package com.tcis.models;

public final class CardRarity {
    public static final CardRarity COMMON = new CardRarity("Common");
    public static final CardRarity UNCOMMON = new CardRarity("Uncommon");
    public static final CardRarity RARE = new CardRarity("Rare");
    public static final CardRarity LEGENDARY = new CardRarity("Legendary");

    private final String displayName;

    private CardRarity(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CardRarity fromString(String text) {
        if (text == null) return COMMON;
        switch (text.trim().toLowerCase()) {
            case "uncommon": return UNCOMMON;
            case "rare": return RARE;
            case "legendary": return LEGENDARY;
            case "common":
            default: return COMMON;
        }
    }
}
