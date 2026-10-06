package retailiq.co3;

/**
 * CO3: Optimal Binary Search Tree (Optimal BST) Dynamic Programming.
 * Minimizes expected search time for catalog lookups given non-uniform search frequencies.
 *
 * Recurrence:
 *   e[i][j] = min_{i <= r <= j} (e[i][r-1] + e[r+1][j] + w[i][j])
 *   where w[i][j] = sum of search frequencies from index i to j.
 *
 * Time Complexity: O(n^3)
 * Space Complexity: O(n^2)
 *
 * Course restriction: Implemented without java.util.*
 */
public class OptimalBST {

    public static class OBSTResult {
        public double minExpectedCost;
        public double[][] cost;
        public int[][] root;
        public String[] keys;
        public double[] freq;

        public OBSTResult(double minExpectedCost, double[][] cost, int[][] root, String[] keys, double[] freq) {
            this.minExpectedCost = minExpectedCost;
            this.cost = cost;
            this.root = root;
            this.keys = keys;
            this.freq = freq;
        }
    }

    public static OBSTResult compute(String[] keys, double[] freq) {
        if (keys == null || freq == null || keys.length == 0 || keys.length != freq.length) {
            return new OBSTResult(0, new double[0][0], new int[0][0], new String[0], new double[0]);
        }

        int n = keys.length;
        double[][] e = new double[n + 2][n + 2];
        double[][] w = new double[n + 2][n + 2];
        int[][] root = new int[n + 1][n + 1];

        for (int i = 1; i <= n + 1; i++) {
            e[i][i - 1] = 0;
            w[i][i - 1] = 0;
        }

        for (int L = 1; L <= n; L++) {
            for (int i = 1; i <= n - L + 1; i++) {
                int j = i + L - 1;
                e[i][j] = Double.MAX_VALUE;
                w[i][j] = w[i][j - 1] + freq[j - 1];

                for (int r = i; r <= j; r++) {
                    double t = e[i][r - 1] + e[r + 1][j] + w[i][j];
                    if (t < e[i][j]) {
                        e[i][j] = t;
                        root[i][j] = r;
                    }
                }
            }
        }

        return new OBSTResult(e[1][n], e, root, keys, freq);
    }
}
