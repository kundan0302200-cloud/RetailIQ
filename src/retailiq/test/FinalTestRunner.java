package retailiq.test;

import retailiq.co2.*;
import retailiq.co3.*;
import retailiq.co4.*;
import retailiq.co5.*;
import retailiq.co6.*;
import retailiq.structures.*;

/**
 * Master Automated Verification Suite for Final Project Evaluation (CO1 - CO6).
 * Evaluates all 6 Course Outcomes against rigorous algorithmic assertions.
 */
public class FinalTestRunner {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        runAllTests();
    }

    public static void runAllTests() {
        System.out.println("=================================================================");
        System.out.println("      RETAILIQ: MASTER AUTOMATED VERIFICATION SUITE (CO1-CO6)");
        System.out.println("=================================================================");
        testsPassed = 0;
        testsFailed = 0;

        testStructures();
        testCO2StringAlgorithms();
        testCO3DynamicProgramming();
        testCO4NetworkFlow();
        testCO5NPAndApproximation();
        testCO6RandomizedAndParallel();

        System.out.println("-----------------------------------------------------------------");
        System.out.printf("FINAL SUMMARY: %d PASSED | %d FAILED | TOTAL: %d\n",
                testsPassed, testsFailed, (testsPassed + testsFailed));
        if (testsFailed == 0) {
            System.out.println("✓ STATUS: ALL DSA-3 MODULES (CO1-CO6) FULLY OPERATIONAL & VERIFIED!");
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

    private static void assertEquals(String testName, double expected, double actual, double epsilon) {
        boolean match = Math.abs(expected - actual) <= epsilon;
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
        System.out.println("\n[1/6] Custom Data Structures (Zero java.util Collections)...");
        MyQueue<String> q = new MyQueue<>();
        q.enqueue("A");
        q.enqueue("B");
        assertEquals("MyQueue FIFO dequeue", "A", q.dequeue());
        assertEquals("MyQueue size", 1, q.size());

        MyStack<Integer> st = new MyStack<>();
        st.push(10);
        st.push(20);
        assertEquals("MyStack LIFO pop", 20, (int) st.pop());

        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("Electronics");
        list.add("Apparel");
        assertTrue("MyLinkedList contains", list.contains("Electronics"));
        assertEquals("MyLinkedList size", 2, list.size());

        MyHashTable<String, Integer> ht = new MyHashTable<>(10);
        ht.put("SKU-100", 45);
        assertEquals("MyHashTable lookup", 45, (int) ht.get("SKU-100"));
    }

    private static void testCO2StringAlgorithms() {
        System.out.println("\n[2/6] CO2: String Algorithms & Text Indexing...");
        String text = "ABABDABACDABABCABAB";
        String pat = "ABABCABAB";

        assertEquals("KMP match count", 1, KMP.search(text, pat).matchCount);
        assertEquals("Z-Algorithm match count", 1, ZAlgorithm.search(text, pat).matchCount);
        assertEquals("Rabin-Karp match count", 1, RabinKarp.search(text, pat).matchCount);

        String[] dict = {"apple", "app", "ple"};
        AhoCorasick ac = new AhoCorasick(dict);
        assertTrue("Aho-Corasick multi-match count", ac.search("apple").length >= 3);

        String s = "banana";
        int[] sa = SAIS.buildSuffixArray(s);
        assertEquals("SA-IS linear construction SA[0]", 5, sa[0]); // "a"

        KasaiLCP.LCPResult lcp = KasaiLCP.computeLCP(s, sa);
        assertEquals("Kasai LCP Max repeated length", 3, lcp.maxLcpLength); // "ana"
    }

    private static void testCO3DynamicProgramming() {
        System.out.println("\n[3/6] CO3: Advanced Dynamic Programming...");
        Levenshtein.EditResult lev = Levenshtein.compute("kitten", "sitting");
        assertEquals("Levenshtein distance (kitten->sitting)", 3, lev.distance);

        DamerauLevenshtein.DamerauResult dam = DamerauLevenshtein.compute("ca", "ac");
        assertEquals("Damerau-Levenshtein transposition (ca->ac)", 1, dam.distance);

        int[] dims = {10, 20, 30, 40};
        MatrixChain.MCMResult mcm = MatrixChain.compute(dims);
        assertEquals("Matrix Chain Multiplication min ops", 18000, mcm.minMultiplications);

        double[][] dist = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        TSP.TSPResult tsp = TSP.solve(dist, null);
        assertEquals("Held-Karp TSP optimal tour cost", 80.0, tsp.minCost, 0.001);

        long[] init = {1, 2, 4, 8};
        SOSDP.SOSResult sos = SOSDP.compute(2, init, null);
        assertEquals("SOS DP submask sum for mask 3", 15L, (long) sos.subsetSums[3]);
    }

    private static void testCO4NetworkFlow() {
        System.out.println("\n[4/6] CO4: Network Flow & Bipartite Matching...");

        // Standard 4-node flow network: 0->1 (10), 0->2 (5), 1->2 (15), 1->3 (10), 2->3 (10)
        FlowGraph g1 = new FlowGraph(4);
        g1.addEdge(0, 1, 10);
        g1.addEdge(0, 2, 5);
        g1.addEdge(1, 2, 15);
        g1.addEdge(1, 3, 10);
        g1.addEdge(2, 3, 10);
        double dinicFlow = Dinic.maxFlow(g1, 0, 3);
        assertEquals("Dinic Max Flow calculation", 15.0, dinicFlow, 0.001);

        FlowGraph g2 = new FlowGraph(4);
        g2.addEdge(0, 1, 10);
        g2.addEdge(0, 2, 5);
        g2.addEdge(1, 2, 15);
        g2.addEdge(1, 3, 10);
        g2.addEdge(2, 3, 10);
        double ekFlow = EdmondsKarp.maxFlow(g2, 0, 3);
        assertEquals("Edmonds-Karp Max Flow equivalence", 15.0, ekFlow, 0.001);

        FlowGraph g3 = new FlowGraph(4);
        g3.addEdge(0, 1, 10);
        g3.addEdge(0, 2, 5);
        g3.addEdge(1, 2, 15);
        g3.addEdge(1, 3, 10);
        g3.addEdge(2, 3, 10);
        double ffFlow = FordFulkerson.maxFlow(g3, 0, 3);
        assertEquals("Ford-Fulkerson Max Flow equivalence", 15.0, ffFlow, 0.001);

        // Max-Flow Min-Cut Theorem Verification:
        // In the residual graph after max flow, s=0 is reachable to {0}, while {1,2,3} are across the cut.
        boolean[] reachable = MinCut.findReachableSet(g1, 0);
        assertTrue("Min-Cut Source reachable", reachable[0]);
        assertTrue("Min-Cut Sink unreachable in residual", !reachable[3]);

        // Bipartite Matching: 3 workers and 3 warehouse bays
        boolean[][] bp = {
            {true, true, false},
            {false, true, false},
            {false, false, true}
        };
        int matching = BipartiteMatching.maxBipartiteMatching(bp, 3, 3);
        assertEquals("Max Bipartite Matching size", 3, matching);
    }

    private static void testCO5NPAndApproximation() {
        System.out.println("\n[5/6] CO5: NP-Completeness & Approximations...");

        // 3-SAT Satisfiable Formula: (x1 v x2 v -x3) ^ (-x1 v x2 v x3)
        int[][] satClauses = {
            {1, 2, -3},
            {-1, 2, 3}
        };
        assertTrue("3-SAT Satisfiable formula detection", ThreeSAT.solve(3, satClauses));

        // 3-SAT Unsatisfiable Formula: All 8 combinations of 3 variables
        int[][] unsatClauses = {
            {1, 2, 3}, {1, 2, -3}, {1, -2, 3}, {1, -2, -3},
            {-1, 2, 3}, {-1, 2, -3}, {-1, -2, 3}, {-1, -2, -3}
        };
        assertTrue("3-SAT Unsatisfiable formula detection", !ThreeSAT.solve(3, unsatClauses));

        // Graph with a triangle (Clique of size 3) between 0, 1, 2
        boolean[][] triangle = {
            {false, true, true, false},
            {true, false, true, false},
            {true, true, false, false},
            {false, false, false, false}
        };
        assertTrue("Clique of size 3 detection", Clique.hasCliqueOfSize(triangle, 3));
        assertTrue("Clique of size 4 absence", !Clique.hasCliqueOfSize(triangle, 4));

        // Independent Set & Vertex Cover Reduction Equivalence:
        // In a graph G=(V,E), S is VC <=> V \ S is IS. For triangle graph (3 vertices), VC=2 and IS=1.
        boolean[][] tri3 = {
            {false, true, true},
            {true, false, true},
            {true, true, false}
        };
        assertTrue("Vertex Cover size 2 in Triangle", VertexCover.hasVertexCover(tri3, 2));
        assertTrue("Independent Set size 1 in Triangle", IndependentSet.hasIndependentSet(tri3, 1));

        // Vertex Cover 2-Approximation Guarantee Test: |C_approx| <= 2 * |C_opt|
        boolean[][] star = {
            {false, true, true, true, true},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, false, false, false, false}
        };
        VertexCoverApprox.ApproxResult approx = VertexCoverApprox.approximateCover(star);
        assertTrue("Vertex Cover Approximation valid cover size", approx.coverSize >= 1);
        assertTrue("Vertex Cover 2-Approximation bound check (|C| <= 2 * |C*|)", approx.coverSize <= 2 * 1);
    }

    private static void testCO6RandomizedAndParallel() {
        System.out.println("\n[6/6] CO6: Randomized & Parallel Algorithms...");

        // Randomized QuickSort
        double[] nums = {45.0, 12.0, 89.0, 3.0, 24.0, 78.0, 6.0, 91.0, 53.0, 19.0};
        RandomizedQuickSort.sort(nums);
        boolean sorted = true;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) sorted = false;
        }
        assertTrue("Randomized QuickSort ordering verification", sorted);

        // Universal Hashing slot bound & determinism
        UniversalHashing uh = new UniversalHashing(50);
        int h1 = uh.getHash(999001L);
        int h2 = uh.getHash(999001L);
        assertEquals("Universal Hashing determinism", h1, h2);
        assertTrue("Universal Hashing bounds [0, m)", h1 >= 0 && h1 < 50);

        // Reservoir Sampling: Sample size k from stream of N
        int[] stream = new int[50];
        for (int i = 0; i < 50; i++) stream[i] = i * 10;
        int[] resSample = ReservoirSampling.sample(stream, 5);
        assertEquals("Reservoir Sampling sample size", 5, resSample.length);

        // Blelloch Work-Efficient Parallel Prefix Scan
        double[] scanIn = {1.0, 2.0, 3.0, 4.0};
        double[] scanOut = BlellochScan.exclusiveScan(scanIn);
        assertEquals("Blelloch Scan exclusive index 0", 0.0, scanOut[0], 0.001);
        assertEquals("Blelloch Scan exclusive index 1", 1.0, scanOut[1], 0.001);
        assertEquals("Blelloch Scan exclusive index 2", 3.0, scanOut[2], 0.001);
        assertEquals("Blelloch Scan exclusive index 3", 6.0, scanOut[3], 0.001);

        // Multi-threaded ForkJoin Parallel Reduce
        double[] reduceIn = {10.5, 20.5, 30.0, 40.0, 50.0};
        double parSum = ParallelReduce.reduceSum(reduceIn);
        assertEquals("ForkJoin Parallel Reduce sum correctness", 151.0, parSum, 0.001);

        // Multi-threaded ForkJoin Parallel Sort
        double[] sortIn = {9.5, 1.2, 5.5, 3.1, 7.8, 0.4};
        ParallelSort.sort(sortIn);
        boolean parSorted = true;
        for (int i = 1; i < sortIn.length; i++) {
            if (sortIn[i] < sortIn[i - 1]) parSorted = false;
        }
        assertTrue("ForkJoin Parallel Sort ordering correctness", parSorted);

        // Miller-Rabin Primality Test
        assertTrue("Miller-Rabin Prime (1000000007)", MillerRabin.isPrime(1000000007L, 10));
        assertTrue("Miller-Rabin Prime (17)", MillerRabin.isPrime(17, 10));
        assertTrue("Miller-Rabin Composite (561 Carmichael number)", !MillerRabin.isPrime(561, 10));

        // Brent's Theorem Theoretical Bounds
        BrentsTheorem.BoundAnalysis bounds = BrentsTheorem.computeBounds(1000, 20, 4);
        assertEquals("Brent's Theorem time bound Tp calculation", 265.0, bounds.theoreticalTimeBound, 0.001);
    }
}
