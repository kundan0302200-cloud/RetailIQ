# RetailIQ - String Algorithms & Text Indexing (CO2)

This document provides mathematical formulations, algorithmic logic, state transitions, and implementation proofs for all CO2 string matching and indexing engines implemented in RetailIQ.

---

## 1. Knuth-Morris-Pratt (KMP) Algorithm

### Purpose in RetailIQ
Exact search for user queries (e.g. `"wireless headphones"`, `"A16 bionic"`) inside product descriptions without text backtracking.

### Mathematical Invariant & LPS Array
The Longest Proper Prefix which is also a Suffix (LPS) array is defined such that:
$$\text{LPS}[i] = \max \{ k : 0 \le k < i \land P[0 \dots k-1] = P[i-k+1 \dots i] \}$$

### Preprocessing Logic ($O(M)$):
```text
len = 0, i = 1
while i < M:
    if P[i] == P[len]:
        len++
        LPS[i] = len
        i++
    else:
        if len != 0:
            len = LPS[len - 1]
        else:
            LPS[i] = 0
            i++
```

### Search Phase ($O(N)$):
The text pointer $i$ strictly increases from $0$ to $N-1$. When a mismatch occurs between $P[j]$ and $T[i]$:
- If $j > 0$, shift pattern without rolling back $i$: $j = \text{LPS}[j - 1]$.
- If $j == 0$, increment $i++$.
- **Time Complexity:** $O(N + M)$ worst case.
- **Space Complexity:** $O(M)$ auxiliary memory for the LPS table.

---

## 2. Z-Algorithm

### Purpose in RetailIQ
Prefix matching, product code search, and detecting repeating phrases across customer feedback.

### Definition of Z-Array
For a string $S$ of length $K$, $Z[i]$ represents the length of the longest substring starting at $S[i]$ that is identical to a prefix of $S$:
$$Z[i] = \max \{ k : S[i \dots i+k-1] = S[0 \dots k-1] \}$$

### Interval Maintenance $[L, R]$ ($O(K)$):
Maintains an active window $[L, R]$ where $S[L \dots R] = S[0 \dots R-L]$.
- If $i > R$: Compute by naive character comparison; update $[L, R]$.
- If $i \le R$: Let $k = i - L$.
  - If $Z[k] < R - i + 1$: $Z[i] = Z[k]$ (contained within the current Z-box).
  - If $Z[k] \ge R - i + 1$: Extend matching beyond $R$ by explicit character comparisons starting from $R+1$.
- **Pattern Search:** Form string $S = P + \text{"\$"} + T$. Any index $i > |P|$ where $Z[i] == |P|$ represents a full match starting at index $i - |P| - 1$ in $T$.

---

## 3. Rabin-Karp with Double Rolling Hash

### Purpose in RetailIQ
Rapid multi-term matching and substring verification using polynomial rolling hash functions.

### Mathematical Formulation
To eliminate spurious hash collisions to near zero, RetailIQ employs **Double Hashing** across two distinct large prime moduli:
$$\text{Hash}_1(S[0 \dots m-1]) = \left( \sum_{i=0}^{m-1} S[i] \cdot B_1^{m-1-i} \right) \pmod{M_1}$$
$$\text{Hash}_2(S[0 \dots m-1]) = \left( \sum_{i=0}^{m-1} S[i] \cdot B_2^{m-1-i} \right) \pmod{M_2}$$

Where:
- $B_1 = 256, M_1 = 1{,}000{,}000{,}007$
- $B_2 = 313, M_2 = 1{,}000{,}000{,}009$

### $O(1)$ Rolling Hash Transition
When shifting the window from $[i \dots i+m-1]$ to $[i+1 \dots i+m]$:
$$\text{Hash}_{\text{new}} = \left( B \cdot (\text{Hash}_{\text{old}} - T[i] \cdot B^{m-1}) + T[i+m] \right) \pmod{M}$$
If both $\text{Hash}_1$ and $\text{Hash}_2$ match the pattern hash, candidate character-by-character verification is executed.

---

## 4. Aho-Corasick Multi-Pattern Search Automaton

### Purpose in RetailIQ
Scanning unstructured customer reviews in a single streaming pass to identify sentiment keywords:
`["delivery", "quality", "refund", "battery", "discount", "broken"]`

### Algorithmic Architecture
1. **Trie Construction:** Build a trie containing all $K$ keywords.
2. **Failure Links (BFS via `MyQueue`):**
   - For root's immediate children, $\text{fail}(v) = \text{root}$.
   - For node $u$ with parent $p$ and edge character $c$:
     Traverse $f = \text{fail}(p)$ until $f.\text{children}[c] \neq \text{null}$, then $\text{fail}(u) = f.\text{children}[c]$.
3. **Dictionary Output Suffix Links:**
   - When $\text{fail}(u)$ represents a keyword match, its match index is propagated to node $u$'s output list.
4. **Text Streaming:**
   - Each incoming character transitions the automaton state.
   - Any state with non-empty output list emits matched keywords immediately.
- **Time Complexity:** $O(N + \sum M_k + Z)$ where $Z$ is the count of matched keywords.

---

## 5. Suffix Array & SA-IS (Linear-Time Suffix Sorting)

### Purpose in RetailIQ
Full-text indexing of the entire product catalog, enabling $O(M \log N)$ binary search for arbitrary substrings.

### Implementations in RetailIQ:
1. **Prefix-Doubling / Manber-Myers (`SuffixArray.java`):**
   - Ranks suffixes by sorting pairs of ranks $(R_k(i), R_k(i + 2^{k-1}))$.
   - Implemented using custom merge sort without `java.util.*`.
   - Complexity: $O(N \log^2 N)$.
2. **SA-IS Suffix Array by Induced Sorting (`SAIS.java`):**
   - **True linear-time $O(N)$ construction.**
   - Suffix classification into $S$-type ($S[i] < S[i+1]$) and $L$-type ($S[i] > S[i+1]$).
   - Identification of LMS (Leftmost $S$-type) characters.
   - Induced sorting of LMS substrings and recursive reduction.
   - Final two-pass induced sorting for all $L$-type and $S$-type suffixes.

---

## 6. Kasai's Longest Common Prefix (LCP) Algorithm

### Purpose in RetailIQ
Identifying duplicate customer reviews, common phrases in complaints, and computing the Longest Repeated Substring (LRS).

### Key Invariant
If $\text{rank}[i]$ is the position of suffix $i$ in the suffix array, and $j = \text{SA}[\text{rank}[i] - 1]$, then:
$$\text{LCP}[\text{rank}[i] - 1] \ge \text{LCP}[\text{rank}[i-1] - 1] - 1$$
This guarantee allows $k$ (the matched length) to decrement by at most $1$ per step, proving linear $O(N)$ runtime.
