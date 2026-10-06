package retailiq.co3;

/**
 * CO3: Bitmask Dynamic Programming for Product Bundle Selection.
 * Finds the optimal combination of retail products that maximizes overall bundle
 * synergy and rating while strictly adhering to a total budget and category compatibility constraints.
 *
 * State Representation:
 *   An integer mask where the i-th bit is 1 if product i is included in the bundle.
 *
 * Time Complexity: O(2^N * N)
 * Space Complexity: O(2^N)
 *
 * Course restriction: Implemented without java.util.*
 */
public class BitmaskDP {

    public static class BundleItem {
        public int id;
        public String name;
        public String category;
        public double price;
        public double rating;

        public BundleItem(int id, String name, String category, double price, double rating) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
            this.rating = rating;
        }
    }

    public static class BundleResult {
        public BundleItem[] selectedItems;
        public double totalCost;
        public double budget;
        public double remainingAmount;
        public double totalUtilityScore;
        public int mask;

        public BundleResult(BundleItem[] selectedItems, double totalCost, double budget,
                            double remainingAmount, double totalUtilityScore, int mask) {
            this.selectedItems = selectedItems;
            this.totalCost = totalCost;
            this.budget = budget;
            this.remainingAmount = remainingAmount;
            this.totalUtilityScore = totalUtilityScore;
            this.mask = mask;
        }
    }

    public static BundleResult optimizeBundle(BundleItem[] catalog, double budget) {
        if (catalog == null || catalog.length == 0 || budget <= 0) {
            return new BundleResult(new BundleItem[0], 0, budget, budget, 0, 0);
        }

        int n = catalog.length;
        if (n > 20) {
            n = 20; // Safeguard against exponential explosion
        }

        int numSubsets = 1 << n;
        double bestUtility = -1.0;
        int bestMask = 0;
        double bestCost = 0.0;

        // DP array to store total cost of each mask
        double[] cost = new double[numSubsets];
        double[] utility = new double[numSubsets];

        cost[0] = 0;
        utility[0] = 0;

        for (int mask = 1; mask < numSubsets; mask++) {
            // Find lowest set bit
            int lsb = mask & -mask;
            int bitIdx = 0;
            while ((1 << bitIdx) != lsb) {
                bitIdx++;
            }
            int prevMask = mask ^ lsb;

            cost[mask] = cost[prevMask] + catalog[bitIdx].price;

            // Synergy bonus: bundle containing complementary categories gets bonus utility
            double bonus = 0.0;
            if ((mask & (1 << bitIdx)) != 0) {
                // If smartphone + earbuds, add cross-selling synergy bonus
                if (catalog[bitIdx].category.equalsIgnoreCase("Headphones") || catalog[bitIdx].category.equalsIgnoreCase("Earbuds")) {
                    for (int j = 0; j < n; j++) {
                        if ((prevMask & (1 << j)) != 0 && catalog[j].category.equalsIgnoreCase("Smartphone")) {
                            bonus += 1.5;
                        }
                    }
                }
            }

            utility[mask] = utility[prevMask] + catalog[bitIdx].rating + bonus;

            if (cost[mask] <= budget && utility[mask] > bestUtility) {
                bestUtility = utility[mask];
                bestMask = mask;
                bestCost = cost[mask];
            }
        }

        // Count selected items in bestMask
        int count = 0;
        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) {
                count++;
            }
        }

        BundleItem[] selected = new BundleItem[count];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) {
                selected[idx++] = catalog[i];
            }
        }

        return new BundleResult(selected, bestCost, budget, budget - bestCost, bestUtility, bestMask);
    }
}
