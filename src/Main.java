import com.tcis.InventorySystem;
import java.util.Scanner;

/*
    Class: Main
    
    Purpose:
    The main entry point for the Trading Card Inventory System. Its sole
    responsibility is to instantiate the primary controller class (`InventorySystem`)
    and start the application's execution loop.
*/
public class Main {
    /*
        Method: main
        
        Purpose:
        The main method that is executed by the Java Virtual Machine to launch the
        application. It creates all necessary top-level components and hands off
        control to the InventorySystem's run method.
        
        Returns:
        void. The application terminates when this method completes.
        
        @param args: Command line arguments, which are not used in this application.
    */
    public static void main(String[] args) {
        // The InventorySystem now manages the Scanner, so we only create it here.
        InventorySystem tcis = new InventorySystem();
        tcis.run();
    }
}
