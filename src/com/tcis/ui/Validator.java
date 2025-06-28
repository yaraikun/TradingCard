package com.tcis.ui;

/**
 * A utility class with static methods for validating business rules on raw input.
 */
public final class Validator {
    private Validator() {}
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    public static boolean isPositive(double value) {
        return value >= 0;
    }
    public static boolean isPositive(int value) {
        return value > 0;
    }
}
