package retailiq.co5;

/**
 * CO5: Hamiltonian Cycle Problem.
 */
public class HamiltonianCycle {
    public static boolean hasCycle(boolean[][] adj) {
        int n = adj.length;
        int[] path = new int[n];
        for (int i = 0; i < n; i++) path[i] = -1;
        path[0] = 0;
        return solveCycle(adj, path, 1);
    }

    private static boolean solveCycle(boolean[][] adj, int[] path, int pos) {
        int n = adj.length;
        if (pos == n) {
            return adj[path[pos - 1]][path[0]];
        }

        for (int v = 1; v < n; v++) {
            if (isSafe(v, adj, path, pos)) {
                path[pos] = v;
                if (solveCycle(adj, path, pos + 1)) return true;
                path[pos] = -1;
            }
        }
        return false;
    }

    private static boolean isSafe(int v, boolean[][] adj, int[] path, int pos) {
        if (!adj[path[pos - 1]][v]) return false;
        for (int i = 0; i < pos; i++) {
            if (path[i] == v) return false;
        }
        return true;
    }
}
