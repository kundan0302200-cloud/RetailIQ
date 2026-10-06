package retailiq.co3;

/**
 * CO3: SOS DP (Sum Over Subsets Dynamic Programming).
 * Efficiently computes aggregated retail metrics (e.g., total sales, customer counts,
 * loyalty segment totals) across all feature submasks in O(N * 2^N) instead of naive O(3^N).
 *
 * Problem:
 *   For every feature bitmask M (e.g., [VIP, AppUser, PrimeMember, PromoSubscriber]),
 *   calculate F[M] = sum_{sub in M} A[sub].
 *
 * DP State & Transition:
 *   dp[i][mask] = sum over submasks matching mask on bits >= i, and subset on bits < i.
 *   dp[i][mask] = dp[i-1][mask] + (if i-th bit of mask is 1: dp[i-1][mask ^ (1<<i)])
 *
 * Time Complexity: O(N * 2^N)
 * Space Complexity: O(2^N) (using 1D in-place state)
 *
 * Course restriction: Implemented without java.util.*
 */
public class SOSDP {

    public static class SOSResult {
        public int numBits;
        public long[] originalValues;
        public long[] subsetSums;
        public String[] featureLabels;

        public SOSResult(int numBits, long[] originalValues, long[] subsetSums, String[] featureLabels) {
            this.numBits = numBits;
            this.originalValues = originalValues;
            this.subsetSums = subsetSums;
            this.featureLabels = featureLabels;
        }
    }

    public static SOSResult compute(int numBits, long[] initialValues, String[] featureLabels) {
        if (numBits <= 0 || initialValues == null) {
            return new SOSResult(0, new long[0], new long[0], new String[0]);
        }

        if (numBits > 16) {
            numBits = 16; // Safeguard
        }

        int totalMasks = 1 << numBits;
        long[] original = new long[totalMasks];
        long[] dp = new long[totalMasks];

        for (int i = 0; i < totalMasks && i < initialValues.length; i++) {
            original[i] = initialValues[i];
            dp[i] = initialValues[i];
        }

        // SOS DP in-place computation across bit dimensions
        for (int i = 0; i < numBits; i++) {
            int bitMask = 1 << i;
            for (int mask = 0; mask < totalMasks; mask++) {
                if ((mask & bitMask) != 0) {
                    dp[mask] += dp[mask ^ bitMask];
                }
            }
        }

        return new SOSResult(numBits, original, dp, featureLabels);
    }
}
