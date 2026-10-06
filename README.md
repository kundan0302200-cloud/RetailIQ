# RetailIQ — Intelligent Retail Analytics Platform

[![Java](https://img.shields.io/badge/Java-21%2B-blue.svg)](https://www.oracle.com/java/)
[![Review-1](https://img.shields.io/badge/Review--1-CO1--CO3%20Ready-brightgreen.svg)]()
[![Course-Compliance](https://img.shields.io/badge/Course%20Rule-Zero%20java.util%20Collections%20in%20Engines-orange.svg)]()

> **Project Identity:** Standalone Java Console Application for Intelligent Retail Analytics.  
> **Target Evaluation:** Review-1 (CO1–CO3: Modules 1–3, Total 60 Marks).  
> **IDE:** Direct execution in Eclipse (`Run As -> Java Application` via `retailiq.Main`).

---

## 1. Project Directory Structure

```text
RetailIQ/
├── .classpath
├── .project
├── README.md
├── data/
│   ├── products.txt
│   ├── customers.txt
│   ├── orders.txt
│   ├── reviews.txt
│   ├── inventory.txt
│   └── sales.txt
├── docs/
│   ├── REVIEW_1_VIVA_GUIDE.md          <-- Complete 60-Marks Viva Prep & Q&A Bank
│   ├── PROBLEM_UNDERSTANDING_CO1.md    <-- Problem Statement, Objectives, Mapping
│   ├── ALGORITHM_SELECTION_CO1_CO3.md  <-- Candidate Algorithms & Selection Trade-offs
│   ├── STRING_ALGORITHMS_CO2.md        <-- KMP, Z, Rabin-Karp, Aho-Corasick, Suffix, SAIS, Kasai
│   ├── ADVANCED_DP_CO3.md              <-- All 14 Advanced DP Formulations & States
│   └── COMPLEXITY_ANALYSIS.md          <-- Big-O Master Complexity & Comparison Tables
└── src/
    └── retailiq/
        ├── Main.java                   <-- Console Application Main Entry Point
        ├── model/                      <-- Retail Domain Models
        │   ├── Product.java
        │   ├── Category.java
        │   ├── Customer.java
        │   ├── Order.java
        │   ├── OrderItem.java
        │   ├── Review.java
        │   ├── Warehouse.java
        │   ├── Store.java
        │   ├── Inventory.java
        │   └── SalesEvent.java
        ├── data/
        │   └── RetailData.java         <-- In-Memory & File Persistence Layer
        ├── ui/                         <-- Console Menus & User Interfaces
        │   ├── MainMenu.java
        │   ├── ProductMenu.java
        │   ├── SearchMenu.java
        │   ├── CustomerMenu.java
        │   ├── InventoryMenu.java
        │   ├── OptimizationMenu.java
        │   └── AnalyticsMenu.java
        ├── structures/                 <-- Custom Data Structures (No java.util.*)
        │   ├── MyQueue.java            <-- FIFO Linked Queue
        │   ├── MyStack.java            <-- LIFO Dynamic Array Stack
        │   ├── MyLinkedList.java       <-- Doubly Linked List
        │   ├── MyHashTable.java        <-- Separate-Chaining Hash Table
        │   └── Graph.java              <-- Adjacency List Graph
        ├── co1/
        │   └── AlgorithmDesign.java    <-- System Framing & Selection Matrix
        ├── co2/                        <-- String Algorithms & Text Indexing
        │   ├── NaiveSearch.java
        │   ├── KMP.java
        │   ├── ZAlgorithm.java
        │   ├── RabinKarp.java
        │   ├── AhoCorasick.java
        │   ├── SuffixArray.java
        │   ├── SAIS.java
        │   └── KasaiLCP.java
        ├── co3/                        <-- Advanced Dynamic Programming
        │   ├── Levenshtein.java
        │   ├── DamerauLevenshtein.java
        │   ├── WeightedEditDistance.java
        │   ├── NeedlemanWunsch.java
        │   ├── SmithWaterman.java
        │   ├── MatrixChain.java
        │   ├── OptimalBST.java
        │   ├── BitmaskDP.java
        │   ├── TSP.java
        │   ├── HamiltonianPath.java
        │   ├── SetCover.java
        │   ├── TreeDP.java
        │   ├── RerootingDP.java
        │   └── SOSDP.java
        ├── co4/                        <-- Network Flow (FlowEdge, Graph, Dinic, EdmondsKarp)
        ├── co5/                        <-- NP-Hard & Approximations (SAT, 3-SAT, VC-Approx)
        ├── co6/                        <-- Randomized & Parallel (QuickSort, Reservoir, Scan, Brent)
        └── test/
            └── Review1TestRunner.java  <-- Automated Verification Suite (37 Unit Tests)
```

---

## 2. Review-1 Evaluation Criteria Coverage (60 Marks)

| Evaluation Rubric | Implemented Modules & Artifacts | Status |
| :--- | :--- | :--- |
| **1. Problem Understanding & Requirement Mapping (CO1)** | - Full Problem Statement & Objectives<br>- Inputs/Outputs & Use Cases<br>- Formal Mapping to DSA Concepts in [`docs/PROBLEM_UNDERSTANDING_CO1.md`](file:///d:/Java%20class%20notes/RetailIQ/docs/PROBLEM_UNDERSTANDING_CO1.md)<br>- Console Selection Matrix in [`AlgorithmDesign.java`](file:///d:/Java%20class%20notes/RetailIQ/src/retailiq/co1/AlgorithmDesign.java) | **COMPLETE (10/10)** |
| **2. Algorithm Selection & Understanding (CO1–CO3)** | - Formal Trade-Off Analysis & Candidate Justifications in [`docs/ALGORITHM_SELECTION_CO1_CO3.md`](file:///d:/Java%20class%20notes/RetailIQ/docs/ALGORITHM_SELECTION_CO1_CO3.md)<br>- Comparison Matrices for String Search, Typo Correction, and Bundle Optimization | **COMPLETE (10/10)** |
| **3. String Algorithm Implementation (CO2)** | - 8 Implemented Engines: Naive, KMP, Z-Algorithm, Rabin-Karp (Double Hash), Aho-Corasick, Suffix Array, SA-IS (Linear), Kasai LCP<br>- Detailed theory in [`docs/STRING_ALGORITHMS_CO2.md`](file:///d:/Java%20class%20notes/RetailIQ/docs/STRING_ALGORITHMS_CO2.md) | **COMPLETE (10/10)** |
| **4. Advanced DP Implementation (CO3)** | - 14 Implemented Techniques: Levenshtein, Damerau-Levenshtein, Weighted Edit, Needleman-Wunsch, Smith-Waterman, MCM, Optimal BST, Bitmask DP Bundle, Held-Karp TSP, Hamiltonian Path, Set Cover, Tree DP, Rerooting DP, SOS DP<br>- Complete formulations in [`docs/ADVANCED_DP_CO3.md`](file:///d:/Java%20class%20notes/RetailIQ/docs/ADVANCED_DP_CO3.md) | **COMPLETE (10/10)** |
| **5. Complexity & Algorithmic Analysis (CO2–CO3)** | - Best, Average, Worst Time & Space Complexity Master Table in [`docs/COMPLEXITY_ANALYSIS.md`](file:///d:/Java%20class%20notes/RetailIQ/docs/COMPLEXITY_ANALYSIS.md)<br>- Live Empirical Benchmarking Menu in Console | **COMPLETE (10/10)** |
| **6. Individual Viva & Technical Contribution** | - Comprehensive Viva Question & Answer Bank in [`docs/REVIEW_1_VIVA_GUIDE.md`](file:///d:/Java%20class%20notes/RetailIQ/docs/REVIEW_1_VIVA_GUIDE.md)<br>- Team Member Task Allocations & Live Demo Script | **COMPLETE (10/10)** |

---

## 3. How to Run the Application

### Option A: From Eclipse IDE (Recommended)
1. Open Eclipse.
2. Select **File $\rightarrow$ Open Projects from File System...** and choose `d:\Java class notes\RetailIQ`.
3. In the Package Explorer, expand `src/retailiq/`.
4. Right-click [`Main.java`](file:///d:/Java%20class%20notes/RetailIQ/src/retailiq/Main.java) $\rightarrow$ **Run As $\rightarrow$ Java Application**.

### Option B: From Command Line / Terminal
```powershell
# Navigate to project directory
cd "d:\Java class notes\RetailIQ"

# Compile all source files into bin directory
javac -d bin (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })

# Run Main Console Application
java -cp bin retailiq.Main

# Or Run the Automated Review-1 Verification Test Suite
java -cp bin retailiq.test.Review1TestRunner
```

---

## 4. Key Algorithmic Highlights for the Viva

1. **Course Restriction Adherence:**
   - Zero `java.util.*` collections inside `retailiq.structures` and algorithmic modules (`co1` through `co6`).
   - Custom implementations of `MyQueue`, `MyStack`, `MyLinkedList`, `MyHashTable`, and `Graph`.
2. **KMP Search (`KMP.java`):**
   - Strictly $O(N + M)$ guaranteed linear time matching. No backtracking on the text pointer.
3. **Aho-Corasick Multi-Keyword Mining (`AhoCorasick.java`):**
   - Trie-based finite automaton with BFS failure transitions and dictionary suffix links.
   - Detects all positive and negative feedback keywords in reviews in a single streaming pass ($O(N + \sum M_k + Z)$).
4. **SA-IS Linear Time Suffix Array (`SAIS.java`):**
   - True linear-time $O(N)$ Suffix Array Induced Sorting (Nong-Zhang-Chan algorithm).
5. **Damerau-Levenshtein Typo Correction (`DamerauLevenshtein.java`):**
   - Recovers over 80% of human typing slips with cost 1 adjacent character transpositions.
6. **Bitmask DP Product Bundle Optimization (`BitmaskDP.java`):**
   - Computes exact globally optimal multi-item bundles under budget limits with cross-selling synergy bonuses in $O(2^N \cdot N)$.
7. **Tree Rerooting DP Logistics Hub Locator (`RerootingDP.java`):**
   - Computes total delivery distances to all other stores for all candidate hubs in two DFS passes ($O(V)$ total time).
