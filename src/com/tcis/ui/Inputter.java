package com.tcis.ui;

import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;
import java.util.Scanner;

/**
 * A utility class that combines getting raw input and validating it in a loop.
 * This class provides a streamlined and robust experience for gathering user data
 * by continuously prompting until the input meets the rules defined in the Validator.
 */
public final class Inputter {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Inputter() {}

    /**
     * Prompts the user for and returns an integer, handling non-numeric input.
     * @param scanner The Scanner instance to use.
     * @param prompt The message to display to the user.
     * @return The validated integer.
     */
    public static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid format. Please enter a whole number: ");
            }
        }
    }
    
    /**
     * Gets a raw string input from the user.
     * @param scanner The Scanner instance to use.
     * @param prompt The message to display to the user.
     * @return The raw string entered by the user.
     */
    public static String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Prompts the user for and returns a non-blank string.
     * @param scanner The Scanner instance to use.
     * @param prompt The message to display to the user.
     * @return A valid, non-blank string.
     */
    public static String getValidName(Scanner scanner, String prompt) {
        String input;
        do {
            input = getStringInput(scanner, prompt);
            if (!Validator.isValidName(input)) {
                System.out.println("Input cannot be blank. Please try again.");
            }
        } while (!Validator.isValidName(input));
        return input.trim();
    }

    /**
     * Prompts the user for and returns a valid Rarity enum.
     * @param scanner The Scanner instance to use.
     * @return A valid Rarity constant.
     */
    public static Rarity getValidRarity(Scanner scanner) {
        Rarity rarity;
        do {
            System.out.println("Select a Rarity:");
            for (Rarity r : Rarity.values()) {
                System.out.printf(" (%d) %s\n", r.ordinal() + 1, r.getDisplayName());
            }
            int rarityInt = getIntInput(scanner, "Choose an option: ");
            rarity = Rarity.fromInt(rarityInt);
            if (rarity == null) {
                System.out.println("Invalid rarity. Please choose from the provided options.");
            }
        } while (rarity == null);
        return rarity;
    }

    /**
     * Prompts the user for and returns a valid Variant enum, if applicable for the given rarity.
     * @param scanner The Scanner instance to use.
     * @param rarity The rarity of the card, which determines if a variant is needed.
     * @return A valid Variant constant.
     */
    public static Variant getValidVariant(Scanner scanner, Rarity rarity) {
        if (rarity != Rarity.RARE && rarity != Rarity.LEGENDARY) {
            return Variant.NORMAL;
        }
        Variant variant;
        do {
            System.out.println("Select a Variant:");
            for (Variant v : Variant.values()) {
                System.out.printf(" (%d) %s\n", v.ordinal() + 1, v.getDisplayName());
            }
            int variantInt = getIntInput(scanner, "Choose an option: ");
            variant = Variant.fromInt(variantInt);
            if (variant == null) {
                System.out.println("Invalid variant. Please choose from the provided options.");
            }
        } while (variant == null);
        return variant;
    }
    
    /**
     * Prompts the user for and returns a positive double value.
     * @param scanner The Scanner instance to use.
     * @param prompt The message to display to the user.
     * @return A valid, non-negative double.
     */
    public static double getValidPositiveDouble(Scanner scanner, String prompt) {
        double value;
        do {
            System.out.print(prompt);
            while(!scanner.hasNextDouble()) {
                System.out.print("Invalid format. Please enter a number: ");
                scanner.next();
            }
            value = scanner.nextDouble();
            scanner.nextLine(); // Consume the rest of the line
            if (!Validator.isPositive(value)) {
                System.out.println("Value cannot be negative.");
            }
        } while (!Validator.isPositive(value));
        return value;
    }

    /**
     * Prompts the user for and returns a strictly positive integer.
     * @param scanner The Scanner instance to use.
     * @param prompt The message to display to the user.
     * @return A valid, strictly positive integer.
     */
    public static int getValidPositiveInt(Scanner scanner, String prompt) {
        int value;
        do {
            value = getIntInput(scanner, prompt);
            if (!Validator.isPositive(value)) {
                System.out.println("Amount must be a positive number.");
            }
        } while (!Validator.isPositive(value));
        return value;
    }
}
