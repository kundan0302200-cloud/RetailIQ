package retailiq.co2;

/**
 * CO2: SA-IS (Suffix Array by Induced Sorting) Algorithm.
 * Linear-time Suffix Array construction developed by Ge Nong, Sen Zhang, and Wai Hong Chan (2009).
 *
 * Theoretical & Practical Time Complexity: O(n) worst-case linear time.
 * Space Complexity: O(n)
 *
 * Core Concept:
 *   1. Classify characters/suffixes as S-type (smaller than next) or L-type (larger than next).
 *   2. Identify LMS (Leftmost S-type) characters.
 *   3. Sort LMS substrings using Induced Sorting.
 *   4. Name LMS substrings and construct reduced problem; if names not unique, recurse.
 *   5. Induce the final suffix array from sorted LMS suffixes.
 *
 * Course restriction: Implemented without java.util.*
 */
public class SAIS {

    private static final boolean S_TYPE = true;
    private static final boolean L_TYPE = false;

    /**
     * Public entry point for string input.
     * Appends a sentinel character 0 to ensure uniqueness of LMS suffixes.
     */
    public static int[] buildSuffixArray(String text) {
        if (text == null || text.length() == 0) {
            return new int[0];
        }

        int n = text.length();
        int[] s = new int[n + 1];
        int maxChar = 0;

        for (int i = 0; i < n; i++) {
            s[i] = (int) text.charAt(i) + 1; // shift by 1 to keep 0 as unique sentinel
            if (s[i] > maxChar) {
                maxChar = s[i];
            }
        }
        s[n] = 0; // Sentinel $
        int K = maxChar + 1;

        int[] saWithSentinel = sais(s, K);

        // The first element is the sentinel at position n; strip it for text's SA
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = saWithSentinel[i + 1];
        }
        return sa;
    }

    private static int[] sais(int[] s, int K) {
        int n = s.length;
        if (n == 1) {
            return new int[]{0};
        }

        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = -1;

        // Step 1: Type array (true for S-type, false for L-type)
        boolean[] t = new boolean[n];
        t[n - 1] = S_TYPE; // Sentinel is S-type

        for (int i = n - 2; i >= 0; i--) {
            if (s[i] < s[i + 1]) {
                t[i] = S_TYPE;
            } else if (s[i] > s[i + 1]) {
                t[i] = L_TYPE;
            } else {
                t[i] = t[i + 1];
            }
        }

        // Step 2: Buckets
        int[] count = new int[K];
        for (int i = 0; i < n; i++) {
            count[s[i]]++;
        }

        int[] bucketHead = new int[K];
        int[] bucketTail = new int[K];
        getBuckets(count, bucketHead, bucketTail);

        // Find LMS characters
        int[] lms = new int[n];
        int lmsCount = 0;
        for (int i = 1; i < n; i++) {
            if (t[i] == S_TYPE && t[i - 1] == L_TYPE) {
                lms[lmsCount++] = i;
            }
        }

        // Initial placement of LMS suffixes at ends of buckets
        int[] bTail = new int[K];
        for (int i = 0; i < K; i++) bTail[i] = bucketTail[i];
        for (int i = 0; i < lmsCount; i++) {
            int p = lms[i];
            sa[bTail[s[p]]--] = p;
        }

        // Induced sorting
        induceSort(s, sa, t, count, bucketHead, bucketTail, K);

        // Name LMS substrings
        int[] lmsNames = new int[n];
        for (int i = 0; i < n; i++) lmsNames[i] = -1;

        int curName = 0;
        int prevLms = -1;
        for (int i = 0; i < n; i++) {
            int p = sa[i];
            if (p > 0 && t[p] == S_TYPE && t[p - 1] == L_TYPE) {
                if (prevLms != -1 && !isLmsSubstringEqual(s, t, prevLms, p)) {
                    curName++;
                }
                prevLms = p;
                lmsNames[p] = curName;
            }
        }

        // Reduced string s1
        int[] s1 = new int[lmsCount];
        int s1Idx = 0;
        for (int i = 0; i < n; i++) {
            if (lmsNames[i] != -1) {
                s1[s1Idx++] = lmsNames[i];
            }
        }

        int[] sa1;
        if (curName + 1 < lmsCount) {
            sa1 = sais(s1, curName + 1);
        } else {
            sa1 = new int[lmsCount];
            for (int i = 0; i < lmsCount; i++) {
                sa1[s1[i]] = i;
            }
        }

        // Place ordered LMS suffixes
        for (int i = 0; i < n; i++) sa[i] = -1;
        for (int i = 0; i < K; i++) bTail[i] = bucketTail[i];

        for (int i = lmsCount - 1; i >= 0; i--) {
            int p = lms[sa1[i]];
            sa[bTail[s[p]]--] = p;
        }

        // Final Induced Sort
        induceSort(s, sa, t, count, bucketHead, bucketTail, K);

        return sa;
    }

    private static void getBuckets(int[] count, int[] head, int[] tail) {
        int sum = 0;
        for (int i = 0; i < count.length; i++) {
            head[i] = sum;
            sum += count[i];
            tail[i] = sum - 1;
        }
    }

    private static void induceSort(int[] s, int[] sa, boolean[] t, int[] count, int[] head, int[] tail, int K) {
        int n = s.length;
        int[] bHead = new int[K];
        for (int i = 0; i < K; i++) bHead[i] = head[i];

        // Left-to-right pass for L-type
        for (int i = 0; i < n; i++) {
            int j = sa[i] - 1;
            if (j >= 0 && t[j] == L_TYPE) {
                sa[bHead[s[j]]++] = j;
            }
        }

        int[] bTail = new int[K];
        for (int i = 0; i < K; i++) bTail[i] = tail[i];

        // Right-to-left pass for S-type
        for (int i = n - 1; i >= 0; i--) {
            int j = sa[i] - 1;
            if (j >= 0 && t[j] == S_TYPE) {
                sa[bTail[s[j]]--] = j;
            }
        }
    }

    private static boolean isLmsSubstringEqual(int[] s, boolean[] t, int i, int j) {
        int n = s.length;
        while (true) {
            if (s[i] != s[j] || t[i] != t[j]) return false;
            boolean iIsLms = (i > 0 && t[i] == S_TYPE && t[i - 1] == L_TYPE);
            boolean jIsLms = (j > 0 && t[j] == S_TYPE && t[j - 1] == L_TYPE);

            if (iIsLms && jIsLms && i != j) {
                return true;
            }
            if (iIsLms != jIsLms) {
                return false;
            }
            i++;
            j++;
            if (i >= n || j >= n) {
                return i == j;
            }
        }
    }
}
