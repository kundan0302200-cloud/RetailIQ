package retailiq;

import retailiq.ui.MainMenu;

/**
 * RetailIQ: Intelligent Retail Analytics Platform.
 * Entry point for the Eclipse standalone console application.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Starting RetailIQ Platform...");
        MainMenu menu = new MainMenu();
        menu.run();
    }
}
