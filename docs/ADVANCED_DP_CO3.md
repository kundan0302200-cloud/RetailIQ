# RetailIQ - Advanced Dynamic Programming (CO3)

This document provides formal dynamic programming state formulations, recurrence equations, boundary conditions, and retail business applications for all 14 CO3 algorithms implemented in RetailIQ.

---

## 1. Classical Levenshtein Distance (Wagner-Fischer)

- **Retail Application:** Customer search typo auto-correction ("Did You Mean?").
- **State:** $dp[i][j]$ = minimum edit operations to transform prefix $A[0 \dots i-1]$ into $B[0 \dots j-1]$.
- **Recurrence:**
  $$dp[i][j] = \begin{cases}
  dp[i-1][j-1] & \text{if } A[i-1] = B[j-1] \\
  1 + \min \begin{cases} dp[i-1][j] & (\text{Delete}) \\ dp[i][j-1] & (\text{Insert}) \\ dp[i-1][j-1] & (\text{Substitute}) \end{cases} & \text{otherwise}
  \end{cases}$$
- **Base Cases:** $dp[i][0] = i$, $dp[0][j] = j$.
- **Complexity:** Time $O(M \cdot N)$, Space $O(M \cdot N)$.

---

## 2. Damerau-Levenshtein Distance

- **Retail Application:** Accurately catches human adjacent finger slips (e.g. `"iphoen"` $\rightarrow$ `"iphone"`, `"samusng"` $\rightarrow$ `"samsung"`).
- **Recurrence:** Incorporates adjacent transposition:
  $$dp[i][j] = \min \begin{cases}
  dp[i-1][j] + 1 \\
  dp[i][j-1] + 1 \\
  dp[i-1][j-1] + \text{cost} \\
  dp[i-2][j-2] + 1 & \text{if } i > 1, j > 1 \text{ and } A[i-1] = B[j-2] \land A[i-2] = B[j-1]
  \end{cases}$$

---

## 3. Weighted Edit Distance

- **Retail Application:** Keyboard proximity typing error weighting.
- **Formulation:** Instead of uniform cost 1, uses non-uniform costs: $W_{\text{ins}} = 1.0, W_{\text{del}} = 1.2, W_{\text{rep}} = 1.5, W_{\text{trans}} = 0.8$.
- Pairs of keys adjacent on standard QWERTY keyboards (e.g., 'o' and 'p') are assigned a 40% discount on substitution penalty.

---

## 4. Needleman-Wunsch Global Sequence Alignment

- **Retail Application:** Aligning product SKU attribute strings and technical specifications end-to-end.
- **Scoring:** Match $+2$, Mismatch $-1$, Gap $-2$.
- **Recurrence:**
  $$dp[i][j] = \max \begin{cases}
  dp[i-1][j-1] + \text{score}(A[i-1], B[j-1]) \\
  dp[i-1][j] + \text{Gap} \\
  dp[i][j-1] + \text{Gap}
  \end{cases}$$
- Full traceback reconstructs optimal globally aligned strings.

---

## 5. Smith-Waterman Local Sequence Alignment

- **Retail Application:** Identifying plagiarized or shared complaint text between customer reviews.
- **Key Difference:** Values floored at $0$ to prevent negative score accumulation:
  $$dp[i][j] = \max \begin{cases}
  0 \\
  dp[i-1][j-1] + \text{score}(A[i-1], B[j-1]) \\
  dp[i-1][j] + \text{Gap} \\
  dp[i][j-1] + \text{Gap}
  \end{cases}$$
- Traceback starts from the absolute maximum cell in the entire matrix and ends as soon as a $0$ cell is reached.

---

## 6. Matrix Chain Multiplication (Academic DP Demo)

- **Retail Application:** Optimizing order of multi-matrix transformations for batch analytical revenue forecasting.
- **State:** $dp[i][j]$ = minimum scalar multiplications to compute $M_i \dots M_j$.
- **Recurrence:**
  $$dp[i][j] = \min_{i \le k < j} \left( dp[i][k] + dp[k+1][j] + p_{i-1} p_k p_j \right)$$
- **Complexity:** Time $O(N^3)$, Space $O(N^2)$.

---

## 7. Optimal Binary Search Tree (Optimal BST)

- **Retail Application:** Organizing high-frequency catalog search keywords to minimize average lookups.
- **Recurrence:**
  $$e[i][j] = \min_{i \le r \le j} \left( e[i][r-1] + e[r+1][j] + w(i, j) \right) \quad \text{where } w(i, j) = \sum_{k=i}^j \text{freq}[k]$$
- **Complexity:** Time $O(N^3)$, Space $O(N^2)$.

---

## 8. Bitmask DP: Product Bundle Optimization Under Budget

- **Retail Application:** Generating the highest-rated product bundles under a spending ceiling (e.g. ₹50,000 budget).
- **State:** Bitmask of $N$ items ($0 \le \text{mask} < 2^N$). The $k$-th bit is $1$ if item $k$ is selected.
- **Transition:** Let $\text{lsb} = \text{mask} \ \& \ (-\text{mask})$ and $\text{prev} = \text{mask} \oplus \text{lsb}$:
  $$\text{cost}[\text{mask}] = \text{cost}[\text{prev}] + \text{price}[\text{bit}]$$
  $$\text{utility}[\text{mask}] = \text{utility}[\text{prev}] + \text{rating}[\text{bit}] + \text{synergy}(\text{mask})$$
- Evaluates valid bundles satisfying $\text{cost}[\text{mask}] \le \text{Budget}$ to maximize utility.

---

## 9. Traveling Salesperson Problem (TSP Held-Karp)

- **Retail Application:** Exact optimal delivery courier route connecting retail stores and urban depots.
- **State:** $dp[\text{mask}][u]$ = minimum distance visiting subset $\text{mask}$ ending at node $u$.
- **Transition:**
  $$dp[\text{mask} \mid (1 \ll v)][v] = \min_{u \in \text{mask}} \left( dp[\text{mask}][u] + \text{dist}[u][v] \right)$$
- **Complexity:** Time $O(2^N \cdot N^2)$, Space $O(2^N \cdot N)$ ($N \le 18$).

---

## 10. Hamiltonian Path Feasibility

- **Retail Application:** Feasibility of Automated Guided Vehicle (AGV) traversing every warehouse aisle exactly once.
- **State:** $dp[\text{mask}][u] \in \{\text{true}, \text{false}\}$.
- **Transition:** $dp[\text{mask} \mid (1 \ll v)][v] = \text{true}$ if $\exists u : dp[\text{mask}][u] \land \text{adj}[u][v]$.

---

## 11. Exact Set Cover via Bitmask DP

- **Retail Application:** Finding the minimal number of marketing campaigns required to cover $100\%$ of customer segments.
- **State:** $dp[\text{mask}]$ = minimum campaign count to cover segment mask.
- **Transition:** $dp[\text{mask} \mid C_i] = \min(dp[\text{mask} \mid C_i], dp[\text{mask}] + 1)$.
- **Complexity:** $O(M \cdot 2^U)$.

---

## 12. Tree Dynamic Programming (Maximum Weight Independent Set)

- **Retail Application:** Distributing department bonuses across store hierarchy trees without activating adjacent supervisor-subordinate pairs.
- **State:**
  - $dp[u][0]$: Maximum bonus in subtree $u$ if role $u$ is **excluded**.
  - $dp[u][1]$: Maximum bonus in subtree $u$ if role $u$ is **included**.
- **Transitions:**
  $$dp[u][0] = \sum_{v \in \text{children}(u)} \max(dp[v][0], dp[v][1])$$
  $$dp[u][1] = \text{weight}[u] + \sum_{v \in \text{children}(u)} dp[v][0]$$
- **Complexity:** $O(V)$ in a single post-order DFS traversal.

---

## 13. Tree Rerooting Dynamic Programming

- **Retail Application:** Finding the central supply chain hub in a tree network that minimizes total distance to all other stores.
- **Pass 1 (Bottom-Up DFS):** Root at node $0$. Compute subtree sizes $sz[u]$ and internal distance sum $inDist[u]$.
- **Pass 2 (Top-Down DFS):** When shifting root from parent $u$ to child $v$:
  $$\text{totalDist}[v] = \text{totalDist}[u] - sz[v] + (V - sz[v])$$
- **Complexity:** $O(V)$ total time.

---

## 14. SOS DP (Sum Over Subsets)

- **Retail Application:** Fast customer sub-profile aggregation across feature masks (e.g. VIP, App User, Prime Member).
- **Problem:** For every mask $M$, compute $F[M] = \sum_{\text{sub} \subseteq M} A[\text{sub}]$.
- **SOS DP In-Place Optimization:**
  For each bit dimension $i \in [0 \dots N-1]$:
  $$\forall \text{mask with bit } i \text{ set}: \quad dp[\text{mask}] += dp[\text{mask} \oplus (1 \ll i)]$$
- **Complexity:** Reduces time from naive submask iteration $O(3^N)$ to $O(N \cdot 2^N)$.
