package retailiq.ui;

import retailiq.co4.*;
import retailiq.data.RetailData;
import retailiq.model.Inventory;
import retailiq.model.Warehouse;

import java.util.Scanner;

/**
 * Console UI for Inventory Management & CO4 Network Flow Logistics.
 */
public class InventoryMenu {

    public static void display(Scanner scanner) {
        RetailData data = RetailData.getInstance();
        boolean back = false;

        while (!back) {
            System.out.println("\n--------- INVENTORY & DISTRIBUTION MANAGEMENT ---------");
            System.out.println("1. View Multi-Warehouse Stock Levels");
            System.out.println("2. Max Flow Logistics Distribution (Ford-Fulkerson & Dinic)");
            System.out.println("3. Max-Flow Min-Cut Bottleneck Analysis");
            System.out.println("4. Warehouse Slot Allocation (Bipartite Matching)");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleViewStock(data);
                    break;
                case "2":
                    handleMaxFlow();
                    break;
                case "3":
                    handleMinCut();
                    break;
                case "4":
                    handleBipartiteMatching();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number between 0 and 4.");
            }
        }
    }

    private static void handleViewStock(RetailData data) {
        System.out.println("\n--- WAREHOUSE FACILITIES ---");
        Warehouse[] whs = data.getAllWarehouses();
        for (int i = 0; i < whs.length; i++) {
            System.out.println(whs[i]);
        }

        System.out.println("\n--- INVENTORY STOCK ALLOCATION ---");
        Inventory[] invs = data.getAllInventory();
        for (int i = 0; i < invs.length; i++) {
            System.out.println(invs[i]);
        }
    }

    private static void handleMaxFlow() {
        System.out.println("\n--- CO4: LOGISTICS NETWORK MAX-FLOW (DINIC & EDMONDS-KARP) ---");
        System.out.println("Network Topology: Source -> Warehouses (W1, W2) -> Distribution Centers (DC1, DC2) -> Retail Stores (Sink)");

        // 6 Nodes: 0=Source, 1=W1, 2=W2, 3=DC1, 4=DC2, 5=Sink
        FlowGraph graph = new FlowGraph(6);
        graph.addEdge(0, 1, 100); // Source -> W1
        graph.addEdge(0, 2, 80);  // Source -> W2
        graph.addEdge(1, 3, 60);  // W1 -> DC1
        graph.addEdge(1, 4, 50);  // W1 -> DC2
        graph.addEdge(2, 3, 40);  // W2 -> DC1
        graph.addEdge(2, 4, 50);  // W2 -> DC2
        graph.addEdge(3, 5, 80);  // DC1 -> Stores (Sink)
        graph.addEdge(4, 5, 90);  // DC2 -> Stores (Sink)

        double dinicFlow = Dinic.maxFlow(graph, 0, 5);
        System.out.println("Maximum Units Shipped per Day (Dinic): " + dinicFlow + " units");
        System.out.println("Dinic Complexity:        O(V^2 * E)");
        System.out.println("Edmonds-Karp Complexity: O(V * E^2)");
    }

    private static void handleMinCut() {
        System.out.println("\n--- CO4: MAX-FLOW MIN-CUT SUPPLY CHAIN BOTTLENECKS ---");
        FlowGraph graph = new FlowGraph(6);
        graph.addEdge(0, 1, 100);
        graph.addEdge(0, 2, 80);
        graph.addEdge(1, 3, 60);
        graph.addEdge(1, 4, 50);
        graph.addEdge(2, 3, 40);
        graph.addEdge(2, 4, 50);
        graph.addEdge(3, 5, 80);
        graph.addEdge(4, 5, 90);

        Dinic.maxFlow(graph, 0, 5);
        boolean[] reachable = MinCut.findReachableSet(graph, 0);

        System.out.println("Bottleneck Cut Partitions (S vs T):");
        String[] nodeNames = {"Source", "Warehouse-1", "Warehouse-2", "DC-1", "DC-2", "Store Sink"};
        for (int i = 0; i < 6; i++) {
            System.out.println("  " + nodeNames[i] + ": " + (reachable[i] ? "In Source Side (S)" : "In Sink Side (T)"));
        }
        System.out.println("Edges crossing from S to T constitute the critical logistics bottleneck.");
    }

    private static void handleBipartiteMatching() {
        System.out.println("\n--- CO4: WAREHOUSE BAY BIPARTITE MATCHING ---");
        System.out.println("Matching 4 inbound product shipments to 4 eligible temperature-controlled bays:");

        boolean[][] bpGraph = {
            {true, true, false, false},   // Lot 1 can go to Bay 0, 1
            {false, true, true, false},   // Lot 2 can go to Bay 1, 2
            {false, false, true, true},   // Lot 3 can go to Bay 2, 3
            {true, false, false, true}    // Lot 4 can go to Bay 0, 3
        };

        int matched = BipartiteMatching.maxBipartiteMatching(bpGraph, 4, 4);
        System.out.println("Maximum Simultaneous Shipments Discharged: " + matched + " / 4");
        System.out.println("König's Theorem: Maximum Matching = Minimum Vertex Cover in Bipartite graphs.");
    }
}
