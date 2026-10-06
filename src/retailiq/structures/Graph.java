package retailiq.structures;

/**
 * Custom Adjacency List Graph representation implemented without java.util.*
 * Supports vertices, directed and undirected edges, weights, and BFS/DFS.
 */
public class Graph {

    public static class Edge {
        public int to;
        public double weight;
        public Edge next;

        public Edge(int to, double weight, Edge next) {
            this.to = to;
            this.weight = weight;
            this.next = next;
        }
    }

    private int numVertices;
    private Edge[] adj;
    private String[] labels;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        this.adj = new Edge[numVertices];
        this.labels = new String[numVertices];
        for (int i = 0; i < numVertices; i++) {
            this.labels[i] = "V" + i;
        }
    }

    public void setLabel(int v, String label) {
        if (v >= 0 && v < numVertices) {
            this.labels[v] = label;
        }
    }

    public String getLabel(int v) {
        if (v >= 0 && v < numVertices) {
            return labels[v];
        }
        return null;
    }

    public void addDirectedEdge(int from, int to, double weight) {
        if (from >= 0 && from < numVertices && to >= 0 && to < numVertices) {
            adj[from] = new Edge(to, weight, adj[from]);
        }
    }

    public void addUndirectedEdge(int u, int v, double weight) {
        addDirectedEdge(u, v, weight);
        addDirectedEdge(v, u, weight);
    }

    public int getNumVertices() {
        return numVertices;
    }

    public Edge getEdges(int v) {
        if (v >= 0 && v < numVertices) {
            return adj[v];
        }
        return null;
    }

    public int getOutDegree(int v) {
        int count = 0;
        Edge curr = adj[v];
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    public int[] bfs(int startVertex) {
        int[] visitOrder = new int[numVertices];
        int orderCount = 0;
        boolean[] visited = new boolean[numVertices];

        MyQueue<Integer> queue = new MyQueue<>();
        visited[startVertex] = true;
        queue.enqueue(startVertex);

        while (!queue.isEmpty()) {
            int u = queue.dequeue();
            visitOrder[orderCount++] = u;

            Edge curr = adj[u];
            while (curr != null) {
                if (!visited[curr.to]) {
                    visited[curr.to] = true;
                    queue.enqueue(curr.to);
                }
                curr = curr.next;
            }
        }

        int[] result = new int[orderCount];
        for (int i = 0; i < orderCount; i++) {
            result[i] = visitOrder[i];
        }
        return result;
    }
}
