package com.tcis.ui;

public final class InputValidator {

    private InputValidator() {}

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isPositive(double value) {
        return value >= 0;
    }
    
    public static boolean isPositive(int value) {
        return value > 0;
    }

    public static boolean isValidRarity(String text) {
        if (text == null) return false;
        String lowerText = text.trim().toLowerCase();
        return lowerText.equals("common") || lowerText.equals("uncommon") ||
               lowerText.equals("rare") || lowerText.equals("legendary");
    }

    public static boolean isValidVariant(String text) {
        if (text == null) return false;
        String lowerText = text.trim().toLowerCase();
        return lowerText.equals("normal") || lowerText.equals("extended-art") ||
               lowerText.equals("full-art") || lowerText.equals("alt-art");
    }
}
