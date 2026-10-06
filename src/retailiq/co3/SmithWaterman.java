package retailiq.co3;

/**
 * CO3: Smith-Waterman Local Sequence Alignment Algorithm.
 * Identifies the highest-scoring local matching regions between two customer reviews
 * or product specification strings.
 *
 * Scoring:
 *   Match: +2
 *   Mismatch: -1
 *   Gap: -2
 *   Floor: 0 (local alignment does not accumulate negative scores)
 *
 * Time Complexity: O(m * n)
 * Space Complexity: O(m * n)
 *
 * Course restriction: Implemented without java.util.*
 */
public class SmithWaterman {

    public static class LocalAlignmentResult {
        public int maxScore;
        public String subSeq1;
        public String subSeq2;
        public int start1, end1;
        public int start2, end2;
        public int[][] scoreMatrix;

        public LocalAlignmentResult(int maxScore, String subSeq1, String subSeq2,
                                    int start1, int end1, int start2, int end2, int[][] scoreMatrix) {
            this.maxScore = maxScore;
            this.subSeq1 = subSeq1;
            this.subSeq2 = subSeq2;
            this.start1 = start1;
            this.end1 = end1;
            this.start2 = start2;
            this.end2 = end2;
            this.scoreMatrix = scoreMatrix;
        }
    }

    private static final int MATCH_SCORE = 2;
    private static final int MISMATCH_PENALTY = -1;
    private static final int GAP_PENALTY = -2;

    public static LocalAlignmentResult align(String s1, String s2) {
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";

        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        int maxScore = 0;
        int maxI = 0;
        int maxJ = 0;

        for (int i = 1; i <= m; i++) {
            char c1 = s1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = s2.charAt(j - 1);
                int scoreDiag = (c1 == c2) ? MATCH_SCORE : MISMATCH_PENALTY;

                int diag = dp[i - 1][j - 1] + scoreDiag;
                int up = dp[i - 1][j] + GAP_PENALTY;
                int left = dp[i][j - 1] + GAP_PENALTY;

                int val = Math.max(0, Math.max(diag, Math.max(up, left)));
                dp[i][j] = val;

                if (val > maxScore) {
                    maxScore = val;
                    maxI = i;
                    maxJ = j;
                }
            }
        }

        // Traceback from (maxI, maxJ) until score becomes 0
        char[] out1 = new char[m + n];
        char[] out2 = new char[m + n];
        int outIdx = 0;

        int i = maxI;
        int j = maxJ;
        int end1 = maxI;
        int end2 = maxJ;

        while (i > 0 && j > 0 && dp[i][j] > 0) {
            char c1 = s1.charAt(i - 1);
            char c2 = s2.charAt(j - 1);
            int scoreDiag = (c1 == c2) ? MATCH_SCORE : MISMATCH_PENALTY;

            if (dp[i][j] == dp[i - 1][j - 1] + scoreDiag) {
                out1[outIdx] = c1;
                out2[outIdx] = c2;
                outIdx++;
                i--;
                j--;
            } else if (dp[i][j] == dp[i - 1][j] + GAP_PENALTY) {
                out1[outIdx] = s1.charAt(i - 1);
                out2[outIdx] = '-';
                outIdx++;
                i--;
            } else {
                out1[outIdx] = '-';
                out2[outIdx] = s2.charAt(j - 1);
                outIdx++;
                j--;
            }
        }

        int start1 = i;
        int start2 = j;

        char[] rev1 = new char[outIdx];
        char[] rev2 = new char[outIdx];
        for (int k = 0; k < outIdx; k++) {
            rev1[k] = out1[outIdx - 1 - k];
            rev2[k] = out2[outIdx - 1 - k];
        }

        return new LocalAlignmentResult(maxScore, new String(rev1), new String(rev2),
                start1, end1, start2, end2, dp);
    }
}
