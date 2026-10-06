package retailiq.co6;

/**
 * CO6: Reservoir Sampling for streaming customer transactions.
 * Selects k items with equal probability 1/N from an unbounded stream.
 */
public class ReservoirSampling {

    public static int[] sample(int[] stream, int k) {
        if (stream == null || k <= 0) return new int[0];
        int sampleSize = Math.min(k, stream.length);
        int[] reservoir = new int[sampleSize];

        for (int i = 0; i < sampleSize; i++) {
            reservoir[i] = stream[i];
        }

        for (int i = sampleSize; i < stream.length; i++) {
            int j = (int) (Math.random() * (i + 1));
            if (j < sampleSize) {
                reservoir[j] = stream[i];
            }
        }

        return reservoir;
    }
}
