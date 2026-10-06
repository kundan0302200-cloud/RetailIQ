package retailiq.co5;

/**
 * CO5: Maximum Independent Set.
 * Relates to Clique: A set S is an Independent Set in G iff S is a Clique in the complement of G.
 */
public class IndependentSet {
    public static boolean hasIndependentSet(boolean[][] adj, int k) {
        int n = adj.length;
        boolean[][] complement = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) complement[i][j] = !adj[i][j];
            }
        }
        return Clique.hasCliqueOfSize(complement, k);
    }
}
