package retailiq.co4;

/**
 * CO4: Edge in a network flow graph.
 */
public class FlowEdge {
    public int from;
    public int to;
    public double capacity;
    public double flow;
    public FlowEdge residual;

    public FlowEdge(int from, int to, double capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    public double remainingCapacity() {
        return capacity - flow;
    }

    public void augment(double bottleNeck) {
        flow += bottleNeck;
        residual.flow -= bottleNeck;
    }
}
