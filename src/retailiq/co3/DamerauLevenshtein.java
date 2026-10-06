package retailiq.co3;

/**
 * CO3: Damerau-Levenshtein Distance.
 * Extends classical Levenshtein by supporting adjacent transpositions (swaps).
 * Captures over 80% of human typographic spelling mistakes in retail queries.
 *
 * Operations:
 *   1. Insertion (cost 1)
 *   2. Deletion (cost 1)
 *   3. Substitution (cost 1)
 *   4. Transposition of adjacent characters (cost 1)
 *
 * Time Complexity: O(m * n)
 * Space Complexity: O(m * n)
 *
 * Course restriction: Implemented without java.util.*
 */
public class DamerauLevenshtein {

    public static class DamerauResult {
        public int distance;
        public int[][] dp;
        public String s1;
        public String s2;

        public DamerauResult(int distance, int[][] dp, String s1, String s2) {
            this.distance = distance;
            this.dp = dp;
            this.s1 = s1;
            this.s2 = s2;
        }
    }

    public static DamerauResult compute(String s1, String s2) {
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";

        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            char c1 = s1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = s2.charAt(j - 1);
                int cost = (c1 == c2) ? 0 : 1;

                int deleteOp = dp[i - 1][j] + 1;
                int insertOp = dp[i][j - 1] + 1;
                int replaceOp = dp[i - 1][j - 1] + cost;

                dp[i][j] = Math.min(replaceOp, Math.min(deleteOp, insertOp));

                // Check transposition of adjacent characters
                if (i > 1 && j > 1 && s1.charAt(i - 1) == s2.charAt(j - 2) && s1.charAt(i - 2) == s2.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 2][j - 2] + 1);
                }
            }
        }

        return new DamerauResult(dp[m][n], dp, s1, s2);
    }

    /**
     * Recommends closest catalog matches with transposition tolerance.
     */
    public static String[] correctQuery(String query, String[] vocabulary) {
        if (vocabulary == null || vocabulary.length == 0) return new String[0];

        int[] dists = new int[vocabulary.length];
        String[] words = new String[vocabulary.length];
        for (int i = 0; i < vocabulary.length; i++) {
            words[i] = vocabulary[i];
            dists[i] = compute(query.toLowerCase(), vocabulary[i].toLowerCase()).distance;
        }

        // Sort ascending
        for (int i = 1; i < words.length; i++) {
            String kw = words[i];
            int kd = dists[i];
            int j = i - 1;
            while (j >= 0 && dists[j] > kd) {
                words[j + 1] = words[j];
                dists[j + 1] = dists[j];
                j--;
            }
            words[j + 1] = kw;
            dists[j + 1] = kd;
        }

        int count = Math.min(3, words.length);
        String[] suggestions = new String[count];
        for (int i = 0; i < count; i++) {
            suggestions[i] = words[i] + " (Damerau distance: " + dists[i] + ")";
        }
        return suggestions;
    }
}
