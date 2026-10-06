package retailiq.co5;

/**
 * CO5: Maximum Clique Problem.
 */
public class Clique {
    public static boolean hasCliqueOfSize(boolean[][] adj, int k) {
        int n = adj.length;
        int totalSubsets = 1 << n;

        for (int mask = 0; mask < totalSubsets; mask++) {
            if (Integer.bitCount(mask) == k) {
                boolean isClique = true;
                for (int u = 0; u < n; u++) {
                    if ((mask & (1 << u)) != 0) {
                        for (int v = u + 1; v < n; v++) {
                            if ((mask & (1 << v)) != 0 && !adj[u][v]) {
                                isClique = false;
                                break;
                            }
                        }
                    }
                    if (!isClique) break;
                }
                if (isClique) return true;
            }
        }
        return false;
    }
}
