package retailiq.co4;

import retailiq.structures.MyLinkedList;
import retailiq.structures.MyQueue;

/**
 * CO4: Dinic's Algorithm for Maximum Flow.
 * Combines BFS level graph and DFS blocking flow.
 * Complexity: O(V^2 * E)
 */
public class Dinic {

    public static double maxFlow(FlowGraph graph, int s, int t) {
        int n = graph.getNumVertices();
        int[] level = new int[n];
        double totalFlow = 0;

        while (bfs(graph, s, t, level)) {
            MyLinkedList.Node<FlowEdge>[] ptr = new MyLinkedList.Node[n];
            for (int i = 0; i < n; i++) {
                ptr[i] = graph.getEdges(i).getHead();
            }

            while (true) {
                double pushed = dfs(graph, s, t, Double.MAX_VALUE, level, ptr);
                if (pushed <= 0) break;
                totalFlow += pushed;
            }
        }

        return totalFlow;
    }

    private static boolean bfs(FlowGraph graph, int s, int t, int[] level) {
        for (int i = 0; i < level.length; i++) level[i] = -1;
        level[s] = 0;

        MyQueue<Integer> q = new MyQueue<>();
        q.enqueue(s);

        while (!q.isEmpty()) {
            int u = q.dequeue();
            MyLinkedList.Node<FlowEdge> curr = graph.getEdges(u).getHead();
            while (curr != null) {
                FlowEdge e = curr.data;
                if (e.remainingCapacity() > 0 && level[e.to] < 0) {
                    level[e.to] = level[u] + 1;
                    q.enqueue(e.to);
                }
                curr = curr.next;
            }
        }
        return level[t] >= 0;
    }

    private static double dfs(FlowGraph graph, int u, int t, double pushed, int[] level, MyLinkedList.Node<FlowEdge>[] ptr) {
        if (pushed <= 0) return 0;
        if (u == t) return pushed;

        for (MyLinkedList.Node<FlowEdge> curr = ptr[u]; curr != null; curr = curr.next) {
            ptr[u] = curr;
            FlowEdge e = curr.data;
            if (level[u] + 1 != level[e.to] || e.remainingCapacity() <= 0) continue;

            double tr = dfs(graph, e.to, t, Math.min(pushed, e.remainingCapacity()), level, ptr);
            if (tr <= 0) continue;

            e.augment(tr);
            return tr;
        }
        return 0;
    }
}
