package retailiq.co3;

/**
 * CO3: Needleman-Wunsch Global Sequence Alignment Algorithm.
 * Computes the optimal end-to-end global alignment between two product attribute
 * sequences or SKU descriptions with gap penalties and character scoring.
 *
 * Scoring:
 *   Match: +2
 *   Mismatch: -1
 *   Gap: -2
 *
 * Time Complexity: O(m * n)
 * Space Complexity: O(m * n)
 *
 * Course restriction: Implemented without java.util.*
 */
public class NeedlemanWunsch {

    public static class AlignmentResult {
        public int score;
        public String alignedSeq1;
        public String alignedSeq2;
        public int[][] scoreMatrix;

        public AlignmentResult(int score, String alignedSeq1, String alignedSeq2, int[][] scoreMatrix) {
            this.score = score;
            this.alignedSeq1 = alignedSeq1;
            this.alignedSeq2 = alignedSeq2;
            this.scoreMatrix = scoreMatrix;
        }
    }

    private static final int MATCH_SCORE = 2;
    private static final int MISMATCH_PENALTY = -1;
    private static final int GAP_PENALTY = -2;

    public static AlignmentResult align(String s1, String s2) {
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";

        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Base cases: all gaps
        for (int i = 0; i <= m; i++) dp[i][0] = i * GAP_PENALTY;
        for (int j = 0; j <= n; j++) dp[0][j] = j * GAP_PENALTY;

        for (int i = 1; i <= m; i++) {
            char c1 = s1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = s2.charAt(j - 1);
                int matchScore = (c1 == c2) ? MATCH_SCORE : MISMATCH_PENALTY;

                int diagonal = dp[i - 1][j - 1] + matchScore;
                int up = dp[i - 1][j] + GAP_PENALTY;
                int left = dp[i][j - 1] + GAP_PENALTY;

                dp[i][j] = Math.max(diagonal, Math.max(up, left));
            }
        }

        // Traceback to reconstruct optimal alignment
        char[] out1 = new char[m + n];
        char[] out2 = new char[m + n];
        int outIdx = 0;

        int i = m;
        int j = n;

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0) {
                char c1 = s1.charAt(i - 1);
                char c2 = s2.charAt(j - 1);
                int scoreDiag = (c1 == c2) ? MATCH_SCORE : MISMATCH_PENALTY;

                if (dp[i][j] == dp[i - 1][j - 1] + scoreDiag) {
                    out1[outIdx] = c1;
                    out2[outIdx] = c2;
                    outIdx++;
                    i--;
                    j--;
                    continue;
                }
            }

            if (i > 0 && dp[i][j] == dp[i - 1][j] + GAP_PENALTY) {
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

        // Reverse reconstructed characters
        char[] rev1 = new char[outIdx];
        char[] rev2 = new char[outIdx];
        for (int k = 0; k < outIdx; k++) {
            rev1[k] = out1[outIdx - 1 - k];
            rev2[k] = out2[outIdx - 1 - k];
        }

        return new AlignmentResult(dp[m][n], new String(rev1), new String(rev2), dp);
    }
}
