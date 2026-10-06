package retailiq.co4;

/**
 * CO4: Bipartite Matching for warehouse slot allocation.
 * Uses Hopcroft-Karp or augmenting paths on flow networks.
 */
public class BipartiteMatching {

    public static int maxBipartiteMatching(boolean[][] bpGraph, int numLeft, int numRight) {
        int[] matchR = new int[numRight];
        for (int i = 0; i < numRight; i++) matchR[i] = -1;

        int result = 0;
        for (int u = 0; u < numLeft; u++) {
            boolean[] seen = new boolean[numRight];
            if (bpm(bpGraph, u, seen, matchR, numRight)) {
                result++;
            }
        }
        return result;
    }

    private static boolean bpm(boolean[][] bpGraph, int u, boolean[] seen, int[] matchR, int numRight) {
        for (int v = 0; v < numRight; v++) {
            if (bpGraph[u][v] && !seen[v]) {
                seen[v] = true;
                if (matchR[v] < 0 || bpm(bpGraph, matchR[v], seen, matchR, numRight)) {
                    matchR[v] = u;
                    return true;
                }
            }
        }
        return false;
    }
}
