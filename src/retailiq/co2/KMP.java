package retailiq.co2;

/**
 * CO2: Knuth-Morris-Pratt (KMP) Pattern Matching Algorithm.
 * Implements linear worst-case single pattern search using the failure function (LPS array).
 * Time Complexity: O(n + m) worst case
 * Space Complexity: O(m) auxiliary space for LPS table
 *
 * Course restriction: Implemented without java.util.*
 */
public class KMP {

    public static class KMPResult {
        public int[] matchIndices;
        public int matchCount;
        public int[] lps;
        public long executionTimeNano;
        public long comparisons;

        public KMPResult(int[] matchIndices, int matchCount, int[] lps, long executionTimeNano, long comparisons) {
            this.matchIndices = matchIndices;
            this.matchCount = matchCount;
            this.lps = lps;
            this.executionTimeNano = executionTimeNano;
            this.comparisons = comparisons;
        }
    }

    /**
     * Precomputes the Longest Proper Prefix which is also Suffix (LPS) array.
     */
    public static int[] computeLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        lps[0] = 0;
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * Searches for pattern in text using KMP.
     */
    public static KMPResult search(String text, String pattern) {
        long startTime = System.nanoTime();
        if (text == null || pattern == null || pattern.length() == 0 || text.length() < pattern.length()) {
            return new KMPResult(new int[0], 0, new int[0], System.nanoTime() - startTime, 0);
        }

        int n = text.length();
        int m = pattern.length();
        int[] lps = computeLPS(pattern);

        int[] tempMatches = new int[n];
        int count = 0;
        long comparisons = 0;

        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < n) {
            comparisons++;
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == m) {
                tempMatches[count++] = i - j;
                j = lps[j - 1];
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        int[] result = new int[count];
        for (int k = 0; k < count; k++) {
            result[k] = tempMatches[k];
        }

        long elapsed = System.nanoTime() - startTime;
        return new KMPResult(result, count, lps, elapsed, comparisons);
    }
}
