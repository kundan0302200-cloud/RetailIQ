package retailiq.co5;

/**
 * CO5: Vertex Cover Problem.
 * A set S is a Vertex Cover of G iff V \ S is an Independent Set of G.
 */
public class VertexCover {
    public static boolean hasVertexCover(boolean[][] adj, int k) {
        int n = adj.length;
        return IndependentSet.hasIndependentSet(adj, n - k);
    }
}
