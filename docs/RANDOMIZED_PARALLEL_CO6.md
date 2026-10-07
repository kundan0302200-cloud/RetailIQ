# RetailIQ — Randomized & Parallel Algorithms (CO6)

## 1. Module Overview & Retail Problem Formulation

High-volume retail operations generate continuous streams of transactions, sales logs, and inventory updates. Standard single-threaded, deterministic algorithms suffer from:
1. **Adversarial Worst-Case Inputs:** Deterministic Quicksort degrades to $O(N^2)$ on pre-sorted inventory SKUs.
2. **Infinite Data Streams:** Transaction logs cannot fit into memory simultaneously for uniform customer sampling.
3. **Hardware Utilization Gaps:** Multi-core CPUs sit idle when executing single-threaded batch revenue aggregations and sorting.

RetailIQ addresses these challenges using **Randomized Algorithms** (Universal Hashing, Randomized QuickSort, Reservoir Sampling, Miller-Rabin) and **Parallel Multithreaded Algorithms** (ForkJoin Parallel Merge Sort, Parallel Reduce, Blelloch Prefix Scan, and Brent's Theorem Bounds).

---

## 2. Randomized Algorithms

### 2.1 Universal Hashing
- **Problem:** Fixed hash functions $h(k) = k \pmod m$ allow adversarial workloads (e.g. SKUs that are multiples of $m$) to collide in a single bucket ($O(N)$ lookup).
- **Universal Family:** A family $\mathcal{H}$ of hash functions from universe $U$ to $\{0, 1, \dots, m-1\}$ is **2-universal** if for any distinct $x \ne y \in U$:
  $$\Pr_{h \in \mathcal{H}}[h(x) = h(y)] \le \frac{1}{m}$$
- **RetailIQ Implementation (`RandomizedHashing.java`, `UniversalHashing.java`):**
  Chooses a large prime $p = 10^9 + 7$. Randomly selects $a \in \{1, 2, \dots, p-1\}$ and $b \in \{0, 1, \dots, p-1\}$:
  $$h_{a,b}(k) = ((a \cdot k + b) \pmod p) \pmod m$$
  This mathematically guarantees that the expected chain length for any SKU lookup in RetailIQ is bounded by $1 + \alpha$ where $\alpha = N/m$ is the load factor ($O(1)$ expected time).

### 2.2 Randomized QuickSort
- **Problem:** Standard Quicksort with first/last element pivot degrades to $O(N^2)$ on already sorted or reverse-sorted retail lists.
- **Randomized Pivot Selection (`RandomizedQuickSort.java`):**
  Picks an index $r \in [\text{low}, \text{high}]$ uniformly at random and swaps it with the pivot before partitioning.
- **Theoretical Guarantee:**
  Regardless of input order, the expected number of comparisons is:
  $$\mathbb{E}[C] = 2n \ln n + O(n) = O(N \log N)$$
  No adversarial input can trigger worst-case $O(N^2)$ behavior.

### 2.3 Reservoir Sampling (Algorithm R)
- **Problem:** Uniformly sampling $k$ transactions from an incoming streaming stream of unknown or unbounded length $N$.
- **Algorithm (`ReservoirSampling.java`):**
  1. Store the first $k$ items in the reservoir array.
  2. For each arriving item $i$ ($i > k$):
     - Pick a random integer $j \in \{0, 1, \dots, i - 1\}$.
     - If $j < k$, replace `reservoir[j]` with item $i$.
- **Mathematical Invariant Proof:**
  By mathematical induction, at step $i$, the probability that any specific item among the first $i$ items is in the reservoir is exactly $\frac{k}{i}$:
  $$\Pr[\text{item } i \text{ enters}] = \frac{k}{i}$$
  For any previous item $x < i$:
  $$\Pr[x \text{ survives}] = \frac{k}{i-1} \cdot \left(1 - \frac{k}{i} \cdot \frac{1}{k}\right) = \frac{k}{i-1} \cdot \frac{i-1}{i} = \frac{k}{i}$$
  Upon stream termination at length $N$, every transaction has an identical probability $k/N$ of being in the sample.

### 2.4 Miller-Rabin Probabilistic Primality Test
- Generates strong probabilistic primes for cryptographic retail tokens and dynamic hash table sizes ($O(k \log^3 N)$ runtime). Error probability for $k$ iterations is bounded by $(1/4)^k \le 10^{-6}$ for $k=10$.

---

## 3. Parallel Algorithms & Work-Span Framework

### 3.1 The Work-Span Model
- **Work ($T_1$):** Total number of operations performed across all processors (sequential running time).
- **Span ($T_\infty$):** The length of the critical path (longest sequence of dependencies).
- **Parallelism ($T_1 / T_\infty$):** Maximum theoretical speedup achievable on an infinite number of processors.

### 3.2 Brent's Scheduling Theorem
- **Theorem:** If a parallel computation has Work $T_1$ and Span $T_\infty$, then on $p$ parallel processors using a greedy scheduler, the running time $T_p$ satisfies:
  $$\frac{T_1}{p} \le T_p \le \frac{T_1 - T_\infty}{p} + T_\infty \le \frac{T_1}{p} + T_\infty$$
- **Theoretical Speedup Bound:**
  $$S_p = \frac{T_1}{T_p} \le \min\left(p, \frac{T_1}{T_\infty}\right)$$
- Implemented and evaluated in [`BrentsTheorem.java`](file:///d:/Java%20class%20notes/RetailIQ/src/retailiq/co6/BrentsTheorem.java).

### 3.3 ForkJoin Multi-Threaded Parallel Merge Sort (`ParallelSort.java`)
- Utilizes Java's `ForkJoinPool` with `RecursiveAction`.
- Breaks arrays into subtasks recursively until size reaches threshold (`SEQUENTIAL_THRESHOLD = 2048`), then executes sequential sort.
- **Work:** $O(N \log N)$
- **Span:** $O(\log^2 N)$
- Demonstrates empirical multi-core speedup on multi-core CPU architectures.

### 3.4 ForkJoin Parallel Reduce (`ParallelReduce.java`)
- Computes aggregation (e.g. total retail revenue across thousands of orders) in parallel using `RecursiveTask<Double>`.
- **Work:** $O(N)$
- **Span:** $O(\log N)$
- **Parallelism:** $O(N / \log N)$

### 3.5 Blelloch Work-Efficient Parallel Prefix Scan (`BlellochScan.java`)
- Computes all prefix sums (e.g., cumulative daily running revenue) in two parallel tree passes:
  1. **Up-Sweep (Reduce):** Builds a balanced summation binary tree from leaves to root in $O(\log N)$ parallel steps.
  2. **Down-Sweep:** Passes cumulative sums downward, replacing root with 0 and computing left/right child values.
- **Total Work:** $O(N)$ (asymptotically work-efficient, identical to sequential scan).
- **Span:** $O(\log N)$.

---

## 4. Master Complexity Table for CO6

| Algorithm | Category | Work ($T_1$) | Span ($T_\infty$) | Parallelism ($T_1 / T_\infty$) | Space Complexity |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Randomized QuickSort** | Randomized | $O(N \log N)$ avg | N/A | N/A | $O(\log N)$ |
| **Universal Hashing** | Randomized | $O(1)$ expected | N/A | N/A | $O(m)$ table size |
| **Reservoir Sampling** | Randomized | $O(N)$ streaming | N/A | N/A | $O(k)$ memory |
| **Miller-Rabin Primality** | Randomized | $O(k \log^3 N)$ | N/A | N/A | $O(1)$ |
| **Parallel Merge Sort** | Parallel | $O(N \log N)$ | $O(\log^2 N)$ | $O(N / \log N)$ | $O(N)$ |
| **Parallel Reduce** | Parallel | $O(N)$ | $O(\log N)$ | $O(N / \log N)$ | $O(\log N)$ stack |
| **Blelloch Prefix Scan** | Parallel | $O(N)$ | $O(\log N)$ | $O(N / \log N)$ | $O(N)$ tree |
| **Brent's Theorem** | Analysis | $O(1)$ | $O(1)$ | N/A | $O(1)$ |
