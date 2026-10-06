package retailiq.co3;

/**
 * CO3: Traveling Salesperson Problem (TSP) using Held-Karp Bitmask DP.
 * Calculates exact shortest delivery tour across a network of retail stores and fulfillment hubs.
 *
 * State:
 *   dp[mask][u] = minimum cost to visit all nodes in 'mask' ending at node 'u'.
 *
 * Transition:
 *   dp[mask | (1 << v)][v] = min_u (dp[mask][u] + dist[u][v]) for v not in mask.
 *
 * Time Complexity: O(2^n * n^2)
 * Space Complexity: O(2^n * n)
 *
 * Course restriction: Implemented without java.util.*
 */
public class TSP {

    public static final int MAX_NODES = 18;
    private static final double INF = 1e9;

    public static class TSPResult {
        public double minCost;
        public int[] tour;
        public String[] nodeNames;

        public TSPResult(double minCost, int[] tour, String[] nodeNames) {
            this.minCost = minCost;
            this.tour = tour;
            this.nodeNames = nodeNames;
        }
    }

    public static TSPResult solve(double[][] dist, String[] names) {
        if (dist == null || dist.length == 0) {
            return new TSPResult(0, new int[0], new String[0]);
        }

        int n = dist.length;
        if (n > MAX_NODES) {
            throw new IllegalArgumentException("TSP input size (" + n + ") exceeds safe limit of " + MAX_NODES + " nodes!");
        }

        if (names == null || names.length != n) {
            names = new String[n];
            for (int i = 0; i < n; i++) names[i] = "Location-" + i;
        }

        int numStates = 1 << n;
        double[][] dp = new double[numStates][n];
        int[][] parent = new int[numStates][n];

        for (int i = 0; i < numStates; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = INF;
                parent[i][j] = -1;
            }
        }

        // Base case: starting at node 0
        dp[1][0] = 0;

        for (int mask = 1; mask < numStates; mask++) {
            for (int u = 0; u < n; u++) {
                if (dp[mask][u] >= INF) continue;

                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) == 0 && dist[u][v] > 0) {
                        int nextMask = mask | (1 << v);
                        double newCost = dp[mask][u] + dist[u][v];
                        if (newCost < dp[nextMask][v]) {
                            dp[nextMask][v] = newCost;
                            parent[nextMask][v] = u;
                        }
                    }
                }
            }
        }

        // Complete tour returning to start node 0
        int finalMask = numStates - 1;
        double minTotalCost = INF;
        int lastNode = -1;

        for (int u = 1; u < n; u++) {
            if (dist[u][0] > 0) {
                double tourCost = dp[finalMask][u] + dist[u][0];
                if (tourCost < minTotalCost) {
                    minTotalCost = tourCost;
                    lastNode = u;
                }
            }
        }

        // Reconstruct tour path
        int[] tour = new int[n + 1];
        if (lastNode != -1) {
            int currMask = finalMask;
            int currNode = lastNode;
            tour[n] = 0; // return to depot

            for (int step = n - 1; step >= 1; step--) {
                tour[step] = currNode;
                int prev = parent[currMask][currNode];
                currMask ^= (1 << currNode);
                currNode = prev;
            }
            tour[0] = 0; // depot start
        }

        return new TSPResult(minTotalCost, tour, names);
    }
}
