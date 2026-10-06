package retailiq.co6;

/**
 * CO6: Blelloch Work-Efficient Parallel Prefix Scan.
 * Up-sweep (reduce) and down-sweep tree phases.
 * Work: O(N), Span: O(log N).
 */
public class BlellochScan {

    public static double[] exclusiveScan(double[] input) {
        if (input == null || input.length == 0) return new double[0];

        int n = input.length;
        // Pad to power of 2
        int m = 1;
        while (m < n) m *= 2;

        double[] tree = new double[2 * m];
        for (int i = 0; i < n; i++) tree[m + i] = input[i];

        // Up-sweep
        for (int d = m / 2; d >= 1; d /= 2) {
            for (int i = d; i < 2 * d; i++) {
                tree[i] = tree[2 * i] + tree[2 * i + 1];
            }
        }

        // Root to zero
        tree[1] = 0;

        // Down-sweep
        for (int d = 1; d < m; d *= 2) {
            for (int i = d; i < 2 * d; i++) {
                double t = tree[2 * i];
                tree[2 * i] = tree[i];
                tree[2 * i + 1] = tree[i] + t;
            }
        }

        double[] result = new double[n];
        for (int i = 0; i < n; i++) result[i] = tree[m + i];
        return result;
    }
}
