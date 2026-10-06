package retailiq.co4;

import retailiq.structures.MyLinkedList;
import retailiq.structures.MyQueue;

/**
 * CO4: Minimum Cut Identification from Maximum Flow.
 * Demonstrates Max-Flow Min-Cut theorem to locate logistics bottlenecks.
 */
public class MinCut {

    public static boolean[] findReachableSet(FlowGraph graph, int s) {
        int n = graph.getNumVertices();
        boolean[] visited = new boolean[n];
        MyQueue<Integer> q = new MyQueue<>();

        visited[s] = true;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int u = q.dequeue();
            MyLinkedList.Node<FlowEdge> curr = graph.getEdges(u).getHead();
            while (curr != null) {
                FlowEdge e = curr.data;
                if (!visited[e.to] && e.remainingCapacity() > 0) {
                    visited[e.to] = true;
                    q.enqueue(e.to);
                }
                curr = curr.next;
            }
        }

        return visited;
    }
}
