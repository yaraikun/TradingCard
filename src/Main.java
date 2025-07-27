import com.tcis.InventorySystem;
import javax.swing.SwingUtilities;

/**
 * The main entry point for the Trading Card Inventory System application.
 * Its sole responsibility is to create the main controller class
 * (`InventorySystem`) and start its execution loop on the AWT Event Dispatch
 * Thread (EDT).
 */
public class Main {
    /**
     * The main method that is executed by the Java Virtual Machine to launch
     * the application.
     * @param args Command line arguments, which are not used in this
     *             application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InventorySystem tcis = new InventorySystem();
                tcis.run();
            }
        });
    }
}
