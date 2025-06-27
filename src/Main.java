import com.tcis.backend.InventorySystem;
import com.tcis.ui.DisplaySystem;
import com.tcis.ui.MainMenu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventorySystem inventorySystem = new InventorySystem();
        DisplaySystem displaySystem = new DisplaySystem();
        MainMenu mainMenu = new MainMenu(inventorySystem, displaySystem, scanner);

        System.out.println("Welcome to the Trading Card Inventory System!");
        mainMenu.run();

        System.out.println("Thank you for using the TCIS. Goodbye!");
        scanner.close();
    }
}
