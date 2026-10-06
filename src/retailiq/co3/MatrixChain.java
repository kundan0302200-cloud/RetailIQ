package retailiq.co3;

/**
 * CO3: Matrix Chain Multiplication (Academic Dynamic Programming Demonstration).
 * Computes optimal parenthesization to minimize scalar multiplications for matrix products.
 *
 * Recurrence:
 *   dp[i][j] = 0 (for i == j)
 *   dp[i][j] = min_{i <= k < j} (dp[i][k] + dp[k+1][j] + p[i-1]*p[k]*p[j])
 *
 * Time Complexity: O(n^3)
 * Space Complexity: O(n^2)
 *
 * Course restriction: Implemented without java.util.*
 */
public class MatrixChain {

    public static class MCMResult {
        public int minMultiplications;
        public int[][] dp;
        public int[][] split;
        public String parenthesization;

        public MCMResult(int minMultiplications, int[][] dp, int[][] split, String parenthesization) {
            this.minMultiplications = minMultiplications;
            this.dp = dp;
            this.split = split;
            this.parenthesization = parenthesization;
        }
    }

    public static MCMResult compute(int[] p) {
        if (p == null || p.length < 2) {
            return new MCMResult(0, new int[0][0], new int[0][0], "");
        }

        int n = p.length - 1; // number of matrices
        int[][] dp = new int[n + 1][n + 1];
        int[][] split = new int[n + 1][n + 1];

        // L is chain length
        for (int L = 2; L <= n; L++) {
            for (int i = 1; i <= n - L + 1; i++) {
                int j = i + L - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                        split[i][j] = k;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        buildParenthesization(split, 1, n, sb);
        return new MCMResult(dp[1][n], dp, split, sb.toString());
    }

    private static void buildParenthesization(int[][] split, int i, int j, StringBuilder sb) {
        if (i == j) {
            sb.append("M").append(i);
        } else {
            sb.append("(");
            buildParenthesization(split, i, split[i][j], sb);
            sb.append(" x ");
            buildParenthesization(split, split[i][j] + 1, j, sb);
            sb.append(")");
        }
    }
}
