# RetailIQ — Network Flow & Bipartite Matching (CO4)

## 1. Module Overview & Retail Problem Formulation

In retail logistics and multi-echelon supply chains, moving goods from supply hubs (factories and central distribution hubs) through regional fulfillment centers to consumer-facing retail outlets is constrained by:
- Daily transport capacity on freight lanes (truck throughput, conveyor limits).
- Bottlenecks where demand exceeds bottleneck capacity.
- Optimal assignment of inventory warehouse slots and sorting bays to incoming freight batches.

RetailIQ models these physical logistical constraints as directed flow networks $G = (V, E)$ with capacity function $c: E \to \mathbb{R}^+$ and source $s$, sink $t$.

---

## 2. Theoretical Foundations & Implemented Algorithms

### 2.1 Ford-Fulkerson Method & Residual Networks
- **Core Principle:** Iteratively finds an augmenting path from $s$ to $t$ in the residual graph $G_f$ where residual capacity $c_f(u, v) = c(u, v) - f(u, v) > 0$.
- **Augmentation:** Pushes bottleneck flow $\Delta = \min_{(u,v) \in P} c_f(u, v)$ along the path.
- **Time Complexity:** $O(E \cdot |f^*|)$ where $|f^*|$ is the maximum flow value (dependent on capacity magnitudes).

### 2.2 Edmonds-Karp Algorithm
- **Core Principle:** Specialization of Ford-Fulkerson choosing the *shortest* augmenting path (in terms of edge count) using Breadth-First Search (BFS).
- **Theoretical Guarantee:** Prevents cycling and guarantees convergence even with irrational capacities.
- **Time Complexity:** $O(V \cdot E^2)$ independent of capacity values.
- **Key Lemma:** Shortest path distance $\delta_f(s, v)$ in $G_f$ increases monotonically with each augmentation.

### 2.3 Dinic's Blocking Flow Algorithm
- **Core Principle:** Deconstructs the residual network into a **Level Graph** $G_L$ using BFS (where edges only connect vertex $u$ to vertex $v$ if $\text{level}[v] = \text{level}[u] + 1$).
- **Blocking Flow:** Pushes flows along multiple paths in $G_L$ using Depth-First Search (DFS) with pointer pruning until no path remains from $s$ to $t$.
- **Time Complexity:** 
  - General networks: $O(V^2 \cdot E)$
  - Unit capacity networks: $O(E \cdot \sqrt{V})$
  - Bipartite matching networks: $O(E \cdot \sqrt{V})$

### 2.4 Max-Flow Min-Cut Theorem
- **Theorem:** For any network, the maximum flow value from $s$ to $t$ equals the minimum capacity of an $s$-$t$ cut $(S, T)$ where $s \in S$ and $t \in T$:
  $$\max |f| = \min_{(S, T)} \sum_{u \in S, v \in T} c(u, v)$$
- **Supply Chain Application in RetailIQ:** 
  Identifies the exact physical bottleneck links between distribution centers and stores. If maximum shipping capacity is capped, upgrading any edge *outside* the minimum cut yields zero throughput improvement. Upgrading edges spanning the cut directly expands daily retail throughput.

### 2.5 Maximum Bipartite Matching
- **Formulation:** Given bipartite graph $G = (L \cup R, E)$ representing $L$ warehouse arrival batches and $R$ loading docks.
- **Reduction to Max-Flow:** Construct super-source $s$ with unit capacity edges to all $u \in L$, directed edges of capacity 1 from $L$ to $R$, and unit edges from all $v \in R$ to super-sink $t$.
- **Integrality Theorem:** If capacities are integers, max flow values are integral, yielding a valid maximum cardinality matching.

---

## 3. Asymptotic Complexity Comparison Matrix

| Algorithm | Method | Level Graph? | Time Complexity | Space Complexity | Best Suited For |
| :--- | :--- | :---: | :---: | :---: | :--- |
| **Ford-Fulkerson** | DFS Augmentation | No | $O(E \cdot \|f^*\|)$ | $O(V + E)$ | Small integer capacities |
| **Edmonds-Karp** | BFS Shortest Path | No | $O(V \cdot E^2)$ | $O(V + E)$ | Arbitrary capacities, simple graphs |
| **Dinic's Algorithm** | BFS Levels + DFS Blocking | **Yes** | $O(V^2 \cdot E)$ | $O(V + E)$ | Dense supply-chain logistics networks |
| **Min-Cut Extraction** | Residual Reachability BFS | No | $O(V + E)$ post-flow | $O(V)$ | Locating supply-chain bottlenecks |
| **Bipartite Matching** | Augmenting Paths / Flow | No | $O(V \cdot E)$ | $O(V + E)$ | Dock and worker slot allocation |

---

## 4. RetailIQ Implementation Architecture

```text
retailiq.co4/
├── FlowEdge.java           <-- Directed capacity, flow, residual capacity & reverse edge pointer
├── FlowGraph.java          <-- Adjacency list representation using MyLinkedList<FlowEdge>
├── FordFulkerson.java      <-- DFS residual path finding
├── EdmondsKarp.java        <-- BFS shortest augmenting path
├── Dinic.java              <-- Level graph BFS + pointer-cached DFS blocking flow
├── MinCut.java             <-- Residual BFS reachability partitioning (S, T)
└── BipartiteMatching.java  <-- Warehouse bay matching
```

---

## 5. Typical Viva Questions & Answers (CO4)

**Q1: What is a residual edge, and why is backward flow necessary?**  
*Answer:* A forward residual edge $u \to v$ has capacity $c(u, v) - f(u, v)$. A backward residual edge $v \to u$ has capacity $f(u, v)$. Backward edges allow the algorithm to "undo" suboptimal flow decisions made in earlier augmentations, guaranteeing global optimality upon termination.

**Q2: Why is Dinic asymptotically faster than Edmonds-Karp?**  
*Answer:* Edmonds-Karp runs a complete BFS search for each augmenting path, finding at most one path per $O(E)$ search ($O(V \cdot E^2)$ total). Dinic builds the BFS level graph once per phase and pushes multiple augmenting paths concurrently via DFS blocking flows, bounding the number of phases to at most $V - 1$, resulting in $O(V^2 \cdot E)$ time.
