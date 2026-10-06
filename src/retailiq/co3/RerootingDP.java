package retailiq.co3;

/**
 * CO3: Tree Rerooting Dynamic Programming (Optimal Logistics Hub Locator).
 * Computes the sum of travel distances from EVERY node to all other nodes in a tree network
 * in two DFS passes (O(N) total time) to identify the optimal central distribution hub.
 *
 * Algorithm Steps:
 *   Pass 1 (Bottom-Up): Compute subtree node counts sz[u] and initial subtree distance sum in[u].
 *   Pass 2 (Top-Down):  Transition root dynamically from parent u to child v:
 *                       totalDist[v] = totalDist[u] - sz[v] + (N - sz[v])
 *
 * Time Complexity: O(N)
 * Space Complexity: O(N)
 *
 * Course restriction: Implemented without java.util.*
 */
public class RerootingDP {

    public static class RerootResult {
        public int bestHubNode;
        public String bestHubName;
        public int minTotalDistance;
        public int[] totalDistances;
        public String[] storeNames;

        public RerootResult(int bestHubNode, String bestHubName, int minTotalDistance,
                            int[] totalDistances, String[] storeNames) {
            this.bestHubNode = bestHubNode;
            this.bestHubName = bestHubName;
            this.minTotalDistance = minTotalDistance;
            this.totalDistances = totalDistances;
            this.storeNames = storeNames;
        }
    }

    private static class NodeList {
        int[] nodes;
        int count;

        NodeList(int capacity) {
            nodes = new int[capacity];
            count = 0;
        }

        void add(int val) {
            if (count == nodes.length) {
                int[] n = new int[nodes.length * 2];
                for (int i = 0; i < count; i++) n[i] = nodes[i];
                nodes = n;
            }
            nodes[count++] = val;
        }
    }

    public static RerootResult findOptimalHub(int numNodes, int[][] edges, String[] names) {
        if (numNodes <= 0 || edges == null) {
            return new RerootResult(-1, "None", 0, new int[0], new String[0]);
        }

        if (names == null || names.length != numNodes) {
            names = new String[numNodes];
            for (int i = 0; i < numNodes; i++) names[i] = "Store-" + i;
        }

        // Build adjacency lists
        NodeList[] adj = new NodeList[numNodes];
        for (int i = 0; i < numNodes; i++) adj[i] = new NodeList(4);

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj[u].add(v);
            adj[v].add(u);
        }

        int[] sz = new int[numNodes];
        int[] inDist = new int[numNodes];
        int[] totalDist = new int[numNodes];

        // Pass 1: Post-order bottom-up computation rooted at 0
        dfs1(0, -1, adj, sz, inDist);

        // Pass 2: Pre-order top-down rerooting
        totalDist[0] = inDist[0];
        dfs2(0, -1, adj, sz, totalDist, numNodes);

        int bestNode = 0;
        int minD = totalDist[0];
        for (int i = 1; i < numNodes; i++) {
            if (totalDist[i] < minD) {
                minD = totalDist[i];
                bestNode = i;
            }
        }

        return new RerootResult(bestNode, names[bestNode], minD, totalDist, names);
    }

    private static void dfs1(int u, int p, NodeList[] adj, int[] sz, int[] inDist) {
        sz[u] = 1;
        inDist[u] = 0;

        for (int i = 0; i < adj[u].count; i++) {
            int v = adj[u].nodes[i];
            if (v != p) {
                dfs1(v, u, adj, sz, inDist);
                sz[u] += sz[v];
                inDist[u] += inDist[v] + sz[v];
            }
        }
    }

    private static void dfs2(int u, int p, NodeList[] adj, int[] sz, int[] totalDist, int n) {
        for (int i = 0; i < adj[u].count; i++) {
            int v = adj[u].nodes[i];
            if (v != p) {
                // Reroot formula: moving root from u to child v
                totalDist[v] = totalDist[u] - sz[v] + (n - sz[v]);
                dfs2(v, u, adj, sz, totalDist, n);
            }
        }
    }
}
