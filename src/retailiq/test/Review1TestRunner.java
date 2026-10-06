package retailiq.test;

import retailiq.co2.*;
import retailiq.co3.*;
import retailiq.structures.*;

/**
 * Automated Verification Suite for Review-1 Evaluation (CO1 - CO3).
 * Tests all required algorithms across typical and edge cases.
 */
public class Review1TestRunner {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        runAllTests();
    }

    public static void runAllTests() {
        System.out.println("=================================================================");
        System.out.println("        RETAILIQ: AUTOMATED VERIFICATION SUITE (REVIEW-1)");
        System.out.println("=================================================================");
        testsPassed = 0;
        testsFailed = 0;

        testStructures();
        testCO2StringAlgorithms();
        testCO3DynamicProgramming();

        System.out.println("-----------------------------------------------------------------");
        System.out.printf("SUMMARY: %d PASSED | %d FAILED | TOTAL: %d\n",
                testsPassed, testsFailed, (testsPassed + testsFailed));
        if (testsFailed == 0) {
            System.out.println("✓ STATUS: ALL REVIEW-1 ALGORITHMIC MODULES FULLY OPERATIONAL!");
        } else {
            System.out.println("✗ STATUS: SOME TESTS FAILED. CHECK LOGS ABOVE.");
        }
        System.out.println("=================================================================\n");
    }

    private static void assertEquals(String testName, Object expected, Object actual) {
        boolean match = (expected == null && actual == null) || (expected != null && expected.equals(actual));
        if (match) {
            System.out.println("  [PASS] " + testName);
            testsPassed++;
        } else {
            System.err.println("  [FAIL] " + testName + " | Expected: " + expected + ", Got: " + actual);
            testsFailed++;
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + testName);
            testsPassed++;
        } else {
            System.err.println("  [FAIL] " + testName + " | Condition was false!");
            testsFailed++;
        }
    }

    private static void testStructures() {
        System.out.println("\n[1/3] Testing Custom Data Structures (No java.util.*)...");

        // MyQueue
        MyQueue<String> q = new MyQueue<>();
        q.enqueue("A");
        q.enqueue("B");
        assertEquals("MyQueue FIFO dequeue", "A", q.dequeue());
        assertEquals("MyQueue peek", "B", q.peek());
        assertEquals("MyQueue size", 1, q.size());

        // MyStack
        MyStack<Integer> s = new MyStack<>();
        s.push(10);
        s.push(20);
        assertEquals("MyStack LIFO pop", 20, s.pop());
        assertEquals("MyStack size", 1, s.size());

        // MyLinkedList
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("First");
        list.add("Second");
        list.addFirst("Zero");
        assertEquals("MyLinkedList index 0", "Zero", list.get(0));
        assertEquals("MyLinkedList size", 3, list.size());
        assertTrue("MyLinkedList contains", list.contains("First"));

        // MyHashTable
        MyHashTable<String, Integer> ht = new MyHashTable<>();
        ht.put("iPhone", 69999);
        ht.put("AirPods", 24999);
        assertEquals("MyHashTable get", 69999, ht.get("iPhone"));
        assertTrue("MyHashTable containsKey", ht.containsKey("AirPods"));
    }

    private static void testCO2StringAlgorithms() {
        System.out.println("\n[2/3] Testing CO2 String Algorithms & Text Indexing...");

        String text = "the quick brown phone jumps over the lazy phone";
        String pattern = "phone";

        // Naive Search
        NaiveSearch.SearchResult rNaive = NaiveSearch.search(text, pattern);
        assertEquals("Naive Search match count", 2, rNaive.matchCount);
        assertEquals("Naive Search first position", 16, rNaive.matchIndices[0]);

        // KMP Search
        KMP.KMPResult rKMP = KMP.search(text, pattern);
        assertEquals("KMP match count", 2, rKMP.matchCount);
        assertEquals("KMP second position", 42, rKMP.matchIndices[1]);

        // Z-Algorithm Search
        ZAlgorithm.ZResult rZ = ZAlgorithm.search(text, pattern);
        assertEquals("Z-Algorithm match count", 2, rZ.matchCount);

        // Rabin-Karp Search
        RabinKarp.RKResult rRK = RabinKarp.search(text, pattern);
        assertEquals("Rabin-Karp match count", 2, rRK.matchCount);

        // Aho-Corasick Multi-Pattern Search
        String reviewText = "Fast delivery and great battery with good sound quality and quick refund.";
        String[] keywords = {"delivery", "battery", "quality", "refund"};
        AhoCorasick ac = new AhoCorasick(keywords);
        AhoCorasick.Match[] acMatches = ac.search(reviewText);
        assertEquals("Aho-Corasick multiple keyword matches", 4, acMatches.length);

        // Suffix Array & SA-IS
        String saText = "banana";
        int[] saLinear = SAIS.buildSuffixArray(saText);
        // "banana" suffixes sorted: a(5), ana(3), anana(1), banana(0), na(4), nana(2)
        assertEquals("SA-IS linear construction SA[0]", 5, saLinear[0]);
        assertEquals("SA-IS linear construction SA[1]", 3, saLinear[1]);

        SuffixArray saObj = new SuffixArray(text);
        int[] saMatches = saObj.searchPattern("phone");
        assertEquals("SuffixArray Binary Search match count", 2, saMatches.length);

        // Kasai LCP
        KasaiLCP.LCPResult lcpRes = KasaiLCP.computeLCP(saText, saLinear);
        assertEquals("Kasai LCP Max Length for 'banana'", 3, lcpRes.maxLcpLength);
        assertEquals("Kasai LCP Longest Repeated Substring", "ana", lcpRes.longestRepeatedSubstring);
    }

    private static void testCO3DynamicProgramming() {
        System.out.println("\n[3/3] Testing CO3 Advanced Dynamic Programming...");

        // Levenshtein
        Levenshtein.EditResult lev = Levenshtein.compute("kitten", "sitting");
        assertEquals("Levenshtein distance kitten->sitting", 3, lev.distance);

        // Damerau-Levenshtein
        DamerauLevenshtein.DamerauResult dam = DamerauLevenshtein.compute("iphoen", "iphone");
        assertEquals("Damerau-Levenshtein adjacent transposition cost", 1, dam.distance);

        // Weighted Edit Distance
        WeightedEditDistance.CostWeights weights = new WeightedEditDistance.CostWeights(1.0, 1.2, 1.5, 0.8);
        WeightedEditDistance.WeightedResult wRes = WeightedEditDistance.compute("phone", "phone", weights);
        assertTrue("Weighted Edit Distance equal strings", wRes.distance == 0.0);

        // Needleman-Wunsch
        NeedlemanWunsch.AlignmentResult nw = NeedlemanWunsch.align("GCATGCG", "GATTACA");
        assertTrue("Needleman-Wunsch aligned length match", nw.alignedSeq1.length() == nw.alignedSeq2.length());

        // Smith-Waterman
        SmithWaterman.LocalAlignmentResult sw = SmithWaterman.align("battery life is good", "good battery life always");
        assertTrue("Smith-Waterman positive local score", sw.maxScore > 0);

        // Matrix Chain Multiplication
        int[] dims = {10, 20, 30, 40};
        MatrixChain.MCMResult mcm = MatrixChain.compute(dims);
        assertEquals("Matrix Chain min scalar multiplications", 18000, mcm.minMultiplications);

        // Optimal BST
        String[] keys = {"A", "B", "C"};
        double[] freq = {0.3, 0.4, 0.3};
        OptimalBST.OBSTResult obst = OptimalBST.compute(keys, freq);
        assertTrue("Optimal BST expected cost positive", obst.minExpectedCost > 0);

        // Bitmask DP Bundle Selection
        BitmaskDP.BundleItem[] catalog = {
            new BitmaskDP.BundleItem(1, "Phone", "Smartphone", 25000, 4.5),
            new BitmaskDP.BundleItem(2, "Headphones", "Audio", 8000, 4.2),
            new BitmaskDP.BundleItem(3, "Case", "Accessories", 1000, 4.0)
        };
        BitmaskDP.BundleResult bundle = BitmaskDP.optimizeBundle(catalog, 30000);
        assertTrue("Bitmask DP bundle cost within budget", bundle.totalCost <= 30000);
        assertTrue("Bitmask DP items selected", bundle.selectedItems.length >= 2);

        // TSP Held-Karp
        double[][] dist = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        TSP.TSPResult tsp = TSP.solve(dist, null);
        assertEquals("TSP Held-Karp optimal tour cost", 80.0, tsp.minCost);

        // Hamiltonian Path
        boolean[][] hamAdj = {
            {false, true, false},
            {true, false, true},
            {false, true, false}
        };
        HamiltonianPath.PathResult ham = HamiltonianPath.findPath(hamAdj, null);
        assertTrue("Hamiltonian path existence", ham.exists);

        // Set Cover
        SetCover.Campaign[] camps = {
            new SetCover.Campaign("C1", 0b011),
            new SetCover.Campaign("C2", 0b110),
            new SetCover.Campaign("C3", 0b100)
        };
        SetCover.SetCoverResult sc = SetCover.solve(3, camps);
        assertEquals("Set Cover minimum campaigns", 2, sc.minCampaignsCount);

        // Tree DP
        TreeDP.TreeNode root = new TreeDP.TreeNode(0, "Root", 100, 2);
        TreeDP.TreeNode c1 = new TreeDP.TreeNode(1, "C1", 60, 0);
        TreeDP.TreeNode c2 = new TreeDP.TreeNode(2, "C2", 70, 0);
        root.addChild(c1);
        root.addChild(c2);
        TreeDP.TreeDPResult treeRes = TreeDP.solve(root, 3);
        // Independent set: either root (100) or both children (60+70=130)
        assertEquals("Tree DP Maximum Independent Set", 130.0, treeRes.maxBonus);

        // Rerooting DP
        int[][] treeEdges = {{0, 1}, {1, 2}};
        RerootingDP.RerootResult reroot = RerootingDP.findOptimalHub(3, treeEdges, null);
        assertEquals("Rerooting DP optimal hub", 1, reroot.bestHubNode);

        // SOS DP
        long[] init = {1, 2, 4, 8}; // 2 bits
        SOSDP.SOSResult sos = SOSDP.compute(2, init, null);
        // For mask 3 (11 in binary), submasks are 00, 01, 10, 11 => 1+2+4+8 = 15
        assertEquals("SOS DP submask sum for mask 3", 15L, sos.subsetSums[3]);
    }
}
