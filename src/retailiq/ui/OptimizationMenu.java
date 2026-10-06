package retailiq.ui;

import retailiq.co3.*;
import retailiq.data.RetailData;
import retailiq.model.Product;

import java.util.Scanner;

/**
 * Console UI for CO3 Advanced Dynamic Programming & Retail Optimization.
 */
public class OptimizationMenu {

    public static void display(Scanner scanner) {
        RetailData data = RetailData.getInstance();
        boolean back = false;

        while (!back) {
            System.out.println("\n========================================================");
            System.out.println("     CO3: ADVANCED DYNAMIC PROGRAMMING & OPTIMIZATION");
            System.out.println("========================================================");
            System.out.println("1.  Levenshtein Distance (Query Correction)");
            System.out.println("2.  Damerau-Levenshtein (Transposition Handling)");
            System.out.println("3.  Weighted Edit Distance (Configurable Operation Costs)");
            System.out.println("4.  Needleman-Wunsch (Global Attribute Alignment)");
            System.out.println("5.  Smith-Waterman (Local Review Alignment)");
            System.out.println("6.  Matrix Chain Multiplication (Academic DP Demo)");
            System.out.println("7.  Optimal BST (Search Frequency Optimization)");
            System.out.println("8.  Product Bundle Selection (Bitmask DP under Budget)");
            System.out.println("9.  TSP (Held-Karp Delivery Route Optimization)");
            System.out.println("10. Hamiltonian Path (Warehouse Tour Feasibility)");
            System.out.println("11. Set Cover (Marketing Campaign Coverage DP)");
            System.out.println("12. Tree DP (Hierarchy Incentive Maximization)");
            System.out.println("13. Rerooting DP (Optimal Logistics Hub Locator)");
            System.out.println("14. SOS DP (Customer Submask Analytics)");
            System.out.println("0.  Back");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleLevenshtein(scanner, data);
                    break;
                case "2":
                    handleDamerauLevenshtein(scanner, data);
                    break;
                case "3":
                    handleWeightedEdit(scanner);
                    break;
                case "4":
                    handleNeedlemanWunsch(scanner);
                    break;
                case "5":
                    handleSmithWaterman(scanner);
                    break;
                case "6":
                    handleMatrixChain();
                    break;
                case "7":
                    handleOptimalBST();
                    break;
                case "8":
                    handleBundleOptimization(scanner, data);
                    break;
                case "9":
                    handleTSP();
                    break;
                case "10":
                    handleHamiltonianPath();
                    break;
                case "11":
                    handleSetCover();
                    break;
                case "12":
                    handleTreeDP();
                    break;
                case "13":
                    handleRerootingDP();
                    break;
                case "14":
                    handleSOSDP();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number between 0 and 14.");
            }
        }
    }

    private static void handleLevenshtein(Scanner scanner, RetailData data) {
        System.out.print("\nEnter misspelled search query (e.g. 'iphno', 'samusng', 'earbud'): ");
        String query = scanner.nextLine().trim();
        Product[] prods = data.getAllProducts();
        String[] vocab = new String[prods.length];
        for (int i = 0; i < prods.length; i++) vocab[i] = prods[i].getName();

        System.out.println("\n--- LEVENSHTEIN QUERY CORRECTION ---");
        System.out.println("Input: " + query);
        String[] candidates = Levenshtein.getClosestCandidates(query, vocab, 6);
        System.out.println("Closest Catalog Matches:");
        for (int i = 0; i < candidates.length; i++) {
            System.out.println("  -> " + candidates[i]);
        }
        System.out.println("Time Complexity:  O(M * N)");
        System.out.println("Space Complexity: O(M * N)");
    }

    private static void handleDamerauLevenshtein(Scanner scanner, RetailData data) {
        System.out.print("\nEnter misspelled search query with adjacent swap (e.g. 'iphoen', 'samusng'): ");
        String query = scanner.nextLine().trim();
        Product[] prods = data.getAllProducts();
        String[] vocab = new String[prods.length];
        for (int i = 0; i < prods.length; i++) vocab[i] = prods[i].getName();

        System.out.println("\n--- DAMERAU-LEVENSHTEIN (TRANSPOSITION TOLERANCE) ---");
        System.out.println("Input: " + query);
        String[] candidates = DamerauLevenshtein.correctQuery(query, vocab);
        for (int i = 0; i < candidates.length; i++) {
            System.out.println("  -> " + candidates[i]);
        }
        System.out.println("Time Complexity:  O(M * N)");
        System.out.println("Transposition:    Handles adjacent character swaps in cost 1.");
    }

    private static void handleWeightedEdit(Scanner scanner) {
        System.out.print("Enter string 1 (e.g. 'phone'): ");
        String s1 = scanner.nextLine().trim();
        System.out.print("Enter string 2 (e.g. 'pjone'): ");
        String s2 = scanner.nextLine().trim();

        WeightedEditDistance.CostWeights weights = new WeightedEditDistance.CostWeights(1.0, 1.2, 1.5, 0.8);
        WeightedEditDistance.WeightedResult res = WeightedEditDistance.compute(s1, s2, weights);

        System.out.println("\n--- WEIGHTED EDIT DISTANCE ---");
        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.printf("Total Weighted Edit Cost: %.2f\n", res.distance);
        System.out.println("Configured Weights: Insert=1.0, Delete=1.2, Replace=1.5, Transpose=0.8");
        System.out.println("QWERTY Proximity: Adjacent keyboard characters discounted by 40%.");
    }

    private static void handleNeedlemanWunsch(Scanner scanner) {
        System.out.println("\n--- NEEDLEMAN-WUNSCH GLOBAL SEQUENCE ALIGNMENT ---");
        System.out.println("Demonstrating alignment of product SKU attribute codes:");
        String seq1 = "IPHONE-15-PRO-256GB";
        String seq2 = "IPHONE-14-PRO-128GB";
        System.out.println("Sequence 1: " + seq1);
        System.out.println("Sequence 2: " + seq2);

        NeedlemanWunsch.AlignmentResult res = NeedlemanWunsch.align(seq1, seq2);
        System.out.println("\nOptimal Global Alignment:");
        System.out.println("Seq 1: " + res.alignedSeq1);
        System.out.println("Seq 2: " + res.alignedSeq2);
        System.out.println("Alignment Score:  " + res.score);
        System.out.println("Time Complexity:  O(M * N)");
        System.out.println("Space Complexity: O(M * N)");
    }

    private static void handleSmithWaterman(Scanner scanner) {
        System.out.println("\n--- SMITH-WATERMAN LOCAL SEQUENCE ALIGNMENT ---");
        String rev1 = "The battery life is really good and charges quickly with fast delivery";
        String rev2 = "Good battery life but high price and fast delivery";
        System.out.println("Review 1: \"" + rev1 + "\"");
        System.out.println("Review 2: \"" + rev2 + "\"");

        SmithWaterman.LocalAlignmentResult res = SmithWaterman.align(rev1, rev2);
        System.out.println("\nHighest Scoring Local Sub-Region:");
        System.out.println("Matched in Rev 1: \"" + res.subSeq1 + "\"");
        System.out.println("Matched in Rev 2: \"" + res.subSeq2 + "\"");
        System.out.println("Local Score:      " + res.maxScore);
        System.out.println("Time Complexity:  O(M * N)");
    }

    private static void handleMatrixChain() {
        System.out.println("\n--- MATRIX CHAIN MULTIPLICATION (DP) ---");
        System.out.println("Scenario: Batch analytical matrix transforms for revenue projections");
        int[] dims = {10, 30, 5, 60, 20}; // Matrices: 10x30, 30x5, 5x60, 60x20
        System.out.println("Matrix Dimensions: 10x30, 30x5, 5x60, 60x20");

        MatrixChain.MCMResult res = MatrixChain.compute(dims);
        System.out.println("Minimum Scalar Multiplications: " + res.minMultiplications);
        System.out.println("Optimal Parenthesization:       " + res.parenthesization);
        System.out.println("Time Complexity:                O(N^3)");
        System.out.println("Space Complexity:               O(N^2)");
    }

    private static void handleOptimalBST() {
        System.out.println("\n--- OPTIMAL BINARY SEARCH TREE (DP) ---");
        System.out.println("Scenario: Product search frequencies for quick catalog indexing");
        String[] keys = {"AirPods", "Galaxy S24", "iPhone 15", "OnePlus", "Sony WH"};
        double[] freq = {0.20, 0.25, 0.35, 0.10, 0.10};

        OptimalBST.OBSTResult res = OptimalBST.compute(keys, freq);
        System.out.printf("Minimum Expected Search Cost: %.4f comparisons\n", res.minExpectedCost);
        System.out.println("Optimal Root Key:             " + keys[res.root[1][keys.length] - 1]);
        System.out.println("Time Complexity:              O(N^3)");
    }

    private static void handleBundleOptimization(Scanner scanner, RetailData data) {
        System.out.println("\n--- BITMASK DP: PRODUCT BUNDLE OPTIMIZATION ---");
        System.out.print("Enter your shopping budget (INR) [Default: ₹50,000]: ");
        String bStr = scanner.nextLine().trim();
        double budget = 50000.0;
        try {
            if (bStr.length() > 0) budget = Double.parseDouble(bStr);
        } catch (Exception ignored) {}

        BitmaskDP.BundleItem[] items = new BitmaskDP.BundleItem[] {
            new BitmaskDP.BundleItem(101, "Apple iPhone 15", "Smartphone", 25000, 4.8),
            new BitmaskDP.BundleItem(104, "Sony WH Headphones", "Headphones", 8000, 4.7),
            new BitmaskDP.BundleItem(111, "Apple Watch SE", "Smartwatch", 10000, 4.5),
            new BitmaskDP.BundleItem(112, "Fast 65W Charger", "Accessories", 2000, 4.3),
            new BitmaskDP.BundleItem(113, "Protective Case", "Accessories", 1000, 4.1),
            new BitmaskDP.BundleItem(105, "AirPods Pro", "Earbuds", 12000, 4.6),
            new BitmaskDP.BundleItem(108, "Logitech Mouse", "Accessories", 4000, 4.4)
        };

        System.out.println("\nCandidate Items Available for Bundling:");
        for (int i = 0; i < items.length; i++) {
            System.out.printf("  [%d] %-20s | Cat: %-12s | Price: ₹%-8.2f | Rating: %.1f\n",
                    i + 1, items[i].name, items[i].category, items[i].price, items[i].rating);
        }

        BitmaskDP.BundleResult res = BitmaskDP.optimizeBundle(items, budget);

        System.out.println("\n========================================");
        System.out.println("OPTIMAL BUNDLE RESULT (Bitmask DP)");
        System.out.println("========================================");
        System.out.printf("Budget:           ₹%.2f\n", res.budget);
        System.out.printf("Total Spent:      ₹%.2f\n", res.totalCost);
        System.out.printf("Remaining Budget: ₹%.2f\n", res.remainingAmount);
        System.out.printf("Bundle Utility:   %.2f points (includes cross-category synergy)\n", res.totalUtilityScore);
        System.out.println("Bitmask State:    " + Integer.toBinaryString(res.mask));
        System.out.println("\nSelected Products in Bundle:");
        for (int i = 0; i < res.selectedItems.length; i++) {
            System.out.printf("  ✓ %-20s (₹%.2f) [%s]\n",
                    res.selectedItems[i].name, res.selectedItems[i].price, res.selectedItems[i].category);
        }
        System.out.println("Time Complexity:  O(2^N * N)");
        System.out.println("Space Complexity: O(2^N)");
    }

    private static void handleTSP() {
        System.out.println("\n--- TSP: HELD-KARP BITMASK DP DELIVERY TOUR ---");
        String[] hubs = {"Central Hub Mumbai", "Store Bandra", "Store Andheri", "Store Thane", "Airport Hub"};
        double[][] dist = {
            {0, 12, 18, 25, 20},
            {12, 0, 10, 22, 15},
            {18, 10, 0, 14, 8},
            {25, 22, 14, 0, 16},
            {20, 15, 8, 16, 0}
        };

        TSP.TSPResult res = TSP.solve(dist, hubs);
        System.out.printf("Minimum Delivery Route Distance: %.2f km\n", res.minCost);
        System.out.print("Optimal Tour Sequence: ");
        for (int i = 0; i < res.tour.length; i++) {
            System.out.print(res.nodeNames[res.tour[i]] + (i < res.tour.length - 1 ? " -> " : "\n"));
        }
        System.out.println("Time Complexity:  O(2^N * N^2)");
        System.out.println("Space Complexity: O(2^N * N)");
    }

    private static void handleHamiltonianPath() {
        System.out.println("\n--- HAMILTONIAN PATH: WAREHOUSE AISLE TOUR ---");
        String[] aisles = {"Aisle-A (Mobiles)", "Aisle-B (Audio)", "Aisle-C (Laptops)", "Aisle-D (Accessories)"};
        boolean[][] adj = {
            {false, true, true, false},
            {true, false, true, true},
            {true, true, false, true},
            {false, true, true, false}
        };

        HamiltonianPath.PathResult res = HamiltonianPath.findPath(adj, aisles);
        if (res.exists) {
            System.out.println("✓ Valid single-pass warehouse traversal found!");
            System.out.print("Tour: ");
            for (int i = 0; i < res.path.length; i++) {
                System.out.print(res.nodeNames[res.path[i]] + (i < res.path.length - 1 ? " -> " : "\n"));
            }
        } else {
            System.out.println("No Hamiltonian path exists in this warehouse layout.");
        }
        System.out.println("Time Complexity: O(2^N * N^2)");
    }

    private static void handleSetCover() {
        System.out.println("\n--- SET COVER: MARKETING CAMPAIGN SEGMENT COVERAGE ---");
        int universeSize = 5; // 5 Customer Segments: [Youth, Techies, Gamers, Professionals, Budget]
        SetCover.Campaign[] campaigns = new SetCover.Campaign[] {
            new SetCover.Campaign("Social Media Youth Blitz", 0b00011),     // Youth + Techies
            new SetCover.Campaign("Tech & Gaming Sponsorship", 0b00110),   // Techies + Gamers
            new SetCover.Campaign("Corporate Executive Ads", 0b01000),     // Professionals
            new SetCover.Campaign("Festive Value Clearance", 0b10001),     // Budget + Youth
            new SetCover.Campaign("All-Rounder Promo", 0b11111)            // All segments
        };

        SetCover.SetCoverResult res = SetCover.solve(universeSize, campaigns);
        System.out.println("Target Segments to Cover: 5");
        System.out.println("Minimum Campaigns Required: " + res.minCampaignsCount);
        for (int i = 0; i < res.selectedCampaignNames.length; i++) {
            System.out.println("  ✓ " + res.selectedCampaignNames[i]);
        }
        System.out.println("Time Complexity: O(M * 2^U)");
    }

    private static void handleTreeDP() {
        System.out.println("\n--- TREE DP: HIERARCHY INCENTIVE OPTIMIZER ---");
        System.out.println("Problem: Maximize store department incentives without activating adjacent supervisor-subordinate levels.");

        TreeDP.TreeNode ceo = new TreeDP.TreeNode(0, "VP Retail (Headquarters)", 100000, 2);
        TreeDP.TreeNode mgr1 = new TreeDP.TreeNode(1, "Regional Mgr North", 60000, 2);
        TreeDP.TreeNode mgr2 = new TreeDP.TreeNode(2, "Regional Mgr South", 55000, 2);
        TreeDP.TreeNode lead1 = new TreeDP.TreeNode(3, "Store Delhi Lead", 40000, 0);
        TreeDP.TreeNode lead2 = new TreeDP.TreeNode(4, "Store Jaipur Lead", 35000, 0);
        TreeDP.TreeNode lead3 = new TreeDP.TreeNode(5, "Store Bangalore Lead", 45000, 0);
        TreeDP.TreeNode lead4 = new TreeDP.TreeNode(6, "Store Chennai Lead", 38000, 0);

        ceo.addChild(mgr1);
        ceo.addChild(mgr2);
        mgr1.addChild(lead1);
        mgr1.addChild(lead2);
        mgr2.addChild(lead3);
        mgr2.addChild(lead4);

        TreeDP.TreeDPResult res = TreeDP.solve(ceo, 7);
        System.out.printf("Maximum Non-Conflicting Bonus: ₹%.2f\n", res.maxBonus);
        System.out.println("Activated Roles in Hierarchy:");
        String[] names = {"VP Retail", "Regional Mgr North", "Regional Mgr South",
                          "Store Delhi Lead", "Store Jaipur Lead", "Store Bangalore Lead", "Store Chennai Lead"};
        for (int i = 0; i < 7; i++) {
            if (res.included[i]) {
                System.out.println("  ✓ " + names[i]);
            }
        }
        System.out.println("Time Complexity:  O(N) single DFS post-order pass");
        System.out.println("Space Complexity: O(N)");
    }

    private static void handleRerootingDP() {
        System.out.println("\n--- REROOTING DP: OPTIMAL LOGISTICS HUB LOCATOR ---");
        System.out.println("Calculates total distance to all stores from every possible hub in 2 DFS passes!");

        int numStores = 5;
        String[] names = {"Hub Mumbai", "Store Pune", "Store Nashik", "Store Surat", "Store Ahmedabad"};
        int[][] edges = {
            {0, 1}, // Mumbai - Pune
            {0, 2}, // Mumbai - Nashik
            {0, 3}, // Mumbai - Surat
            {3, 4}  // Surat - Ahmedabad
        };

        RerootingDP.RerootResult res = RerootingDP.findOptimalHub(numStores, edges, names);
        System.out.println("Total Distances if chosen as Central Hub:");
        for (int i = 0; i < numStores; i++) {
            System.out.printf("  %s: %d total distance units\n", names[i], res.totalDistances[i]);
        }
        System.out.println("Optimal Central Logistics Hub: " + res.bestHubName + " (Total distance: " + res.minTotalDistance + ")");
        System.out.println("Time Complexity: O(N) linear time");
    }

    private static void handleSOSDP() {
        System.out.println("\n--- SOS DP: CUSTOMER SUBMASK ANALYTICS ---");
        System.out.println("Aggregates customer metric totals across all feature subsets in O(N * 2^N).");

        int numFeatures = 3; // Features: [LoyaltyMember, AppUser, PrimeSubscriber]
        String[] labels = {"LoyaltyMember", "AppUser", "PrimeSubscriber"};
        long[] initialSales = {
            100, // 000: Base shopper
            150, // 001: Prime
            200, // 010: App User
            300, // 011: App + Prime
            250, // 100: Loyalty
            350, // 101: Loyalty + Prime
            400, // 110: Loyalty + App
            600  // 111: All three
        };

        SOSDP.SOSResult res = SOSDP.compute(numFeatures, initialSales, labels);
        System.out.println("Mask | Profile Features                   | Initial Spend | Aggregated Submask Sum");
        System.out.println("---------------------------------------------------------------------------------");
        for (int mask = 0; mask < (1 << numFeatures); mask++) {
            StringBuilder sb = new StringBuilder();
            for (int bit = 0; bit < numFeatures; bit++) {
                if ((mask & (1 << bit)) != 0) {
                    sb.append(labels[bit]).append(" ");
                }
            }
            if (sb.length() == 0) sb.append("(Standard)");
            System.out.printf("%-4s | %-32s | ₹%-12d | ₹%-12d\n",
                    Integer.toBinaryString(mask), sb.toString().trim(), res.originalValues[mask], res.subsetSums[mask]);
        }
        System.out.println("Time Complexity: O(N * 2^N)");
        System.out.println("Improvement:     Faster than naive submask iteration O(3^N).");
    }
}
