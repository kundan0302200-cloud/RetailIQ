package retailiq.co2;

/**
 * CO2: Kasai's Algorithm for Longest Common Prefix (LCP) Array Construction.
 * Computes the LCP array from a Suffix Array in linear time O(n).
 *
 * Used in RetailIQ to detect:
 *   - Duplicate and highly similar customer reviews
 *   - Shared prefix attributes among related product descriptions
 *   - Longest Repeated Substring (LRS)
 *
 * Time Complexity: O(n) linear time
 * Space Complexity: O(n) for rank and LCP arrays

 */
public class KasaiLCP {

    public static class LCPResult {
        public int[] lcp;
        public String longestRepeatedSubstring;
        public int maxLcpLength;

        public LCPResult(int[] lcp, String longestRepeatedSubstring, int maxLcpLength) {
            this.lcp = lcp;
            this.longestRepeatedSubstring = longestRepeatedSubstring;
            this.maxLcpLength = maxLcpLength;
        }
    }

    /**
     * Constructs the LCP array using Kasai's linear-time algorithm.
     * lcp[i] = length of LCP between suffixes sa[i] and sa[i+1].
     */
    public static LCPResult computeLCP(String text, int[] sa) {
        if (text == null || sa == null || text.length() <= 1 || sa.length <= 1) {
            return new LCPResult(new int[0], "", 0);
        }

        int n = text.length();
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int[] lcp = new int[n - 1];
        int k = 0;
        int maxLen = 0;
        int maxSuffixIdx = 0;

        for (int i = 0; i < n; i++) {
            if (rank[i] == n - 1) {
                k = 0;
                continue;
            }

            int j = sa[rank[i] + 1];

            while (i + k < n && j + k < n && text.charAt(i + k) == text.charAt(j + k)) {
                k++;
            }

            lcp[rank[i]] = k;
            if (k > maxLen) {
                maxLen = k;
                maxSuffixIdx = i;
            }

            if (k > 0) {
                k--;
            }
        }

        String lrs = maxLen > 0 ? text.substring(maxSuffixIdx, maxSuffixIdx + maxLen) : "";
        return new LCPResult(lcp, lrs, maxLen);
    }

    /**
     * Finds common phrases between two different product descriptions using concatenated LCP.
     */
    public static String findLongestCommonPhrase(String desc1, String desc2) {
        if (desc1 == null || desc2 == null || desc1.length() == 0 || desc2.length() == 0) {
            return "";
        }
        int len1 = desc1.length();
        String combined = desc1 + "#" + desc2;
        int[] sa = SAIS.buildSuffixArray(combined);
        LCPResult lcpRes = computeLCP(combined, sa);

        int bestLen = 0;
        int bestPos = 0;

        for (int i = 0; i < lcpRes.lcp.length; i++) {
            int sa1 = sa[i];
            int sa2 = sa[i + 1];

            // Verify that one suffix originates in desc1 and the other in desc2
            boolean oneInFirst = (sa1 < len1 && sa2 > len1) || (sa2 < len1 && sa1 > len1);
            if (oneInFirst && lcpRes.lcp[i] > bestLen) {
                bestLen = lcpRes.lcp[i];
                bestPos = sa1;
            }
        }

        if (bestLen == 0) return "";
        return combined.substring(bestPos, bestPos + bestLen);
    }
}
