package retailiq.co6;

/**
 * CO6: Parallel Merge Sort demonstration.
 * Work: O(N log N), Span: O(log^3 N).
 */
public class ParallelSort {

    public static void sort(double[] arr) {
        if (arr == null || arr.length <= 1) return;
        parallelMergeSort(arr, 0, arr.length - 1);
    }

    private static void parallelMergeSort(double[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            parallelMergeSort(arr, left, mid);
            parallelMergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(double[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        double[] L = new double[n1];
        double[] R = new double[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
}
