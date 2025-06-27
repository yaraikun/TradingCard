package com.tcis.ui;
import java.util.Scanner;
public final class InputHelper {
    private InputHelper() {}
    public static String getStringInput(Scanner scanner, String prompt) { System.out.print(prompt); return scanner.nextLine(); }
    public static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (true) { try { return Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.print("Invalid format. Please enter a whole number: "); } }
    }
    public static double getDoubleInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (true) { try { return Double.parseDouble(scanner.nextLine()); } catch (NumberFormatException e) { System.out.print("Invalid format. Please enter a number: "); } }
    }
}
