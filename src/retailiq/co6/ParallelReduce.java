package retailiq.co6;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * CO6: Parallel Reduce for revenue and sales aggregation.
 * Utilizes Java ForkJoin framework for parallel reduction tree execution.
 * Work: O(N), Span: O(log N), Ideal Parallelism: O(N / log N).
 */
public class ParallelReduce {

    private static final int SEQUENTIAL_THRESHOLD = 5000;
    private static final ForkJoinPool POOL = new ForkJoinPool();

    public static double reduceSum(double[] arr) {
        if (arr == null || arr.length == 0) return 0;
        return POOL.invoke(new ForkJoinReduceTask(arr, 0, arr.length - 1));
    }

    public static double sequentialReduceSum(double[] arr) {
        if (arr == null || arr.length == 0) return 0;
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }

    private static class ForkJoinReduceTask extends RecursiveTask<Double> {
        private final double[] arr;
        private final int left;
        private final int right;

        ForkJoinReduceTask(double[] arr, int left, int right) {
            this.arr = arr;
            this.left = left;
            this.right = right;
        }

        @Override
        protected Double compute() {
            if (right - left < SEQUENTIAL_THRESHOLD) {
                double sum = 0;
                for (int i = left; i <= right; i++) {
                    sum += arr[i];
                }
                return sum;
            }

            int mid = left + (right - left) / 2;
            ForkJoinReduceTask leftTask = new ForkJoinReduceTask(arr, left, mid);
            ForkJoinReduceTask rightTask = new ForkJoinReduceTask(arr, mid + 1, right);

            leftTask.fork();
            double rightResult = rightTask.compute();
            double leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }

    public static void runBenchmark(int size) {
        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            data[i] = Math.random() * 100.0;
        }

        long t0 = System.nanoTime();
        double seqSum = sequentialReduceSum(data);
        long tSeq = System.nanoTime() - t0;

        long t1 = System.nanoTime();
        double parSum = reduceSum(data);
        long tPar = System.nanoTime() - t1;

        double msSeq = tSeq / 1_000_000.0;
        double msPar = tPar / 1_000_000.0;
        double speedup = msPar > 0 ? (msSeq / msPar) : 1.0;

        System.out.println("=== Parallel vs Sequential Reduce Benchmark (N = " + size + ") ===");
        System.out.printf("Sequential Sum: %.2f in %.3f ms\n", seqSum, msSeq);
        System.out.printf("Parallel Sum  : %.2f in %.3f ms (Cores: %d)\n", parSum, msPar, Runtime.getRuntime().availableProcessors());
        System.out.printf("Observed Speedup: %.2fx\n", speedup);
    }
}
