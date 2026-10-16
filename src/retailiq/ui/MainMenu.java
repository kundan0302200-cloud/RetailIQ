package retailiq.ui;

import retailiq.co1.AlgorithmDesign;
import retailiq.co2.*;
import retailiq.co3.*;
import retailiq.co6.*;
import retailiq.data.RetailData;
import retailiq.model.Review;
import retailiq.test.FinalTestRunner;
import retailiq.test.Review1TestRunner;

import java.util.Scanner;

/**
 * Main Console Menu orchestrating the RetailIQ Analytics Platform.
 */
public class MainMenu {

    private Scanner scanner;
    private RetailData data;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.data = RetailData.getInstance();
    }

    public void run() {
        boolean exit = false;

        while (!exit) {
            int choice = ArrowKeyMenu.select("RETAILIQ  /  Intelligent Retail Analytics", "Home",
                    new String[] { "Products", "Customers", "Orders and sales", "Product search",
                            "Review analysis", "Query correction", "Product bundles", "Inventory",
                            "Product constraints", "Sales analytics", "Algorithm demos and tests", "Quit" },
                    new String[] { "Browse and manage items", "View customer profiles", "Manage retail activity",
                            "Search the catalog", "Search and compare review text", "Find corrected product names",
                            "Optimize product bundles", "View warehouse stock", "Explore product constraints",
                            "Explore sales data", "Run demonstrations and verification", "Exit RetailIQ" },
                    "↑/↓ Move   Enter Select   Esc Back   Q Quit");
            if (choice == -1) break;
            if (choice == -2) {
                if (!scanner.hasNextLine()) break;
                String typedChoice = scanner.nextLine().trim();
                try { choice = Integer.parseInt(typedChoice) - 1; }
                catch (NumberFormatException e) { continue; }
            }
            switch (choice) {
                case 0:
                    ProductMenu.display(scanner);
                    break;
                case 1:
                    CustomerMenu.display(scanner);
                    break;
                case 2:
                    AnalyticsMenu.display(scanner);
                    break;
                case 3:
                    SearchMenu.display(scanner);
                    break;
                case 4:
                    handleReviewAnalysis();
                    break;
                case 5:
                    handleQueryCorrection();
                    break;
                case 6:
                    OptimizationMenu.display(scanner);
                    break;
                case 7:
                    InventoryMenu.display(scanner);
                    break;
                case 8:
                    AnalyticsMenu.display(scanner);
                    break;
                case 9:
                    AnalyticsMenu.display(scanner);
                    break;
                case 10:
                    handleAlgorithmDemonstrations();
                    break;
                case 11:
                    exit = true;
                    System.out.println("\nThank you for using RetailIQ Intelligent Retail Analytics. Goodbye!");
                    break;
                default:
                    break;
            }
        }
    }

    private void handleReviewAnalysis() {
        boolean back = false;
        while (!back) {
            System.out.println("\n---------- REVIEW ANALYSIS ----------");
            System.out.println("1. Add Review");
            System.out.println("2. View Reviews");
            System.out.println("3. Search Keyword (KMP Algorithm)");
            System.out.println("4. Detect Multiple Keywords (Aho-Corasick)");
            System.out.println("5. Find Repeated Phrases (Z-Algorithm & Kasai LCP)");
            System.out.println("6. Compare Reviews (Sequence Alignment)");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String ch = scanner.nextLine().trim();
            switch (ch) {
                case "1":
                    try {
                        System.out.print("Enter Product ID: ");
                        int pid = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Enter Review Text: ");
                        String txt = scanner.nextLine().trim();
                        System.out.print("Enter Rating (1-5): ");
                        int rating = Integer.parseInt(scanner.nextLine().trim());
                        data.addReview(new Review(pid, txt, rating));
                        System.out.println("Review added successfully!");
                    } catch (Exception e) {
                        System.out.println("Invalid review input.");
                    }
                    break;
                case "2":
                    Review[] revs = data.getAllReviews();
                    System.out.println("\n--- CUSTOMER REVIEWS (" + revs.length + ") ---");
                    for (int i = 0; i < revs.length; i++) {
                        System.out.println(revs[i]);
                    }
                    break;
                case "3":
                    System.out.print("Enter keyword to search in reviews: ");
                    String kw = scanner.nextLine().trim().toLowerCase();
                    Review[] allRevs = data.getAllReviews();
                    int found = 0;
                    for (int i = 0; i < allRevs.length; i++) {
                        KMP.KMPResult r = KMP.search(allRevs[i].getReviewText().toLowerCase(), kw);
                        if (r.matchCount > 0) {
                            System.out.printf("  ✓ [Product #%d]: \"%s\" (%d occurrences)\n",
                                    allRevs[i].getProductId(), allRevs[i].getReviewText(), r.matchCount);
                            found++;
                        }
                    }
                    if (found == 0) System.out.println("No reviews contained the keyword.");
                    break;
                case "4":
                    String[] defaultKeywords = {"battery", "delivery", "quality", "refund", "sound", "price", "broken"};
                    AhoCorasick ac = new AhoCorasick(defaultKeywords);
                    System.out.println("\nScanning all reviews for keywords: [battery, delivery, quality, refund, sound, price, broken]");
                    Review[] reviewList = data.getAllReviews();
                    for (int i = 0; i < reviewList.length; i++) {
                        AhoCorasick.Match[] m = ac.search(reviewList[i].getReviewText());
                        if (m.length > 0) {
                            System.out.println("Review: \"" + reviewList[i].getReviewText() + "\"");
                            for (int j = 0; j < m.length; j++) {
                                System.out.println("   -> " + m[j]);
                            }
                        }
                    }
                    break;
                case "5":
                    System.out.println("\nAnalyzing repeated phrases and common vocabulary across reviews...");
                    Review[] rList = data.getAllReviews();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < rList.length; i++) sb.append(rList[i].getReviewText()).append(" ");
                    String aggregated = sb.toString();

                    int[] sa = SAIS.buildSuffixArray(aggregated);
                    KasaiLCP.LCPResult lcpRes = KasaiLCP.computeLCP(aggregated, sa);
                    System.out.println("Longest Repeated Substring in Reviews: \"" + lcpRes.longestRepeatedSubstring + "\"");
                    System.out.println("LCP Length: " + lcpRes.maxLcpLength + " characters");
                    break;
                case "6":
                    Review[] rs = data.getAllReviews();
                    if (rs.length < 2) {
                        System.out.println("Need at least 2 reviews to compare.");
                        break;
                    }
                    System.out.println("Comparing Review 1 and Review 2:");
                    System.out.println("Rev 1: " + rs[0].getReviewText());
                    System.out.println("Rev 2: " + rs[1].getReviewText());
                    SmithWaterman.LocalAlignmentResult sw = SmithWaterman.align(rs[0].getReviewText(), rs[1].getReviewText());
                    System.out.println("Best Shared Sub-segment: \"" + sw.subSeq1 + "\" (Score: " + sw.maxScore + ")");
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void handleQueryCorrection() {
        System.out.println("\n---------- QUERY CORRECTION & AUTO-SUGGEST ----------");
        System.out.print("Enter a search term (try misspelling e.g. 'iphne', 'sansung', 'airpds'): ");
        String query = scanner.nextLine().trim();

        String[] vocab = {"iPhone 15", "Samsung Galaxy S24", "OnePlus 12", "Sony WH-1000XM5",
                          "AirPods Pro", "Dell Inspiron 15", "HP Pavilion", "Logitech Mouse",
                          "Samsung Monitor", "Anker Power Bank"};

        System.out.println("\n1. Classical Levenshtein (Wagner-Fischer):");
        String[] levSuggestions = Levenshtein.getClosestCandidates(query, vocab, 8);
        for (int i = 0; i < Math.min(3, levSuggestions.length); i++) {
            System.out.println("   -> " + levSuggestions[i]);
        }

        System.out.println("\n2. Damerau-Levenshtein (Handles Transpositions):");
        String[] damSuggestions = DamerauLevenshtein.correctQuery(query, vocab);
        for (int i = 0; i < damSuggestions.length; i++) {
            System.out.println("   -> " + damSuggestions[i]);
        }
    }

    private void handleAlgorithmDemonstrations() {
        boolean back = false;
        while (!back) {
            System.out.println("\n---------- ALGORITHM DEMONSTRATIONS & VIVA PREPARATION ----------");
            System.out.println("1. Run Master Automated Verification Tests (CO1 - CO6 Final Evaluation)");
            System.out.println("2. Run Review-1 Verification Tests (CO1 - CO3)");
            System.out.println("3. Run Multi-Core Parallel Empirical Benchmarks (ForkJoin Speedup)");
            System.out.println("4. Display CO1 Algorithm Selection Matrix & Rationale");
            System.out.println("5. Interactive Search Benchmark (CO2 Comparison)");
            System.out.println("6. Product Bundle Optimization Demo (CO3 Bitmask DP)");
            System.out.println("7. Logistics Hub Rerooting Demo (CO3 Rerooting DP)");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String ch = scanner.nextLine().trim();
            switch (ch) {
                case "1":
                    FinalTestRunner.runAllTests();
                    break;
                case "2":
                    Review1TestRunner.runAllTests();
                    break;
                case "3":
                    System.out.println("\nLaunching empirical multi-core parallel speedup benchmarks...");
                    ParallelSort.runBenchmark(50000);
                    ParallelReduce.runBenchmark(500000);
                    break;
                case "4":
                    AlgorithmDesign.displaySelectionTable();
                    break;
                case "5":
                    SearchMenu.display(scanner);
                    break;
                case "6":
                    OptimizationMenu.display(scanner);
                    break;
                case "7":
                    OptimizationMenu.display(scanner);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
