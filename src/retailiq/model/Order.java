package retailiq.model;

/**
 * Represents a customer order in RetailIQ.
 */
public class Order {
    private int id;
    private int customerId;
    private int productId;
    private int quantity;
    private double total;

    public Order(int id, int customerId, int productId, int quantity, double total) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format("Order #%-4d | Customer: #%-4d | Product: #%-4d | Qty: %-3d | Total: ₹%-8.2f",
                id, customerId, productId, quantity, total);
    }
}
