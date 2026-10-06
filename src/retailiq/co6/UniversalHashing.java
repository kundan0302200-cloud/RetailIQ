package retailiq.co6;

/**
 * CO6: Universal Hashing family guarantee: Pr[h(k1) == h(k2)] <= 1/m.
 */
public class UniversalHashing {
    private RandomizedHashing hashFunc;

    public UniversalHashing(int tableSize) {
        this.hashFunc = new RandomizedHashing(tableSize);
    }

    public int getHash(long sku) {
        return hashFunc.hash(sku);
    }
}
