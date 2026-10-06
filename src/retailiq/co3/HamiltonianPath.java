package retailiq.co3;

/**
 * CO3: Hamiltonian Path using Bitmask Dynamic Programming.
 * Determines if a delivery courier or automated guided vehicle (AGV) can visit every
 * warehouse aisle/outlet exactly once.
 *
 * State:
 *   dp[mask][u] = true if a valid path exists visiting all vertices in 'mask' ending at vertex u.
 *
 * Time Complexity: O(2^n * n^2)
 * Space Complexity: O(2^n * n)
 *
 * Course restriction: Implemented without java.util.*
 */
public class HamiltonianPath {

    public static final int MAX_NODES = 20;

    public static class PathResult {
        public boolean exists;
        public int[] path;
        public String[] nodeNames;

        public PathResult(boolean exists, int[] path, String[] nodeNames) {
            this.exists = exists;
            this.path = path;
            this.nodeNames = nodeNames;
        }
    }

    public static PathResult findPath(boolean[][] adj, String[] names) {
        if (adj == null || adj.length == 0) {
            return new PathResult(false, new int[0], new String[0]);
        }

        int n = adj.length;
        if (n > MAX_NODES) {
            throw new IllegalArgumentException("Input size (" + n + ") exceeds safe limit of " + MAX_NODES + " nodes!");
        }

        if (names == null || names.length != n) {
            names = new String[n];
            for (int i = 0; i < n; i++) names[i] = "Node-" + i;
        }

        int numStates = 1 << n;
        boolean[][] dp = new boolean[numStates][n];
        int[][] parent = new int[numStates][n];

        for (int i = 0; i < numStates; i++) {
            for (int j = 0; j < n; j++) {
                parent[i][j] = -1;
            }
        }

        // Base cases: single node paths
        for (int i = 0; i < n; i++) {
            dp[1 << i][i] = true;
        }

        for (int mask = 1; mask < numStates; mask++) {
            for (int u = 0; u < n; u++) {
                if (!dp[mask][u]) continue;

                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) == 0 && adj[u][v]) {
                        int nextMask = mask | (1 << v);
                        dp[nextMask][v] = true;
                        parent[nextMask][v] = u;
                    }
                }
            }
        }

        int fullMask = numStates - 1;
        int endNode = -1;
        for (int i = 0; i < n; i++) {
            if (dp[fullMask][i]) {
                endNode = i;
                break;
            }
        }

        if (endNode == -1) {
            return new PathResult(false, new int[0], names);
        }

        // Reconstruct path
        int[] path = new int[n];
        int currMask = fullMask;
        int currNode = endNode;

        for (int step = n - 1; step >= 0; step--) {
            path[step] = currNode;
            int prev = parent[currMask][currNode];
            currMask ^= (1 << currNode);
            currNode = prev;
        }

        return new PathResult(true, path, names);
    }
}
