# RetailIQ - Complexity & Algorithmic Analysis (CO2–CO3)

## 1. Master Algorithmic Complexity Table

The table below compiles asymptotic time and space complexities for all string matching and advanced dynamic programming implementations in RetailIQ:

| Category | Algorithm | Best Time | Average Time | Worst Time | Space Complexity | RetailIQ Application |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **String (CO2)** | **Naive Search** | $O(M)$ | $O(N)$ | $O(N \cdot M)$ | $O(1)$ | Baseline Search Benchmark |
| **String (CO2)** | **KMP Algorithm** | $O(N)$ | $O(N + M)$ | $O(N + M)$ | $O(M)$ | Catalog Live Search |
| **String (CO2)** | **Z-Algorithm** | $O(N + M)$ | $O(N + M)$ | $O(N + M)$ | $O(N + M)$ | Prefix Matching & Phrase Search |
| **String (CO2)** | **Rabin-Karp** | $O(N + M)$ | $O(N + M)$ | $O(N \cdot M)$ | $O(1)$ | SKU & Code Multi-pattern Match |
| **String (CO2)** | **Aho-Corasick** | $O(N + \sum M_k)$ | $O(N + \sum M_k + Z)$ | $O(N + \sum M_k + Z)$ | $O(\sum M_k \cdot \|\Sigma\|)$ | Multi-Keyword Review Mining |
| **String (CO2)** | **Suffix Array (Prefix Doubling)** | $O(N \log N)$ | $O(N \log^2 N)$ | $O(N \log^2 N)$ | $O(N)$ | Substring Binary Lookup |
| **String (CO2)** | **SA-IS Suffix Array** | $O(N)$ | $O(N)$ | $O(N)$ | $O(N)$ | Linear Catalog Text Indexing |
| **String (CO2)** | **Kasai's LCP** | $O(N)$ | $O(N)$ | $O(N)$ | $O(N)$ | Longest Repeated Substring in Reviews |
| **DP (CO3)** | **Levenshtein Distance** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | Typo Auto-Correct ("Did You Mean") |
| **DP (CO3)** | **Damerau-Levenshtein** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | Keyboard Transposition Correction |
| **DP (CO3)** | **Weighted Edit Distance** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | Layout-Proximity Query Distance |
| **DP (CO3)** | **Needleman-Wunsch** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | Global SKU Attribute Alignment |
| **DP (CO3)** | **Smith-Waterman** | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | $O(M \cdot N)$ | Local Review Plagiarism Detection |
| **DP (CO3)** | **Matrix Chain Mult.** | $O(N^3)$ | $O(N^3)$ | $O(N^3)$ | $O(N^2)$ | Analytical Query Transform Order |
| **DP (CO3)** | **Optimal BST** | $O(N^3)$ | $O(N^3)$ | $O(N^3)$ | $O(N^2)$ | Catalog Keyword Search Frequency |
| **DP (CO3)** | **Bitmask DP Bundle** | $O(2^N \cdot N)$ | $O(2^N \cdot N)$ | $O(2^N \cdot N)$ | $O(2^N)$ | Optimal Product Bundle under Budget |
| **DP (CO3)** | **Held-Karp TSP** | $O(2^N \cdot N^2)$ | $O(2^N \cdot N^2)$ | $O(2^N \cdot N^2)$ | $O(2^N \cdot N)$ | Exact Minimum Delivery Courier Tour |
| **DP (CO3)** | **Hamiltonian Path** | $O(2^N \cdot N^2)$ | $O(2^N \cdot N^2)$ | $O(2^N \cdot N^2)$ | $O(2^N \cdot N)$ | AGV Warehouse Feasibility Tour |
| **DP (CO3)** | **Exact Set Cover DP** | $O(M \cdot 2^U)$ | $O(M \cdot 2^U)$ | $O(M \cdot 2^U)$ | $O(2^U)$ | Minimum Campaign Segment Coverage |
| **DP (CO3)** | **Tree DP (Indep. Set)** | $O(V)$ | $O(V)$ | $O(V)$ | $O(V)$ | Store Hierarchy Incentive Maximizer |
| **DP (CO3)** | **Tree Rerooting DP** | $O(V)$ | $O(V)$ | $O(V)$ | $O(V)$ | Optimal Central Logistics Hub Placement |
| **DP (CO3)** | **SOS DP (Subsets)** | $O(N \cdot 2^N)$ | $O(N \cdot 2^N)$ | $O(N \cdot 2^N)$ | $O(2^N)$ | Customer Profile Submask Aggregator |

---

## 2. In-Depth Complexity Trade-Offs

### String Search: Naive vs KMP vs Rabin-Karp vs Z
- **Naive vs KMP:**
  Naive exhibits worst-case degradation on repetitive texts (e.g. text of $10^5$ 'A's and pattern of $10^3$ 'A's followed by 'B') performing $10^8$ character comparisons. KMP bounds this strictly to $10^5 + 10^3$ comparisons due to the LPS failure function.
- **Rabin-Karp vs KMP:**
  Rabin-Karp achieves $O(N + M)$ expected time via polynomial rolling hashing. However, in adversarial inputs where multiple substrings hash to the same value (hash collision), verifying false matches degrades runtime to $O(N \cdot M)$. KMP has no such vulnerability. RetailIQ uses double hashing (two large prime moduli: $10^9 + 7$ and $10^9 + 9$) to bound collision probability to virtually zero ($\approx 10^{-18}$).

### Dynamic Programming: Naive Subset Iteration vs SOS DP
- Aggregating metrics across all sub-profiles of an $N$-bit customer feature mask:
  - **Naive Submask Enumeration:**
    $$\sum_{k=0}^N \binom{N}{k} 2^k = (1 + 2)^N = 3^N$$
    For $N = 16$: $3^{16} \approx 43{,}046{,}721$ iterations.
  - **SOS DP (Sum Over Subsets):**
    $$N \cdot 2^N$$
    For $N = 16$: $16 \cdot 2^{16} = 1{,}048{,}576$ operations ($\approx 41\times$ faster!).
