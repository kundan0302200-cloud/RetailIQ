package retailiq.co2;

/**
 * CO2: Z-Algorithm for Pattern Matching and Repeated Phrase Detection.
 * Constructs the Z-array in linear time O(n) using a sliding interval [L, R].
 * Time Complexity: O(n + m) for pattern search
 * Space Complexity: O(n + m) for the Z-array
 *
 * Course restriction: Implemented without java.util.*
 */
public class ZAlgorithm {

    public static class ZResult {
        public int[] matchIndices;
        public int matchCount;
        public int[] zArray;
        public long executionTimeNano;

        public ZResult(int[] matchIndices, int matchCount, int[] zArray, long executionTimeNano) {
            this.matchIndices = matchIndices;
            this.matchCount = matchCount;
            this.zArray = zArray;
            this.executionTimeNano = executionTimeNano;
        }
    }

    /**
     * Constructs the Z-array for string s in O(n) time.
     * Z[i] is the length of the longest substring starting at s[i] that is also a prefix of s.
     */
    public static int[] calculateZArray(String s) {
        int n = s.length();
        int[] z = new int[n];
        z[0] = n;

        int l = 0;
        int r = 0;

        for (int i = 1; i < n; i++) {
            if (i > r) {
                l = r = i;
                while (r < n && s.charAt(r - l) == s.charAt(r)) {
                    r++;
                }
                z[i] = r - l;
                r--;
            } else {
                int k = i - l;
                if (z[k] < r - i + 1) {
                    z[i] = z[k];
                } else {
                    l = i;
                    while (r < n && s.charAt(r - l) == s.charAt(r)) {
                        r++;
                    }
                    z[i] = r - l;
                    r--;
                }
            }
        }
        return z;
    }

    /**
     * Searches for pattern in text using the Z-algorithm with concatenation: pattern + "$" + text.
     */
    public static ZResult search(String text, String pattern) {
        long startTime = System.nanoTime();
        if (text == null || pattern == null || pattern.length() == 0 || text.length() < pattern.length()) {
            return new ZResult(new int[0], 0, new int[0], System.nanoTime() - startTime);
        }

        int m = pattern.length();
        int n = text.length();
        String concat = pattern + "$" + text;
        int concatLen = concat.length();
        int[] z = calculateZArray(concat);

        int[] tempMatches = new int[n];
        int count = 0;

        for (int i = m + 1; i < concatLen; i++) {
            if (z[i] == m) {
                tempMatches[count++] = i - m - 1;
            }
        }

        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = tempMatches[i];
        }

        long elapsed = System.nanoTime() - startTime;
        return new ZResult(result, count, z, elapsed);
    }

    /**
     * Finds repeated prefixes or phrases in review text.
     */
    public static String findLongestRepeatedPrefix(String text) {
        if (text == null || text.length() < 2) return "";
        int[] z = calculateZArray(text);
        int maxLen = 0;
        int bestIdx = 0;

        for (int i = 1; i < z.length; i++) {
            if (z[i] > maxLen) {
                maxLen = z[i];
                bestIdx = i;
            }
        }

        if (maxLen == 0) return "";
        return text.substring(0, maxLen);
    }
}
