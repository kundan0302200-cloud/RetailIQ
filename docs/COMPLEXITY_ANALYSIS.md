# RetailIQ — Master Algorithmic Complexity & Theoretical Analysis (CO1–CO6)

## 1. Master Algorithmic Complexity Table (All Modules)

The table below compiles asymptotic time, work, span, and space complexities for all algorithmic engines implemented across RetailIQ:

| Module / CO | Algorithm | Best Time / Work ($T_1$) | Average Time | Worst Time | Span ($T_\infty$) | Space Complexity | RetailIQ Application |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **CO2 (Strings)** | **Naive Search** | $O(M)$ | $O(N)$ | $O(N \cdot M)$ | — | $O(1)$ | Baseline Benchmarking |
| **CO2 (Strings)** | **KMP Algorithm** | $O(N)$ | $O(N + M)$ | $O(N + M)$ | — | $O(M)$ | Live Product Search |
| **CO2 (Strings)** | **Z-Algorithm** | $O(N + M)$ | $O(N + M)$ | $O(N + M)$ | — | $O(N + M)$ | Prefix Matching & Phrase Search |
| **CO2 (Strings)** | **Rabin-Karp** | $O(N + M)$ | $O(N + M)$ | $O(N \cdot M)$ | — | $O(1)$ | SKU & Code Multi-pattern Match |
| **CO2 (Strings)** | **Aho-Corasick** | $O(N + \sum M_k)$ | $O(N + \sum M_k + Z)$ | $O(N + \sum M_k + Z)$ | — | $O(\sum M_k \cdot \|\Sigma\|)$ | Multi-Keyword Review Mining |
| **CO2 (Strings)** | **Suffix Array (Doubling)** | $O(N \log N)$ | $O(N \log^2 N)$ | $O(N \log^2 N)$ | — | $O(N)$ | Catalog Substring Binary Search |
| **CO2 (Strings)** | **SA-IS Algorithm** | $O(N)$ | $O(N)$ | $O(N)$ | — | $O(N)$ | Linear Catalog Text Indexing |
| **CO2 (Strings)** | **Kasai's LCP** | $O(N)$ | $O(N)$ | $O(N)$ | — | $O(N)$ | Longest Repeated Substring in Reviews |
| **CO3 (Adv DP)** | **Levenshtein Distance** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | — | $O(M \cdot N)$ | Typo Auto-Correct ("Did You Mean") |
| **CO3 (Adv DP)** | **Damerau-Levenshtein** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | — | $O(M \cdot N)$ | Adjacent Transposition Correction |
| **CO3 (Adv DP)** | **Weighted Edit Dist** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | — | $O(M \cdot N)$ | Proximity-Weighted Query Correction |
| **CO3 (Adv DP)** | **Needleman-Wunsch** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | — | $O(M \cdot N)$ | Global SKU Feature Alignment |
| **CO3 (Adv DP)** | **Smith-Waterman** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | — | $O(M \cdot N)$ | Local Review Plagiarism Detection |
| **CO3 (Adv DP)** | **Matrix Chain Mult.** | $O(N^3)$ | $O(N^3)$ | $O(N^3)$ | — | $O(N^2)$ | Analytical Query Transform Order |
| **CO3 (Adv DP)** | **Optimal BST** | $O(N^3)$ | $O(N^3)$ | $O(N^3)$ | — | $O(N^2)$ | Dynamic Search Frequency Index |
| **CO3 (Adv DP)** | **Bitmask DP Bundle** | $O(2^N \cdot N)$ | $O(2^N \cdot N)$ | $O(2^N \cdot N)$ | — | $O(2^N)$ | Optimal Product Bundle under Budget |
| **CO3 (Adv DP)** | **Held-Karp TSP** | $O(2^N \cdot N^2)$ | $O(2^N \cdot N^2)$ | $O(2^N \cdot N^2)$ | — | $O(2^N \cdot N)$ | Courier Delivery Route Optimization |
| **CO3 (Adv DP)** | **Hamiltonian Path** | $O(2^N \cdot N^2)$ | $O(2^N \cdot N^2)$ | $O(2^N \cdot N^2)$ | — | $O(2^N \cdot N)$ | AGV Warehouse Feasibility Tour |
| **CO3 (Adv DP)** | **Exact Set Cover DP** | $O(M \cdot 2^U)$ | $O(M \cdot 2^U)$ | $O(M \cdot 2^U)$ | — | $O(2^U)$ | Campaign Customer Segment Coverage |
| **CO3 (Adv DP)** | **Tree DP (Indep. Set)** | $O(V)$ | $O(V)$ | $O(V)$ | — | $O(V)$ | Store Hierarchy Incentive Maximizer |
| **CO3 (Adv DP)** | **Tree Rerooting DP** | $O(V)$ | $O(V)$ | $O(V)$ | — | $O(V)$ | Central Logistics Hub Placement |
| **CO3 (Adv DP)** | **SOS DP (Subsets)** | $O(N \cdot 2^N)$ | $O(N \cdot 2^N)$ | $O(N \cdot 2^N)$ | — | $O(2^N)$ | Customer Profile Submask Aggregator |
| **CO4 (Flow)** | **Ford-Fulkerson** | $O(E \cdot \|f^*\|)$ | $O(E \cdot \|f^*\|)$ | $O(E \cdot \|f^*\|)$ | — | $O(V + E)$ | Baseline Flow Augmentation |
| **CO4 (Flow)** | **Edmonds-Karp** | $O(V \cdot E^2)$ | $O(V \cdot E^2)$ | $O(V \cdot E^2)$ | — | $O(V + E)$ | Polynomial Max Flow Distribution |
| **CO4 (Flow)** | **Dinic's Algorithm** | $O(V^2 \cdot E)$ | $O(V^2 \cdot E)$ | $O(V^2 \cdot E)$ | — | $O(V + E)$ | Supply Chain Max Daily Throughput |
| **CO4 (Flow)** | **Min-Cut Extraction** | $O(V + E)$ | $O(V + E)$ | $O(V + E)$ | — | $O(V)$ | Supply Chain Bottleneck Detection |
| **CO4 (Flow)** | **Bipartite Matching** | $O(V \cdot E)$ | $O(V \cdot E)$ | $O(V \cdot E)$ | — | $O(V + E)$ | Warehouse Bay Allocation |
| **CO5 (NP/Approx)** | **3-SAT Solver** | $O(2^N \cdot M)$ | $O(2^N \cdot M)$ | $O(2^N \cdot M)$ | — | $O(N + M)$ | Product Compatibility Constraints |
| **CO5 (NP/Approx)** | **Clique Solver** | $O(2^V \cdot V^2)$ | $O(2^V \cdot V^2)$ | $O(2^V \cdot V^2)$ | — | $O(V)$ | Mutually Compatible Item Clusters |
| **CO5 (NP/Approx)** | **Independent Set** | $O(2^V \cdot V^2)$ | $O(2^V \cdot V^2)$ | $O(2^V \cdot V^2)$ | — | $O(V)$ | Non-Interfering Promo Campaigns |
| **CO5 (NP/Approx)** | **Vertex Cover Exact** | $O(2^V \cdot V^2)$ | $O(2^V \cdot V^2)$ | $O(2^V \cdot V^2)$ | — | $O(V)$ | Optimal Route Supervisor Placement |
| **CO5 (NP/Approx)** | **VC 2-Approximation** | $O(V + E)$ | $O(V + E)$ | $O(V + E)$ | — | $O(V)$ | Fast Route Supervisor Placement |
| **CO6 (Rand/Par)** | **Rand QuickSort** | $O(N \log N)$ | $O(N \log N)$ | $O(N^2)$ worst prob 0 | — | $O(\log N)$ | Sales Ranking Without Adversarial Worst-Case |
| **CO6 (Rand/Par)** | **Universal Hashing** | $O(1)$ | $O(1)$ | $O(N)$ worst prob $\le 1/m$ | — | $O(m)$ | Collision-Free SKU In-Memory Tables |
| **CO6 (Rand/Par)** | **Reservoir Sampling**| $O(N)$ | $O(N)$ | $O(N)$ | — | $O(k)$ | Infinite Stream Transaction Sampling |
| **CO6 (Rand/Par)** | **Miller-Rabin** | $O(k \log^3 N)$| $O(k \log^3 N)$| $O(k \log^3 N)$ | — | $O(1)$ | Cryptographic Key & Prime Generation |
| **CO6 (Rand/Par)** | **Parallel MergeSort**| $O(N \log N)$ | $O(N \log N)$ | $O(N \log N)$ | $O(\log^2 N)$ | $O(N)$ | Multi-Core Batch Transaction Sorting |
| **CO6 (Rand/Par)** | **Parallel Reduce** | $O(N)$ | $O(N)$ | $O(N)$ | $O(\log N)$ | $O(\log N)$ | Multi-Core Revenue Aggregation |
| **CO6 (Rand/Par)** | **Blelloch Prefix Scan**| $O(N)$ | $O(N)$ | $O(N)$ | $O(\log N)$ | $O(N)$ | Work-Efficient Cumulative Revenue Scan |
| **CO6 (Rand/Par)** | **Brent's Theorem** | $O(1)$ | $O(1)$ | $O(1)$ | — | $O(1)$ | Theoretical Scheduling Bound Modeling |

---

## 2. In-Depth Complexity Trade-Off Analyses

### 2.1 String Matching: Naive vs KMP vs Rabin-Karp vs Aho-Corasick
- **Single Pattern Search (KMP vs Naive):**
  - Text: $N = 10^5$, Pattern: $M = 10^3$.
  - Naive worst-case comparisons: $10^5 \cdot 10^3 = 10^8$.
  - KMP guaranteed comparisons: $10^5 + 10^3 = 1.01 \cdot 10^5$ ($\approx 1000\times$ faster).
- **Multi-Keyword Search (KMP loop vs Aho-Corasick):**
  - Searching $K = 50$ keywords of length $M = 10$ in review corpus $N = 10^6$:
  - Repeated KMP: $K \cdot O(N + M) = 50 \cdot (10^6 + 10) \approx 5 \cdot 10^7$ operations.
  - Aho-Corasick: $O(N + \sum M_k + Z) \approx 10^6 + 500 + Z \approx 10^6$ operations ($\approx 50\times$ faster).

### 2.2 Network Flow: Edmonds-Karp vs Dinic
- Edmonds-Karp requires finding each path using BFS, pushing flow, and updating residuals. Each augmentation costs $O(E)$, bounded by $O(V \cdot E)$ augmentations $\implies O(V \cdot E^2)$.
- Dinic constructs the layered level graph once via BFS ($O(E)$) and saturates blocking flows via DFS ($O(V \cdot E)$). At most $V - 1$ level phases occur before sink becomes unreachable:
  $$\text{Total Dinic Time} = (V - 1) \cdot (O(E) + O(V \cdot E)) = O(V^2 \cdot E)$$
- On dense supply networks ($E \approx V^2$):
  - Edmonds-Karp: $O(V \cdot (V^2)^2) = O(V^5)$
  - Dinic: $O(V^2 \cdot V^2) = O(V^4)$ (an entire order of magnitude faster).

### 2.3 Exact vs Approximation: Vertex Cover
- For a retail network with $V = 30$ distribution hubs:
  - Exact Vertex Cover: $2^{30} \approx 1.07 \cdot 10^9$ subsets to evaluate.
  - 2-Approximation (Maximal Matching): $O(V + E) \le 30 + 400 \approx 430$ operations ($> 2{,}000{,}000\times$ faster) with a provable guarantee $|C| \le 2 \cdot |C^*|$.

### 2.4 Parallel Work-Span & Brent's Speedup
- Under Brent's Theorem for Parallel Reduce on $N = 1{,}000{,}000$ operations:
  - Work $T_1 = 1{,}000{,}000$, Span $T_\infty = \lceil \log_2 10^6 \rceil \approx 20$.
  - On $p = 8$ CPU cores:
    $$T_8 \le \frac{10^6 - 20}{8} + 20 \approx 125{,}018 \text{ steps}$$
    $$\text{Speedup } S_8 = \frac{10^6}{125018} \approx 8.0\times \text{ (near linear speedup)}$$
