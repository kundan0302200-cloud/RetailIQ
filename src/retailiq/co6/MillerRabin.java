package retailiq.co6;

/**
 * CO6: Miller-Rabin Probabilistic Primality Test for large SKU cryptography/hashing.
 */
public class MillerRabin {

    public static boolean isPrime(long n, int k) {
        if (n <= 1 || n == 4) return false;
        if (n <= 3) return true;

        long d = n - 1;
        while (d % 2 == 0) {
            d /= 2;
        }

        for (int i = 0; i < k; i++) {
            if (!millerTest(d, n)) {
                return false;
            }
        }
        return true;
    }

    private static boolean millerTest(long d, long n) {
        long a = 2 + (long) (Math.random() * (n - 4));
        long x = power(a, d, n);

        if (x == 1 || x == n - 1) return true;

        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;
            if (x == 1) return false;
            if (x == n - 1) return true;
        }
        return false;
    }

    private static long power(long x, long y, long p) {
        long res = 1;
        x = x % p;
        while (y > 0) {
            if ((y & 1) == 1) res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }
}
