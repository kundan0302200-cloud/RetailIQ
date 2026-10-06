package retailiq.co2;

/**
 * CO2: Suffix Array with Prefix-Doubling Construction and Binary Search Substring Lookup.
 * Allows rapid indexing of product catalogs, descriptions, and customer reviews.
 *
 * Construction Complexity: O(n log^2 n) via custom merge sort on rank tuples.
 * Pattern Search Complexity: O(m log n) via binary search.
 * Space Complexity: O(n) auxiliary space.
 *
 * Course restriction: Implemented without java.util.*
 */
public class SuffixArray {

    private String text;
    private int n;
    private int[] suffixArray;

    private static class SuffixRank {
        int index;
        int rank1;
        int rank2;

        SuffixRank(int index, int rank1, int rank2) {
            this.index = index;
            this.rank1 = rank1;
            this.rank2 = rank2;
        }
    }

    public SuffixArray(String text) {
        this.text = text != null ? text : "";
        this.n = this.text.length();
        buildSuffixArray();
    }

    private void buildSuffixArray() {
        if (n == 0) {
            suffixArray = new int[0];
            return;
        }

        SuffixRank[] suffixes = new SuffixRank[n];
        for (int i = 0; i < n; i++) {
            int nextRank = (i + 1 < n) ? (text.charAt(i + 1)) : -1;
            suffixes[i] = new SuffixRank(i, text.charAt(i), nextRank);
        }

        customMergeSort(suffixes, 0, n - 1);

        int[] ind = new int[n];
        for (int k = 4; k < 2 * n; k = k * 2) {
            int rank = 0;
            int prevRank = suffixes[0].rank1;
            suffixes[0].rank1 = rank;
            ind[suffixes[0].index] = 0;

            for (int i = 1; i < n; i++) {
                if (suffixes[i].rank1 == prevRank && suffixes[i].rank2 == suffixes[i - 1].rank2) {
                    prevRank = suffixes[i].rank1;
                    suffixes[i].rank1 = rank;
                } else {
                    prevRank = suffixes[i].rank1;
                    suffixes[i].rank1 = ++rank;
                }
                ind[suffixes[i].index] = i;
            }

            for (int i = 0; i < n; i++) {
                int nextIndex = suffixes[i].index + k / 2;
                suffixes[i].rank2 = (nextIndex < n) ? suffixes[ind[nextIndex]].rank1 : -1;
            }

            customMergeSort(suffixes, 0, n - 1);
        }

        suffixArray = new int[n];
        for (int i = 0; i < n; i++) {
            suffixArray[i] = suffixes[i].index;
        }
    }

    private static void customMergeSort(SuffixRank[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            customMergeSort(arr, left, mid);
            customMergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(SuffixRank[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        SuffixRank[] L = new SuffixRank[n1];
        SuffixRank[] R = new SuffixRank[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (compareSuffix(L[i], R[j]) <= 0) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    private static int compareSuffix(SuffixRank a, SuffixRank b) {
        if (a.rank1 != b.rank1) {
            return a.rank1 - b.rank1;
        }
        return a.rank2 - b.rank2;
    }

    public int[] getSuffixArray() {
        return suffixArray;
    }

    public String getText() {
        return text;
    }

    /**
     * Binary search to find all occurrences of pattern in O(m log n).
     */
    public int[] searchPattern(String pattern) {
        if (pattern == null || pattern.length() == 0 || n == 0) {
            return new int[0];
        }

        int m = pattern.length();
        int low = 0, high = n - 1;
        int firstOccur = -1;

        // Find lower bound
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = comparePrefix(pattern, suffixArray[mid]);
            if (cmp <= 0) {
                if (cmp == 0) firstOccur = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if (firstOccur == -1) {
            return new int[0];
        }

        // Find upper bound
        low = firstOccur;
        high = n - 1;
        int lastOccur = firstOccur;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = comparePrefix(pattern, suffixArray[mid]);
            if (cmp >= 0) {
                if (cmp == 0) lastOccur = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        int count = lastOccur - firstOccur + 1;
        int[] matches = new int[count];
        for (int i = 0; i < count; i++) {
            matches[i] = suffixArray[firstOccur + i];
        }
        return matches;
    }

    private int comparePrefix(String pattern, int suffixStart) {
        int m = pattern.length();
        for (int i = 0; i < m; i++) {
            if (suffixStart + i >= n) {
                return 1; // pattern is longer than remaining suffix
            }
            char pChar = pattern.charAt(i);
            char tChar = text.charAt(suffixStart + i);
            if (pChar != tChar) {
                return pChar - tChar;
            }
        }
        return 0; // matches prefix of suffix
    }
}
