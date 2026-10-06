package retailiq.data;

import retailiq.model.*;
import retailiq.structures.MyLinkedList;

import java.io.*;

/**
 * Data management and persistence layer for RetailIQ.
 * Maintains in-memory collections of retail entities using custom MyLinkedList
 * with optional text file loading/saving.
 */
public class RetailData {

    private MyLinkedList<Product> products;
    private MyLinkedList<Customer> customers;
    private MyLinkedList<Order> orders;
    private MyLinkedList<Review> reviews;
    private MyLinkedList<Warehouse> warehouses;
    private MyLinkedList<Store> stores;
    private MyLinkedList<Inventory> inventory;
    private MyLinkedList<SalesEvent> salesEvents;

    private static RetailData instance;

    private RetailData() {
        products = new MyLinkedList<>();
        customers = new MyLinkedList<>();
        orders = new MyLinkedList<>();
        reviews = new MyLinkedList<>();
        warehouses = new MyLinkedList<>();
        stores = new MyLinkedList<>();
        inventory = new MyLinkedList<>();
        salesEvents = new MyLinkedList<>();

        seedDefaultData();
        loadFilesIfPresent();
    }

    public static synchronized RetailData getInstance() {
        if (instance == null) {
            instance = new RetailData();
        }
        return instance;
    }

    private void seedDefaultData() {
        // Seed Warehouses
        warehouses.add(new Warehouse(1, "Central Hub Mumbai", 5000));
        warehouses.add(new Warehouse(2, "North DC Delhi", 4000));
        warehouses.add(new Warehouse(3, "South DC Bengaluru", 4500));

        // Seed Stores
        stores.add(new Store(1, "RetailIQ Flagship Store", "Mumbai - Bandra"));
        stores.add(new Store(2, "RetailIQ Tech Hub", "Bengaluru - Indiranagar"));
        stores.add(new Store(3, "RetailIQ Express", "Delhi - Connaught Place"));

        // Seed Products
        addProduct(new Product(101, "Apple iPhone 15", "Smartphone", "A premium flagship smartphone with A16 bionic chip and high quality camera", 69999.00, 25));
        addProduct(new Product(102, "Samsung Galaxy S24", "Smartphone", "Next-generation AI phone with dynamic AMOLED display and excellent battery", 64999.00, 18));
        addProduct(new Product(103, "OnePlus 12", "Smartphone", "Fast charging flagship smartphone with Snapdragon processor and vibrant screen", 54999.00, 30));
        addProduct(new Product(104, "Sony WH-1000XM5", "Headphones", "Industry-leading noise cancelling wireless headphones with premium sound quality", 29999.00, 12));
        addProduct(new Product(105, "Apple AirPods Pro", "Earbuds", "Wireless active noise cancelling earbuds with spatial audio and wireless case", 24999.00, 20));
        addProduct(new Product(106, "Dell Inspiron 15", "Laptop", "Reliable 15-inch laptop powered by Intel Core i7 with 16GB RAM and fast SSD", 59999.00, 15));
        addProduct(new Product(107, "HP Pavilion", "Laptop", "Sleek entertainment laptop with high resolution screen and long battery life", 57999.00, 10));
        addProduct(new Product(108, "Logitech MX Master 3S", "Mouse", "Ergonomic performance wireless mouse with ultra-fast scrolling and quiet clicks", 8999.00, 35));
        addProduct(new Product(109, "Samsung 27 Inch Monitor", "Monitor", "Ultra HD IPS gaming and productivity monitor with HDR and HDMI ports", 18999.00, 22));
        addProduct(new Product(110, "Anker Power Bank", "Accessories", "Ultra-compact high capacity portable charger with fast delivery power bank", 3999.00, 40));

        // Seed Customers
        customers.add(new Customer(1, "Aarav Sharma", "aarav.sharma@example.com"));
        customers.add(new Customer(2, "Priya Patel", "priya.patel@example.com"));
        customers.add(new Customer(3, "Rohan Verma", "rohan.verma@example.com"));
        customers.add(new Customer(4, "Ananya Iyer", "ananya.iyer@example.com"));
        customers.add(new Customer(5, "Vikram Rao", "vikram.rao@example.com"));

        // Seed Orders
        orders.add(new Order(1001, 1, 101, 1, 69999.00));
        orders.add(new Order(1002, 2, 104, 1, 29999.00));
        orders.add(new Order(1003, 3, 105, 2, 49998.00));
        orders.add(new Order(1004, 1, 108, 1, 8999.00));
        orders.add(new Order(1005, 4, 102, 1, 64999.00));

        // Seed Reviews
        reviews.add(new Review(101, "Excellent battery and very fast delivery.", 5));
        reviews.add(new Review(102, "Good phone but delivery was slow.", 3));
        reviews.add(new Review(104, "Excellent sound quality and comfortable headphones.", 5));
        reviews.add(new Review(101, "Battery life is good but the price is high.", 4));
        reviews.add(new Review(105, "Top notch sound quality with fast refund when needed.", 4));
        reviews.add(new Review(106, "Battery drains fast and broken charging cable was replaced.", 2));
        reviews.add(new Review(108, "Super smooth wireless mouse with great discount and quality.", 5));

        // Seed Inventory
        inventory.add(new Inventory(1, 101, 15));
        inventory.add(new Inventory(1, 102, 10));
        inventory.add(new Inventory(1, 104, 8));
        inventory.add(new Inventory(2, 101, 10));
        inventory.add(new Inventory(2, 105, 15));
        inventory.add(new Inventory(2, 106, 10));
        inventory.add(new Inventory(3, 108, 20));
        inventory.add(new Inventory(3, 109, 12));
        inventory.add(new Inventory(3, 110, 25));

        // Seed SalesEvents
        salesEvents.add(new SalesEvent(1, 101, 1, 69999.00, 1700000001L));
        salesEvents.add(new SalesEvent(2, 104, 2, 29999.00, 1700000002L));
        salesEvents.add(new SalesEvent(3, 105, 3, 49998.00, 1700000003L));
        salesEvents.add(new SalesEvent(4, 108, 1, 8999.00, 1700000004L));
        salesEvents.add(new SalesEvent(5, 102, 4, 64999.00, 1700000005L));
    }

    private void loadFilesIfPresent() {
        File prodFile = new File("data/products.txt");
        if (prodFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(prodFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        int id = Integer.parseInt(parts[0].trim());
                        if (getProductById(id) == null) {
                            String name = parts[1].trim();
                            String cat = parts[2].trim();
                            String desc = parts[3].trim();
                            double price = Double.parseDouble(parts[4].trim());
                            int stock = Integer.parseInt(parts[5].trim());
                            addProduct(new Product(id, name, cat, desc, price, stock));
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public Product getProductById(int id) {
        MyLinkedList.Node<Product> curr = products.getHead();
        while (curr != null) {
            if (curr.data.getId() == id) {
                return curr.data;
            }
            curr = curr.next;
        }
        return null;
    }

    public Product[] getAllProducts() {
        Product[] arr = new Product[products.size()];
        return products.toArray(arr);
    }

    public Customer[] getAllCustomers() {
        Customer[] arr = new Customer[customers.size()];
        return customers.toArray(arr);
    }

    public Order[] getAllOrders() {
        Order[] arr = new Order[orders.size()];
        return orders.toArray(arr);
    }

    public Review[] getAllReviews() {
        Review[] arr = new Review[reviews.size()];
        return reviews.toArray(arr);
    }

    public Warehouse[] getAllWarehouses() {
        Warehouse[] arr = new Warehouse[warehouses.size()];
        return warehouses.toArray(arr);
    }

    public Store[] getAllStores() {
        Store[] arr = new Store[stores.size()];
        return stores.toArray(arr);
    }

    public Inventory[] getAllInventory() {
        Inventory[] arr = new Inventory[inventory.size()];
        return inventory.toArray(arr);
    }

    public SalesEvent[] getAllSalesEvents() {
        SalesEvent[] arr = new SalesEvent[salesEvents.size()];
        return salesEvents.toArray(arr);
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public Customer getCustomerById(int id) {
        MyLinkedList.Node<Customer> curr = customers.getHead();
        while (curr != null) {
            if (curr.data.getId() == id) return curr.data;
            curr = curr.next;
        }
        return null;
    }

    public void addOrder(Order o) {
        orders.add(o);
    }

    public void addReview(Review r) {
        reviews.add(r);
    }
}
