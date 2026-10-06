package retailiq.model;

/**
 * Represents a fulfillment warehouse.
 */
public class Warehouse {
    private int id;
    private String name;
    private int capacity;

    public Warehouse(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return String.format("[WH #%d] %-20s | Capacity: %d units", id, name, capacity);
    }
}
