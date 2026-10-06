package retailiq.co4;

import retailiq.structures.MyLinkedList;

/**
 * CO4: Network Flow Graph for warehouse-to-store logistics.
 */
public class FlowGraph {
    private int numVertices;
    private MyLinkedList<FlowEdge>[] adj;

    @SuppressWarnings("unchecked")
    public FlowGraph(int numVertices) {
        this.numVertices = numVertices;
        this.adj = new MyLinkedList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adj[i] = new MyLinkedList<>();
        }
    }

    public void addEdge(int from, int to, double capacity) {
        FlowEdge forward = new FlowEdge(from, to, capacity);
        FlowEdge backward = new FlowEdge(to, from, 0);
        forward.residual = backward;
        backward.residual = forward;
        adj[from].add(forward);
        adj[to].add(backward);
    }

    public int getNumVertices() {
        return numVertices;
    }

    public MyLinkedList<FlowEdge> getEdges(int v) {
        return adj[v];
    }
}
