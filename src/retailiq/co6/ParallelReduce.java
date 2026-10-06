package retailiq.co6;

/**
 * CO6: Parallel Reduce for revenue and sales aggregation.
 * Work: O(N), Span: O(log N), Parallelism: O(N / log N).
 */
public class ParallelReduce {

    public static double reduceSum(double[] arr) {
        if (arr == null || arr.length == 0) return 0;
        return recursiveReduce(arr, 0, arr.length - 1);
    }

    private static double recursiveReduce(double[] arr, int left, int right) {
        if (left == right) return arr[left];
        int mid = left + (right - left) / 2;
        double leftSum = recursiveReduce(arr, left, mid);
        double rightSum = recursiveReduce(arr, mid + 1, right);
        return leftSum + rightSum;
    }
}
