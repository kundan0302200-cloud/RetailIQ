package retailiq.co4;

import retailiq.structures.MyLinkedList;
import retailiq.structures.MyQueue;

/**
 * CO4: Edmonds-Karp Algorithm (BFS-based augmenting paths).
 * Complexity: O(V * E^2)
 */
public class EdmondsKarp {

    public static double maxFlow(FlowGraph graph, int s, int t) {
        double maxFlow = 0;
        int n = graph.getNumVertices();

        while (true) {
            FlowEdge[] edgeTo = new FlowEdge[n];
            boolean[] visited = new boolean[n];
            MyQueue<Integer> queue = new MyQueue<>();

            visited[s] = true;
            queue.enqueue(s);

            while (!queue.isEmpty() && !visited[t]) {
                int u = queue.dequeue();
                MyLinkedList.Node<FlowEdge> curr = graph.getEdges(u).getHead();
                while (curr != null) {
                    FlowEdge edge = curr.data;
                    if (!visited[edge.to] && edge.remainingCapacity() > 0) {
                        visited[edge.to] = true;
                        edgeTo[edge.to] = edge;
                        queue.enqueue(edge.to);
                    }
                    curr = curr.next;
                }
            }

            if (!visited[t]) break;

            double bottleNeck = Double.MAX_VALUE;
            for (int v = t; v != s; v = edgeTo[v].from) {
                bottleNeck = Math.min(bottleNeck, edgeTo[v].remainingCapacity());
            }

            for (int v = t; v != s; v = edgeTo[v].from) {
                edgeTo[v].augment(bottleNeck);
            }

            maxFlow += bottleNeck;
        }

        return maxFlow;
    }
}
