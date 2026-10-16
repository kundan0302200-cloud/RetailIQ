package retailiq.ui;

import retailiq.data.RetailData;
import retailiq.model.Product;

import java.util.Scanner;

/**
 * Console UI for Product Management.
 */
public class ProductMenu {

    public static void display(Scanner scanner) {
        RetailData data = RetailData.getInstance();
        boolean back = false;

        while (!back) {
            int choice = ArrowKeyMenu.select("RETAILIQ  /  Products", "Home / Products",
                    new String[] { "Browse products", "Add product", "Search products", "Update stock",
                            "Deactivate product", "Sort by price", "Product details", "Back" },
                    new String[] { "View the catalog", "Add an item to the catalog", "Find by name or category",
                            "Change available quantity", "Set a product's stock to zero", "Sort by ascending price",
                            "View one product's information", "Return to the main menu" },
                    "↑/↓ Move   Enter Select   Esc Back");
            if (choice == -1) break;
            if (choice == -2) {
                if (!scanner.hasNextLine()) break;
                try { choice = Integer.parseInt(scanner.nextLine().trim()) - 1; }
                catch (NumberFormatException e) { continue; }
            }
            switch (choice) {
                case 1:
                    handleAddProduct(scanner, data);
                    break;
                case 0:
                    handleViewProducts(data);
                    break;
                case 2:
                    handleSearchProduct(scanner, data);
                    break;
                case 3:
                    handleUpdateStock(scanner, data);
                    break;
                case 4:
                    handleDeleteProduct(scanner, data);
                    break;
                case 5:
                    handleSortProducts(data);
                    break;
                case 6:
                    handleProductDetails(scanner, data);
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    break;
            }
        }
    }

    private static void handleAddProduct(Scanner scanner, RetailData data) {
        try {
            System.out.print("Enter Product ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (data.getProductById(id) != null) {
                System.out.println("Error: Product with ID " + id + " already exists!");
                return;
            }
            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Category: ");
            String cat = scanner.nextLine().trim();
            System.out.print("Enter Description: ");
            String desc = scanner.nextLine().trim();
            System.out.print("Enter Price (INR): ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            if (price < 0) {
                System.out.println("Error: Price cannot be negative!");
                return;
            }
            System.out.print("Enter Stock Quantity: ");
            int stock = Integer.parseInt(scanner.nextLine().trim());
            if (stock < 0) {
                System.out.println("Error: Stock cannot be negative!");
                return;
            }

            data.addProduct(new Product(id, name, cat, desc, price, stock));
            System.out.println("Success: Product added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Input Error: Please enter valid numeric values for ID, price, and stock.");
        }
    }

    public static void handleViewProducts(RetailData data) {
        Product[] products = data.getAllProducts();
        System.out.println("\n--- PRODUCT CATALOG (" + products.length + " items) ---");
        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i]);
        }
    }

    private static void handleSearchProduct(Scanner scanner, RetailData data) {
        System.out.print("Enter search keyword (name/category): ");
        String query = scanner.nextLine().trim().toLowerCase();
        Product[] products = data.getAllProducts();
        int found = 0;
        System.out.println("\nMatching Products:");
        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            if (p.getName().toLowerCase().contains(query) || p.getCategory().toLowerCase().contains(query)) {
                System.out.println(p);
                found++;
            }
        }
        if (found == 0) {
            System.out.println("No matching products found.");
        }
    }

    private static void handleUpdateStock(Scanner scanner, RetailData data) {
        try {
            System.out.print("Enter Product ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Product p = data.getProductById(id);
            if (p == null) {
                System.out.println("Product not found with ID " + id);
                return;
            }
            System.out.print("Enter New Stock Quantity: ");
            int newStock = Integer.parseInt(scanner.nextLine().trim());
            if (newStock < 0) {
                System.out.println("Error: Stock cannot be negative!");
                return;
            }
            p.setStock(newStock);
            System.out.println("Stock updated successfully to " + newStock + " for " + p.getName());
        } catch (NumberFormatException e) {
            System.out.println("Input Error: Invalid number entered.");
        }
    }

    private static void handleDeleteProduct(Scanner scanner, RetailData data) {
        try {
            System.out.print("Enter Product ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Product p = data.getProductById(id);
            if (p == null) {
                System.out.println("Product not found with ID " + id);
                return;
            }
            // Mark inactive or zero stock
            p.setStock(0);
            System.out.println("Product " + p.getName() + " stock marked as 0 (deactivated).");
        } catch (NumberFormatException e) {
            System.out.println("Input Error: Invalid number entered.");
        }
    }

    private static void handleSortProducts(RetailData data) {
        Product[] products = data.getAllProducts();
        // Custom insertion sort by price
        for (int i = 1; i < products.length; i++) {
            Product key = products[i];
            int j = i - 1;
            while (j >= 0 && products[j].getPrice() > key.getPrice()) {
                products[j + 1] = products[j];
                j--;
            }
            products[j + 1] = key;
        }
        System.out.println("\n--- PRODUCTS SORTED BY PRICE (ASCENDING) ---");
        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i]);
        }
    }

    private static void handleProductDetails(Scanner scanner, RetailData data) {
        try {
            System.out.print("Enter Product ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Product p = data.getProductById(id);
            if (p == null) {
                System.out.println("Product not found with ID " + id);
                return;
            }
            System.out.println("\n========================================");
            System.out.println("PRODUCT DETAILS: " + p.getName());
            System.out.println("========================================");
            System.out.println("ID:          " + p.getId());
            System.out.println("Name:        " + p.getName());
            System.out.println("Category:    " + p.getCategory());
            System.out.println("Price:       ₹" + p.getPrice());
            System.out.println("Stock:       " + p.getStock() + " units");
            System.out.println("Description: " + p.getDescription());
            System.out.println("========================================");
        } catch (NumberFormatException e) {
            System.out.println("Input Error: Invalid number entered.");
        }
    }
}
