package retailiq.ui;

import retailiq.co5.*;
import retailiq.co6.*;
import retailiq.data.RetailData;
import retailiq.model.Order;
import retailiq.model.Product;

import java.util.Scanner;

/**
 * Console UI for Order/Sales Management, CO5 Constraints, and CO6 Analytics.
 */
public class AnalyticsMenu {

    public static void display(Scanner scanner) {
        RetailData data = RetailData.getInstance();
        boolean back = false;

        while (!back) {
            System.out.println("\n--------- SALES ANALYTICS & ADVANCED MODULES ---------");
            System.out.println("1. View Orders & Revenue Aggregation");
            System.out.println("2. CO5: Product Constraint Satisfaction (SAT & Reductions)");
            System.out.println("3. CO5: Vertex Cover 2-Approximation");
            System.out.println("4. CO6: Randomized QuickSort Sales Ranking");
            System.out.println("5. CO6: Reservoir Sampling Streaming Transactions");
            System.out.println("6. CO6: Universal Hashing SKU Collision Analysis");
            System.out.println("7. CO6: Parallel Reduce & Blelloch Prefix Scan");
            System.out.println("8. CO6: Brent's Theorem Bounds Display");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleOrdersRevenue(data);
                    break;
                case "2":
                    handleCO5SAT();
                    break;
                case "3":
                    handleCO5VertexCover();
                    break;
                case "4":
                    handleCO6QuickSort();
                    break;
                case "5":
                    handleCO6Reservoir();
                    break;
                case "6":
                    handleCO6Hashing();
                    break;
                case "7":
                    handleCO6Parallel();
                    break;
                case "8":
                    handleCO6Brents();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number between 0 and 8.");
            }
        }
    }

    private static void handleOrdersRevenue(RetailData data) {
        Order[] orders = data.getAllOrders();
        System.out.println("\n--- RECORDED ORDERS (" + orders.length + ") ---");
        double total = 0;
        for (int i = 0; i < orders.length; i++) {
            System.out.println(orders[i]);
            total += orders[i].getTotal();
        }
        System.out.printf("Total Retail Revenue: ₹%.2f\n", total);
    }

    private static void handleCO5SAT() {
        System.out.println("\n--- CO5: 3-SAT PRODUCT COMPATIBILITY SOLVER ---");
        System.out.println("Clauses representing bundle constraints across 3 product options:");
        int[][] clauses = {
            {1, 2, -3},
            {-1, 2, 3},
            {1, -2, 3}
        };
        boolean sat = ThreeSAT.solve(3, clauses);
        System.out.println("Product configuration satisfiability: " + (sat ? "SATISFIABLE" : "UNSATISFIABLE"));
        ReductionDemo.displayReductionChain();
    }

    private static void handleCO5VertexCover() {
        System.out.println("\n--- CO5: VERTEX COVER 2-APPROXIMATION (MAXIMAL MATCHING) ---");
        boolean[][] adj = {
            {false, true, true, false},
            {true, false, true, true},
            {true, true, false, true},
            {false, true, true, false}
        };
        VertexCoverApprox.ApproxResult res = VertexCoverApprox.approximateCover(adj);
        System.out.println("Approximate Vertex Cover Size: " + res.coverSize);
        System.out.println("Note: " + res.note);
    }

    private static void handleCO6QuickSort() {
        System.out.println("\n--- CO6: RANDOMIZED QUICKSORT DEMAND RANKING ---");
        double[] salesFigures = {45000, 12000, 89000, 34000, 67000, 15000, 92000, 28000};
        System.out.print("Unsorted Sales: ");
        for (int i = 0; i < salesFigures.length; i++) System.out.print(salesFigures[i] + " ");
        System.out.println();

        RandomizedQuickSort.sort(salesFigures, 0, salesFigures.length - 1);

        System.out.print("Sorted Sales:   ");
        for (int i = 0; i < salesFigures.length; i++) System.out.print(salesFigures[i] + " ");
        System.out.println();
        System.out.println("Expected Time Complexity: O(N log N) without worst-case pivot vulnerability.");
    }

    private static void handleCO6Reservoir() {
        System.out.println("\n--- CO6: RESERVOIR SAMPLING (STREAMING TRANSACTIONS) ---");
        int[] transactionStream = new int[100];
        for (int i = 0; i < 100; i++) transactionStream[i] = 1000 + i;

        int k = 5;
        int[] sample = ReservoirSampling.sample(transactionStream, k);
        System.out.println("Stream Size: 100 transactions | Retained Reservoir Sample Size: " + k);
        System.out.print("Sampled Transaction IDs: ");
        for (int i = 0; i < sample.length; i++) System.out.print(sample[i] + " ");
        System.out.println("\nEvery item has equal probability k/N = 5/100 of retention.");
    }

    private static void handleCO6Hashing() {
        System.out.println("\n--- CO6: UNIVERSAL HASHING FOR PRODUCT SKUS ---");
        UniversalHashing uh = new UniversalHashing(100);
        long[] skus = {101001L, 101002L, 102005L, 103010L, 104050L};
        System.out.println("SKU Code    -> Hash Slot");
        System.out.println("-------------------------");
        for (int i = 0; i < skus.length; i++) {
            System.out.printf("%-11d -> %d\n", skus[i], uh.getHash(skus[i]));
        }
        System.out.println("Universal Guarantee: Collision probability Pr[h(x)=h(y)] <= 1/m");
    }

    private static void handleCO6Parallel() {
        System.out.println("\n--- CO6: PARALLEL REDUCE & BLELLOCH SCAN ---");
        double[] revenue = {10, 20, 30, 40, 50, 60, 70, 80};
        double totalSum = ParallelReduce.reduceSum(revenue);
        System.out.println("Parallel Reduce Total: ₹" + totalSum);

        double[] prefix = BlellochScan.exclusiveScan(revenue);
        System.out.print("Blelloch Exclusive Scan: [");
        for (int i = 0; i < prefix.length; i++) {
            System.out.print(prefix[i] + (i < prefix.length - 1 ? ", " : ""));
        }
        System.out.println("]");
        System.out.println("Work: O(N) | Span: O(log N)");
    }

    private static void handleCO6Brents() {
        System.out.println("\n--- CO6: BRENT'S THEOREM BOUNDS ---");
        double work = 1000;
        double span = 20;
        int processors = 8;
        BrentsTheorem.BoundAnalysis b = BrentsTheorem.computeBounds(work, span, processors);

        System.out.println("Total Work T1:            " + b.work);
        System.out.println("Critical Path Span T_inf: " + b.span);
        System.out.println("Available Processors:     " + b.processors);
        System.out.printf("Theoretical Time Bound:   %.2f steps\n", b.theoreticalTimeBound);
        System.out.printf("Theoretical Speedup:      %.2fx\n", b.theoreticalSpeedup);
        System.out.printf("Maximum Parallelism:      %.2f\n", b.maxParallelism);
    }
}
