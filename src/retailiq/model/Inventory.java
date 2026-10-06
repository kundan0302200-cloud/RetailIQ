package retailiq.model;

/**
 * Represents stock levels across warehouses.
 */
public class Inventory {
    private int warehouseId;
    private int productId;
    private int quantity;

    public Inventory(int warehouseId, int productId, int quantity) {
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
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

    @Override
    public String toString() {
        return String.format("Warehouse #%d -> Product #%d: %d units", warehouseId, productId, quantity);
    }
}
