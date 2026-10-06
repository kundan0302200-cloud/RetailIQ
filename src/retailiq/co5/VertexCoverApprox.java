package retailiq.co5;

/**
 * CO5: 2-Approximation Algorithm for Vertex Cover using Maximal Matching.
 * Guarantees |C| <= 2 * |C*|.
 */
public class VertexCoverApprox {

    public static class ApproxResult {
        public boolean[] inCover;
        public int coverSize;
        public String note;

        public ApproxResult(boolean[] inCover, int coverSize, String note) {
            this.inCover = inCover;
            this.coverSize = coverSize;
            this.note = note;
        }
    }

    public static ApproxResult approximateCover(boolean[][] adj) {
        int n = adj.length;
        boolean[] inCover = new boolean[n];
        boolean[][] visitedEdge = new boolean[n][n];
        int count = 0;

        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                if (adj[u][v] && !visitedEdge[u][v]) {
                    if (!inCover[u] && !inCover[v]) {
                        inCover[u] = true;
                        inCover[v] = true;
                        count += 2;
                    }
                    visitedEdge[u][v] = true;
                    visitedEdge[v][u] = true;
                }
            }
        }

        return new ApproxResult(inCover, count, "Approximate 2-factor guarantee (Maximal Matching approach)");
    }
}
