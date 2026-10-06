# RetailIQ - Algorithm Selection & Trade-Off Analysis (CO1–CO3)

## 1. Algorithmic Selection Methodology

For every retail business challenge identified in RetailIQ, candidate algorithms were rigorously benchmarked based on:
1. **Asymptotic Worst-Case Time Complexity:** Does the algorithm guarantee predictable execution bounds under adversarial or repetitive inputs?
2. **Auxiliary Space Complexity:** Can the memory footprint be maintained in cache-friendly, bounded arrays without garbage collection churn?
3. **Domain Applicability:** How naturally does the mathematical formulation reflect retail business constraints (e.g. adjacent key swaps, multi-keyword reviews, budget subsets)?

---

## 2. In-Depth Comparative Evaluations

### Problem 1: Exact Catalog Search (Single Pattern)

```
                       Catalog Search Problem
                                 |
        +------------------------+------------------------+
        |                        |                        |
      Naive                     KMP                   Rabin-Karp
     O(N * M)                 O(N + M)                 O(N + M) avg
  Backtracks text        No text backtracking       Spurious collisions
```

- **Candidate A: Naive Search**
  - *Time:* $O(N \cdot M)$ worst case (e.g., searching `AAAA...` in `AAAA...B`).
  - *Verdict:* **Rejected as Primary**. Inefficient when scanning large SKU descriptions. Retained purely as an empirical baseline.
- **Candidate B: Rabin-Karp Algorithm**
  - *Time:* $O(N + M)$ average, but degrades to $O(N \cdot M)$ worst case under hash collisions (spurious hits).
  - *Verdict:* Effective for fixed-length multiple rolling patterns, but requires collision verification overhead.
- **Candidate C: Knuth-Morris-Pratt (KMP)**
  - *Time:* Strictly $O(N + M)$ guaranteed worst-case time; precomputes Longest Proper Prefix which is also Suffix (LPS) in $O(M)$.
  - *Verdict:* **SELECTED**. Eliminates text pointer backtracking. Guarantees deterministic millisecond response times regardless of text repetition.

---

### Problem 2: Review Keyword Mining (Multiple Patterns)

```
                   Multi-Keyword Review Scanning
                                 |
        +------------------------+------------------------+
        |                                                 |
  Repeated KMP                                      Aho-Corasick
  O(K * (N + M))                                    O(N + L + Z)
  Scans text K times                                Single text pass
```

- **Candidate A: Repeated KMP / Naive Searches**
  - *Time:* $O(K \cdot (N + M))$ where $K$ is the number of sentiment keywords.
  - *Verdict:* **Rejected**. Scans the entire review text repeatedly for every single keyword in the dictionary.
- **Candidate B: Aho-Corasick Automaton**
  - *Time:* Preprocessing: $O(L \cdot |\Sigma|)$ where $L = \sum |P_k|$. Text search: $O(N + Z)$ where $Z$ is the total count of matches.
  - *Verdict:* **SELECTED**. Consolidates the keyword dictionary into a Trie enriched with BFS failure links and dictionary suffix links. Streams through review text in a single contiguous pass, detecting all positive and negative retail keywords simultaneously.

---

### Problem 3: Typo Correction & Did-You-Mean Auto-Complete

```
                   Search Query Typo Correction
                                 |
        +------------------------+------------------------+
        |                                                 |
   Levenshtein                                   Damerau-Levenshtein
   O(M * N)                                      O(M * N)
   Insert, Delete, Replace only                  Handles adjacent swaps ("iphoen")
```

- **Candidate A: Classical Levenshtein (Wagner-Fischer)**
  - *Operations:* Insertion, Deletion, Substitution.
  - *Limitation:* Treating a transposition (e.g. `"samusng"` $\rightarrow$ `"samsung"`) requires two operations (1 deletion + 1 insertion, cost = 2), causing the correct suggestion to rank below distant words.
- **Candidate B: Damerau-Levenshtein Distance**
  - *Operations:* Insertion, Deletion, Substitution, and **Adjacent Transposition**.
  - *Verdict:* **SELECTED**. In human keyboard typing, adjacent key swaps constitute over 80% of accidental misspellings. Damerau-Levenshtein scores a transposition with a cost of 1, accurately recovering intended catalog items.
- **Candidate C: Weighted Edit Distance**
  - *Verdict:* **SELECTED for layout-aware search**. Models QWERTY physical keyboard layout distances (e.g., pressing 'o' instead of 'p' costs 60% of arbitrary substitution).

---

### Problem 4: Product Bundle Selection Under Budget

```
                     Product Bundle Optimization
                                 |
        +------------------------+------------------------+
        |                                                 |
  Greedy Knapsack                                      Bitmask DP
  Approximate only                                    Exact Optimum
  Fails pairwise synergy                              Captures cross-selling bonuses
```

- **Candidate A: Greedy Knapsack Approximation**
  - *Limitation:* Fails to capture non-linear category synergy bonuses (e.g., pairing a Smartphone with Earbuds grants a multi-buy rating bonus).
- **Candidate B: Bitmask Dynamic Programming**
  - *Time:* $O(2^N \cdot N)$, Space: $O(2^N)$ for catalog candidate set $N \le 20$.
  - *Verdict:* **SELECTED**. Examines all $2^N$ product subsets in bottom-up submask order, strictly enforcing budget limits while evaluating cross-category synergy bonuses to yield the exact global maximum bundle utility score.

---

### Problem 5: Central Logistics Hub Placement

```
                  Optimal Logistics Hub Placement
                                 |
        +------------------------+------------------------+
        |                                                 |
  All-Pairs Floyd-Warshall / BFS                       Rerooting DP
  O(V^3) or O(V^2)                                     O(V) Linear Time
  Recomputes from every node                          Two DFS passes total
```

- **Candidate A: Repeated Tree Traversals (BFS from each node)**
  - *Time:* $O(V \cdot (V + E)) = O(V^2)$ on tree networks.
  - *Verdict:* Inefficient for large regional logistics hierarchies.
- **Candidate B: Tree Rerooting DP**
  - *Time:* $O(V)$ in two passes:
    1. Pass 1 (Bottom-up): Compute subtree sizes and aggregate subtree travel distances.
    2. Pass 2 (Top-down): Dynamically shift root from parent $u$ to child $v$:
       $$\text{totalDist}[v] = \text{totalDist}[u] - \text{sz}[v] + (V - \text{sz}[v])$$
  - *Verdict:* **SELECTED**. Solves the optimal logistics hub placement problem in linear time.
