# RetailIQ - Review-1 Comprehensive Viva Preparation Guide

**Target Evaluation:** Module 1–3 (CO1–CO3)  
**Total Marks:** 60 Marks  
**Environment:** Standalone Java Console Application (Eclipse IDE)

---

## 1. Evaluation Rubric & Mark Distribution

| Rubric Component | Evaluated Concepts | Target Score |
| :--- | :--- | :--- |
| **1. Problem Understanding & Requirement Mapping (CO1)** | Problem statement, objectives, inputs/outputs, use cases, retail domain mapping to DSA concepts. | **10 Marks** |
| **2. Algorithm Selection & Understanding (CO1–CO3)** | Rationale for algorithm selection, comparison with rejected candidates, trade-off matrices. | **10 Marks** |
| **3. String Algorithm Implementation (CO2)** | Working implementations of Naive, KMP, Z-Algorithm, Rabin-Karp, Aho-Corasick, Suffix Array, SA-IS, Kasai LCP without `java.util.*`. | **10 Marks** |
| **4. Advanced DP Implementation (CO3)** | Working implementations of Levenshtein, Damerau-Levenshtein, Weighted Edit, Needleman-Wunsch, Smith-Waterman, MCM, Optimal BST, Bitmask DP, Held-Karp TSP, Hamiltonian, Set Cover, Tree DP, Rerooting DP, SOS DP. | **10 Marks** |
| **5. Complexity & Algorithmic Analysis (CO2–CO3)** | Asymptotic Time/Space complexities, empirical runtime comparisons, worst vs average cases. | **10 Marks** |
| **6. Individual Viva & Technical Contribution** | Deep line-by-line understanding of code logic, theoretical invariants, and individual contribution. | **10 Marks** |
| **TOTAL** | | **60 Marks** |

---

## 2. Review-1 Live Demonstration Script (Step-by-Step)

When the examiner asks you to demonstrate your project, follow this exact sequence:

### Step 1: Start Application
- In Eclipse: Right-click `retailiq.Main` $\rightarrow$ **Run As $\rightarrow$ Java Application**.
- Highlight to the examiner:
  1. The application starts from `Main.java` in pure console mode.
  2. The custom data structures (`structures/`) and algorithmic engines (`co1/`, `co2/`, `co3/`) strictly adhere to the syllabus restriction and contain **zero imports of `java.util.*` collections**.

### Step 2: Run Automated Verification Suite (Option 11 $\rightarrow$ 1)
- Enter `11`, then enter `1`.
- Point to the console:
  > *"All 37 automated test cases for custom data structures, string algorithms, and dynamic programming pass with 0 failures."*

### Step 3: Demonstrate CO1 Requirement Mapping (Option 11 $\rightarrow$ 2)
- Shows the **Algorithm Selection Matrix**, clearly mapping each retail problem (Catalog search, Review tagging, Typo correction, Bundle selection, Hub location) to its selected algorithm, Big-O bounds, and rationale.

### Step 4: Demonstrate CO2 String Algorithms (Option 4 $\rightarrow$ 7)
- Select option 7 ("Compare All Search Algorithms").
- Enter search pattern: `"phone"`.
- Show the comparison table:
  - Naive, KMP, Rabin-Karp, and Z-Algorithm all find identical match counts.
  - Show real Java execution times in nanoseconds.
  - Explain the LPS array in KMP and why KMP guarantees $O(N + M)$ without text backtracking.

### Step 5: Demonstrate Aho-Corasick Multi-Keyword Mining (Option 5 $\rightarrow$ 4)
- Select option 4 ("Detect Multiple Keywords").
- Demonstrate scanning customer reviews simultaneously for `["delivery", "battery", "quality", "refund", "sound", "price", "broken"]` in a single streaming pass ($O(N + \sum M_k + Z)$).

### Step 6: Demonstrate CO3 Typo Correction (Option 6)
- Enter misspelled word: `"iphoen"`.
- Show that **Classical Levenshtein** assigns cost 2 (1 delete + 1 insert), while **Damerau-Levenshtein** correctly identifies the adjacent key transposition with cost 1, ranking `"Apple iPhone 15"` at the top!

### Step 7: Demonstrate CO3 Product Bundle Optimization (Option 7 $\rightarrow$ 8)
- Enter budget: `50000`.
- Show the Bitmask DP engine evaluating candidate items, adhering strictly to budget, adding cross-selling category synergy bonuses (e.g. Phone + Audio), and outputting the optimal bundle, spend, and remaining balance.

### Step 8: Demonstrate CO3 Logistics Hub Rerooting DP (Option 7 $\rightarrow$ 13)
- Show the 2-pass linear time $O(V)$ Tree Rerooting DP computing the total delivery distance from every store to all other stores, picking the optimal central warehouse.

---

## 3. High-Frequency Viva Questions & Model Answers

### Question 1: Why did you not use `java.util.*` in your algorithmic packages?
**Answer:**
> *"The course syllabus specifies that students must demonstrate fundamental algorithmic understanding by constructing underlying data structures from scratch. In `retailiq.structures`, we implemented our own generic FIFO `MyQueue`, array-backed `MyStack`, doubly-linked `MyLinkedList`, separate-chaining `MyHashTable`, and adjacency-list `Graph`. All string algorithms (KMP, Z, SA-IS) and DP engines directly operate on primitive arrays and custom nodes."*

### Question 2: How does KMP achieve $O(N+M)$ worst-case time without backtracking the text index?
**Answer:**
> *"KMP precomputes the LPS (Longest Proper Prefix which is also a Suffix) array for the pattern in $O(M)$ time. During matching, the text pointer $i$ strictly moves forward. When a mismatch occurs at $P[j] \neq T[i]$, we do not reset $i$. Instead, the prefix property ensures that the first $LPS[j-1]$ characters of the pattern already match the text immediately preceding $T[i]$. Hence, we update $j = LPS[j-1]$ and resume comparison immediately, achieving guaranteed $O(N+M)$ linear time."*

### Question 3: What is the difference between Levenshtein and Damerau-Levenshtein distance?
**Answer:**
> *"Classical Levenshtein supports three operations: Insertion, Deletion, and Substitution. Damerau-Levenshtein introduces a fourth primitive operation: **Transposition of adjacent characters**. In real-world retail queries, over 80% of human typing slips are adjacent swaps (e.g. 'samusng' for 'samsung'). Classical Levenshtein treats this as a deletion followed by an insertion (cost 2), whereas Damerau-Levenshtein treats it as a single transposition (cost 1)."*

### Question 4: How does Aho-Corasick differ from running KMP multiple times?
**Answer:**
> *"If we have a review text of length $N$ and a dictionary of $K$ keywords of average length $M$, running KMP for each keyword takes $O(K \cdot (N + M))$ time, rescanning the text $K$ times. Aho-Corasick constructs a Trie enriched with BFS failure links and dictionary suffix links in $O(\sum M_k \cdot |\Sigma|)$ time. It processes the text in a **single pass** of $O(N + Z)$ time, where $Z$ is the count of matches, by transitioning between states of the finite automaton."*

### Question 5: What is SA-IS and why is it significant compared to standard Suffix Array algorithms?
**Answer:**
> *"Standard suffix array algorithms such as prefix-doubling (Manber-Myers) take $O(N \log^2 N)$ or $O(N \log N)$ time. SA-IS (Suffix Array by Induced Sorting, Nong-Zhang-Chan 2009) is a **strictly linear-time $O(N)$** algorithm. It classifies suffixes into $S$-type and $L$-type, identifies Leftmost $S$-type (LMS) characters, sorts LMS substrings, induces the order of all $L$-suffixes and $S$-suffixes into bucket ends, and recurses on LMS substrings if names are not unique."*

### Question 6: Explain the recurrence relation of Matrix Chain Multiplication (CO3).
**Answer:**
> *"Let $dp[i][j]$ be the minimum number of scalar multiplications needed to multiply matrices $M_i$ through $M_j$. If $i == j$, $dp[i][j] = 0$. For $i < j$, we test every split point $k$ between $i$ and $j-1$:
> $$dp[i][j] = \min_{i \le k < j} \left( dp[i][k] + dp[k+1][j] + p_{i-1} \cdot p_k \cdot p_j \right)$$
> It has $O(N^2)$ states and each state checks $O(N)$ split points, yielding $O(N^3)$ time and $O(N^2)$ space."*

### Question 7: Explain how Bitmask DP works for Product Bundle Optimization.
**Answer:**
> *"Each product subset is represented as an integer bitmask where the $i$-th bit is 1 if product $i$ is included. There are $2^N$ possible states. We iterate from mask 1 to $2^N - 1$. By isolating the lowest set bit ($lsb = mask \ \& \ (-mask)$), we compute the cost and utility of the current mask in $O(1)$ from the submask $mask \oplus lsb$. We also evaluate cross-selling synergy bonuses (e.g. Phone + Headphones). We then select the mask that maximizes utility score subject to $cost[mask] \le Budget$ in $O(2^N \cdot N)$ total time."*

### Question 8: How does Tree Rerooting DP find the optimal logistics hub in $O(V)$ time instead of $O(V^2)$?
**Answer:**
> *"A naive approach runs BFS/DFS from every single node, taking $O(V^2)$ time. Tree Rerooting DP solves this in two DFS passes:
> 1. Pass 1 (Bottom-up): We root the tree arbitrarily at node 0 and calculate subtree size $sz[u]$ and internal distance sum $inDist[u]$.
> 2. Pass 2 (Top-down): When shifting the root from parent $u$ to child $v$, the vertices in $v$'s subtree move 1 unit closer, while the remaining $V - sz[v]$ vertices move 1 unit farther. Thus:
>    $$\text{totalDist}[v] = \text{totalDist}[u] - sz[v] + (V - sz[v])$$
> This allows calculating the total distance for all $V$ potential hubs in $O(V)$ time."*

### Question 9: What is SOS DP and how does it improve over naive submask iteration?
**Answer:**
> *"SOS DP (Sum Over Subsets) computes $F[M] = \sum_{sub \subseteq M} A[sub]$ for every submask $M$ of an $N$-bit mask. The naive submask enumeration visits $\sum \binom{N}{k} 2^k = 3^N$ states. SOS DP uses dynamic programming over the bit dimensions, updating submask sums in-place in $O(N \cdot 2^N)$ time. For $N=16$, this drops the operation count from 43 million to 1 million."*

---

## 4. Team Member Technical Contribution Division

For Review-1 individual viva evaluation, divide the module responsibilities across team members as follows:

- **Team Member 1 (Lead & CO1 Architecture / Core Structures):**
  - Problem definition, system architecture, requirement-to-DSA mapping (`retailiq.co1.AlgorithmDesign`).
  - Custom data structures implementation: `MyQueue`, `MyStack`, `MyLinkedList`, `MyHashTable`, `Graph`.
  - Console menu orchestration and overall test runner (`Review1TestRunner`).

- **Team Member 2 (CO2 String Search & Multi-Pattern Algorithms):**
  - Exact pattern matching: `NaiveSearch`, `KMP` (LPS array, state machine).
  - Multi-pattern search: `AhoCorasick` (Trie, failure transitions, output links).
  - Rolling hash matching: `RabinKarp` (double hashing, rolling shift, collision handling).

- **Team Member 3 (CO2 Text Indexing & Suffix Structures):**
  - Suffix Array construction: Prefix-doubling (`SuffixArray`) and linear-time `SAIS`.
  - Longest Common Prefix: Kasai's algorithm (`KasaiLCP`), Longest Repeated Substring (LRS).
  - Z-Algorithm implementation (`ZAlgorithm`) and repeated review phrase detection.

- **Team Member 4 (CO3 Sequence Alignment & Edit Distance DP):**
  - Query correction: `Levenshtein`, `DamerauLevenshtein` (transposition handling), `WeightedEditDistance` (QWERTY layout).
  - Sequence alignment: `NeedlemanWunsch` (global alignment) and `SmithWaterman` (local alignment).

- **Team Member 5 (CO3 Combinatorial & Advanced DP Optimization):**
  - Combinatorial optimization: `BitmaskDP` (Product Bundle Selection under Budget), `TSP` (Held-Karp), `SetCover`.
  - Tree & Submask DP: `TreeDP` (Hierarchy Independent Set), `RerootingDP` (Logistics Hub Locator), `SOSDP` (Submask Aggregator), `MatrixChain`, `OptimalBST`.
