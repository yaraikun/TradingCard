package com.tcis.ui;

import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;
import java.util.Scanner;

/*
    Class: Inputter
    
    Purpose:
    A utility class that combines getting raw input and validating it in a loop.
    This class provides a streamlined and robust experience for gathering user data
    by continuously prompting until the input meets the rules defined in the Validator.
*/
public final class Inputter {
    /*
        Constructor: Inputter
        
        Purpose:
        A private constructor to prevent instantiation of this utility class, as all
        its methods are static.
    */
    private Inputter() {}

    /*
        Method: getIntInput
        
        Purpose:
        Prompts the user for and returns an integer. It handles non-numeric input by
        re-prompting the user until a valid integer is entered.
        
        Returns:
        A validated integer provided by the user.
        
        @param scanner: The Scanner instance to use for reading user input.
        @param prompt: The message to display to the user before they enter input.
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
    
    /*
        Method: getStringInput
        
        Purpose:
        Gets a raw string input from the user without any validation.
        
        Returns:
        The raw string entered by the user.
        
        @param scanner: The Scanner instance to use for reading user input.
        @param prompt: The message to display to the user.
    */
    public static String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /*
        Method: getValidName
        
        Purpose:
        Prompts the user for and returns a non-blank string, suitable for names of
        cards, binders, or decks.
        
        Returns:
        A valid, non-blank, trimmed string.
        
        @param scanner: The Scanner instance to use.
        @param prompt: The message to display to the user.
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

    /*
        Method: getValidRarity
        
        Purpose:
        Prompts the user to select a Rarity from a numbered list and returns the
        corresponding enum constant.
        
        Returns:
        A valid Rarity constant chosen by the user.
        
        @param scanner: The Scanner instance to use.
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
                System.out.println("Invalid selection. Please try again.");
            }
        } while (rarity == null);
        return rarity;
    }

    /*
        Method: getValidVariant
        
        Purpose:
        Prompts the user to select a Variant from a numbered list, but only if the
        card's rarity allows for variants (Rare or Legendary). Otherwise, it defaults
        to NORMAL.
        
        Returns:
        A valid Variant constant.
        
        @param scanner: The Scanner instance to use.
        @param rarity: The rarity of the card, which determines if a variant is needed.
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
                System.out.println("Invalid selection. Please try again.");
            }
        } while (variant == null);
        return variant;
    }
    
    /*
        Method: getValidPositiveDouble
        
        Purpose:
        Prompts the user for and returns a positive double value, handling both
        format errors and negative number errors.
        
        Returns:
        A valid, non-negative double.
        
        @param scanner: The Scanner instance to use.
        @param prompt: The message to display to the user.
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

    /*
        Method: getValidPositiveInt
        
        Purpose:
        Prompts the user for and returns a strictly positive integer (> 0).
        
        Returns:
        A valid, strictly positive integer.
        
        @param scanner: The Scanner instance to use.
        @param prompt: The message to display to the user.
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
