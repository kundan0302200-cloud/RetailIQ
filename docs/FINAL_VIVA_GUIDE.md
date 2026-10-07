# RetailIQ — Final Comprehensive Technical Viva Guide (CO1–CO6)

> **Evaluation Target:** Final Project Evaluation (60 Marks Total).  
> **Prepared for:** Final Technical Viva, Code Walkthrough, and Examiner Defense.

---

## Table of Contents
1. [Project Overview, Architecture & Course Restrictions (CO1)](#1-project-overview-architecture--course-restrictions-co1)
2. [String Algorithms & Text Indexing (CO2)](#2-string-algorithms--text-indexing-co2)
3. [Advanced Dynamic Programming (CO3)](#3-advanced-dynamic-programming-co3)
4. [Network Flow & Logistics Optimization (CO4)](#4-network-flow--logistics-optimization-co4)
5. [NP-Completeness, Reductions & Approximations (CO5)](#5-np-completeness-reductions--approximations-co5)
6. [Randomized & Parallel Algorithms (CO6)](#6-randomized--parallel-algorithms-co6)
7. [High-Yield Examiner Trap Questions & Model Answers](#7-high-yield-examiner-trap-questions--model-answers)

---

## 1. Project Overview, Architecture & Course Restrictions (CO1)

### Q1: What is the core problem RetailIQ solves, and how does it map to DSA-3?
**Answer:**
RetailIQ is an end-to-end Intelligent Retail Analytics platform designed to solve operational computational bottlenecks across the retail supply chain:
- **Search & Customer Experience (CO2, CO3):** Sub-linear text indexing and typo correction for fast catalog browsing.
- **Commercial Merchandising (CO3):** High-margin product bundle optimization under budget constraints.
- **Logistics & Supply Chain Throughput (CO3, CO4):** Distribution hub location selection, maximum freight flow through multi-echelon networks, and warehouse bay scheduling.
- **System Constraints & Catalog Consistency (CO5):** Boolean constraint satisfaction on customizable product kits.
- **High-Throughput Stream & Batch Analytics (CO6):** Unbiased streaming transaction sampling and multi-core parallel revenue aggregation.

### Q2: Did your project use any Java Collections Framework classes (`java.util.ArrayList`, `HashMap`, `LinkedList`) in algorithmic engines?
**Answer:**
**Strictly NO.** As mandated by the course rules, every algorithmic engine (`retailiq.co1` through `co6`) uses custom foundational data structures built from scratch in `retailiq.structures`:
- `MyLinkedList<T>`: Generic doubly linked list.
- `MyStack<T>`: Dynamic array-backed LIFO stack with automatic geometric resizing ($O(1)$ amortized push/pop).
- `MyQueue<T>`: Head/tail pointer FIFO linked queue ($O(1)$ enqueue/dequeue).
- `MyHashTable<K, V>`: Separate-chaining hash table with load-factor monitoring and re-hashing.
- `Graph`: Adjacency list representation using `MyLinkedList`.
`java.util.Scanner` is used exclusively for console user input, and `java.util.concurrent.ForkJoinPool` is used for hardware thread pool dispatch in CO6.

---

## 2. String Algorithms & Text Indexing (CO2)

### Q3: Explain how KMP avoids backtracking on the text string.
**Answer:**
KMP constructs the $\pi$-table (Longest Proper Prefix which is also a Suffix, LPS) in $O(M)$ preprocessing time. If a mismatch occurs at index $j$ of the pattern while matching text character $i$, the algorithm knows that the prefix of length $\pi[j - 1]$ matches the text immediately preceding $i$. It resets the pattern pointer to $\pi[j - 1]$ without ever decrementing the text pointer $i$. This guarantees $O(N + M)$ time and at most $2N$ comparisons.

### Q4: How does Aho-Corasick achieve linear multi-pattern matching?
**Answer:**
Aho-Corasick builds a Trie containing all dictionary keywords ($O(\sum M_k)$). It computes BFS **failure transitions** (analogous to KMP's LPS) and **dictionary suffix links**. While streaming the review text of length $N$, it transitions between states in $O(1)$ amortized time. Every state reached with matching patterns reports all matched keywords via suffix link traversal. Total time is $O(N + \sum M_k + Z)$ where $Z$ is the count of matches, performing all matches in a single stream pass.

### Q5: What makes SA-IS (Suffix Array Induced Sorting) linear in time?
**Answer:**
Standard prefix doubling takes $O(N \log^2 N)$ or $O(N \log N)$. SA-IS (Nong, Zhang, Chan) classifies characters as S-type (smaller than next) or L-type (larger than next). It identifies Left-Most S-type (LMS) suffixes, recursively sorts LMS substrings, and induces the sorted order of all L-type and S-type suffixes via bucket pointers in strictly two linear passes ($O(N)$ time and space).

---

## 3. Advanced Dynamic Programming (CO3)

### Q6: Differentiate between Levenshtein and Damerau-Levenshtein distance.
**Answer:**
Classical Levenshtein permits 3 operations: Insertion, Deletion, and Substitution. Damerau-Levenshtein adds a 4th operation: **Adjacent Character Transposition** ($s[i-1] == t[j] \land s[i] == t[j-1]$) with cost 1:
$$DP[i][j] = \min(DP[i-1][j]+1, DP[i][j-1]+1, DP[i-1][j-1]+\text{cost}, DP[i-2][j-2]+1)$$
This models over 80% of human typographic slips (e.g., typing "iphoen" instead of "iphone").

### Q7: Explain the Tree Rerooting DP technique used for logistics hubs.
**Answer:**
A naive approach runs BFS/DFS from every node to find total tree distances in $O(V^2)$ time. Tree Rerooting achieves this in $O(V)$ time using two DFS passes:
1. **DFS 1 (Bottom-Up):** For an arbitrary root, computes subtree sizes $size[u]$ and subtree total distances $distDown[u]$.
2. **DFS 2 (Top-Down Rerooting):** When moving the root from parent $u$ to child $v$, all nodes in $v$'s subtree become 1 edge closer, while all nodes outside become 1 edge further:
   $$totalDist[v] = totalDist[u] - size[v] + (|V| - size[v])$$
This computes the exact distance sum for all $|V|$ candidates in $O(V)$ time.

### Q8: What is SOS DP (Sum Over Subsets) and why is it faster than naive submask iteration?
**Answer:**
To compute $F(\text{mask}) = \sum_{i \subseteq \text{mask}} A[i]$, iterating over all submasks for all masks takes $\sum_{k=0}^N \binom{N}{k} 2^k = 3^N$ steps. SOS DP uses multidimensional prefix sums, treating each of the $N$ bits as an independent dimension. Transitioning bit by bit bounds the operations to $N \cdot 2^N$. For $N=16$, $N \cdot 2^N \approx 10^6$ operations vs $3^{16} \approx 4.3 \cdot 10^7$ ($\approx 41\times$ faster).

---

## 4. Network Flow & Logistics Optimization (CO4)

### Q9: State the Max-Flow Min-Cut Theorem. How is the minimum cut extracted?
**Answer:**
**Theorem:** In any flow network, the maximum flow from source $s$ to sink $t$ is equal to the minimum capacity among all $s$-$t$ cuts.  
**Extraction:** After computing maximum flow, execute BFS starting from $s$ in the residual graph $G_f$, visiting only edges with residual capacity $c_f(u, v) > 0$. The set of visited vertices forms partition $S$, and unvisited vertices form $T = V \setminus S$. The cut edges are all original edges $(u, v)$ where $u \in S$ and $v \in T$, whose capacities sum exactly to $|f^*|$.

### Q10: Why does Edmonds-Karp have $O(V \cdot E^2)$ complexity while Dinic has $O(V^2 \cdot E)$?
**Answer:**
- **Edmonds-Karp:** Uses BFS to find the shortest augmenting path. In each augmentation, at least one edge becomes critical (saturated). An edge can become critical at most $V/2$ times, giving $O(V \cdot E)$ augmentations, each costing $O(E)$ for BFS $\implies O(V \cdot E^2)$.
- **Dinic:** Uses BFS to build a Level Graph $G_L$ in $O(E)$, then uses DFS with current-arc optimization to find a blocking flow in $O(V \cdot E)$. Because the distance from $s$ to $t$ in $G_L$ strictly increases in each phase, there are at most $V - 1$ phases $\implies O(V \cdot (E + V \cdot E)) = O(V^2 \cdot E)$.

---

## 5. NP-Completeness, Reductions & Approximations (CO5)

### Q11: Explain the reduction from 3-SAT to CLIQUE.
**Answer:**
Given a 3-CNF formula with $m$ clauses, create a graph $G$ where each clause of 3 literals is represented by 3 vertices ($3m$ total vertices). Add an edge between vertex $u$ and $v$ iff:
1. $u$ and $v$ are in different clause triplets.
2. The literals of $u$ and $v$ are not negations of each other ($u \ne \neg v$).
A satisfying assignment selects at least 1 true literal per clause that are mutually consistent, which forms a clique of size $k = m$ in $G$. The reduction takes polynomial time ($O(m^2)$).

### Q12: Prove that the Maximal Matching algorithm gives a 2-approximation for Vertex Cover.
**Answer:**
1. Let $M$ be the maximal matching selected. The algorithm puts both endpoints of every edge in $M$ into cover $C$, so $|C| = 2 \cdot |M|$.
2. Any valid vertex cover $C^*$ must cover every edge in $M$. Since the edges in $M$ share no common vertices, at least one distinct vertex is needed to cover each edge in $M$, so $|C^*| \ge |M|$.
3. Thus:
   $$|C| = 2 \cdot |M| \le 2 \cdot |C^*| \implies \frac{|C|}{|C^*|} \le 2$$
This guarantees that the computed cover is at most twice the optimal size.

---

## 6. Randomized & Parallel Algorithms (CO6)

### Q13: What is a 2-Universal Hash Family, and why does it protect RetailIQ from DoS attacks?
**Answer:**
A family $\mathcal{H}$ of hash functions is 2-universal if for any two keys $x \ne y$:
$$\Pr_{h \in \mathcal{H}}[h(x) = h(y)] \le \frac{1}{m}$$
RetailIQ uses $h_{a,b}(k) = ((ak + b) \pmod p) \pmod m$ with random $a \in [1, p-1], b \in [0, p-1]$. Because the coefficients are chosen randomly at runtime, an external attacker cannot craft collision keys in advance. The expected length of any collision chain is bounded by $1 + N/m = O(1)$.

### Q14: State Brent's Scheduling Theorem and explain Work and Span.
**Answer:**
- **Work ($T_1$):** Total operations in the computation graph.
- **Span ($T_\infty$):** Critical path length (minimum time with infinite processors).
- **Brent's Theorem:** On $p$ processors with a greedy scheduler:
  $$T_p \le \frac{T_1 - T_\infty}{p} + T_\infty \le \frac{T_1}{p} + T_\infty$$
As long as $p \ll T_1 / T_\infty$ (processors are much fewer than parallelism), speedup is linear: $S_p \approx p$.

### Q15: How does Blelloch Prefix Scan achieve work-efficiency ($O(N)$ work, $O(\log N)$ span)?
**Answer:**
A naive parallel prefix scan does $O(N \log N)$ work. Blelloch achieves $O(N)$ work using a two-pass binary tree:
1. **Up-Sweep (Reduce):** Sums pairs at each level up to root in $\log N$ steps ($N/2 + N/4 + \dots + 1 = N - 1$ additions).
2. **Down-Sweep:** Sets root to 0. At each node, its left child gets the node's current value, and its right child gets (node's value + saved left child sum).
Total additions are $2(N - 1) = O(N)$ (work-efficient) with critical path depth $2 \log N = O(\log N)$ (span).

---

## 7. High-Yield Examiner Trap Questions & Model Answers

| Trap Question | Bad Answer | Model High-Scoring Answer |
| :--- | :--- | :--- |
| *"Can Ford-Fulkerson run forever?"* | *"No, it always stops."* | *"Yes, if capacities are irrational numbers, Ford-Fulkerson can fail to terminate and may converge to an incorrect value. Edmonds-Karp solves this by using BFS, guaranteeing polynomial termination in $O(VE^2)$ regardless of capacity values."* |
| *"Is 2-Approximation Vertex Cover guaranteed to be smaller than the optimal?"* | *"Yes, it's an approximation."* | *"No! It is a minimization problem, so $|C| \ge |C^*|$. The 2-approximation guarantees an upper bound: $|C| \le 2 \cdot |C^*|$."* |
| *"Does Randomized QuickSort have a worst-case of $O(N \log N)$?"* | *"Yes, it's always $O(N \log N)$."* | *"No. Its worst-case running time is still $O(N^2)$, but that depends only on the random number generator's seed, not the input permutation. The probability of hitting worst-case pivot selections is exponentially small ($\le (1/2)^N$)."* |
| *"Why not use Floyd-Warshall instead of Held-Karp for TSP?"* | *"Floyd-Warshall is faster."* | *"Floyd-Warshall solves All-Pairs Shortest Paths in $O(V^3)$, but TSP requires visiting every vertex in a single simple cycle, which is NP-hard. Held-Karp uses Bitmask DP to solve TSP in $O(2^N \cdot N^2)$, which is exact and optimal."* |
