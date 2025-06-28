package com.tcis.ui;

/*
    Class: Validator
    Purpose: A utility class with static methods for validating business rules on raw input.
*/
public final class Validator {
    /*
        Constructor: Validator

        Purpose: To prevent accidental instantiation.
     */
    private Validator() {}

    /*
        Method: isValidName

        Purpose: checks for valid name.

        Returns: true if valid, otherwise false
    */
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /*
        Method: isPositive

        Purpose: checks for valid double.

        Returns: true if valid, otherwise false

        @param value: value to check if valid
    */
    public static boolean isPositive(double value) {
        return value >= 0;
    }

    /*
        Method: isPositive

        Purpose: checks for valid int.

        Returns: true if valid, otherwise false

        @param value: value to check if valid
    */
    public static boolean isPositive(int value) {
        return value > 0;
    }
}
