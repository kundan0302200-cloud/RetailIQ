# RetailIQ — Final Project Demonstration Script & Complete CO Master Guide

> **Project Identity:** RetailIQ — Intelligent Retail Analytics Platform  
> **Course:** Data Structures & Algorithms (DSA-3)  
> **Target Evaluation:** Final Project Demonstration & Technical Viva (60 Marks)  
> **Repository:** `d:\Java class notes\RetailIQ`

---

## Table of Contents
1. [Live Demonstration Script (Step-by-Step with Spoken Dialogues)](#1-live-demonstration-script)
2. [Course Outcome (CO) Concept, Purpose & Rationale Guide](#2-course-outcome-co-concept-purpose--rationale-guide)
   - [CO1: System Architecture & Custom Data Structures](#co1-system-architecture--custom-data-structures)
   - [CO2: String Algorithms & Text Indexing](#co2-string-algorithms--text-indexing)
   - [CO3: Advanced Dynamic Programming](#co3-advanced-dynamic-programming)
   - [CO4: Network Flow & Logistics Optimization](#co4-network-flow--logistics-optimization)
   - [CO5: NP-Completeness, Reductions & Approximations](#co5-np-completeness-reductions--approximations)
   - [CO6: Randomized & Parallel Algorithms](#co6-randomized--parallel-algorithms)
3. [Top 5 Examiner Defense Questions & Model Answers](#3-top-5-examiner-defense-questions--model-answers)
4. [Master Asymptotic Complexity Table](#4-master-asymptotic-complexity-table)

---

# 1. Live Demonstration Script

*Follow this exact step-by-step sequence during your project evaluation.*

---

### Step 0: The Opening Pitch & Course Rules Compliance (1 Minute)

> **What to Say:**  
> *"Good morning/afternoon, professors. Today we are presenting **RetailIQ — an Intelligent Retail Analytics and Optimization Platform** designed entirely in Java.*  
>  
> *Before we demonstrate our algorithmic engines, we would like to emphasize our strict compliance with the DSA-3 course regulations:  
> **We have used ZERO classes from the `java.util.*` collections framework in our core engines.**  
> Every foundational structure — `MyQueue`, `MyStack`, `MyLinkedList`, `MyHashTable`, and `Graph` — was engineered from first principles from scratch.*  
>  
> *Now, let us begin by running our automated verification suite to prove the operational correctness of all 6 Course Outcomes."*

---

### Step 1: Execute Automated Verification Test Suite (CO1–CO6)

**Terminal Command:**
```powershell
java -cp bin retailiq.test.FinalTestRunner
```

> **What to Say:**  
> *"Here, our `FinalTestRunner` executes 45 comprehensive automated unit assertions covering:*  
> - *Custom generic data structures with zero library dependencies.*  
> - *CO2 string indexing algorithms (including linear SA-IS suffix array and Kasai LCP).*  
> - *CO3 advanced dynamic programming (Held-Karp TSP, SOS DP, Matrix Chain Multiplication).*  
> - *CO4 network flow (Dinic, Edmonds-Karp, and Max-Flow Min-Cut verification).*  
> - *CO5 3-SAT constraint verification and Vertex Cover 2-approximation bounds.*  
> - *CO6 multi-threaded ForkJoin parallel reductions, parallel sorts, and universal hashing.*  
>  
> *All 45 test assertions pass cleanly with 100% success."*

---

### Step 2: Customer Query Typo Correction (CO2 & CO3)

**Terminal Command:**
```powershell
java -cp bin retailiq.Main
```
**Menu Action:** Type **`6`** *(Query Correction & Auto-Suggest)* $\rightarrow$ Enter typo: **`iphoen`** or **`airpds`**

> **What to Say:**  
> *"In e-commerce search, over 25% of customer queries contain typing slips.  
> We evaluate two algorithms here:*  
> 1. *Classical Levenshtein (Wagner-Fischer): Handles insertion, deletion, and substitution.*  
> 2. *Damerau-Levenshtein: Adds adjacent character transpositions with cost 1.*  
>  
> *When a customer types `'iphoen'`, standard Levenshtein reports distance 2 (substituting 'o' and 'e'), whereas Damerau-Levenshtein recognizes the single adjacent key inversion and calculates distance 1, immediately suggesting `'iPhone 15'` as the top match."*

---

### Step 3: Multi-Keyword Feedback Mining in Reviews (CO2)

**Menu Action:** Type **`5`** *(Review Analysis)* $\rightarrow$ Type **`4`** *(Detect Multiple Keywords via Aho-Corasick)*

> **What to Say:**  
> *"Customer sentiment analysis requires scanning thousands of review comments against catalogs of positive and negative keywords ('battery', 'refund', 'broken', 'delivery', etc.).  
> Instead of running $K$ individual searches with KMP which would cost $O(K \cdot N)$, we construct an **Aho-Corasick Trie Automaton** with BFS failure transitions and dictionary suffix links.  
> It scans the entire review corpus in a **single streaming pass** in $O(N + \sum M_k + Z)$ time, detecting all keyword occurrences simultaneously."*

---

### Step 4: Supply Chain Logistics & Max Flow / Min Cut (CO4)

**Menu Action:** Type **`0`** (Back) $\rightarrow$ Type **`8`** *(Inventory Distribution)* $\rightarrow$ Type **`2`** *(Max Flow Distribution)* $\rightarrow$ Then Type **`3`** *(Min-Cut Bottleneck)*

> **What to Say:**  
> *"In multi-echelon retail supply chains, moving stock from Central Warehouses through Distribution Centers to Retail Stores is constrained by daily transport capacities.  
> We implemented both **Edmonds-Karp ($O(V \cdot E^2)$)** and **Dinic’s Algorithm ($O(V^2 \cdot E)$)** using level graphs and DFS blocking flows.  
> Furthermore, by applying the **Max-Flow Min-Cut Theorem**, we extract the exact saturated bottleneck links. This informs retail managers that upgrading capacity on non-cut routes provides zero throughput gain, whereas upgrading cut edges directly increases daily shipped volume."*

---

### Step 5: High-Margin Product Bundle Optimization (CO3)

**Menu Action:** Type **`0`** (Back) $\rightarrow$ Type **`7`** *(Product Bundle Optimization)* $\rightarrow$ Enter budget (e.g., **`30000`**)

> **What to Say:**  
> *"Retailers make higher margins by selling curated product kits rather than individual items. However, items have non-linear synergy bonuses (e.g., buying a Phone with Headphones and Case yields higher customer utility).  
> A greedy approach fails to find the global optimum. We implemented a **Bitmask Dynamic Programming engine** with state $DP[\text{mask}]$, representing subsets as binary integers. In $O(2^N \cdot N)$ time, it computes the mathematically guaranteed highest-utility bundle within the customer's budget."*

---

### Step 6: Product Compatibility Constraints & Reductions (CO5)

**Menu Action:** Type **`0`** (Back) $\rightarrow$ Type **`9`** *(Product Constraint Analysis)* $\rightarrow$ Type **`2`** *(3-SAT Solver)* $\rightarrow$ Then Type **`3`** *(Vertex Cover 2-Approximation)*

> **What to Say:**  
> *"When configuring custom PC builds or appliance packages, compatibility constraints arise (e.g., Component A requires B or C, but cannot co-exist with D).  
> This is modeled as **3-SAT**, which is NP-Complete by the Cook-Levin theorem.  
> We demonstrate the formal polynomial-time reduction pipeline: `3-SAT -> CLIQUE -> Independent Set -> Vertex Cover`.  
> For large route monitoring instances where exact vertex cover is intractable ($O(2^V)$), we execute a **Maximal Matching 2-Approximation Algorithm** running in $O(V + E)$ linear time with a provable guarantee $|C| \le 2 \cdot |C^*|$."*

---

### Step 7: Multi-Core Concurrency & Empirical Benchmarks (CO6)

**Menu Action:** Type **`0`** (Back) $\rightarrow$ Type **`11`** *(Algorithm Demonstrations)* $\rightarrow$ Type **`3`** *(Run Multi-Core Parallel Empirical Benchmarks)*

> **What to Say:**  
> *"To handle massive retail transaction streams, we avoid single-threaded bottlenecks by implementing genuine multi-threaded parallel algorithms using Java's `ForkJoinPool`:  
> 1. **Parallel Merge Sort:** Divides arrays across CPU cores down to a threshold, achieving Span $O(\log^2 N)$ and empirical multi-core speedup.  
> 2. **Parallel Reduce:** Aggregates transaction revenues across cores in a parallel binary tree with Span $O(\log N)$ and Work $O(N)$.  
> 3. **Blelloch Work-Efficient Scan:** Computes running cumulative daily revenue in two parallel passes (Up-Sweep and Down-Sweep) in $O(N)$ work and $O(\log N)$ span.  
> 4. **Brent’s Scheduling Theorem:** Models theoretical execution bounds $T_p \le T_1/p + T_\infty$ across processor counts."*

---

### Step 8: Conclusion & Viva Readiness (30 Seconds)

> **What to Say:**  
> *"In summary, RetailIQ provides a mathematically rigorous, fully functional retail analytics suite mapping every major DSA-3 theoretical concept into real-world business applications with zero library dependencies. We are now open for technical questions."*

---

# 2. Course Outcome (CO) Concept, Purpose & Rationale Guide

---

## CO1: System Architecture & Custom Data Structures

- **Concepts Used:** Custom generic `MyQueue`, `MyStack`, `MyLinkedList`, `MyHashTable`, and `Graph`.
- **Retail Purpose:** Forms the memory backbone for catalog caching, order transaction queues, and network graphs.
- **Why We Chose It:** Course guidelines strictly prohibit `java.util.*` collections inside algorithmic engines. We implemented:
  - `MyLinkedList<T>`: Generic Doubly Linked List with head/tail pointers ($O(1)$ add/remove).
  - `MyStack<T>`: Dynamic array LIFO stack with geometric doubling ($O(1)$ amortized push/pop).
  - `MyQueue<T>`: Linked FIFO queue ($O(1)$ enqueue/dequeue).
  - `MyHashTable<K, V>`: Separate-chaining hash table with load factor monitoring and prime modulus hashing.
- **Complexity:** Stack/Queue operations: $O(1)$. Hash table lookup: $O(1)$ average, $O(N)$ worst. Space: $O(N)$.

---

## CO2: String Algorithms & Text Indexing

### 1. KMP (Knuth-Morris-Pratt)
- **Retail Purpose:** Fast catalog search for product titles and descriptions.
- **Why We Chose It:** Naive string search degrades to $O(N \cdot M)$ on repetitive text. KMP precomputes the $\pi$ (LPS) table in $O(M)$ time and never backtracks on the text pointer $i$, strictly guaranteeing $O(N + M)$ comparisons.
- **Complexity:** Time: $O(N + M)$, Space: $O(M)$.

### 2. Z-Algorithm
- **Retail Purpose:** Autocomplete query suggestions and prefix matching.
- **Why We Chose It:** Computes the Z-box in a single linear pass $O(N + M)$ without building explicit state machines, making it ideal for real-time keystroke suggestions.
- **Complexity:** Time: $O(N + M)$, Space: $O(N + M)$.

### 3. Rabin-Karp (Double Hashing)
- **Retail Purpose:** SKU code and serial number multi-pattern detection.
- **Why We Chose It:** Uses polynomial rolling hash. We implemented **Double Hashing** (moduli $10^9+7$ and $10^9+9$) to reduce false-positive collision probability to $\approx 10^{-18}$, avoiding spurious character comparisons.
- **Complexity:** Time: $O(N + M)$ expected, $O(N \cdot M)$ worst. Space: $O(1)$.

### 4. Aho-Corasick Automaton
- **Retail Purpose:** Multi-keyword sentiment mining in customer review texts.
- **Why We Chose It:** Scanning $K$ keywords using KMP costs $O(K \cdot (N + M))$. Aho-Corasick constructs a single Trie with BFS failure transitions and dictionary suffix links, scanning the entire review corpus in **one streaming pass** ($O(N + \sum M_k + Z)$).
- **Complexity:** Time: $O(N + \sum M_k + Z)$, Space: $O(\sum M_k \cdot |\Sigma|)$.

### 5. Suffix Array (SA-IS Linear Time)
- **Retail Purpose:** Full-text catalog indexing and substring pattern matching.
- **Why We Chose It:** Suffix arrays require $4\times$ less memory than suffix trees. The Nong-Zhang-Chan (SA-IS) algorithm achieves strictly linear $O(N)$ time by classifying suffixes into S-type/L-type and using LMS induced bucket sorting.
- **Complexity:** Time: $O(N)$ strictly linear, Space: $O(N)$.

### 6. Kasai's LCP Algorithm
- **Retail Purpose:** Finding repeated phrases and duplicate review text.
- **Why We Chose It:** Computes the Longest Common Prefix (LCP) array from the suffix array in linear $O(N)$ time utilizing the height inequality $LCP[rank[i]] \ge LCP[rank[i - 1]] - 1$.
- **Complexity:** Time: $O(N)$, Space: $O(N)$.

---

## CO3: Advanced Dynamic Programming

### 1. Damerau-Levenshtein Distance
- **Retail Purpose:** User search typo correction.
- **Why We Chose It:** Standard Levenshtein handles only insertions, deletions, and substitutions. Damerau-Levenshtein adds adjacent character transpositions with cost 1, capturing $>80\%$ of human typing slips (e.g., "iphoen" $\to$ "iphone").
- **Complexity:** Time: $O(M \cdot N)$, Space: $O(M \cdot N)$.

### 2. Needleman-Wunsch & Smith-Waterman
- **Retail Purpose:** Global SKU attribute alignment and local review plagiarism detection.
- **Why We Chose It:** Needleman-Wunsch aligns entire sequences using affine gap penalties. Smith-Waterman clamps negative scores to 0, isolating shared sub-phrases embedded in differing texts.
- **Complexity:** Time: $O(M \cdot N)$, Space: $O(M \cdot N)$.

### 3. Matrix Chain Multiplication (MCM)
- **Retail Purpose:** Query optimizer for multidimensional sales cube joins.
- **Why We Chose It:** Computes parenthesization that minimizes scalar matrix operations when joining analytical database tables.
- **Complexity:** Time: $O(N^3)$, Space: $O(N^2)$.

### 4. Optimal Binary Search Tree (OBST)
- **Retail Purpose:** Search index organized by product search frequencies.
- **Why We Chose It:** Places high-frequency search keywords closer to the root, minimizing expected search depth $\sum p_i \cdot \text{depth}(k_i)$.
- **Complexity:** Time: $O(N^3)$, Space: $O(N^2)$.

### 5. Bitmask DP Product Bundling
- **Retail Purpose:** High-margin product kit optimization under customer budget.
- **Why We Chose It:** Greedy bundle selection fails due to non-linear synergy bonuses between complementary products. Bitmask DP evaluates all $2^N$ subsets exactly.
- **Complexity:** Time: $O(2^N \cdot N)$, Space: $O(2^N)$.

### 6. Held-Karp TSP & Hamiltonian Path
- **Retail Purpose:** Exact courier delivery route optimization.
- **Why We Chose It:** Reduces brute-force permutation search from $O(N!)$ to $O(2^N \cdot N^2)$ using subset state $DP[\text{mask}][u]$.
- **Complexity:** Time: $O(2^N \cdot N^2)$, Space: $O(2^N \cdot N)$.

### 7. Tree Rerooting DP
- **Retail Purpose:** Finding the optimal central logistics hub in a retail store hierarchy.
- **Why We Chose It:** Naive all-pairs BFS costs $O(V^2)$. Tree Rerooting computes sum of distances from all candidate hubs in **two DFS passes** ($O(V)$ total).
- **Complexity:** Time: $O(V)$, Space: $O(V)$.

### 8. SOS DP (Sum Over Subsets)
- **Retail Purpose:** Fast aggregation across customer preference sub-profiles.
- **Why We Chose It:** Naive submask iteration takes $3^N$ steps. SOS DP uses multidimensional prefix sums along bit dimensions, bounding operations to $N \cdot 2^N$ ($41\times$ faster for $N=16$).
- **Complexity:** Time: $O(N \cdot 2^N)$, Space: $O(2^N)$.

---

## CO4: Network Flow & Logistics Optimization

### 1. Ford-Fulkerson Method
- **Retail Purpose:** Baseline freight flow augmentation.
- **Why We Chose It:** Introduces residual graphs ($c_f(u,v) = c(u,v) - f(u,v)$) and backward edge augmentation to correct earlier suboptimal routing decisions.
- **Complexity:** Time: $O(E \cdot |f^*|)$, Space: $O(V + E)$.

### 2. Edmonds-Karp Algorithm
- **Retail Purpose:** Guaranteed polynomial max-flow for arbitrary capacities.
- **Why We Chose It:** Uses BFS to select shortest augmenting paths. Prevents infinite looping on irrational capacities and guarantees polynomial $O(V \cdot E^2)$ runtime.
- **Complexity:** Time: $O(V \cdot E^2)$, Space: $O(V + E)$.

### 3. Dinic's Algorithm
- **Retail Purpose:** Multi-echelon logistics network daily throughput maximization.
- **Why We Chose It:** Constructing a BFS Level Graph once per phase and using DFS blocking flows with current-arc pointer pruning makes Dinic strictly faster on dense networks ($O(V^2 \cdot E)$ vs $O(V \cdot E^2)$).
- **Complexity:** Time: $O(V^2 \cdot E)$, Space: $O(V + E)$.

### 4. Max-Flow Min-Cut Theorem
- **Retail Purpose:** Isolating physical supply chain bottlenecks.
- **Why We Chose It:** Max flow equals minimum cut capacity. Performing BFS reachability from $s$ in the residual graph partitions the network into $(S, T)$, exposing the exact saturated links where adding truck capacity increases throughput.
- **Complexity:** Time: $O(V + E)$ post-flow, Space: $O(V)$.

### 5. Maximum Bipartite Matching
- **Retail Purpose:** Optimal assignment of freight arrival batches to warehouse unloading bays.
- **Why We Chose It:** Reduces bipartite matching to max-flow with unit capacities. Integrality theorem guarantees valid integer assignments.
- **Complexity:** Time: $O(V \cdot E)$, Space: $O(V + E)$.

---

## CO5: NP-Completeness, Reductions & Approximations

### 1. 3-SAT Solver
- **Retail Purpose:** Checking product compatibility constraints in customizable bundles.
- **Why We Chose It:** Foundational NP-Complete problem that verifies whether a set of component compatibility clauses can be simultaneously satisfied.
- **Complexity:** Time: $O(2^N \cdot M)$, Space: $O(N + M)$.

### 2. Reduction Pipeline (`ReductionDemo.java`)
- **Retail Purpose:** Demonstrating theoretical equivalence among NP-Complete problems.
- **Why We Chose It:** Implements the polynomial-time chain: `3-SAT -> CLIQUE -> Independent Set -> Vertex Cover`.
- **Complexity:** Polynomial $O(V^2)$ transformations.

### 3. Clique & Independent Set
- **Retail Purpose:** Finding largest mutually compatible item clusters.
- **Why We Chose It:** Demonstrates the graph complement theorem: $S$ is a Clique in $G \iff S$ is an Independent Set in $\overline{G}$.
- **Complexity:** Time: $O(2^V \cdot V^2)$, Space: $O(V)$.

### 4. Vertex Cover 2-Approximation (`VertexCoverApprox.java`)
- **Retail Purpose:** Placement of route supervisors on retail delivery networks ($V > 100$).
- **Why We Chose It:** Finding exact vertex cover is NP-hard. We implemented a **Maximal Matching** approximation running in linear time $O(V + E)$. By taking both endpoints of edges in maximal matching $M$, $|C| = 2|M| \le 2|C^*|$, guaranteeing the result is at most $2\times$ optimal.
- **Complexity:** Time: $O(V + E)$ linear, Approximation Ratio: $\alpha = 2$.

---

## CO6: Randomized & Parallel Algorithms

### 1. Randomized QuickSort
- **Retail Purpose:** Sorting transaction logs and sales ranking without worst-case degradation.
- **Why We Chose It:** Standard Quicksort degrades to $O(N^2)$ on pre-sorted inventory SKUs. Choosing a random pivot guarantees expected comparisons $\mathbb{E}[C] = 2N \ln N + O(N)$ regardless of input order.
- **Complexity:** Time: $O(N \log N)$ expected, Space: $O(\log N)$.

### 2. 2-Universal Hashing Family
- **Retail Purpose:** Collision-resistant SKU in-memory indexing.
- **Why We Chose It:** Fixed hash functions $h(k) = k \pmod m$ permit adversarial inputs to collapse into a single bucket ($O(N)$ lookup). Choosing $h_{a,b}(k) = ((ak + b) \pmod p) \pmod m$ guarantees collision probability $\Pr[h(x)=h(y)] \le 1/m$.
- **Complexity:** Time: $O(1)$ expected lookup, Space: $O(m)$.

### 3. Reservoir Sampling (Algorithm R)
- **Retail Purpose:** Uniform customer sampling from continuous transaction streams.
- **Why We Chose It:** Continuous sales streams have unbounded or unknown length $N$. Reservoir sampling maintains $k$ items such that at step $i$, each item has exact probability $k/i$ of being retained.
- **Complexity:** Time: $O(N)$ single pass, Space: $O(k)$ memory.

### 4. Multi-Threaded ForkJoin Parallel Merge Sort
- **Retail Purpose:** High-throughput batch sorting across multi-core CPU processors.
- **Why We Chose It:** Single-threaded sorting leaves modern multi-core processors underutilized. We use Java's `ForkJoinPool` with `RecursiveAction`, recursively dividing work above threshold $2048$ to achieve true hardware parallelism.
- **Work ($T_1$):** $O(N \log N)$, **Span ($T_\infty$):** $O(\log^2 N)$, **Parallelism:** $O(N / \log N)$.

### 5. ForkJoin Parallel Reduce
- **Retail Purpose:** Multi-core retail revenue and sales total aggregation.
- **Why We Chose It:** Evaluates a binary reduction tree concurrently using `RecursiveTask<Double>`.
- **Work ($T_1$):** $O(N)$, **Span ($T_\infty$):** $O(\log N)$, **Parallelism:** $O(N / \log N)$.

### 6. Blelloch Work-Efficient Prefix Scan
- **Retail Purpose:** Cumulative running daily revenue and inventory progression.
- **Why We Chose It:** Naive parallel scan requires $O(N \log N)$ operations. Blelloch achieves $O(N)$ work (work-efficient) using a balanced two-pass binary tree (Up-Sweep and Down-Sweep).
- **Work ($T_1$):** $O(N)$, **Span ($T_\infty$):** $O(\log N)$.

### 7. Brent's Scheduling Theorem
- **Retail Purpose:** Modeling theoretical parallel speedup bounds.
- **Why We Chose It:** Quantifies execution time $T_p \le (T_1 - T_\infty)/p + T_\infty \le T_1/p + T_\infty$ across processor counts.
- **Complexity:** Analytical Model.

---

# 3. Top 5 Examiner Defense Questions & Model Answers

### Q1: "Why did you build your own data structures instead of using Java's built-in `ArrayList` or `HashMap`?"
> **Model Answer:**  
> *"Per course regulations, we must demonstrate mastery over low-level memory layout and pointer manipulation. We implemented `MyLinkedList` with explicit bidirectional pointers, `MyStack` with dynamic doubling, `MyQueue` with FIFO node links, and `MyHashTable` with separate-chaining linked bucket arrays. Built-in collections were used only for console input scanning and concurrency dispatch."*

### Q2: "Why is Dinic's algorithm asymptotically superior to Edmonds-Karp?"
> **Model Answer:**  
> *"Edmonds-Karp uses BFS to find a single shortest augmenting path, requiring up to $O(V \cdot E)$ augmentations, each taking $O(E)$ time, leading to $O(V \cdot E^2)$. Dinic constructs a Level Graph using BFS once per phase and pushes multiple augmenting paths concurrently via DFS blocking flows. Because the distance from source to sink in the level graph strictly increases after each phase, there are at most $V - 1$ phases, yielding $O(V^2 \cdot E)$ overall time."*

### Q3: "Prove the 2-approximation ratio for Vertex Cover."
> **Model Answer:**  
> *"Our algorithm computes a maximal matching $M$ and takes both endpoints of every edge in $M$, so $|C| = 2 \cdot |M|$. Since edges in matching $M$ share no vertices, any optimal vertex cover $C^*$ must pick at least one distinct vertex for every edge in $M$, meaning $|C^*| \ge |M|$. Therefore, $|C| = 2 \cdot |M| \le 2 \cdot |C^*| \implies |C| / |C^*| \le 2$."*

### Q4: "What is the difference between Work and Span in parallel computing?"
> **Model Answer:**  
> *"Work ($T_1$) is the total number of operations performed by a sequential single processor. Span ($T_\infty$) is the critical path length—the sequence of operations that must be executed serially due to data dependencies. The ratio $T_1 / T_\infty$ represents the maximum theoretical parallelism. By Brent's theorem, execution time on $p$ processors satisfies $T_p \le T_1/p + T_\infty$."*

### Q5: "How does Reservoir Sampling guarantee uniform probability for an infinite stream?"
> **Model Answer:**  
> *"By induction: at step $i$, item $i$ is kept with probability $k/i$. Any previously retained item survives with probability $(k / (i-1)) \times (1 - (k/i) \times (1/k)) = k/i$. Upon stream completion at length $N$, every single transaction has an identical probability of exactly $k/N$ of being present in the sample."*

---

# 4. Master Asymptotic Complexity Table

| Module | Algorithm | Time / Work ($T_1$) | Span ($T_\infty$) | Space Complexity | RetailIQ Application |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **CO2** | **KMP** | $O(N + M)$ | — | $O(M)$ | Catalog Live Search |
| **CO2** | **Z-Algorithm** | $O(N + M)$ | — | $O(N + M)$ | Prefix Matching & Phrase Search |
| **CO2** | **Rabin-Karp** | $O(N + M)$ avg | — | $O(1)$ | SKU & Code Multi-pattern Match |
| **CO2** | **Aho-Corasick** | $O(N + \sum M_k + Z)$| — | $O(\sum M_k \cdot \|\Sigma\|)$ | Multi-Keyword Review Mining |
| **CO2** | **SA-IS Suffix Array** | $O(N)$ strictly linear | — | $O(N)$ | Linear Catalog Text Indexing |
| **CO2** | **Kasai's LCP** | $O(N)$ | — | $O(N)$ | Repeated Phrases in Reviews |
| **CO3** | **Damerau-Levenshtein** | $O(M \cdot N)$ | — | $O(M \cdot N)$ | Keyboard Transposition Correction |
| **CO3** | **Needleman-Wunsch** | $O(M \cdot N)$ | — | $O(M \cdot N)$ | Global SKU Feature Alignment |
| **CO3** | **Smith-Waterman** | $O(M \cdot N)$ | — | $O(M \cdot N)$ | Local Review Plagiarism Detection |
| **CO3** | **Matrix Chain Mult.** | $O(N^3)$ | — | $O(N^2)$ | Analytical Join Optimization |
| **CO3** | **Bitmask DP Bundle** | $O(2^N \cdot N)$ | — | $O(2^N)$ | Optimal Product Bundle under Budget |
| **CO3** | **Held-Karp TSP** | $O(2^N \cdot N^2)$ | — | $O(2^N \cdot N)$ | Courier Delivery Route Optimization |
| **CO3** | **Tree Rerooting DP** | $O(V)$ | — | $O(V)$ | Central Logistics Hub Placement |
| **CO3** | **SOS DP (Subsets)** | $O(N \cdot 2^N)$ | — | $O(2^N)$ | Customer Profile Submask Aggregator |
| **CO4** | **Edmonds-Karp** | $O(V \cdot E^2)$ | — | $O(V + E)$ | Polynomial Max Flow Distribution |
| **CO4** | **Dinic's Algorithm** | $O(V^2 \cdot E)$ | — | $O(V + E)$ | Supply Chain Max Daily Throughput |
| **CO4** | **Min-Cut Extraction** | $O(V + E)$ | — | $O(V)$ | Supply Chain Bottleneck Detection |
| **CO4** | **Bipartite Matching** | $O(V \cdot E)$ | — | $O(V + E)$ | Warehouse Bay Allocation |
| **CO5** | **3-SAT Solver** | $O(2^N \cdot M)$ | — | $O(N + M)$ | Product Compatibility Constraints |
| **CO5** | **VC 2-Approximation** | $O(V + E)$ linear | — | $O(V)$ | Fast Route Supervisor Placement |
| **CO6** | **Rand QuickSort** | $O(N \log N)$ expected| — | $O(\log N)$ | Sales Ranking Without Adversarial Worst-Case |
| **CO6** | **Universal Hashing** | $O(1)$ expected | — | $O(m)$ | Collision-Free SKU In-Memory Tables |
| **CO6** | **Reservoir Sampling**| $O(N)$ streaming | — | $O(k)$ | Infinite Stream Transaction Sampling |
| **CO6** | **Parallel MergeSort**| $O(N \log N)$ | $O(\log^2 N)$ | $O(N)$ | Multi-Core Batch Transaction Sorting |
| **CO6** | **Parallel Reduce** | $O(N)$ | $O(\log N)$ | $O(\log N)$ | Multi-Core Revenue Aggregation |
| **CO6** | **Blelloch Prefix Scan**| $O(N)$ work-efficient | $O(\log N)$ | $O(N)$ | Work-Efficient Cumulative Revenue Scan |
| **CO6** | **Brent's Theorem** | $O(1)$ | $O(1)$ | $O(1)$ | Theoretical Scheduling Bound Modeling |
