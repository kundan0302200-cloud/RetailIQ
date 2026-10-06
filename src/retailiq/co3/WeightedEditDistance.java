package retailiq.co3;

/**
 * CO3: Weighted Edit Distance with Configurable Operation Costs.
 * Permits non-uniform penalty weights for insertion, deletion, substitution,
 * and keyboard layout proximity (e.g. QWERTY neighbor keys cost less).
 *
 * Time Complexity: O(m * n)
 * Space Complexity: O(m * n)
 *
 * Course restriction: Implemented without java.util.*
 */
public class WeightedEditDistance {

    public static class CostWeights {
        public double insertCost;
        public double deleteCost;
        public double replaceCost;
        public double transposeCost;

        public CostWeights(double insertCost, double deleteCost, double replaceCost, double transposeCost) {
            this.insertCost = insertCost;
            this.deleteCost = deleteCost;
            this.replaceCost = replaceCost;
            this.transposeCost = transposeCost;
        }

        public static CostWeights defaultRetailWeights() {
            return new CostWeights(1.0, 1.2, 1.5, 0.8);
        }
    }

    public static class WeightedResult {
        public double distance;
        public double[][] dp;

        public WeightedResult(double distance, double[][] dp) {
            this.distance = distance;
            this.dp = dp;
        }
    }

    public static WeightedResult compute(String s1, String s2, CostWeights weights) {
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";
        if (weights == null) weights = CostWeights.defaultRetailWeights();

        int m = s1.length();
        int n = s2.length();
        double[][] dp = new double[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i * weights.deleteCost;
        for (int j = 0; j <= n; j++) dp[0][j] = j * weights.insertCost;

        for (int i = 1; i <= m; i++) {
            char c1 = s1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = s2.charAt(j - 1);
                double subCost = (c1 == c2) ? 0.0 : getSubstitutionCost(c1, c2, weights.replaceCost);

                double del = dp[i - 1][j] + weights.deleteCost;
                double ins = dp[i][j - 1] + weights.insertCost;
                double rep = dp[i - 1][j - 1] + subCost;

                dp[i][j] = Math.min(rep, Math.min(del, ins));

                if (i > 1 && j > 1 && s1.charAt(i - 1) == s2.charAt(j - 2) && s1.charAt(i - 2) == s2.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 2][j - 2] + weights.transposeCost);
                }
            }
        }

        return new WeightedResult(dp[m][n], dp);
    }

    /**
     * QWERTY Keyboard proximity heuristic: Adjacent keys cost 60% of base substitution cost.
     */
    private static double getSubstitutionCost(char c1, char c2, double baseCost) {
        c1 = Character.toLowerCase(c1);
        c2 = Character.toLowerCase(c2);
        if (c1 == c2) return 0.0;

        // Check frequent keyboard neighbor pairs
        String neighbors = "qw as zx er df cv ty gh bn ui jk nm op kl";
        String pair1 = "" + c1 + c2;
        String pair2 = "" + c2 + c1;

        if (neighbors.indexOf(pair1) != -1 || neighbors.indexOf(pair2) != -1) {
            return baseCost * 0.6;
        }
        return baseCost;
    }
}
