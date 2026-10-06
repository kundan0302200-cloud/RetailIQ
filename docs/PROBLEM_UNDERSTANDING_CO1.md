# RetailIQ - Module 1: Problem Understanding & Requirement Mapping (CO1)

## 1. Executive Summary & Problem Statement

Modern e-commerce and retail management systems encounter extreme computational bottlenecks across large-scale product catalogs, real-time customer search queries, unstructured review streams, and supply chain logistics. Standard database lookups and naive algorithmic approaches fail to scale when processing:
1. High-frequency typographical search queries across millions of SKU attributes.
2. Unstructured natural language customer feedback containing sentiment keywords, product flaws, and duplicate feedback.
3. Multi-item product bundle recommendations under tight customer budgets and category compatibility constraints.
4. Optimal logistics routing and supply chain distribution across fulfillment hubs.

**RetailIQ** is an Intelligent Retail Analytics Platform engineered specifically to address these core retail computational challenges through rigorous, custom-built Data Structures and Advanced Algorithms (DSA) without relying on high-level standard library shortcuts (`java.util.*` collections).

---

## 2. Project Objectives

- **O1: Scalable Text Search & Catalog Querying (CO2):** Provide real-time, linear-time exact and multi-keyword pattern searching across product names, descriptions, and catalog attributes.
- **O2: Intelligent Typo Correction & Text Normalization (CO3):** Accurately correct human keyboard errors, adjacent character transpositions, and phonetic slips in customer queries to prevent lost sales.
- **O3: Automated Feedback & Review Analysis (CO2 & CO3):** Detect sentiment tags, identify repeated complaint phrases, and detect plagiarized or duplicate reviews using sequence alignment and suffix indexing.
- **O4: Combinatorial Bundle Optimization (CO3):** Compute optimal product combinations that maximize overall bundle synergy and customer ratings while strictly adhering to budget limits.
- **O5: Supply Chain Network & Hierarchy Analysis (CO3 & CO4):** Determine the most cost-effective central logistics distribution hubs and optimize incentive allocation throughout corporate retail hierarchies.
- **O6: Architectural Purity & Academic Rigor:** Implement all data structures (linked lists, stacks, queues, hash tables, graphs) and algorithmic engines using pure primitive arrays and custom classes, adhering to course restrictions.

---

## 3. Retail Use Cases, Inputs, and Outputs

### Use Case 1: Live Catalog Text Search
- **Business Need:** A shopper types `"wireless headphones"` into the catalog search bar. The system must immediately scan 10,000+ SKU descriptions without lag.
- **Input:** 
  - `Text`: Complete concatenated catalog descriptions ($N$ characters).
  - `Pattern`: User query ($M$ characters).
- **Output:** 
  - Array of exact match indices, total occurrence count, execution time in nanoseconds, and comparison operations.
- **DSA Concept:** Knuth-Morris-Pratt (KMP) linear pattern matching ($O(N+M)$) and Suffix Array indexing.

### Use Case 2: Customer Query Typo Auto-Correction ("Did You Mean?")
- **Business Need:** A user types `"iphno 15"` or `"samusng"`. The engine must identify the closest intended catalog item.
- **Input:** Misspelled query string ($M$ characters), catalog vocabulary ($V$ words).
- **Output:** Ranked list of valid product names sorted by minimal edit cost with detailed operation distance.
- **DSA Concept:** Levenshtein and Damerau-Levenshtein Dynamic Programming ($O(M \cdot N)$) supporting adjacent character transpositions.

### Use Case 3: Customer Feedback Keyword & Anomaly Tagger
- **Business Need:** Continuous streams of customer reviews must be scanned simultaneously for critical flags: `"delivery"`, `"quality"`, `"refund"`, `"battery"`, `"broken"`, `"discount"`.
- **Input:** Unstructured review text ($N$ characters), dictionary of $K$ retail keywords ($L$ total characters).
- **Output:** Exact match coordinates for all dictionary keywords found in the review text in a single traversal.
- **DSA Concept:** Aho-Corasick Automaton ($O(N + L + Z)$).

### Use Case 4: Smart Product Bundle Optimization
- **Business Need:** A customer with a budget of ₹50,000 wants the best combination of gadgets (e.g. phone + earbuds + charger + protective case) that yields the highest total customer satisfaction and cross-selling synergy.
- **Input:** Array of candidate products (price, rating, category) and a maximum budget constraint $B$.
- **Output:** Optimal product subset, total expenditure, remaining budget balance, and achieved bundle utility score.
- **DSA Concept:** Bitmask Dynamic Programming ($O(2^N \cdot N)$).

### Use Case 5: Optimal Central Logistics Hub Locator
- **Business Need:** In an expanding regional store tree network, retail planners need to locate the single best warehouse or store that minimizes the total aggregate travel distance to all other stores.
- **Input:** Tree graph $T = (V, E)$ representing connected stores and regional warehouses.
- **Output:** The vertex index and name of the optimal central hub, along with total network delivery cost.
- **DSA Concept:** Tree Rerooting Dynamic Programming ($O(V)$ linear 2-pass DFS).

---

## 4. DSA Concept Requirement Mapping Matrix

| Retail Requirement | Business Domain | Input / Constraints | Core DSA Concept | Module |
| :--- | :--- | :--- | :--- | :--- |
| **Catalog Exact Search** | Product Discovery | Catalog text $N \le 100k$, Pattern $M \le 100$ | Knuth-Morris-Pratt (KMP) | CO2 |
| **Multi-Keyword Tagging** | Review Mining | Reviews $N$ chars, $K$ keywords | Aho-Corasick Automaton | CO2 |
| **Repeated Phrase Detection** | Review Mining | Review text, finding repeated complaints | Kasai LCP & Suffix Array | CO2 |
| **Catalog Substring Index** | Full-Text Search | Large static catalog text | Suffix Array (SA-IS / Manber-Myers) | CO2 |
| **Typo Correction** | Search UX | Misspelled query $M \le 30$ | Damerau-Levenshtein DP | CO3 |
| **Layout Proximity Typing** | Search UX | Keyboard typo slip distances | Weighted Edit Distance DP | CO3 |
| **Catalog Attribute Alignment** | Master Data Mgmt | SKU specification strings | Needleman-Wunsch DP | CO3 |
| **Review Plagiarism Check** | Content Moderation | Pairwise customer reviews | Smith-Waterman Local Alignment | CO3 |
| **Smart Bundle Selection** | Sales & Promotion | $N \le 20$ items, Budget $B$ | Bitmask DP | CO3 |
| **Courier Delivery Route** | Last-Mile Logistics | $N \le 18$ retail outlets, distance matrix | Held-Karp TSP Bitmask DP | CO3 |
| **Marketing Campaign Cover** | Customer Acquisition | $U$ customer segments, $M$ campaigns | Exact Set Cover Bitmask DP | CO3 |
| **Store Hierarchy Bonus** | Organizational Mgmt | Corporate hierarchy tree, non-adjacent | Tree DP (Max Weight Indep. Set) | CO3 |
| **Central Hub Placement** | Logistics Planning | Tree supply network of $V$ nodes | Rerooting DP (2-pass DFS) | CO3 |
| **Customer Segment Analytics**| Customer Intelligence| $N$ customer feature attributes | SOS DP (Sum Over Subsets) | CO3 |
