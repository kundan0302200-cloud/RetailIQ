package retailiq.co1;

/**
 * CO1: System Framing, Requirement Mapping, and Algorithm Selection.
 * Provides formal rationale, complexity comparisons, and mapping between retail
 * business problems and core algorithmic paradigms.
 */
public class AlgorithmDesign {

    public static class RequirementMapping {
        public String retailProblem;
        public String inputConstraints;
        public String candidateAlgorithms;
        public String selectedAlgorithm;
        public String timeComplexity;
        public String spaceComplexity;
        public String rationale;
        public String retailFeature;

        public RequirementMapping(String retailProblem, String inputConstraints, String candidateAlgorithms,
                                  String selectedAlgorithm, String timeComplexity, String spaceComplexity,
                                  String rationale, String retailFeature) {
            this.retailProblem = retailProblem;
            this.inputConstraints = inputConstraints;
            this.candidateAlgorithms = candidateAlgorithms;
            this.selectedAlgorithm = selectedAlgorithm;
            this.timeComplexity = timeComplexity;
            this.spaceComplexity = spaceComplexity;
            this.rationale = rationale;
            this.retailFeature = retailFeature;
        }
    }

    public static RequirementMapping[] getSystemMappings() {
        return new RequirementMapping[] {
            new RequirementMapping(
                "Catalog Exact Search",
                "Product descriptions (N <= 100k chars), Search queries (M <= 100 chars)",
                "Naive O(NM), Rabin-Karp O(N+M) avg, KMP O(N+M) worst",
                "Knuth-Morris-Pratt (KMP)",
                "O(N + M)",
                "O(M) for LPS array",
                "Guaranteed linear worst-case matching without backtracking; ideal for live search queries.",
                "Product Search & Description Filter"
            ),
            new RequirementMapping(
                "Review Multi-Keyword Detection",
                "Reviews text (N chars), Dictionary of K retail keywords (total length L)",
                "Repeated KMP O(K*(N+M)), Aho-Corasick O(N + L + Z)",
                "Aho-Corasick Automaton",
                "O(N + L + Z)",
                "O(L * Sigma) for Trie + Failure links",
                "Simultaneously locates all positive/negative sentiment keywords in a single streaming pass.",
                "Review Sentiment & Issue Tagger"
            ),
            new RequirementMapping(
                "Customer Query Typo Correction",
                "Mistyped search words (len <= 30), Catalog vocabulary (V <= 10k terms)",
                "Hamming Distance, Levenshtein, Damerau-Levenshtein, Soundex",
                "Damerau-Levenshtein Distance",
                "O(M * N)",
                "O(M * N) or O(min(M, N))",
                "Supports 80%+ of real typing errors including adjacent transpositions, insertions, and deletions.",
                "Fuzzy Search & Did-You-Mean Auto-correct"
            ),
            new RequirementMapping(
                "Review Sequence Alignment",
                "Pairs of customer complaints or supplier specifications",
                "Greedy Diff, Needleman-Wunsch (Global), Smith-Waterman (Local)",
                "Needleman-Wunsch / Smith-Waterman",
                "O(N * M)",
                "O(N * M)",
                "Optimal global/local sequence alignment identifying plagiarized reviews and spec mismatches.",
                "Review Plagiarism & Spec Difference Detector"
            ),
            new RequirementMapping(
                "Product Bundle Optimization",
                "Catalog of N items (N <= 20) with price, rating, and compatibility constraints under budget B",
                "Greedy Knapsack, Branch & Bound, Bitmask Dynamic Programming",
                "Bitmask DP",
                "O(2^N * N)",
                "O(2^N)",
                "Computes exact globally optimal multi-item bundle combinations satisfying pairwise constraints.",
                "Smart Bundle Recommendation Engine"
            ),
            new RequirementMapping(
                "Warehouse Delivery Route Optimization",
                "Small cluster of N retail outlets/warehouses (N <= 18) with distance matrix",
                "Brute Force Permutations O(N!), Held-Karp Bitmask DP O(2^N * N^2)",
                "Held-Karp TSP DP",
                "O(2^N * N^2)",
                "O(2^N * N)",
                "Exact optimal minimum travel cost for delivery fleet on small intra-city hub clusters.",
                "Fleet Route Planner"
            ),
            new RequirementMapping(
                "Retail Hierarchy Incentive Optimization",
                "Organizational/store hierarchy tree with N nodes",
                "Greedy Tree Traversal, Tree Dynamic Programming",
                "Tree DP (Independent Set / Vertex Cover)",
                "O(N)",
                "O(N) recursion/state stack",
                "Finds optimal non-conflicting department bonuses and role activations in linear time.",
                "Store Organizational Incentive Optimizer"
            ),
            new RequirementMapping(
                "Optimal Logistics Hub Selection",
                "Tree network of supply chains; need best central hub",
                "All-Pairs Shortest Path O(N^3), Tree Rerooting DP O(N)",
                "Rerooting DP",
                "O(N)",
                "O(N)",
                "Calculates total distances for all possible hub choices in two DFS passes without recomputing.",
                "Supply Chain Hub Locator"
            )
        };
    }

    public static void displaySelectionTable() {
        System.out.println("==========================================================================================================");
        System.out.println("                                 RETAILIQ - ALGORITHM SELECTION MATRIX (CO1)");
        System.out.println("==========================================================================================================");
        System.out.printf("%-28s | %-16s | %-12s | %-14s | %-24s\n",
                "Retail Problem", "Selected Alg", "Time", "Space", "RetailIQ Feature");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        RequirementMapping[] mappings = getSystemMappings();
        for (int i = 0; i < mappings.length; i++) {
            RequirementMapping m = mappings[i];
            System.out.printf("%-28s | %-16s | %-12s | %-14s | %-24s\n",
                    m.retailProblem, m.selectedAlgorithm, m.timeComplexity, m.spaceComplexity, m.retailFeature);
        }
        System.out.println("==========================================================================================================");
    }

    public static void displayDetailedJustification(int index) {
        RequirementMapping[] mappings = getSystemMappings();
        if (index < 0 || index >= mappings.length) return;
        RequirementMapping m = mappings[index];
        System.out.println("\n------------------------------------------------------------");
        System.out.println("Problem:             " + m.retailProblem);
        System.out.println("Input Constraints:   " + m.inputConstraints);
        System.out.println("Candidates:          " + m.candidateAlgorithms);
        System.out.println("Selected Algorithm:  " + m.selectedAlgorithm);
        System.out.println("Time Complexity:     " + m.timeComplexity);
        System.out.println("Space Complexity:    " + m.spaceComplexity);
        System.out.println("Theoretical Reason:  " + m.rationale);
        System.out.println("Retail Application:  " + m.retailFeature);
        System.out.println("------------------------------------------------------------");
    }
}
