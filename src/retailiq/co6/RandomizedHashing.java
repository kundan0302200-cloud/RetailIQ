package retailiq.co6;

/**
 * CO6: Randomized Hashing and Universal Hashing for Product SKUs.
 */
public class RandomizedHashing {
    private long a;
    private long b;
    private long p;
    private int m;

    public RandomizedHashing(int m) {
        this.m = m;
        this.p = 1000000007L;
        this.a = 1 + (long) (Math.random() * (p - 1));
        this.b = (long) (Math.random() * p);
    }

    public int hash(long key) {
        return (int) (((a * key + b) % p) % m);
    }
}
