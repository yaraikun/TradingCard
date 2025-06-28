import com.tcis.InventorySystem;

/**
 * The main entry point for the Trading Card Inventory System.
 * Its sole responsibility is to create and run the main InventorySystem.
 */
public class Main {
    /**
     * The main method that launches the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        InventorySystem tcis = new InventorySystem();
        tcis.run();
    }
}
