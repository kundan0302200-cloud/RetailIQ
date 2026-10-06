package retailiq.co4;

import retailiq.structures.MyLinkedList;
import retailiq.structures.MyQueue;

/**
 * CO4: Ford-Fulkerson Method for Maximum Network Flow.
 */
public class FordFulkerson {

    public static double maxFlow(FlowGraph graph, int s, int t) {
        double maxFlow = 0;
        int n = graph.getNumVertices();

        while (true) {
            FlowEdge[] edgeTo = new FlowEdge[n];
            boolean[] visited = new boolean[n];

            // DFS to find augmenting path
            if (!dfs(graph, s, t, visited, edgeTo)) {
                break;
            }

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

    private static boolean dfs(FlowGraph graph, int u, int t, boolean[] visited, FlowEdge[] edgeTo) {
        visited[u] = true;
        if (u == t) return true;

        MyLinkedList.Node<FlowEdge> curr = graph.getEdges(u).getHead();
        while (curr != null) {
            FlowEdge edge = curr.data;
            if (!visited[edge.to] && edge.remainingCapacity() > 0) {
                edgeTo[edge.to] = edge;
                if (dfs(graph, edge.to, t, visited, edgeTo)) {
                    return true;
                }
            }
            curr = curr.next;
        }
        return false;
    }
}
