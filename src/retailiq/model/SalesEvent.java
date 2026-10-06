package retailiq.model;

/**
 * Represents a streaming or recorded sales event.
 */
public class SalesEvent {
    private int id;
    private int productId;
    private int customerId;
    private double amount;
    private long timestamp;

    public SalesEvent(int id, int productId, int customerId, double amount, long timestamp) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("SalesEvent #%-4d | Product: #%-4d | Customer: #%-4d | ₹%-8.2f | Time: %d",
                id, productId, customerId, amount, timestamp);
    }
}
