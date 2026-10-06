package retailiq.co2;

/**
 * CO2: Rabin-Karp String Matching Algorithm with Double Polynomial Rolling Hashing.
 * Features rolling hash window updates, double hashing to minimize collision probability,
 * and explicit character verification upon hash match.
 *
 * Time Complexity:
 *   - Average / Best Case: O(n + m)
 *   - Worst Case (spurious hits): O(n * m)
 * Space Complexity: O(1) auxiliary space
 *
 * Course restriction: Implemented without java.util.*
 */
public class RabinKarp {

    public static class RKResult {
        public int[] matchIndices;
        public int matchCount;
        public int spuriousHits;
        public long executionTimeNano;
        public long hashCalculations;

        public RKResult(int[] matchIndices, int matchCount, int spuriousHits, long executionTimeNano, long hashCalculations) {
            this.matchIndices = matchIndices;
            this.matchCount = matchCount;
            this.spuriousHits = spuriousHits;
            this.executionTimeNano = executionTimeNano;
            this.hashCalculations = hashCalculations;
        }
    }

    private static final long BASE1 = 256;
    private static final long MOD1 = 1_000_000_007L;

    private static final long BASE2 = 313;
    private static final long MOD2 = 1_000_000_009L;

    public static RKResult search(String text, String pattern) {
        long startTime = System.nanoTime();
        if (text == null || pattern == null || pattern.length() == 0 || text.length() < pattern.length()) {
            return new RKResult(new int[0], 0, 0, System.nanoTime() - startTime, 0);
        }

        int n = text.length();
        int m = pattern.length();

        long pHash1 = 0, tHash1 = 0;
        long pHash2 = 0, tHash2 = 0;
        long h1 = 1, h2 = 1;
        long hashCalculations = 0;
        int spuriousHits = 0;

        // Precompute (BASE^(m-1)) % MOD
        for (int i = 0; i < m - 1; i++) {
            h1 = (h1 * BASE1) % MOD1;
            h2 = (h2 * BASE2) % MOD2;
        }

        // Calculate initial hashes for pattern and first window of text
        for (int i = 0; i < m; i++) {
            pHash1 = (pHash1 * BASE1 + pattern.charAt(i)) % MOD1;
            tHash1 = (tHash1 * BASE1 + text.charAt(i)) % MOD1;

            pHash2 = (pHash2 * BASE2 + pattern.charAt(i)) % MOD2;
            tHash2 = (tHash2 * BASE2 + text.charAt(i)) % MOD2;
            hashCalculations++;
        }

        int[] tempMatches = new int[n - m + 1];
        int count = 0;

        for (int i = 0; i <= n - m; i++) {
            if (pHash1 == tHash1 && pHash2 == tHash2) {
                // Verify candidate match against actual characters
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        spuriousHits++;
                        break;
                    }
                }
                if (match) {
                    tempMatches[count++] = i;
                }
            }

            // Rolling hash shift
            if (i < n - m) {
                tHash1 = (BASE1 * (tHash1 - text.charAt(i) * h1) + text.charAt(i + m)) % MOD1;
                if (tHash1 < 0) tHash1 += MOD1;

                tHash2 = (BASE2 * (tHash2 - text.charAt(i) * h2) + text.charAt(i + m)) % MOD2;
                if (tHash2 < 0) tHash2 += MOD2;

                hashCalculations++;
            }
        }

        int[] result = new int[count];
        for (int k = 0; k < count; k++) {
            result[k] = tempMatches[k];
        }

        long elapsed = System.nanoTime() - startTime;
        return new RKResult(result, count, spuriousHits, elapsed, hashCalculations);
    }
}
