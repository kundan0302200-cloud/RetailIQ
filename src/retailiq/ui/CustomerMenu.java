package retailiq.ui;

import retailiq.data.RetailData;
import retailiq.model.Customer;
import retailiq.model.Order;

import java.util.Scanner;

/**
 * Console UI for Customer Management.
 */
public class CustomerMenu {

    public static void display(Scanner scanner) {
        RetailData data = RetailData.getInstance();
        boolean back = false;

        while (!back) {
            System.out.println("\n--------- CUSTOMER MANAGEMENT ---------");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Search Customer");
            System.out.println("4. View Purchase History");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleAddCustomer(scanner, data);
                    break;
                case "2":
                    handleViewCustomers(data);
                    break;
                case "3":
                    handleSearchCustomer(scanner, data);
                    break;
                case "4":
                    handlePurchaseHistory(scanner, data);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number between 0 and 4.");
            }
        }
    }

    private static void handleAddCustomer(Scanner scanner, RetailData data) {
        try {
            System.out.print("Enter Customer ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (data.getCustomerById(id) != null) {
                System.out.println("Customer with ID " + id + " already exists!");
                return;
            }
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            data.addCustomer(new Customer(id, name, email));
            System.out.println("Customer added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void handleViewCustomers(RetailData data) {
        Customer[] customers = data.getAllCustomers();
        System.out.println("\n--- CUSTOMER LIST (" + customers.length + ") ---");
        for (int i = 0; i < customers.length; i++) {
            System.out.println(customers[i]);
        }
    }

    private static void handleSearchCustomer(Scanner scanner, RetailData data) {
        System.out.print("Enter customer name or email: ");
        String query = scanner.nextLine().trim().toLowerCase();
        Customer[] customers = data.getAllCustomers();
        int count = 0;
        System.out.println("\nMatching Customers:");
        for (int i = 0; i < customers.length; i++) {
            Customer c = customers[i];
            if (c.getName().toLowerCase().contains(query) || c.getEmail().toLowerCase().contains(query)) {
                System.out.println(c);
                count++;
            }
        }
        if (count == 0) System.out.println("No matching customers found.");
    }

    private static void handlePurchaseHistory(Scanner scanner, RetailData data) {
        try {
            System.out.print("Enter Customer ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Customer c = data.getCustomerById(id);
            if (c == null) {
                System.out.println("Customer not found.");
                return;
            }
            System.out.println("\nOrders for " + c.getName() + " (" + c.getEmail() + "):");
            Order[] orders = data.getAllOrders();
            int orderCount = 0;
            double totalSpent = 0;
            for (int i = 0; i < orders.length; i++) {
                if (orders[i].getCustomerId() == id) {
                    System.out.println(orders[i]);
                    totalSpent += orders[i].getTotal();
                    orderCount++;
                }
            }
            if (orderCount == 0) {
                System.out.println("No past orders found for this customer.");
            } else {
                System.out.printf("Total Orders: %d | Lifetime Spend: ₹%.2f\n", orderCount, totalSpent);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    }
}
