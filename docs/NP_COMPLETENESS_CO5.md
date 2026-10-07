# RetailIQ — NP-Completeness, Reductions & Approximations (CO5)

## 1. Module Overview & Retail Problem Formulation

Many core optimization challenges in retail management are computationally intractable:
1. **Catalog Compatibility & Bundle Feasibility:** Given customer constraints (e.g., "if buying smartphone, must select either wireless earbuds or protective case, but cannot select incompatible charger voltage"), does a valid configuration exist? (Modeled as **3-SAT**).
2. **Mutually Compatible Product Portfolios:** Finding the largest set of cross-compatible products. (Modeled as **CLIQUE** and **Maximum Independent Set**).
3. **Retail Store Inspector Coverage:** Choosing the minimum number of regional supervisors so that every distribution route between stores is monitored. (Modeled as **Vertex Cover**).
4. **Warehouse Delivery Routing:** Finding the shortest route visiting all stores exactly once. (Modeled as **TSP / Hamiltonian Cycle**).

Since these problems belong to the **NP-Hard / NP-Complete** complexity classes, exact polynomial-time solutions are impossible (assuming $P \ne NP$). RetailIQ demonstrates:
- Exponential exact solvers for constrained problem instances.
- Formal polynomial-time reduction pipelines.
- Guaranteed factor-2 approximation algorithms.

---

## 2. Theoretical Complexity Foundations

### 2.1 The Complexity Classes: P, NP, NP-Complete, and NP-Hard
- **Class P:** Problems decidable by a deterministic Turing machine in polynomial time $O(n^c)$.
- **Class NP:** Decision problems verifiable in polynomial time given a certificate.
- **NP-Hard:** Problems to which every problem in NP can be reduced in polynomial time ($L' \le_P L$).
- **NP-Complete:** Problems that are both in NP and NP-Hard ($L \in NP \land L \in \text{NP-Hard}$).

### 2.2 The Cook-Levin Theorem
- Established that **Boolean Satisfiability (SAT)** is NP-Complete by proving that any non-deterministic polynomial-time computation can be encoded as a Boolean formula in conjunctive normal form (CNF).

---

## 3. Polynomial-Time Reduction Pipeline in RetailIQ

RetailIQ implements and demonstrates the canonical reduction chain:

```mermaid
flowchart LR
    A["3-SAT"] -->|"Gadget Transformation"| B["CLIQUE"]
    B -->|"Graph Complement (G')"| C["INDEPENDENT SET"]
    C -->|"Vertex Set Complement (V \\ S)"| D["VERTEX COVER"]
```

### 3.1 3-SAT to CLIQUE Reduction
- For a 3-CNF formula with $m$ clauses, create a graph $G$ with $3m$ vertices (one for each literal occurrence).
- Add an edge between two vertices $u$ and $v$ iff:
  1. $u$ and $v$ belong to **different** clauses.
  2. $u$ and $v$ are **consistent** (not negations of each other, e.g., $x_1$ and $\neg x_1$).
- **Equivalence:** The formula is satisfiable if and only if $G$ contains a clique of size $k = m$.

### 3.2 CLIQUE to INDEPENDENT SET Reduction
- A set of vertices $S \subseteq V$ forms a **Clique** in $G = (V, E)$ if and only if $S$ forms an **Independent Set** in the complement graph $\overline{G} = (V, \overline{E})$.
- Complexity of transformation: $O(V^2)$ complement graph inversion.

### 3.3 INDEPENDENT SET to VERTEX COVER Reduction
- **Theorem:** A subset $S \subseteq V$ is an Independent Set in $G = (V, E)$ if and only if $V \setminus S$ is a Vertex Cover of $G$.
- **Proof:**
  - Let $S$ be an independent set. For any edge $(u, v) \in E$, both $u$ and $v$ cannot be in $S$. Therefore, at least one endpoint must belong to $V \setminus S$. Thus, $V \setminus S$ covers all edges.
  - Conversely, if $V \setminus S$ is a vertex cover, no edge can connect two vertices in $S$, meaning $S$ is independent.
- **Size Relation:** $\text{VC}^*(G) + \text{IS}^*(G) = |V|$.

---

## 4. Approximation Algorithms: Vertex Cover 2-Approximation

### 4.1 The Need for Approximations
Finding the exact minimum vertex cover is NP-hard ($O(2^V)$). For large retail distribution graphs ($V > 100$), an approximation algorithm running in polynomial time is necessary.

### 4.2 Maximal Matching 2-Approximation Algorithm
Implemented in [`VertexCoverApprox.java`](file:///d:/Java%20class%20notes/RetailIQ/src/retailiq/co5/VertexCoverApprox.java):

```text
Algorithm ApproximateVertexCover(G = (V, E)):
1. C = empty set
2. E' = E
3. While E' is not empty:
     Pick an arbitrary edge (u, v) from E'
     C = C U {u, v}
     Remove from E' all edges incident to either u or v
4. Return C
```

### 4.3 Proof of Approximation Ratio $\alpha = 2$
1. Let $M$ be the set of edges selected in Step 3. By construction, no two edges in $M$ share an endpoint; thus, $M$ is a **matching**.
2. Because $C$ contains both endpoints of every edge in $M$, $|C| = 2 \cdot |M|$.
3. Any valid vertex cover $C^*$ must cover every edge in $M$. Because all edges in $M$ are pairwise vertex-disjoint, no single vertex can cover more than one edge in $M$. Therefore:
   $$|C^*| \ge |M|$$
4. Combining the two relations:
   $$|C| = 2 \cdot |M| \le 2 \cdot |C^*|$$
   $$\frac{|C|}{|C^*|} \le 2$$
5. **Guarantee:** The computed cover is at most twice the size of the true minimum cover.
6. **Time Complexity:** $O(V + E)$ (strictly linear time).

---

## 5. RetailIQ CO5 Implementation Summary

| Class | Algorithm / Role | Complexity | Retail Domain Context |
| :--- | :--- | :--- | :--- |
| `SAT.java` | Exact Boolean CNF Verifier | $O(2^N \cdot M)$ | Product feature compatibility validation |
| `ThreeSAT.java` | 3-Literal CNF Engine | $O(2^N \cdot M)$ | Bundle rule verification |
| `Clique.java` | Maximum Clique Solver | $O(2^V \cdot V^2)$ | Maximum mutually compatible SKU sets |
| `IndependentSet.java` | Independent Set Solver | $O(2^V \cdot V^2)$ | Non-conflicting marketing promos |
| `VertexCover.java` | Exact Minimum Vertex Cover | $O(2^V \cdot V^2)$ | Minimum supervisor placement |
| `ReductionDemo.java` | Reduction Pipeline Visualizer | $O(1)$ | Theoretical viva demonstration |
| `VertexCoverApprox.java` | 2-Approximation (Maximal Matching) | $O(V + E)$ | Fast logistics route monitor placement |
