package retailiq.co2;

import retailiq.structures.MyQueue;

/**
 * CO2: Aho-Corasick Multi-Pattern String Search Automaton.
 * Constructs a finite state automaton (Trie + BFS Failure Links + Output Links)
 * to locate all occurrences of a dictionary of keywords in a text in a single pass.
 *
 * Time Complexity:
 *   - Trie & Automaton Construction: O(Sum of keyword lengths * Alphabet_Size)
 *   - Search: O(Text_Length + Match_Count)
 * Space Complexity: O(Sum of keyword lengths * Alphabet_Size)
 *
 * Course restriction: Implemented without java.util.*
 */
public class AhoCorasick {

    public static class Match {
        public String keyword;
        public int keywordIndex;
        public int startPos;
        public int endPos;

        public Match(String keyword, int keywordIndex, int startPos, int endPos) {
            this.keyword = keyword;
            this.keywordIndex = keywordIndex;
            this.startPos = startPos;
            this.endPos = endPos;
        }

        @Override
        public String toString() {
            return String.format("Found '%s' at position [%d..%d]", keyword, startPos, endPos);
        }
    }

    private static class TrieNode {
        // Support extended ASCII 128 characters
        TrieNode[] children = new TrieNode[128];
        TrieNode fail = null;
        int[] outputKeywords = new int[4];
        int outputCount = 0;

        void addOutput(int keywordIdx) {
            if (outputCount == outputKeywords.length) {
                int[] newArr = new int[outputKeywords.length * 2];
                for (int i = 0; i < outputCount; i++) {
                    newArr[i] = outputKeywords[i];
                }
                outputKeywords = newArr;
            }
            outputKeywords[outputCount++] = keywordIdx;
        }
    }

    private TrieNode root;
    private String[] keywords;

    public AhoCorasick(String[] keywords) {
        this.keywords = keywords;
        buildAutomaton();
    }

    private void buildAutomaton() {
        root = new TrieNode();

        // 1. Build standard Trie from dictionary
        for (int i = 0; i < keywords.length; i++) {
            String kw = keywords[i].toLowerCase();
            TrieNode curr = root;
            for (int j = 0; j < kw.length(); j++) {
                char c = kw.charAt(j);
                int idx = c < 128 ? c : 0;
                if (curr.children[idx] == null) {
                    curr.children[idx] = new TrieNode();
                }
                curr = curr.children[idx];
            }
            curr.addOutput(i);
        }

        // 2. BFS to build Failure and Output links
        MyQueue<TrieNode> queue = new MyQueue<>();
        for (int c = 0; c < 128; c++) {
            if (root.children[c] != null) {
                root.children[c].fail = root;
                queue.enqueue(root.children[c]);
            }
        }

        while (!queue.isEmpty()) {
            TrieNode u = queue.dequeue();

            for (int c = 0; c < 128; c++) {
                TrieNode v = u.children[c];
                if (v != null) {
                    // Find failure transition for child v
                    TrieNode f = u.fail;
                    while (f != null && f.children[c] == null) {
                        f = f.fail;
                    }
                    v.fail = (f != null) ? f.children[c] : root;

                    // Union output keywords from failure link
                    if (v.fail != null) {
                        for (int k = 0; k < v.fail.outputCount; k++) {
                            v.addOutput(v.fail.outputKeywords[k]);
                        }
                    }

                    queue.enqueue(v);
                }
            }
        }
    }

    public Match[] search(String text) {
        if (text == null || text.length() == 0 || keywords == null || keywords.length == 0) {
            return new Match[0];
        }

        Match[] tempMatches = new Match[256];
        int matchCount = 0;
        String lowerText = text.toLowerCase();
        TrieNode curr = root;

        for (int i = 0; i < lowerText.length(); i++) {
            char c = lowerText.charAt(i);
            int idx = c < 128 ? c : 0;

            // Follow failure links if next transition does not exist
            while (curr != root && curr.children[idx] == null) {
                curr = curr.fail;
            }

            if (curr.children[idx] != null) {
                curr = curr.children[idx];
            } else {
                curr = root;
            }

            // Check if current state emits any keyword matches
            if (curr.outputCount > 0) {
                for (int k = 0; k < curr.outputCount; k++) {
                    int kwIdx = curr.outputKeywords[k];
                    String kw = keywords[kwIdx];
                    int startPos = i - kw.length() + 1;
                    int endPos = i;

                    if (matchCount == tempMatches.length) {
                        Match[] newArr = new Match[tempMatches.length * 2];
                        for (int m = 0; m < matchCount; m++) {
                            newArr[m] = tempMatches[m];
                        }
                        tempMatches = newArr;
                    }
                    tempMatches[matchCount++] = new Match(kw, kwIdx, startPos, endPos);
                }
            }
        }

        Match[] result = new Match[matchCount];
        for (int i = 0; i < matchCount; i++) {
            result[i] = tempMatches[i];
        }
        return result;
    }
}
