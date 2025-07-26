import com.tcis.InventorySystem;
import javax.swing.SwingUtilities;

/**
 * The main entry point for the Trading Card Inventory System application.
 * Its sole responsibility is to create the main controller class (`InventorySystem`)
 * and start its execution loop on the AWT Event Dispatch Thread (EDT), which is
 * the standard and required practice for all Swing applications to ensure thread safety.
 */
public class Main {
    /**
     * The main method that is executed by the Java Virtual Machine to launch the application.
     * @param args Command line arguments, which are not used in this application.
     */
    public static void main(String[] args) {
        // Swing GUI operations should be performed on the Event Dispatch Thread (EDT)
        // to prevent concurrency issues. SwingUtilities.invokeLater places the
        // GUI creation and startup code on the EDT's event queue.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // The InventorySystem now manages the entire application lifecycle,
                // including the creation of the main GUI frame.
                InventorySystem tcis = new InventorySystem();
                tcis.run();
            }
        });
    }
}
