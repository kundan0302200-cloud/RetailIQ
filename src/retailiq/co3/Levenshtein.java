package retailiq.co3;

/**
 * CO3: Levenshtein Distance (Wagner-Fischer Algorithm).
 * Computes minimum edit operations (Insert, Delete, Substitute) to convert query to catalog term.
 *
 * Recurrence:
 *   dp[i][j] = dp[i-1][j-1] if s1[i-1] == s2[j-1]
 *   dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) otherwise
 *
 * Time Complexity: O(m * n)
 * Space Complexity: O(m * n) (or O(min(m, n)) for distance only)
 *
 * Course restriction: Implemented without java.util.*
 */
public class Levenshtein {

    public static class EditResult {
        public int distance;
        public int[][] dp;
        public String query;
        public String target;

        public EditResult(int distance, int[][] dp, String query, String target) {
            this.distance = distance;
            this.dp = dp;
            this.query = query;
            this.target = target;
        }
    }

    public static EditResult compute(String query, String target) {
        if (query == null) query = "";
        if (target == null) target = "";

        int m = query.length();
        int n = target.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            char c1 = query.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = target.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int insertOp = dp[i][j - 1];
                    int deleteOp = dp[i - 1][j];
                    int replaceOp = dp[i - 1][j - 1];
                    dp[i][j] = 1 + Math.min(replaceOp, Math.min(insertOp, deleteOp));
                }
            }
        }

        return new EditResult(dp[m][n], dp, query, target);
    }

    /**
     * Finds the closest catalog terms for an erroneous query.
     */
    public static String[] getClosestCandidates(String query, String[] vocabulary, int maxDistance) {
        if (vocabulary == null || vocabulary.length == 0) {
            return new String[0];
        }

        String[] matchedWords = new String[vocabulary.length];
        int[] distances = new int[vocabulary.length];
        int count = 0;

        for (int i = 0; i < vocabulary.length; i++) {
            String word = vocabulary[i];
            int dist = compute(query.toLowerCase(), word.toLowerCase()).distance;
            if (dist <= maxDistance) {
                matchedWords[count] = word;
                distances[count] = dist;
                count++;
            }
        }

        // Sort candidates by distance ascending (custom insertion sort)
        for (int i = 1; i < count; i++) {
            String keyWord = matchedWords[i];
            int keyDist = distances[i];
            int j = i - 1;
            while (j >= 0 && distances[j] > keyDist) {
                matchedWords[j + 1] = matchedWords[j];
                distances[j + 1] = distances[j];
                j--;
            }
            matchedWords[j + 1] = keyWord;
            distances[j + 1] = keyDist;
        }

        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = matchedWords[i] + " (dist: " + distances[i] + ")";
        }
        return result;
    }
}
