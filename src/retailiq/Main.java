package retailiq;

import retailiq.ui.MainMenu;

/**
 * RetailIQ: Intelligent Retail Analytics Platform.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Starting RetailIQ Platform...");
        MainMenu menu = new MainMenu();
        menu.run();
    }
}
