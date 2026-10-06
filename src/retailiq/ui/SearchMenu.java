package retailiq.ui;

import retailiq.co2.*;
import retailiq.data.RetailData;
import retailiq.model.Product;

import java.util.Scanner;

/**
 * Console UI for CO2 String Algorithms & Product Search.
 */
public class SearchMenu {

    public static void display(Scanner scanner) {
        RetailData data = RetailData.getInstance();
        boolean back = false;

        while (!back) {
            System.out.println("\n==========================================");
            System.out.println("     PRODUCT SEARCH & STRING ALGORITHMS");
            System.out.println("==========================================");
            System.out.println("1. Naive Search");
            System.out.println("2. KMP (Knuth-Morris-Pratt)");
            System.out.println("3. Z Algorithm");
            System.out.println("4. Rabin-Karp (Double Rolling Hash)");
            System.out.println("5. Aho-Corasick Multi-Pattern Search");
            System.out.println("6. Suffix Array Substring Search");
            System.out.println("7. Compare All Search Algorithms");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleNaive(scanner, data);
                    break;
                case "2":
                    handleKMP(scanner, data);
                    break;
                case "3":
                    handleZ(scanner, data);
                    break;
                case "4":
                    handleRabinKarp(scanner, data);
                    break;
                case "5":
                    handleAhoCorasick(scanner, data);
                    break;
                case "6":
                    handleSuffixArray(scanner, data);
                    break;
                case "7":
                    handleCompareAll(scanner, data);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number between 0 and 7.");
            }
        }
    }

    private static String getAggregatedCatalogText(RetailData data) {
        Product[] products = data.getAllProducts();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < products.length; i++) {
            sb.append(products[i].getName()).append(" - ").append(products[i].getDescription()).append(" | ");
        }
        return sb.toString();
    }

    private static void handleNaive(Scanner scanner, RetailData data) {
        System.out.print("\nEnter search pattern: ");
        String pattern = scanner.nextLine().trim();
        String text = getAggregatedCatalogText(data);

        NaiveSearch.SearchResult res = NaiveSearch.search(text.toLowerCase(), pattern.toLowerCase());

        System.out.println("\n========================================");
        System.out.println("NAIVE SEARCH");
        System.out.println("========================================");
        System.out.println("Pattern:          \"" + pattern + "\"");
        System.out.println("Catalog Length:   " + text.length() + " characters");
        System.out.println("Matches found:    " + res.matchCount);
        System.out.println("Comparisons:      " + res.comparisons);
        System.out.println("Execution Time:   " + res.executionTimeNano + " ns (" + (res.executionTimeNano / 1_000_000.0) + " ms)");
        System.out.println("Time Complexity:  O(n * m) worst case");
        System.out.println("Space Complexity: O(1) auxiliary");
        printMatchContexts(text, res.matchIndices, pattern.length());
    }

    private static void handleKMP(Scanner scanner, RetailData data) {
        System.out.print("\nEnter search pattern: ");
        String pattern = scanner.nextLine().trim();
        String text = getAggregatedCatalogText(data);

        KMP.KMPResult res = KMP.search(text.toLowerCase(), pattern.toLowerCase());

        System.out.println("\n========================================");
        System.out.println("KMP SEARCH (Knuth-Morris-Pratt)");
        System.out.println("========================================");
        System.out.println("Pattern:          \"" + pattern + "\"");
        System.out.println("Catalog Length:   " + text.length() + " characters");
        System.out.println("Matches found:    " + res.matchCount);
        System.out.println("Comparisons:      " + res.comparisons);
        System.out.println("Execution Time:   " + res.executionTimeNano + " ns (" + (res.executionTimeNano / 1_000_000.0) + " ms)");
        System.out.println("Time Complexity:  O(n + m) guaranteed linear");
        System.out.println("Space Complexity: O(m) for LPS table");

        System.out.print("LPS Array:        [");
        for (int i = 0; i < res.lps.length; i++) {
            System.out.print(res.lps[i] + (i < res.lps.length - 1 ? ", " : ""));
        }
        System.out.println("]");
        printMatchContexts(text, res.matchIndices, pattern.length());
    }

    private static void handleZ(Scanner scanner, RetailData data) {
        System.out.print("\nEnter search pattern: ");
        String pattern = scanner.nextLine().trim();
        String text = getAggregatedCatalogText(data);

        ZAlgorithm.ZResult res = ZAlgorithm.search(text.toLowerCase(), pattern.toLowerCase());

        System.out.println("\n========================================");
        System.out.println("Z-ALGORITHM SEARCH");
        System.out.println("========================================");
        System.out.println("Pattern:          \"" + pattern + "\"");
        System.out.println("Matches found:    " + res.matchCount);
        System.out.println("Execution Time:   " + res.executionTimeNano + " ns");
        System.out.println("Time Complexity:  O(n + m)");
        System.out.println("Space Complexity: O(n + m) for Z-array");
        printMatchContexts(text, res.matchIndices, pattern.length());
    }

    private static void handleRabinKarp(Scanner scanner, RetailData data) {
        System.out.print("\nEnter search pattern: ");
        String pattern = scanner.nextLine().trim();
        String text = getAggregatedCatalogText(data);

        RabinKarp.RKResult res = RabinKarp.search(text.toLowerCase(), pattern.toLowerCase());

        System.out.println("\n========================================");
        System.out.println("RABIN-KARP SEARCH (Double Rolling Hash)");
        System.out.println("========================================");
        System.out.println("Pattern:          \"" + pattern + "\"");
        System.out.println("Matches found:    " + res.matchCount);
        System.out.println("Spurious Hits:    " + res.spuriousHits + " (Collision count)");
        System.out.println("Execution Time:   " + res.executionTimeNano + " ns");
        System.out.println("Time Complexity:  O(n + m) average, O(n*m) worst");
        System.out.println("Space Complexity: O(1) auxiliary");
        printMatchContexts(text, res.matchIndices, pattern.length());
    }

    private static void handleAhoCorasick(Scanner scanner, RetailData data) {
        System.out.println("\nRetail Keyword Dictionary:");
        String[] keywords = {"phone", "sound", "battery", "laptop", "fast", "wireless", "display", "apple"};
        for (int i = 0; i < keywords.length; i++) {
            System.out.print("[" + keywords[i] + "] ");
        }
        System.out.println();

        System.out.print("Use default retail keywords? (Y/n): ");
        String ans = scanner.nextLine().trim();
        if (ans.equalsIgnoreCase("n")) {
            System.out.print("Enter comma-separated keywords: ");
            String[] custom = scanner.nextLine().trim().split(",");
            for (int i = 0; i < custom.length; i++) custom[i] = custom[i].trim();
            keywords = custom;
        }

        AhoCorasick ac = new AhoCorasick(keywords);
        String text = getAggregatedCatalogText(data);
        long start = System.nanoTime();
        AhoCorasick.Match[] matches = ac.search(text);
        long elapsed = System.nanoTime() - start;

        System.out.println("\n========================================");
        System.out.println("AHO-CORASICK MULTI-PATTERN SEARCH");
        System.out.println("========================================");
        System.out.println("Total Matches:    " + matches.length);
        System.out.println("Execution Time:   " + elapsed + " ns (" + (elapsed / 1_000_000.0) + " ms)");
        System.out.println("Time Complexity:  O(N + sum(M_k) + Z)");
        System.out.println("Space Complexity: O(sum(M_k) * Alphabet_Size)");
        System.out.println("\nSample Keyword Matches:");
        int show = Math.min(10, matches.length);
        for (int i = 0; i < show; i++) {
            System.out.println("  " + (i + 1) + ". " + matches[i]);
        }
        if (matches.length > 10) {
            System.out.println("  ... and " + (matches.length - 10) + " more matches.");
        }
    }

    private static void handleSuffixArray(Scanner scanner, RetailData data) {
        System.out.print("\nEnter pattern for Suffix Array index search: ");
        String pattern = scanner.nextLine().trim();
        String text = getAggregatedCatalogText(data);

        long start = System.nanoTime();
        SuffixArray sa = new SuffixArray(text.toLowerCase());
        int[] matches = sa.searchPattern(pattern.toLowerCase());
        long elapsed = System.nanoTime() - start;

        System.out.println("\n========================================");
        System.out.println("SUFFIX ARRAY BINARY SEARCH");
        System.out.println("========================================");
        System.out.println("Pattern:          \"" + pattern + "\"");
        System.out.println("Matches found:    " + matches.length);
        System.out.println("Execution Time:   " + elapsed + " ns (includes SA build)");
        System.out.println("Search Complexity:O(m log n)");
        System.out.println("Build Complexity: O(n log^2 n)");
        printMatchContexts(text, matches, pattern.length());
    }

    private static void handleCompareAll(Scanner scanner, RetailData data) {
        System.out.print("\nEnter search pattern to benchmark: ");
        String pattern = scanner.nextLine().trim();
        if (pattern.length() == 0) pattern = "phone";

        String text = getAggregatedCatalogText(data).toLowerCase();
        String pat = pattern.toLowerCase();

        System.out.println("\n--------- SEARCH COMPARISON ---------");
        System.out.println("Text:    Aggregated product catalog (" + text.length() + " chars)");
        System.out.println("Pattern: \"" + pattern + "\"");
        System.out.println();

        // Warm up JVM
        for (int i = 0; i < 5; i++) {
            NaiveSearch.search(text, pat);
            KMP.search(text, pat);
            RabinKarp.search(text, pat);
            ZAlgorithm.search(text, pat);
        }

        // Run Naive
        NaiveSearch.SearchResult rNaive = NaiveSearch.search(text, pat);
        // Run KMP
        KMP.KMPResult rKMP = KMP.search(text, pat);
        // Run Rabin-Karp
        RabinKarp.RKResult rRK = RabinKarp.search(text, pat);
        // Run Z-Algorithm
        ZAlgorithm.ZResult rZ = ZAlgorithm.search(text, pat);

        System.out.printf("%-18s | %-12s | %-16s | %-16s\n", "Algorithm", "Matches", "Time (ns)", "Worst-Case Time");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-18s | %-12d | %-16d | %-16s\n", "Naive", rNaive.matchCount, rNaive.executionTimeNano, "O(n * m)");
        System.out.printf("%-18s | %-12d | %-16d | %-16s\n", "KMP", rKMP.matchCount, rKMP.executionTimeNano, "O(n + m)");
        System.out.printf("%-18s | %-12d | %-16d | %-16s\n", "Rabin-Karp", rRK.matchCount, rRK.executionTimeNano, "O(n * m)");
        System.out.printf("%-18s | %-12d | %-16d | %-16s\n", "Z-Algorithm", rZ.matchCount, rZ.executionTimeNano, "O(n + m)");
        System.out.println("-------------------------------------------------------------------------");

        boolean allMatch = (rNaive.matchCount == rKMP.matchCount) &&
                           (rKMP.matchCount == rRK.matchCount) &&
                           (rRK.matchCount == rZ.matchCount);

        System.out.println("Correctness Verification:");
        if (allMatch) {
            System.out.println("✓ SUCCESS: All 4 algorithms produced identical match counts (" + rKMP.matchCount + " matches).");
        } else {
            System.out.println("✗ WARNING: Discrepancy detected in match counts!");
        }
    }

    private static void printMatchContexts(String text, int[] indices, int len) {
        if (indices.length == 0) return;
        System.out.println("First few occurrences in text:");
        int show = Math.min(3, indices.length);
        for (int i = 0; i < show; i++) {
            int start = Math.max(0, indices[i] - 15);
            int end = Math.min(text.length(), indices[i] + len + 15);
            System.out.println("  [" + indices[i] + "]: \"..." + text.substring(start, end).replace("\n", " ") + "...\"");
        }
    }
}
