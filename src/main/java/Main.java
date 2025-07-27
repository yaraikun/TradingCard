import javax.swing.SwingUtilities;

import com.tcis.InventorySystem;

/**
 * The main entry point for the Trading Card Inventory System application.
 *
 * <p>Its sole responsibility is to instantiate the primary controller class
 * ({@code InventorySystem}) and start the application's execution loop.
 * It ensures the application starts on the correct thread for GUI operations.
 * </p>
 */
public class Main {
    /**
     * The main method that is executed by the Java Virtual Machine to launch
     * the application.
     *
     * <p>This method schedules the creation and execution of the application on
     * the AWT Event Dispatch Thread (EDT) to ensure thread-safety for all
     * Swing components, which is a required practice.</p>
     *
     * @param args Command line arguments, which are not used in this
     *             application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * The task executed by the EDT, which constructs the main system
             * controller and starts the application.
             */
            public void run() {
                InventorySystem tcis = new InventorySystem();
                tcis.run();
            }
        });
    }
}
