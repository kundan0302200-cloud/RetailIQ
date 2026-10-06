package retailiq.model;

/**
 * Represents an individual line item within an order.
 */
public class OrderItem {
    private int productId;
    private int quantity;
    private double unitPrice;

    public OrderItem(int productId, int quantity, double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return String.format("Product #%-4d x %-3d @ ₹%-8.2f = ₹%-8.2f",
                productId, quantity, unitPrice, getSubtotal());
    }
}
