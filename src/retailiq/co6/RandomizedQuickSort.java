package retailiq.co6;

/**
 * CO6: Randomized QuickSort for ranking sales/demand.
 */
public class RandomizedQuickSort {

    public static void sort(double[] arr) {
        if (arr == null || arr.length <= 1) return;
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(double[] arr, int low, int high) {
        if (low < high) {
            int pi = randomizedPartition(arr, low, high);
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }

    private static int randomizedPartition(double[] arr, int low, int high) {
        int randomIdx = low + (int) (Math.random() * (high - low + 1));
        swap(arr, randomIdx, high);
        return partition(arr, low, high);
    }

    private static int partition(double[] arr, int low, int high) {
        double pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
