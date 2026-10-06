package retailiq.co2;

/**
 * CO2: Naive String Matching Algorithm.
 * Serves as the fundamental baseline for substring search.
 * Time Complexity: O(n * m) worst case
 * Space Complexity: O(1) auxiliary space
 *
 * Course restriction: Implemented without java.util.*
 */
public class NaiveSearch {

    public static class SearchResult {
        public int[] matchIndices;
        public int matchCount;
        public long executionTimeNano;
        public long comparisons;

        public SearchResult(int[] matchIndices, int matchCount, long executionTimeNano, long comparisons) {
            this.matchIndices = matchIndices;
            this.matchCount = matchCount;
            this.executionTimeNano = executionTimeNano;
            this.comparisons = comparisons;
        }
    }

    public static SearchResult search(String text, String pattern) {
        long startTime = System.nanoTime();
        if (text == null || pattern == null || pattern.length() == 0 || text.length() < pattern.length()) {
            return new SearchResult(new int[0], 0, System.nanoTime() - startTime, 0);
        }

        int n = text.length();
        int m = pattern.length();
        int maxPossibleMatches = n - m + 1;
        int[] tempIndices = new int[maxPossibleMatches];
        int count = 0;
        long comparisons = 0;

        for (int i = 0; i <= n - m; i++) {
            boolean match = true;
            for (int j = 0; j < m; j++) {
                comparisons++;
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                tempIndices[count++] = i;
            }
        }

        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = tempIndices[i];
        }

        long elapsed = System.nanoTime() - startTime;
        return new SearchResult(result, count, elapsed, comparisons);
    }
}
