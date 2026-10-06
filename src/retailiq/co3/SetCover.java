package retailiq.co3;

/**
 * CO3: Exact Minimum Set Cover via Bitmask Dynamic Programming.
 * Selects the minimum number of retail marketing campaigns or supplier bundles
 * necessary to cover 100% of target customer segments or product feature requirements.
 *
 * State:
 *   dp[mask] = minimum number of campaigns required to cover segment subset 'mask'.
 *
 * Transition:
 *   dp[mask | campaignMask[i]] = min(dp[mask | campaignMask[i]], dp[mask] + 1)
 *
 * Time Complexity: O(M * 2^U) where U is universe size and M is number of sets.
 * Space Complexity: O(2^U)
 *
 * Course restriction: Implemented without java.util.*
 */
public class SetCover {

    public static class SetCoverResult {
        public int minCampaignsCount;
        public String[] selectedCampaignNames;
        public int finalCoverageMask;

        public SetCoverResult(int minCampaignsCount, String[] selectedCampaignNames, int finalCoverageMask) {
            this.minCampaignsCount = minCampaignsCount;
            this.selectedCampaignNames = selectedCampaignNames;
            this.finalCoverageMask = finalCoverageMask;
        }
    }

    public static class Campaign {
        public String name;
        public int coveredSegmentsMask;

        public Campaign(String name, int coveredSegmentsMask) {
            this.name = name;
            this.coveredSegmentsMask = coveredSegmentsMask;
        }
    }

    public static SetCoverResult solve(int universeSize, Campaign[] campaigns) {
        if (universeSize <= 0 || campaigns == null || campaigns.length == 0) {
            return new SetCoverResult(0, new String[0], 0);
        }

        if (universeSize > 18) {
            universeSize = 18; // Limit
        }

        int fullMask = (1 << universeSize) - 1;
        int numStates = 1 << universeSize;
        int[] dp = new int[numStates];
        int[] parentMask = new int[numStates];
        int[] parentCampaign = new int[numStates];

        for (int i = 0; i < numStates; i++) {
            dp[i] = 1_000_000;
            parentMask[i] = -1;
            parentCampaign[i] = -1;
        }

        dp[0] = 0;

        for (int mask = 0; mask < numStates; mask++) {
            if (dp[mask] >= 1_000_000) continue;

            for (int i = 0; i < campaigns.length; i++) {
                int nextMask = mask | campaigns[i].coveredSegmentsMask;
                if (dp[mask] + 1 < dp[nextMask]) {
                    dp[nextMask] = dp[mask] + 1;
                    parentMask[nextMask] = mask;
                    parentCampaign[nextMask] = i;
                }
            }
        }

        if (dp[fullMask] >= 1_000_000) {
            return new SetCoverResult(-1, new String[0], 0);
        }

        // Traceback selected campaigns
        int count = dp[fullMask];
        String[] selected = new String[count];
        int curr = fullMask;
        int idx = count - 1;

        while (curr > 0 && idx >= 0) {
            int campIdx = parentCampaign[curr];
            selected[idx--] = campaigns[campIdx].name;
            curr = parentMask[curr];
        }

        return new SetCoverResult(count, selected, fullMask);
    }
}
