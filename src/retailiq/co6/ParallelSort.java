package retailiq.co6;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * CO6: Multi-Threaded Parallel Merge Sort using Java ForkJoin Framework.
 * Computes parallel sorting over multi-core hardware.
 * Work: O(N log N), Span: O(log^3 N) / O(log^2 N) depending on parallel merge.
 * Speedup follows Brent's Scheduling Theorem.
 */
public class ParallelSort {

    private static final int SEQUENTIAL_THRESHOLD = 2048;
    private static final ForkJoinPool POOL = new ForkJoinPool();

    public static void sort(double[] arr) {
        if (arr == null || arr.length <= 1) return;
        POOL.invoke(new ForkJoinSortAction(arr, 0, arr.length - 1));
    }

    public static void sequentialSort(double[] arr) {
        if (arr == null || arr.length <= 1) return;
        sequentialMergeSort(arr, 0, arr.length - 1);
    }

    private static class ForkJoinSortAction extends RecursiveAction {
        private final double[] arr;
        private final int left;
        private final int right;

        ForkJoinSortAction(double[] arr, int left, int right) {
            this.arr = arr;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (right - left < SEQUENTIAL_THRESHOLD) {
                sequentialMergeSort(arr, left, right);
                return;
            }

            int mid = left + (right - left) / 2;
            ForkJoinSortAction leftTask = new ForkJoinSortAction(arr, left, mid);
            ForkJoinSortAction rightTask = new ForkJoinSortAction(arr, mid + 1, right);

            invokeAll(leftTask, rightTask);
            merge(arr, left, mid, right);
        }
    }

    private static void sequentialMergeSort(double[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            sequentialMergeSort(arr, left, mid);
            sequentialMergeSort(arr, mid + 1, right);
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

    public static void runBenchmark(int size) {
        double[] dataSeq = new double[size];
        double[] dataPar = new double[size];
        for (int i = 0; i < size; i++) {
            double v = Math.random() * 100000.0;
            dataSeq[i] = v;
            dataPar[i] = v;
        }

        long t0 = System.nanoTime();
        sequentialSort(dataSeq);
        long tSeq = System.nanoTime() - t0;

        long t1 = System.nanoTime();
        sort(dataPar);
        long tPar = System.nanoTime() - t1;

        double msSeq = tSeq / 1_000_000.0;
        double msPar = tPar / 1_000_000.0;
        double speedup = msPar > 0 ? (msSeq / msPar) : 1.0;

        System.out.println("=== Parallel vs Sequential Sort Benchmark (N = " + size + ") ===");
        System.out.printf("Sequential Sort Time : %.3f ms\n", msSeq);
        System.out.printf("Parallel Sort Time   : %.3f ms (Cores: %d)\n", msPar, Runtime.getRuntime().availableProcessors());
        System.out.printf("Observed Speedup     : %.2fx\n", speedup);
    }
}
